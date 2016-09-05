package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.Address;
import com.thinkcoo.mobile.model.entity.CheckVcode;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.model.entity.License;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.model.entity.MyGoods;
import com.thinkcoo.mobile.model.entity.ThinkCooPhoto;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.model.entity.UserHarvest;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.entity.UserWorkExperienceEntity;
import com.thinkcoo.mobile.model.strategy.SingleLineEditAndAutoHintStrategy;
import com.thinkcoo.mobile.model.strategy.SingleLineEditAndAutoHintStrategyFactory;
import com.thinkcoo.mobile.model.strategy.SingleLineEditStrategy;
import com.thinkcoo.mobile.model.strategy.SingleLineEditStrategyFactory;
import com.thinkcoo.mobile.model.strategy.StringSingleLineEditContent;
import com.thinkcoo.mobile.presentation.views.activitys.AboutActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ActiveMemberActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ActivityDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.AddEditScheduleActivity;
import com.thinkcoo.mobile.presentation.views.activitys.AddResourceActivity;
import com.thinkcoo.mobile.presentation.views.activitys.AddShieldCompanyActivity;
import com.thinkcoo.mobile.presentation.views.activitys.BirthPlaceActivity;
import com.thinkcoo.mobile.presentation.views.activitys.BuyGoodsDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CaptureActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CertificateNumberActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ChangeNameActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CompleteFindPasswordActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CompleteRegisterActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CourseDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CreateActivityGroupActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CreateClassActivity;
import com.thinkcoo.mobile.presentation.views.activitys.CreateCodeActivity;
import com.thinkcoo.mobile.presentation.views.activitys.DayViewActivity;
import com.thinkcoo.mobile.presentation.views.activitys.DepartmentEditActivity;
import com.thinkcoo.mobile.presentation.views.activitys.DownloadManagerActivity;
import com.thinkcoo.mobile.presentation.views.activitys.FeedBackActivity;
import com.thinkcoo.mobile.presentation.views.activitys.FindCooperationMainActivity;
import com.thinkcoo.mobile.presentation.views.activitys.GetJobActivity;
import com.thinkcoo.mobile.presentation.views.activitys.GetJobMainActivity;
import com.thinkcoo.mobile.presentation.views.activitys.GoodsSearchActivity;
import com.thinkcoo.mobile.presentation.views.activitys.GoodsSearchAndFilterActivity;
import com.thinkcoo.mobile.presentation.views.activitys.IndustryDirectionActivity;
import com.thinkcoo.mobile.presentation.views.activitys.LivePlaceActivity;
import com.thinkcoo.mobile.presentation.views.activitys.LoadSchoolBaiduAddressActivity;
import com.thinkcoo.mobile.presentation.views.activitys.LoginActivity;
import com.thinkcoo.mobile.presentation.views.activitys.MainActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ManualAddActivity;
import com.thinkcoo.mobile.presentation.views.activitys.MerbermanagerDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ModifyPasswordActivity;
import com.thinkcoo.mobile.presentation.views.activitys.MyCollectGoodsActivity;
import com.thinkcoo.mobile.presentation.views.activitys.MyGoodsActivity;
import com.thinkcoo.mobile.presentation.views.activitys.MyShieldCompanyActivity;
import com.thinkcoo.mobile.presentation.views.activitys.MyTradeActivity;
import com.thinkcoo.mobile.presentation.views.activitys.NoticeActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ReleaseMySellGoodsActivity;
import com.thinkcoo.mobile.presentation.views.activitys.RequestFindPasswordActivity;
import com.thinkcoo.mobile.presentation.views.activitys.RequestRegisterActivity;
import com.thinkcoo.mobile.presentation.views.activitys.RockCallResultActivity;
import com.thinkcoo.mobile.presentation.views.activitys.RockCallSingleResultActivity;
import com.thinkcoo.mobile.presentation.views.activitys.RollCallActivity;
import com.thinkcoo.mobile.presentation.views.activitys.ScoreAnalysisActivity;
import com.thinkcoo.mobile.presentation.views.activitys.SelectFileToUploadActivity;
import com.thinkcoo.mobile.presentation.views.activitys.SelectLocationActivity;
import com.thinkcoo.mobile.presentation.views.activitys.SelfLeanDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.SellGoodsDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.SignatureActivity;
import com.thinkcoo.mobile.presentation.views.activitys.SingleLineEditActivity;
import com.thinkcoo.mobile.presentation.views.activitys.SingleLineEditAndAutoHintActivity;
import com.thinkcoo.mobile.presentation.views.activitys.StudentManageActivity;
import com.thinkcoo.mobile.presentation.views.activitys.TradeMainActivity;
import com.thinkcoo.mobile.presentation.views.activitys.TrainHomeActivity;
import com.thinkcoo.mobile.presentation.views.activitys.TrainMyAppointmentActivity;
import com.thinkcoo.mobile.presentation.views.activitys.TrainMyCollectionActivity;
import com.thinkcoo.mobile.presentation.views.activitys.TrainSearchActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserAccountSafeActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserBasicInfoActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserChangePhoneNumberActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserHarvestActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserHarvestDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserHobbyActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserLicenseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserMainActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserMainInfoActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserSettingActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserSkillActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserStatusActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserStatusDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserWorkExperienceActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserWorkExperienceDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.WebViewActivity;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;
import com.thinkcoo.mobile.presentation.views.component.viewBean.UserWorkStatusContentView;

