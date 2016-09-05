package com.thinkcoo.mobile.presentation.mvp.presenters;

import android.text.TextUtils;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.entity.UserStatusDetail;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.BaseDetailView;
import com.thinkcoo.mobile.presentation.views.activitys.UserStatusDetailActivity;
import com.thinkcoo.mobile.utils.FieldCheckUtil;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Created by Administrator on 2016/6/2.
 */
public class UserStatusDetailPresenter extends BaseDetailPresenter<BaseDetailView<UserStatus, UserStatusDetail>,UserStatus,UserStatusDetail> {

    @Inject
    FieldCheckUtil mFieldCheckUtil;

    @Inject
    public UserStatusDetailPresenter(@Named("updateUserStatus") UseCase editDetailUseCase,
                                     @Named("addUserStatus") UseCase addDetailUseCase,
                                     @Named("loadUserStatusDetail") UseCase loadDetailUseCase,
                                     ErrorMessageFactory errorMessageFactory) {
        super(editDetailUseCase, addDetailUseCase, loadDetailUseCase, errorMessageFactory);

    }

    @Override
    protected boolean checkHost(UserStatus host) {
        if (null == host) {
            getView().showToast(getView().getActivityContext().getString(R.string.necessary_field_is_empty));
            return false;
        }

        int type = host.getType();
        if (TextUtils.isEmpty(host.getStartTime())){
            getView().showToast(getView().getActivityContext().getString(R.string.time_is_empty));
            return false;
        }

        if (TextUtils.isEmpty(host.getTitle())) {
            noticeTitleFieldIsEmpty(type);
            return false;
        }

        if (type == UserStatusDetailActivity.EDUCATION_STATUS_TYPE && TextUtils.isEmpty(host.getExtraInfo())) {
            getView().showToast(getView().getActivityContext().getString(R.string.education_level_is_empty));
            return false;
        }
        return mFieldCheckUtil.checkEntityFieldsFormat(getView().getActivityContext(),host.getUserStatusDetail(),(BaseDetailView)getView());
    }

    private void noticeTitleFieldIsEmpty(int type) {
        int resourceId = R.string.please_fill_harvest_name;
        switch (type) {
            case UserStatusDetailActivity.EDUCATION_STATUS_TYPE:
                resourceId = R.string.school_name_is_empty;
                break;
            case UserStatusDetailActivity.TRAIN_STATUS_TYPE:
                resourceId = R.string.trainCompany_name_is_empty;
                break;
            case UserStatusDetailActivity.FULL_TIME_WORK_STATUS_TYPE:
            case UserStatusDetailActivity.PART_TIME_WORK_STATUS_TYPE:
                resourceId = R.string.please_fill_harvest_department;
                break;
        }
        getView().showToast(getView().getActivityContext().getString(resourceId));
    }

    @Override
    protected boolean checkAndCompareHost(UserStatus newHost, UserStatus rawHost) {
        if (null == newHost || null == rawHost) {
            return false;
        }
        return newHost.equals(rawHost);
    }

    @Override
    protected void detailAttachToHost(UserStatus hostObject, UserStatusDetail detail) {
        hostObject.setUserStatusDetail(detail);
    }

}
