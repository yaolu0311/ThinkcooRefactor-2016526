package com.thinkcoo.mobile.model.repository.impl;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.thinkcoo.mobile.model.cache.LoginUserCache;
import com.thinkcoo.mobile.model.entity.JobResultEmptyException;
import com.thinkcoo.mobile.model.entity.RequestParam.SearchJobParams;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.model.entity.serverresponse.FindJobListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.FindJobResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectJobListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectJobResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyShieldCompanyListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyShieldCompanyResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.QueryRequestJobListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.QueryRequestJobResponse;
import com.thinkcoo.mobile.model.exception.MyCollectJobEmptyException;
import com.thinkcoo.mobile.model.exception.RequestJobEmptyException;
import com.thinkcoo.mobile.model.exception.ServerResponseException;
import com.thinkcoo.mobile.model.repository.GetJobRepository;
import com.thinkcoo.mobile.model.rest.apis.GetJobApi;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class GetJobRepositoryImpl  implements GetJobRepository{

    @Inject
    GetJobApi mGetJobApi;
    @Inject
    LoginUserCache mLoginUserCache;

    @Inject
    public GetJobRepositoryImpl() {
    }

    @Override
    public Observable<List<FindJobResponse>> searchJob(final SearchJobParams searchJobParams) {
        return mGetJobApi.searchjob(searchJobParams.getJobFilter().getInputKey()
                ,searchJobParams.getJobFilter().getLocation().getCity()
                ,searchJobParams.getJobFilter().getType()
                ,searchJobParams.getJobFilter().getVocation()
                ,searchJobParams.getJobFilter().getReleaseTime()
                ,searchJobParams.getPageMachine().getPageIndex()
                ,searchJobParams.getPageMachine().getPageContentCount()
        ).flatMap(new Func1<BaseResponse<FindJobListResponse>, Observable<List<FindJobResponse>>>() {
            @Override
            public Observable<List<FindJobResponse>> call(BaseResponse<FindJobListResponse> listBaseResponse) {
                if (!listBaseResponse.isSuccess()){
                    return Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                }
                if (null == listBaseResponse.getData() || null == listBaseResponse.getData().getList()|| listBaseResponse.getData().getList().isEmpty()){
                    return Observable.error(new JobResultEmptyException());
                }
                searchJobParams.getPageMachine().setTotalPage(listBaseResponse.getPage().getPageSize());
                return Observable.just(listBaseResponse.getData().getList());
            }
        });
    }

    @Override
    public Observable<List<QueryRequestJobResponse>> loadRequestJobList(final PageMachine pageMachine) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<List<QueryRequestJobResponse>>>() {
            @Override
            public Observable<List<QueryRequestJobResponse>> call(User user) {
                return mGetJobApi.queryRequestJobListResponse(user.getUserId(),pageMachine.getPageIndex(),pageMachine.getPageContentCount()).flatMap(new Func1<BaseResponse<QueryRequestJobListResponse>, Observable<List<QueryRequestJobResponse>>>() {
                    @Override
                    public Observable<List<QueryRequestJobResponse>> call(BaseResponse<QueryRequestJobListResponse> listBaseResponse) {
                        if (!listBaseResponse.isSuccess()){
                            return Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                        }
                        if (null == listBaseResponse.getData() || null == listBaseResponse.getData().getList()|| listBaseResponse.getData().getList().isEmpty()){
                            return Observable.error(new RequestJobEmptyException());
                        }
                        pageMachine.setTotalPage(listBaseResponse.getPage().getPageSize());
                        return Observable.just(listBaseResponse.getData().getList());
                    }
                });
            }
        });
    }

    @Override
    public Observable<List<MyCollectJobResponse>> loadMyCollect(final PageMachine pageMachine) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<List<MyCollectJobResponse>>>() {
            @Override
            public Observable<List<MyCollectJobResponse>> call(User user) {
                return mGetJobApi.queryJobcollection(user.getUserId(),pageMachine.getPageIndex(),pageMachine.getPageContentCount()).flatMap(new Func1<BaseResponse<MyCollectJobListResponse>, Observable<List<MyCollectJobResponse>>>() {
                    @Override
                    public Observable<List<MyCollectJobResponse>> call(BaseResponse<MyCollectJobListResponse> listBaseResponse) {
                        if (!listBaseResponse.isSuccess()){
                            return Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                        }
                        if (null == listBaseResponse.getData() || null == listBaseResponse.getData().getList()|| listBaseResponse.getData().getList().isEmpty()){
                            return Observable.error(new MyCollectJobEmptyException());
                        }
                        pageMachine.setTotalPage(listBaseResponse.getPage().getPageSize());
                        return Observable.just(listBaseResponse.getData().getList());
                    }
                });
            }
        });
    }

    @Override
    public Observable<List<MyShieldCompanyResponse>> loadMyShieldCompany(final PageMachine pageMachine) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<List<MyShieldCompanyResponse>>>() {
            @Override
            public Observable<List<MyShieldCompanyResponse>> call(User user) {
                return mGetJobApi.loadMyShieldCompany(user.getUserId(),pageMachine.getPageIndex(),pageMachine.getPageContentCount()).flatMap(new Func1<BaseResponse<MyShieldCompanyListResponse>, Observable<List<MyShieldCompanyResponse>>>() {
                    @Override
                    public Observable<List<MyShieldCompanyResponse>> call(BaseResponse<MyShieldCompanyListResponse> listBaseResponse) {
                        if (!listBaseResponse.isSuccess()){
                            return Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                        }
                        if (null == listBaseResponse.getData() || null == listBaseResponse.getData().getList()|| listBaseResponse.getData().getList().isEmpty()){
                            return Observable.error(new MyCollectJobEmptyException());
                        }
                        pageMachine.setTotalPage(listBaseResponse.getPage().getPageSize());
                        return Observable.just(listBaseResponse.getData().getList());
                    }
                });
            }
        });
    }
}