import javax.inject.Inject;


/**
 * Created by Robert.yao on 2016/3/21.
 * 全局导航类，负责整个应用程序的页面跳转
 */
public class Navigator {


    @Inject
    public Navigator() {
    }

    /**
     * 跳转到新用户使用提醒界面
     *
     * @param context activity context
     */
    public void navigateToNewGuidance(Context context) {
        //TODO
        //context.startActivity(NewUserGuidanceActivity.getNewUserGuidanceIntent(context));
    }

    /**
     * 跳转到登录界面
     *
     * @param context activity context
     */
    public void navigateToLogin(Context context) {
        context.startActivity(LoginActivity.getLoginIntent(context));
    }

    /**
     * 请求跳转到登录界面并填写一个账户名
     *
     * @param context
     * @param accountName
     */
    public void navigateToLoginAndFillAccountName(Context context, String accountName) {
        context.startActivity(LoginActivity.getLoginIntent(context, accountName));
    }


    /**
     * 跳转到注册
     */
    public void navigateToRegister(Context context) {
        Intent intent = RequestRegisterActivity.getRequestRegisterIntent(context);
        context.startActivity(intent);
    }

    /**
     * 跳转到找回密码
     */
    public void navigateToRequestFindPassword(Context context) {
        Intent intent = RequestFindPasswordActivity.getRequestFindPassWordIntent(context);
        context.startActivity(intent);
    }

    /**
     * 跳转到完成找回密码
     */
    public void navigateToCompletefindPassword(Context context, CheckVcode checkVcode) {
        Intent intent = CompleteFindPasswordActivity.getCompleteFindPasswordIntent(context, checkVcode);
        context.startActivity(intent);
    }

    /**
     * 跳转到主界面
     *
     * @param context
     */
    public void navigateToHome(Context context) {
        Intent intent = UserMainActivity.getUserMainIntent(context);
        context.startActivity(intent);
    }

    /***
     * 跳转到完成注册界面(设置密码)
     *
     * @param context
     * @param phoneNumber
     */
    public void navigateToCompleteRegister(Context context, String phoneNumber) {
        Intent intent = CompleteRegisterActivity.getCompleteRegisterIntent(context, phoneNumber);
        context.startActivity(intent);
    }
    public void navigateToCreateCode(Context context) {
        Intent intent = CreateCodeActivity.getJumpIntent(context);
        context.startActivity(intent);
    }
    /**
     * 跳转到用户许可协议界面
     *
     * @param context
     * @param License 用户许可协议界面依赖的实体对象
     */
    public void navigateToUserLicense(Context context, License License) {
        Intent intent = UserLicenseActivity.getUserLicenseIntent(context, License);
        context.startActivity(intent);
    }

