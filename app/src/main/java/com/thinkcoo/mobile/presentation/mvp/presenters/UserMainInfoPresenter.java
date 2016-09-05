package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.user.ChangeUserSexCase;
import com.thinkcoo.mobile.domain.user.GetUserBasicInfoUseCase;
import com.thinkcoo.mobile.domain.user.UploadPhotoUseCase;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.UserMainInfoView;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by WangYY 2016/3/22.
 */
public class UserMainInfoPresenter extends MvpBasePresenter<MvpView>{


    GetUserBasicInfoUseCase mGetUserBasicInfoUseCase;
    UploadPhotoUseCase mUploadPhotoUseCase;
    ChangeUserSexCase mChangeUserSexUseCase;
    ErrorMessageFactory mErrorMessageFactory;
    UserBasicInfo mUserBasicInfo;

    @Inject
    public UserMainInfoPresenter(GetUserBasicInfoUseCase getUserBasicInfoUseCase, ChangeUserSexCase changeUserSexUseCase,UploadPhotoUseCase uploadPhotoUseCase, ErrorMessageFactory errorMessageFactory ) {
        this.mGetUserBasicInfoUseCase = getUserBasicInfoUseCase;
        this.mChangeUserSexUseCase = changeUserSexUseCase;
        this.mErrorMessageFactory = errorMessageFactory;
        this.mUploadPhotoUseCase = uploadPhotoUseCase;
    }

    public void getUserBasicInfo(boolean isUpdateNow){
        if (!isViewAttached()){
            return;
        }
        getUserMainInfoView().showProgressDialog(R.string.loading);
        mGetUserBasicInfoUseCase.execute(getUserBasicInfoSub(),isUpdateNow);
    }

    public void uploadPhoto(String photoFilePath){

        getUserMainInfoView().showProgressDialog(R.string.dl_waiting);
        mUploadPhotoUseCase.execute(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()){
                    return;
                }
                getUserMainInfoView().hideProgressDialogIfShowing();
                getUserMainInfoView().showToast(getUserMainInfoView().getActivityContext().getString(R.string.toast_updatephoto_fail));
            }

            @Override
            public void onNext(Boolean o) {
                if (!isViewAttached()){
                    return;
                }
                getUserMainInfoView().hideProgressDialogIfShowing();
                if (!o){
                    getUserMainInfoView().showToast(getUserMainInfoView().getActivityContext().getString(R.string.toast_updatephoto_fail));
                }else {
                    getUserBasicInfo(true);
                }
            }
        },photoFilePath);
    }

    public UserMainInfoView getUserMainInfoView(){
        return (UserMainInfoView)getView();
    }

    private Subscriber getUserBasicInfoSub () {
        return new Subscriber<UserBasicInfo>() {
            @Override
            public void onCompleted() {
                getUserMainInfoView().hideProgressDialogIfShowing();
            }

            @Override
            public void onError(Throwable throwable) {
                if (!isViewAttached()) {
                    return;
                }
                getUserMainInfoView().hideProgressDialogIfShowing();
                getUserMainInfoView().showToast(mErrorMessageFactory.createErrorMsg(throwable));
            }

            @Override
            public void onNext(UserBasicInfo userBasicInfo) {
                if (!isViewAttached()) {
                    return;
                }
                mUserBasicInfo = userBasicInfo;
                getUserMainInfoView().setUserMainInfo(userBasicInfo);
            }
        };
    }

    public void changeSex(String sex) {
        if (!isViewAttached()){
            return;
        }
        getUserMainInfoView().showProgressDialog(R.string.loading);
        mChangeUserSexUseCase.execute(ChangeSexSub(),sex);
    }

    private Subscriber ChangeSexSub () {
        return new Subscriber<Void > () {
            @Override
            public void onCompleted () {
                mGetUserBasicInfoUseCase.execute(getUserBasicInfoSub(),true);
            }

            @Override
            public void onError (Throwable throwable){
                if (!isViewAttached()) {
                    return;
                }
                getUserMainInfoView().hideProgressDialogIfShowing();
                getUserMainInfoView().showToast(mErrorMessageFactory.createErrorMsg(throwable));
            }

            @Override
            public void onNext (Void aVoid){

            }
        };
    }
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mChangeUserSexUseCase.unSubscribe();
        mGetUserBasicInfoUseCase.unSubscribe();
        mUploadPhotoUseCase.unSubscribe();
    }
}
