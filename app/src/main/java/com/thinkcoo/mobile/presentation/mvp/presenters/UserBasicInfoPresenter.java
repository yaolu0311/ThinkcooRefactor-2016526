package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.user.SetUserBasicInfoUseCase;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.UserBasicInfoView;
import com.thinkcoo.mobile.utils.FieldCheckUtil;
import com.thinkcoo.mobile.utils.InputCheckUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Wangyy on 2016/3/22.
 */
public class UserBasicInfoPresenter extends MvpBasePresenter<MvpView>{


    SetUserBasicInfoUseCase mSetUserBasicInfoUseCase;
    ErrorMessageFactory mErrorMessageFactory;
    @Inject
    FieldCheckUtil mFieldCheckUtil;
    @Inject
    InputCheckUtil mInputCheckUtil;
    @Inject
    public UserBasicInfoPresenter(SetUserBasicInfoUseCase setUserBasicInfoUseCase,ErrorMessageFactory errorMessageFactory ) {
        mSetUserBasicInfoUseCase = setUserBasicInfoUseCase;
        this.mErrorMessageFactory = errorMessageFactory;
    }

    public UserBasicInfoView getUserBasicInfoView(){
        return (UserBasicInfoView)getView();
    }

    protected boolean checkHost(UserBasicInfo userBasicInfo) {
        return mFieldCheckUtil.checkEntityFieldsFormat(getUserBasicInfoView().getActivityContext(),userBasicInfo,getUserBasicInfoView());
    }
    public void setUserBasicInfo(){
        if (!isViewAttached()){
            return;
        }
        if(!checkHost(getUserBasicInfoView().getUserBasicInfo())){
            return;
        }
        UserBasicInfo userBasicInfo = getUserBasicInfoView().getUserBasicInfo();
        if(!checkInputId(userBasicInfo.getCertificateNumber(),userBasicInfo.getCertificateType())){
            return;
        }
        getUserBasicInfoView().showProgressDialog(R.string.loading);
        mSetUserBasicInfoUseCase.execute(setUserBasicInfoSub(),userBasicInfo);
    }

    private Subscriber setUserBasicInfoSub () {
        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                getUserBasicInfoView().hideProgressDialogIfShowing();
//              getUserBasicInfoView().closeSelf();
                getUserBasicInfoView().resultToUserMainInfoActivity();
            }

            @Override
            public void onError(Throwable throwable) {
                if (!isViewAttached()) {
                    return;
                }
                getUserBasicInfoView().hideProgressDialogIfShowing();
                getUserBasicInfoView().showToast(mErrorMessageFactory.createErrorMsg(throwable));
            }

            @Override
            public void onNext(Void avoid) {
                if (!isViewAttached()) {
                    return;
                }
            }
        };
    }
    public boolean checkInputId(String certificate ,String certificateType) {
        if(certificateType.equals("0")){
            if (!mInputCheckUtil.checkPersonID(certificate)){
                getUserBasicInfoView().showToast(getString(R.string.personId_format_error));
                return false;
            }
        }else if (certificateType.equals("1")){
            if (!mInputCheckUtil.ishzId(certificate)){
                getUserBasicInfoView().showToast(getString(R.string.hzId_format_error));
                return false;
            }
        }else if (certificateType.equals("6")){
            if (!mInputCheckUtil.IsGAId(certificate)){
                getUserBasicInfoView().showToast(getString(R.string.gaId_format_error));
                return false;
            }
        }

        return  true;
    }
    public String getString(int resId){
        return getUserBasicInfoView().getActivityContext().getString(resId);
    }
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mSetUserBasicInfoUseCase.unSubscribe();
    }
}