    /**
     * 跳转到状态列表界面
     *
     * @param context
     */
    public void navigateToUserStatusList(Context context) {
        Intent intent = UserStatusActivity.getUserStatusListIntent(context);
        context.startActivity(intent);
    }

    /**
     * 跳转到新增状态详情页面
     *
     * @param context
     */
    public void navigateToUserStatusDetailByAddMode(Context context, UserStatus userStatus) {
        Intent intent = UserStatusDetailActivity.getUserStatusDetailIntent(context, userStatus, BaseDetailActivity.ADD_MODE);
        ((UserStatusActivity) context).startActivityForResult(intent, UserStatusActivity.USER_STATUS_REQUEST_CODE);
    }

    /**
     * 跳转到编辑状态详情页面
     *
     * @param context
     */
    public void navigateToUserStatusDetailByEditMode(Context context, UserStatus userStatus) {
        Intent intent = UserStatusDetailActivity.getUserStatusDetailIntent(context, userStatus, BaseDetailActivity.EDIT_MODE);
        ((UserStatusActivity) context).startActivityForResult(intent, UserStatusActivity.USER_STATUS_REQUEST_CODE);
    }

    /**
     * 跳转到查看状态详情页面
     *
     * @param context
     */
    public void navigateToUserStatusDetailByViewMode(Context context, UserStatus userStatus) {
        Intent intent = UserStatusDetailActivity.getUserStatusDetailIntent(context, userStatus, BaseDetailActivity.VIEW_MODE);
        ((UserStatusActivity) context).startActivityForResult(intent, UserStatusActivity.USER_STATUS_REQUEST_CODE);
    }

    /**
     * 跳转到收获详情界面  编辑收获
     *
     * @param context
     */
    public void navigateToHarvestDetailWithEditMode(Context context, UserHarvest userHarvest) {
        Intent editHarvestIntent = UserHarvestDetailActivity.getHarvestIntentWithEditMode(context, userHarvest);
        ((UserHarvestActivity) context).startActivityForResult(editHarvestIntent, UserHarvestActivity.USER_HARVEST_REQUEST_CODE);
    }

    /**
     * 跳转到收获详情界面  查看收获
     *
     * @param context
     */
    public void navigateToHarvestDetailWithViewMode(Context context, UserHarvest userHarvest) {
        Intent viewHarvestIntent = UserHarvestDetailActivity.getHarvestIntentWithViewMode(context, userHarvest);
        context.startActivity(viewHarvestIntent);
    }


    /**
     * 跳转到收获详情界面  添加收获
     *
     * @param context
     */
    public void navigateToHarvestDetailWithAddMode(Context context) {
        Intent addHarvestIntent = UserHarvestDetailActivity.getHarvestIntentWithAddMode(context);
        ((UserHarvestActivity) context).startActivityForResult(addHarvestIntent, UserHarvestActivity.USER_HARVEST_REQUEST_CODE);
    }

    /**
     * 跳转到收获列表界面
     *
     * @param context
     */
    public void navigateToUserHarvest(Context context) {
        Intent userHarvestIntent = UserHarvestActivity.getUserHarvestIntent(context);
        context.startActivity(userHarvestIntent);
    }

    /**
     * 跳转到我的技能界面
     *
     * @param context
     */
    public void navigateToUserSkill(Context context) {
        Intent userUserSkillIntent = UserSkillActivity.getUserSkillIntent(context);
        context.startActivity(userUserSkillIntent);
    }

    /**
     * 跳转到我的爱好的界面
     *
     * @param context
     */
    public void navigateToUserHobby(Context context) {
        Intent userUserHobbyIntent = UserHobbyActivity.getUserHobbyIntent(context);
        context.startActivity(userUserHobbyIntent);
    }


