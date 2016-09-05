package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.domain.cooperation.LoadCooperationCompanyDetailCase;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;

import rx.Subscriber;

/**
 * author ：ml on 2016/7/27
 */
public class CooperationCompanyDetailPresenter extends MvpBasePresenter<MvpView> {
    private LoadCooperationCompanyDetailCase mLoadCooperationCompanyDetailCase;
    private ErrorMessageFactory mErrorMessageFactory;

    public CooperationCompanyDetailPresenter(LoadCooperationCompanyDetailCase loadCooperationCompanyDetailCase, ErrorMessageFactory errorMessageFactory) {
        this.mLoadCooperationCompanyDetailCase = loadCooperationCompanyDetailCase;
        this.mErrorMessageFactory = errorMessageFactory;
    }

//  获取公司详情的case   根据companyId 查看公司信息
    public void loadData() {
        if (!isViewAttached()) {
            return;
        }
        // TODO: 2016/7/27   这个case 执行接口有问题
        mLoadCooperationCompanyDetailCase.execute(getCompanyDetailSub());
    }
    public Subscriber getCompanyDetailSub(){
        return new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        };
    }


}
