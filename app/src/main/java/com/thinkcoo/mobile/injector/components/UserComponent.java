package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.ActivityModule;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.presentation.views.activitys.BirthPlaceActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CaptureActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CertificateNumberActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ChangeNameActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CreateClassActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CreateCodeActivity;
import com.thinkcoo.mobile.presentation.views.activitys.FeedBackActivity;
import com.thinkcoo.mobile.presentation.views.activitys.IndustryDirectionActivity;
import com.thinkcoo.mobile.presentation.views.activitys.LivePlaceActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ModifyPasswordActivity;
import com.thinkcoo.mobile.presentation.views.activitys.SignatureActivity;
import com.thinkcoo.mobile.presentation.views.activitys.SingleLineEditActivity;
import com.thinkcoo.mobile.presentation.views.activitys.SingleLineEditAndAutoHintActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserBasicInfoActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserHarvestActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserHarvestDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserHobbyActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserMainInfoActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserSkillActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserStatusActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserStatusDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserWorkExperienceActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserWorkExperienceDetailActivity;
import com.thinkcoo.mobile.presentation.views.fragment.UserMainFragment;

import dagger.Component;

/**
 * Created by Robert.yao on 2016/3/22.
 */
@Component(dependencies = ApplicationComponent.class , modules ={UserModule.class , ActivityModule.class})
@ActivityScope
public interface UserComponent {

    void inject(SignatureActivity signatureActivity);
    void inject(UserMainInfoActivity userMainInfoActivity);
    void inject(ChangeNameActivity changeNameActivity);
    void inject(FeedBackActivity feedBackActivity);
    void inject(UserBasicInfoActivity userBasicInfoActivity);
    void inject(UserMainFragment personFragment);
    void inject(LivePlaceActivity livePlaceActivity);
    void inject(BirthPlaceActivity birthPlaceActivity);
    void inject(UserStatusActivity userStatusActivity);
    void inject(UserHarvestActivity userHarvestActivity);
    void inject(UserHarvestDetailActivity userHarvestDetailActivity);
    void inject(UserSkillActivity userSkillActivity);
    void inject(UserHobbyActivity userHobbyActivity);
    void inject(UserStatusDetailActivity userStatusDetailActivity);
    void inject(SingleLineEditActivity singleLineEditActivity);
    void inject(SingleLineEditAndAutoHintActivity singleLineEditAndAutoHintActivity);
    void inject(IndustryDirectionActivity industryDirectionActivity);
    void inject(UserWorkExperienceActivity userWorkExperienceActivity);
    void inject(UserWorkExperienceDetailActivity userWorkExperienceDetailActivity);
    void inject(CertificateNumberActivity certificateNumberActivity);
    void inject(ModifyPasswordActivity modifyPasswordActivity);
    void inject(CaptureActivity captureActivity);
    void inject(CreateClassActivity createClassActivity);
    void inject(CreateCodeActivity createCodeActivity);
}