    /**
     * 跳转到自信主界面
     *
     * @param context
     */
    public void navigateToUserMain(Context context) {
        Intent intent = UserMainActivity.getUserMainIntent(context);
        context.startActivity(intent);
    }

    /**
     * 跳转到基本主信息界面
     *
     * @param context
     */
    public void navigateToUserBasicInfo(Context context, UserBasicInfo userBasicInfo) {
        Intent intent = UserBasicInfoActivity.getUserBasicInfoIntent(context, userBasicInfo);
        ((UserMainInfoActivity) context).startActivityForResult(intent, UserMainInfoActivity.REQUEST_USER_INFO_CODE);
    }

    /**
     * 跳转到自信主信息界面
     *
     * @param context
     */
    public void navigateToUserMainInfo(Context context) {
        Intent intent = UserMainInfoActivity.getUserMainInfoIntent(context);
        context.startActivity(intent);
    }

    /**
     * 跳转到修改居住地界面
     *
     * @param context
     */

    public void navigateToLivePlace(Context context, Address address, String liveStreet) {
        Intent intent = LivePlaceActivity.getLivePlaceIntent(context, address, liveStreet);
        ((UserBasicInfoActivity) context).startActivityForResult(intent, UserBasicInfoActivity.REQUEST_USERINFO_LIVEPLACE_CODE);
    }

    /**
     * 跳转到修改出生地界面
     *
     * @param context
     */

    public void navigateToBirthPlace(Context context, Address address, String birthStreet) {
        Intent intent = BirthPlaceActivity.getBirthPlaceIntent(context, address, birthStreet);
        ((UserBasicInfoActivity) context).startActivityForResult(intent, UserBasicInfoActivity.REQUEST_USERINFO__BIRTHPLACE_CODE);
    }

    /**
     * 跳转到修改姓名界面
     *
     * @param context
     */
    public void navigateToChangeName(Context context, String name) {
        Intent intent = ChangeNameActivity.getChangeNameIntent(context, name);
        ((UserMainInfoActivity) context).startActivityForResult(intent, UserMainInfoActivity.REQUEST_USER_INFO_NAME_CODE);
    }

    /**
     * 跳转到修改签名界面
     *
     * @param context
     */
    public void navigateToSignature(Context context, String signature) {
        Intent intent = SignatureActivity.getSignatureIntent(context, signature);
        ((UserMainInfoActivity) context).startActivityForResult(intent, UserMainInfoActivity.REQUEST_USER_INFO_SINGATURE_CODE);
    }

    /**
     * 跳转到修改身份证号码界面
     *
     * @param context
     */
    public void navigateToCertificateNumber(Context context, String certificateNumber) {
        Intent intent = CertificateNumberActivity.getCertificateNumberInfoIntent(context, certificateNumber);
        ((UserBasicInfoActivity) context).startActivityForResult(intent, UserBasicInfoActivity.REQUEST_USERINFO_CERTIFICATENUMBER_CODE);
    }

    /**
     * 跳转到行业选择页面
     *
     * @param context
     * @param industry
     */
    public void navigateToIndustryDirectionPage(Context context, String industry) {
        Intent intent = IndustryDirectionActivity.getIndustryDirectionIntent(context, industry);
        ((UserStatusDetailActivity) context).startActivityForResult(intent, UserWorkStatusContentView.REQUEST_INDUSTRY_DIRECTORY);
    }

    public void navigateToUserWorkExperiencePage(Context context, String workId) {
        Intent intent = UserWorkExperienceActivity.getWorkExperienceIntent(context, workId);
        context.startActivity(intent);
    }


    public void navigateToWorkExperienceDetailPageByAddMode(Context context, String workId) {
        Intent intent = UserWorkExperienceDetailActivity.getWorkExperienceDetailIntent(context, workId, null, UserWorkExperienceDetailActivity.MODE_ADD);
        ((UserWorkExperienceActivity) context).startActivityForResult(intent, UserWorkExperienceDetailActivity.REQUEST_EXPERIENCE_CODE);
    }

