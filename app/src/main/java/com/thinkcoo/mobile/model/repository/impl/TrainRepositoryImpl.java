package com.thinkcoo.mobile.model.repository.impl;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.thinkcoo.mobile.model.cache.LoginUserCache;
import com.thinkcoo.mobile.model.db.DataDictionaryDao;
import com.thinkcoo.mobile.model.entity.Address;
import com.thinkcoo.mobile.model.entity.TrainCourse;
import com.thinkcoo.mobile.model.entity.TrainCourseFilter;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.model.entity.serverresponse.TrainCourseResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;
import com.thinkcoo.mobile.model.exception.ServerResponseException;
import com.thinkcoo.mobile.model.repository.TrainRepository;
import com.thinkcoo.mobile.model.rest.apis.TrainApi;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Leevin
 * CreateTime: 2016/8/19  14:22
 */
public class TrainRepositoryImpl implements TrainRepository {

    TrainApi mTrainApi;
    DataDictionaryDao mDataDictionaryDao;
    LoginUserCache mLoginUserCache;
    ServerDataConverter mServerDataConverter;

    @Inject
    public TrainRepositoryImpl(TrainApi trainApi, LoginUserCache loginUserCache, ServerDataConverter serverDataConverter ,DataDictionaryDao dataDictionaryDao) {
        mTrainApi = trainApi;
        mLoginUserCache = loginUserCache;
        mServerDataConverter = serverDataConverter;
        mDataDictionaryDao = dataDictionaryDao;
    }

    @Override
    public Observable<List<TrainCourse>> loadTrainCourseList(final TrainCourseFilter trainCourseFilter) {

        return mLoginUserCache.get().flatMap(new Func1<User,  Observable<BaseResponse<List<TrainCourseResponse>>>>() {
            @Override
            public  Observable<BaseResponse<List<TrainCourseResponse>>> call(User user) {
                return mTrainApi.searchTrainCourse(user.getUserId(),trainCourseFilter.getKeyword(),trainCourseFilter.getAreaCode(),trainCourseFilter.getPrice(),trainCourseFilter.getCourseType(),trainCourseFilter.getStartTime(),trainCourseFilter.getPageMachine().getPageIndex(),trainCourseFilter.getPageMachine().getTotalPage());
            }
        }).flatMap(new Func1<BaseResponse<List<TrainCourseResponse>>, Observable<List<TrainCourse>>>() {
            @Override
            public Observable<List<TrainCourse>> call(BaseResponse<List<TrainCourseResponse>> listBaseResponse) {
                if (!listBaseResponse.isSuccess()) {
                    Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                }
                return Observable.just(TrainCourse.fromServerResponse(listBaseResponse.getData()));
            }
        });
    }

    @Override
    public Observable<List<TrainCourse>> loadMyAppointmentList(String pageNow, String pageSize) {
        return null;
    }

    @Override
    public Observable<List<TrainCourse>> loadMyCollectionList(String pageNow, String pageSize) {
        return null;
    }

    @Override
    public Observable deleteMyAppointmentOrCollection(final String id) {
        return mLoginUserCache.get().flatMap(new Func1<User,   Observable<BaseResponse>>() {
            @Override
            public  Observable<BaseResponse> call(User user) {
                return mTrainApi.deleteAppointmentOrCollection(user.getUserId(),id);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

    @Override
    public Observable cancelAppointmentOrCollection(final String id) {
        return mLoginUserCache.get().flatMap(new Func1<User,   Observable<BaseResponse>>() {
            @Override
            public  Observable<BaseResponse> call(User user) {
                return mTrainApi.deleteAppointmentOrCollection(user.getUserId(),id);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

    @Override
    public Observable addAppointmentOrCollection(final String courseId, final String type) {
        Observable<User> userObservable = mLoginUserCache.get();
        return userObservable.flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mTrainApi.addAppointmentOrCollection(user.getUserId(), courseId, type);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

    @Override
    public Observable<Object> loadCompanyDetails(String companyId) {
        // TODO: 2016/8/22  
        return null;
    }

    @Override
    public Observable<Object> loadTrainCourseDetails(String courseId) {
        // TODO: 2016/8/22
        return null;
    }

    @Override
    public Observable<List<Address>> loadAreaByParentCode(String areaCode) {
        return mDataDictionaryDao.queryChildAreaByCode(areaCode);
    }
}
