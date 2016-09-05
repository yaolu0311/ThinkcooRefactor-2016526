package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.data_dictionary.GetDataDictionaryUseCase;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategy;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.IndustryDirectionView;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/16.
 */
public class IndustryDirectionPresenter extends MvpBasePresenter<IndustryDirectionView> {

    private final static String TAG = "IndustryDirectionPresenter";

    GetDataDictionaryUseCase mGetDataDictionaryUseCase;
    InputCheckUtil mInputCheckUtil;
    ErrorMessageFactory mErrorMessageFactory;

    @Inject
    public IndustryDirectionPresenter(GetDataDictionaryUseCase getDataDictionaryUseCase, InputCheckUtil inputCheckUtil, ErrorMessageFactory errorMessageFactory) {
        mGetDataDictionaryUseCase = getDataDictionaryUseCase;
        mInputCheckUtil = inputCheckUtil;
        mErrorMessageFactory = errorMessageFactory;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mGetDataDictionaryUseCase.unSubscribe();
    }

    public void queryIndustryFromDb(DataDictionaryStrategy dataDictionaryStrategy){
        if (!isViewAttached()) {
            return;
        }
        getView().showProgressDialog(R.string.loading);
        mGetDataDictionaryUseCase.execute(getIndustrySub(), dataDictionaryStrategy);
    }

    private Subscriber getIndustrySub() {
        return new Subscriber<List<DataDictionary>>() {
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
                ThinkcooLog.e(TAG,e.getMessage(),e);
            }

            @Override
            public void onNext(List<DataDictionary> list) {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().setListData(list);
            }
        };
    }
}