    public void navigateToWorkExperienceDetailPageByEditMode(Context context, String workId, UserWorkExperienceEntity workExperienceListResponse) {
        Intent intent = UserWorkExperienceDetailActivity.getWorkExperienceDetailIntent(context, workId, workExperienceListResponse, UserWorkExperienceDetailActivity.MODE_EDIT);
        ((UserWorkExperienceActivity) context).startActivityForResult(intent, UserWorkExperienceDetailActivity.REQUEST_EXPERIENCE_CODE);
    }

    public void navigateToWorkExperienceDetailPageByViewMode(Context context, String workId, UserWorkExperienceEntity workExperienceListResponse) {
        Intent intent = UserWorkExperienceDetailActivity.getWorkExperienceDetailIntent(context, workId, workExperienceListResponse, UserWorkExperienceDetailActivity.MODE_VIEW);
        context.startActivity(intent);
    }

    public void navigateToSingleLineEditActivity(Context context, int singleLineEditType, String editText) {
        SingleLineEditStrategy singleLineEditStrategy = SingleLineEditStrategyFactory.create(singleLineEditType, new StringSingleLineEditContent(editText));
        Intent intent = SingleLineEditActivity.getSingleLineEditIntent(context, singleLineEditStrategy);
        ((Activity) context).startActivityForResult(intent, singleLineEditType);
    }

    public void navigateToSingleLineEditAndAutoHintActivity(Context context, int singleLineEditType, String editText) {

        SingleLineEditAndAutoHintStrategy singleLineEditStrategy = SingleLineEditAndAutoHintStrategyFactory.create(singleLineEditType, new StringSingleLineEditContent(editText));
        Intent intent = SingleLineEditAndAutoHintActivity.getSingleLineEditAndAutoHintIntent(context, singleLineEditStrategy);
        ((Activity) context).startActivityForResult(intent, singleLineEditType);
    }

    public void navigateToDepartmentEditActivity(Context context, int singleLineEditType, String editText , String schoolName){
        SingleLineEditAndAutoHintStrategy singleLineEditStrategy = SingleLineEditAndAutoHintStrategyFactory.create(singleLineEditType, new StringSingleLineEditContent(editText));
        Intent intent = DepartmentEditActivity.getDepartmentEditIntent(context,singleLineEditStrategy,schoolName);
        ((Activity) context).startActivityForResult(intent, singleLineEditType);
    }

    public void navigateToMainActivity(Context context){
        Intent intent = MainActivity.getMainActivityIntent(context);
        context.startActivity(intent);
    }

    /**
     *
     * 跳转到 AddEditScheduleActivity add
     */
    public void navigateToEditScheduleToAdd(Context context, long startMillis, long endMillis, boolean isAllDay) {
        Intent intent = AddEditScheduleActivity.getScheduleIntentWithAddMode(context,startMillis,endMillis,isAllDay);
        context.startActivity(intent);
    }

    /**
     *
     * 跳转到 AddEditScheduleActivity edit
     */
    public void navigateToEditScheduleToEdit(Context context, String scheduleId) {
        Intent intent = AddEditScheduleActivity.getScheduleIntentWithEditMode(context,scheduleId);
        context.startActivity(intent);
    }

