package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.BaseDetailView;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/6/1.
 */
public abstract class BaseDetailPresenter<V extends BaseDetailView<H ,D> ,H ,D> extends MvpBasePresenter<V>{

    private static final String TAG = "BaseDetailPresenter";
    UseCase mEditDetailUseCase;
    UseCase mAddDetailUseCase;
    UseCase mLoadDetailUseCase;
    ErrorMessageFactory mErrorMessageFactory;

    private H mHostObject;

    public BaseDetailPresenter(UseCase editDetailUseCase, UseCase addDetailUseCase , UseCase loadDetailUseCase, ErrorMessageFactory errorMessageFactory) {
        mEditDetailUseCase  = editDetailUseCase;
        mAddDetailUseCase = addDetailUseCase;
        mLoadDetailUseCase = loadDetailUseCase;
        mErrorMessageFactory = errorMessageFactory;
    }

    @Override
    public void detachView(boolean retainInstance) {
        mEditDetailUseCase.unSubscribe();
        mAddDetailUseCase.unSubscribe();
        mLoadDetailUseCase.unSubscribe();
    }

    public void loadDetail(H hostObject) {
        if (!isViewAttached()){
            return;
        }
        getView().showProgressDialog(R.string.loading);
        mLoadDetailUseCase.execute(getLoadDetailSub(),hostObject);
        mHostObject = hostObject;
    }

    private Subscriber getLoadDetailSub() {
        return new Subscriber<D>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                ThinkcooLog.e("TAG",e.getMessage(),e);

                if (!isViewAttached()){
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(D detail) {
                if (!isViewAttached()){
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().setDetail(detail);
                detailAttachToHost(mHostObject,detail);
            }
        };
    }


    public void editHost() {

        if (!isViewAttached()){
            return;
        }
        if (!checkHost(getView().getHostFromUi())){
            return;
        }
        if (checkAndCompareHost(getView().getHostFromUi(),mHostObject)){
            ThinkcooLog.e(TAG,"===no change===");
            getView().setResultCancelAndCloseSelf();
            return;
        }
        getView().showProgressDialog(R.string.loading);
        mEditDetailUseCase.execute(getCompleteSub(),getView().getHostFromUi());

    }
    public void addHost(){

        if (!isViewAttached()){
            return;
        }
        if (!checkHost(getView().getHostFromUi())){
            return;
        }
        getView().showProgressDialog(R.string.loading);
        mAddDetailUseCase.execute(getCompleteSub(),getView().getHostFromUi());

    }

    private Subscriber getCompleteSub() {
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().setResultOkAndCloseSelf();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }

            @Override
            public void onNext(Void o) {

            }
        };
    }
    protected abstract boolean checkHost(H host);
    protected abstract boolean checkAndCompareHost(H newHost,H rawHost);
    protected abstract void detailAttachToHost(H hostObject, D detail);
}
