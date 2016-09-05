package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.BaseSearchView;
import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/13.
 */
public abstract class BaseSearchPresenter extends MvpBasePresenter<BaseSearchView> {

    UseCase mLoadDataUseCase;
    UseCase mSaveDataUseCase;
    UseCase mQueryDataUseCase;
    ErrorMessageFactory mErrorMessageFactory;

//    public BaseSearchPresenter(ErrorMessageFactory errorMessageFactory) {
//        mErrorMessageFactory = errorMessageFactory;
//    }

    public BaseSearchPresenter(UseCase searchUseCase, UseCase mSaveDataUseCase, UseCase mQueryDataUseCase, ErrorMessageFactory errorMessageFactory) {
        this.mLoadDataUseCase = searchUseCase;
        this.mSaveDataUseCase = mSaveDataUseCase;
        this.mQueryDataUseCase = mQueryDataUseCase;
        this.mErrorMessageFactory = errorMessageFactory;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        unSubscribe(mLoadDataUseCase);
        unSubscribe(mSaveDataUseCase);
    }

    public void doSaveData(String content) {
        if (!isViewAttached()) {
            return;
        }

        if (TextUtils.isEmpty(content)) {
            getView().showToast(getString(R.string.errTips_user_empty));
            return;
        }

        getView().showProgressDialog(R.string.loading);
        mSaveDataUseCase.execute(getSaveDataSub(), content);
    }

    private Subscriber getSaveDataSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().closeSelf();
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
            public void onNext(Object o) {

            }
        };
    }

    public List<Object> loadData(String index) {
        List<Object> objectList = new ArrayList<>();
        if (!isViewAttached()) {
            return objectList;
        }

        if (TextUtils.isEmpty(index)) {
            getView().showToast(getString(R.string.errTips_user_empty));
            return objectList;
        }
        getView().showProgressDialog(R.string.loading);
        mLoadDataUseCase.execute(getLoadDataSub(), index);

        return objectList;
    }

    private Subscriber getLoadDataSub() {
        return new Subscriber<List<Object>>() {
            @Override
            public void onCompleted() {
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
            public void onNext(List<Object> objects) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().setData(objects);
            }
        };
    }

    private String getString(int resId) {
        return getView().getActivityContext().getString(resId);
    }

    private void unSubscribe(UseCase useCase) {
        if (null == useCase) {
            return;
        }
        useCase.unSubscribe();
    }
}