    /**
     * 跳转到自贸区主页
     */
    public void navigateToTradeMainActivity(Context context) {
        context.startActivity(TradeMainActivity.getJumpIntent(context));
    }
    /**
     * 跳转到找工作主页
     */
    public void navigateToGetJobActivity(Context context) {
        context.startActivity(GetJobMainActivity.getJumpIntent(context));
    }
    /**
     * 跳转到找培训主页
     */
    public void navigateToGetTrainActivity(Context context) {
        context.startActivity(TrainHomeActivity.getJumpIntent(context));
    }
    /**
     * 跳转到找合作主页
     */
    public void navigateToGetCollaborateActivity(Context context) {

    }
    /**
     * 跳转到选择位置界面
     */
    public void navigateToSelectLocationActivity(Context context , Location location){
        ((Activity)context).startActivityForResult(SelectLocationActivity.getJumpIntent(context,location),SelectLocationActivity.REQUEST_CODE);
    }
    /**
     *
     * 跳转到 创建班组
     */
    public void navigateToCreatelass(Context context, Event event) {
        Intent intent = CreateClassActivity.getCreateClassIntent(context,event);
        ((StudentManageActivity) context).startActivityForResult(intent, StudentManageActivity.REQUEST_CREATE_CLASS_CODE);
    }
    /**
     *
     * 跳转到 手动添加成员
     */
    public void navigateToManualAdd(Context context, Event event,String groupId) {
        Intent intent = ManualAddActivity.getManualAddIntent(context,event,groupId);
        ((StudentManageActivity) context).startActivityForResult(intent, StudentManageActivity.REQUEST_MANUAL_ADD_CODE);
    }

    /**
     * 跳转找合作的界面
     */
    public void navigateToFindCooperationMain(Context context) {
        Intent findCooperationIntent = FindCooperationMainActivity.getFindCooperationIntent(context);
        context.startActivity(findCooperationIntent);
    }

    /**
     * 跳转管理事件详情的界面
     */
    public void navigateToMerbermanagerDetail(Context context, Event event) {
        Intent intent = MerbermanagerDetailActivity.getMerbermanagerDetailIntent(context,event);
        context.startActivity(intent);
    }
    /**
     * 跳转课程事件详情的界面
     */
    public void navigateToCourseDetail(Context context, Event event) {
        Intent intent = CourseDetailActivity.getCourseDetailIntent(context,event);
        context.startActivity(intent);
    }
    /**
     * 跳转自学事件详情的界面
     */
    public void navigateToSelfLeanDetail(Context context, Event event) {
        Intent intent = SelfLeanDetailActivity.getSelfLeanDetailIntent(context,event);
        context.startActivity(intent);
    }
    /**
     * 跳转活动事件详情的界面
     */
    public void navigateToActivityDetail(Context context, Event event) {
        Intent intent = ActivityDetailActivity.getActivityDetailIntent(context,event);
        context.startActivity(intent);
    }

    /**
     * 跳转到点名的界面
     */
    public void navigateToRockCall(Context context, Event event){
        Intent rockcall= RollCallActivity.getRollCallIntent(context,event);
        context.startActivity(rockcall);
    }

    /**
     * 跳转到点名结果的界面
     */

    public void navigateToRockCallResult(Context context, String uuid,String eventId) {
        Intent rockCallResult = RockCallResultActivity.getRockCallResult(context, uuid,eventId);
        context.startActivity(rockCallResult);
    }
    /**
     *  跳转到单次 点名结果的界面
     */
    public void navigateToRockCallSingleReslut(Context context,String uuid,String eventId){
        Intent rockCallSingle= RockCallSingleResultActivity.getRockCallSingle(context, uuid,eventId);
        context.startActivity(rockCallSingle);

    }


    /**
     * 跳转到创建活动组的界面
     */
    public void navigateToCreateActivityGroup(Context context, Event event){
        Intent intent= CreateActivityGroupActivity.getCreateActivityGroupIntent(context,event);
        ((StudentManageActivity) context).startActivityForResult(intent, StudentManageActivity.REQUEST_CREATE_CLASS_CODE);
    }

