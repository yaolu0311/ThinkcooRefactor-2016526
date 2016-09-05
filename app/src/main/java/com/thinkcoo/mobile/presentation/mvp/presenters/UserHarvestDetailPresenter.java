package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.UserHarvest;
import com.thinkcoo.mobile.model.entity.UserHarvestDetail;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.BaseDetailView;
import com.thinkcoo.mobile.utils.FieldCheckUtil;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Leevin
 * CreateTime: 2016/6/2  11:36
 */
public class UserHarvestDetailPresenter extends BaseDetailPresenter<BaseDetailView<UserHarvest,UserHarvestDetail>,UserHarvest,UserHarvestDetail> {

    @Inject
    FieldCheckUtil mFieldCheckUtil;

    @Inject
    public UserHarvestDetailPresenter(@Named("updateUserHarvest") UseCase editDetailUseCase, @Named("addUserHarvest") UseCase addDetailUseCase, @Named("loadUserHarvestDetail") UseCase loadDetailUseCase, ErrorMessageFactory errorMessageFactory) {
        super(editDetailUseCase, addDetailUseCase, loadDetailUseCase, errorMessageFactory);
    }

    @Override
    protected boolean checkHost(UserHarvest userHarvest) {
        return mFieldCheckUtil.checkEntityFieldsFormat(getView().getActivityContext(),userHarvest,(BaseDetailView)getView());
    }

    @Override
    protected boolean checkAndCompareHost(UserHarvest newHost, UserHarvest rawHost) {
        return newHost.equals(rawHost);
    }

    @Override
    protected void detailAttachToHost(UserHarvest userHarvest, UserHarvestDetail detail) {
        userHarvest.setUserHarvestDetail(detail);
    }

    public void uoloadHarvestPhotoList(List<String> photoList){}
}