    /**
     * 跳转到活动添加成员
     */
    public void navigateToActiveMember(Context context, Event event, String groupid){
        Intent intent= ActiveMemberActivity.getActiveMemberIntent(context,event,groupid);
        ((CreateActivityGroupActivity) context).startActivityForResult(intent, CreateActivityGroupActivity.REQUEST_CREATE_ACTIVITY_CODE);
//        context.startActivity(intent);
    }
    /**
     * 成员管理
     */
    public void navigateToStudentManage(Context context, Event event){
        Intent intent= StudentManageActivity.getStudentManageIntent(context,event);
        context.startActivity(intent);
    }
    /**
     * 通告
     */
    public void navigateToNotice(Context context, Event event){
        Intent intent= NoticeActivity.getNoticeIntent(context,event);
        context.startActivity(intent);
    }
    /**
     * 成绩分析
     */
    public void navigateScoreAnalysis(Context context, Event event){
        Intent intent= ScoreAnalysisActivity.getScoreAnalysisIntent(context,event);
        context.startActivity(intent);
    }

    /**
     * 跳转到产品搜索界面
     * @param context
     * @param goodsType 搜索的是出售还是求购
     * @param location 地理位置
     */
    public void navigateToGoodsSearchActivity(Context context ,int goodsType , Location location){
        Intent jumpIntent = GoodsSearchActivity.getJumpIntent(context,goodsType,location);
        context.startActivity(jumpIntent);
    }

    /**
     * 跳转到产品搜索并带有过滤条件的界面
     * @param context
     * @param goodFilterViewType
     * @param goodsType
     * @param location
     */
    public void navigateToGoodsSearchAndFilterActivity(Context context , int goodFilterViewType , int goodsType ,String searchKey, Location location) {
        Intent jumpIntent = GoodsSearchAndFilterActivity.getJumpIntent(context,goodFilterViewType,goodsType,searchKey,location);
        context.startActivity(jumpIntent);
    }
    /**
     * 导航到用户设置界面
     * @param context
     */
    public void navigateToUserSetting(Context context){
        context.startActivity(UserSettingActivity.getJumpIntent(context));
    }
    /**
     * 导航到用户账号安全管理界面
     * @param context
     */
    public void navigateToUserAccountSafeActivity(Context context) {
        context.startActivity(UserAccountSafeActivity.getJumpActivity(context));
    }
    /**
     * 导航到用户修改密码界面
     */
    public void navigateToChangePasswordActivity(Context context) {
        context.startActivity(ModifyPasswordActivity.getJumpActivity(context));

    }
    /**
     * 导航到修改手机号界面
     */
    public void navigateToChangePhoneNumberActivity(Context context){
        context.startActivity(UserChangePhoneNumberActivity.getJumpIntent(context));
    }
    /**
     * 导航到关于界面
     */
    public void navigateToAboutActivity(Context context) {
        context.startActivity(AboutActivity.getJumpIntent(context));
    }
    /**
     * 导航意见建议界面
     */
    public void navigateToFeedBackActivity(Context context) {
        context.startActivity(FeedBackActivity.getFeedBackIntent(context));
    }
    /**
     *
     * 导航到添加资源界面
     */
    public void navigateToAddResourceActivity(Context context) {
        context.startActivity(AddResourceActivity.getJumpInten(context));
    }
    /**
     *
     * 导航到添加资源界面
     */
    public void navigateToCaptureActivity(Context context) {
        context.startActivity(CaptureActivity.getJumpIntent(context));
    }
    public void navigateToSelectFileToUploadActivity(Context context) {
        context.startActivity(SelectFileToUploadActivity.getJumpInten(context));
    }
    public void navigatorMyTradeActivity(Context context){
        context.startActivity(MyTradeActivity.getJumpIntent(context));
    }
    /**
     * 跳转到我的求购列表查看我的求购信息
     */
    public void navigatorToMyGoodsActivity(Context context,String type) {
        context.startActivity(MyGoodsActivity.getJumpIntent(context,type));
    }


    /**
     * 跳转到webview
     * @param context
     * @param
     */
    public void navigateToWebView(Context context, String imageLinkUrl,String title) {
        context.startActivity(WebViewActivity.getwebviewIntent(context,imageLinkUrl,title));
    }
    /**
     * 跳转到查看大图界面
     * @param context
     * @param item
     */
    public void navigateToViewBigImageActivity(Context context, ThinkCooPhoto...item) {
        //context.startActivity(ViewBigImageActivity.getJumpIntent(context,item));
    }

    /**
     * 跳转到发布出售商品界面
     * @param context
     */
    public void navigatorToReleaseMySellGoodsActivity(Context context) {
        context.startActivity(ReleaseMySellGoodsActivity.getReleaseMySellGoodsIntent(context));
    }
    /**
     * 跳转到编辑出售商品界面
     * @param context
     * @param myGoods
     */
    public void navigatorToEditMySellGoodsActivity(Context context,MyGoods myGoods) {

    }
    /**
     * 跳转到发布求购界面
     * @param context
     */
    public void navigatorToReleaseMyBuyGoodsActivity(Context context) {

    }
    /**
     * 跳转到编辑求购界面
     * @param context
     * @param myGoods
     */
    public void navigatorToEditMyBuyGoodsActivity(Context context,MyGoods myGoods) {

    }
    /**
     * 跳转到日视图界面
     * @param context
     * @param selectedTime
     */
    public void navigateToDayViewActivity(Context context, Time selectedTime) {
        context.startActivity(DayViewActivity.getJumpIntent(context, selectedTime.toMillis(false)));
        ((Activity)context).overridePendingTransition(R.anim.fade, R.anim.hold);

    }
    /**
     * 跳转到下载管理界面
     * @param context
     */
    public void navigateToDownloadManagerActivity(Context context) {
        context.startActivity(DownloadManagerActivity.getJumpIntent(context));

    }
    public void navigatorToSchoolBaiduLocationSelectActivity(Context context){
        //Select
    }
    /**
     * 跳转到选择学校百度地址界面
     * @param context
     * @param schoolText
     */
    public void navigatorLoadBaiduSchoolAddress(Context context, String schoolText) {
        ((Activity)context).startActivityForResult(LoadSchoolBaiduAddressActivity.getJumpIntent(context,schoolText),LoadSchoolBaiduAddressActivity.REQUEST_CODE);
    }
    public void navigatorToMyCollectionGoodsActivity(Context context) {
        context.startActivity(MyCollectGoodsActivity.getJumpIntent(context));
    }
    /**
     * 跳转到主页 自动登录
     * @param activityContext
     * @param account
     */
    public void navigateToHomeAndAutoLogin(Context activityContext, Account account) {
        activityContext.startActivity(MainActivity.getMainActivityAutoLoginIntent(activityContext,account));
    }
    /**
     * 跳转到登录界面回填
     */
    public void navigateToTrainSearchActivity(Context context, String searchKeyword,Location location) {
        context.startActivity(TrainSearchActivity.getJumpIntent(context,searchKeyword,location));
    }

    public void navigateToTrainAppointmentActivity(Context context) {
        context.startActivity(TrainMyAppointmentActivity.getJumpIntent(context));
    }

    public void navigateToTrainCollectionsActivity(Context context) {
        context.startActivity(TrainMyCollectionActivity.getJumpIntent(context));
    }


    public void navigateToLoginFillAccount(Context activityContext,Account account) {
        activityContext.startActivity(LoginActivity.getLoginIntent(activityContext,account));
    }
    /**
     * 跳转到求购商品详情
     * @param activityContext
     * @param goodsId
     */
    public void navigateToBuyGoodsDetailActivity(Context activityContext,String goodsId){
        activityContext.startActivity(BuyGoodsDetailActivity.getJumpIntent(activityContext, goodsId));
    }
    /**
     * 跳转到出售商品详情
     * @param activityContext
     * @param goodsId
     */
    public void navigateToSellGoodsDetailActivity(Context activityContext,String goodsId){
        activityContext.startActivity(SellGoodsDetailActivity.getJumpIntent(activityContext, goodsId));
    }
    /**
     * 跳转到添加屏蔽公司界面
     */
    public void navigatorToAddShieldCompanyActivity(Context activityContext) {
        activityContext.startActivity(AddShieldCompanyActivity.getJumpActivity(activityContext));
    }
}

