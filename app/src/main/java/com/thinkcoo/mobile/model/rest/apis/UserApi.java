package com.thinkcoo.mobile.model.rest.apis;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserBasicInfoResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserEducationStatusDetails;
import com.thinkcoo.mobile.model.entity.serverresponse.UserHarvestDetailResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserHarvestsResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserHobbyResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserJobStatusDetails;
import com.thinkcoo.mobile.model.entity.serverresponse.UserSkillResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserStatusInfoResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserTrainStatusDetails;
import com.thinkcoo.mobile.model.entity.serverresponse.UserWorkExperienceResponse;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public interface UserApi {

    /**
     * @param userId
     * @return
     * @function 用户更换头像
     */
    @POST("uploadheadportrait.json")
    @Multipart
    Observable<BaseResponse> upLoadUserPortraits(@Part("file") RequestBody photo, @Part("userId") RequestBody userId);

    /**
     * @param signature 新签名
     * @param userId
     * @return
     * @function 更新用户签名
     */
    @POST("updatesignature.json")
    Observable<BaseResponse> updateUserSignature(@Query("signature") String signature, @Query("userId") String userId);

    /**
     * 更新用户性别
     * @param sex
     * @param userId
     * @return
     */
    @POST("updatepersonmsg.json")
    Observable<BaseResponse> updateUserSex(@Query("sex") String sex,@Query("userId") String userId);
    /**
     * 更新用户名称
     * @param fullName
     * @param userId
     * @return
     */
    @POST("updatepersonmsg.json")
    Observable<BaseResponse> updateUserName(@Query("fullName") String fullName, @Query("userId") String userId);
    /**
     * @param userId
     * @param certificateType   证件类型
     * @param certificateNumber 证件号码
     * @param nation            民族
     * @param birthDate         出生日期
     * @param personalPhone     手机号码
     * @param mail              邮箱
     * @param politicalStatus   政治面貌
     * @param maritalStatus     婚姻状态
     * @param highestEducation  最高学历
     * @param liveAreaCode
     * @param liveStreet        居住地
     * @param birthPlace
     * @param birthStreet       出生地
     * @return
     * @function 提交用户基本信息
     */
    @POST("updatepersonalinformationv160519.json")
    Observable<BaseResponse> updateUserBaseInfo(@Query("userId") String userId, @Query("certificateType") String certificateType,
                                                @Query("certificateNumber") String certificateNumber, @Query("nation") String nation,
                                                @Query("birthDate") String birthDate, @Query("personalPhone") String personalPhone,
                                                @Query("mail") String mail, @Query("politicalStatus") String politicalStatus,
                                                @Query("maritalStatus") String maritalStatus, @Query("highestEducation") String highestEducation,
                                                @Query("liveAreaCode") String liveAreaCode, @Query("liveStreet") String liveStreet,
                                                @Query("birthPlace") String birthPlace, @Query("birthStreet") String birthStreet);


    /**
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     * @function 网络获取用户所有收获
     */
    @POST("querygrant.json")
    Observable<BaseResponse<UserHarvestsResponse>> loadUserHarvestList(@Query("userId") String userId, @Query("pageIndex") String pageIndex, @Query("pageSize") String pageSize);

    /***
     * @param userId
     * @param honorId
     * @return
     * @function 删除收获
     */
    @POST("delhonor.json")
    Observable<BaseResponse> deleteUserHarvest(@Query("userId") String userId, @Query("honorId") String honorId);

    /**
     * @param userId     *
     * @param grantName  *
     * @param grantUnits *
     * @param grantTime  *
     * @param grantLevel
     * @param ranking
     * @param attachment
     * @return
     * @function 添加新的收获
     * 标记为 * 号的是必填字段
     */
    @POST("addhonor.json")
    Observable<BaseResponse> addUserHarvest(@Query("userId") String userId, @Query("grantName") String grantName, @Query("grantUnits") String grantUnits, @Query("grantTime") String grantTime,
                                            @Query("grantLevel") String grantLevel, @Query("ranking") String ranking, @Query("attachment") String attachment);

    /**
     * @param userId
     * @param filePaths 本地图片路径
     * @return
     * @function 发布收获时添加图片
     */
    @POST("uploadhonor.json")
    Observable<BaseResponse> uploadHarvestPicture(@Query("userId") String userId, @Query("filePaths") String filePaths);

    /**
     * @param userId
     * @param grantId 收获id
     * @return
     * @function 查看收获详情
     */
    @POST("querygrantbyid.json")
    Observable<BaseResponse<UserHarvestDetailResponse>> loadUserHarvestDetail(@Query("userId") String userId, @Query("grantId") String grantId);

    /**删除收获中的一张图片*/
    @POST("delhonorpic.json")
    Observable<BaseResponse> deleteHarvestPicture(@Query("userId") String userId, @Query("picId") String picId);

    /**
     * @function 修改收获信息
     * 标记为 * 号的是必填字段
     * @param userId  *
     * @param grantId  *
     * @param grantName  *
     * @param grantUnits  *
     * @param grantTime  *
     * @param grantLevel
     * @param ranking
     * @param attachment
     * @return
     */
    @POST("updategrant.json")
    Observable<BaseResponse> updateUserHarvest(@Query("userId") String userId, @Query("grantId") String grantId, @Query("grantName") String grantName, @Query("grantUnits") String grantUnits,
                                               @Query("grantTime") String grantTime, @Query("grantLevel") String grantLevel, @Query("ranking") String ranking, @Query("attachment") String attachment);

    @POST("addinvited.json")
    Observable<BaseResponse> inviteFriend(@Query("userId") String userId, @Query("invitedUserId") String invitedUserId);

    //用户基本信息
    @POST("querypersonalinformationv160614.json")
    Observable<BaseResponse<UserBasicInfoResponse>> loadUserBasicInfo(@Query("userId") String userId);

    // 用户状态信息
    @POST("querystatuslist.json")
    Observable<BaseResponse<List<UserStatusInfoResponse>>> loadUserStatusInfo(@Query("userId") String userId);

    // 添加教育状态
    @POST("addstudystatusandupdatecircleversion5.json")
    Observable<BaseResponse> addEducationStatus(@Query("userId") String userId, @Query("userName") String userName,
                                                @Query("statusTime") String statusTime, @Query("schoolName") String schoolName,
                                                @Query("dept") String department, @Query("professional") String professional,
                                                @Query("className") String className, @Query("studentNo") String studentNo,
                                                @Query("positionName") String positionName, @Query("postLevel") String postLevel);

    // 修改教育状态
    @POST("updatestudystatusandupdatecircleversion5.json")
    Observable<BaseResponse> updateEducationStatus(@Query("studyId") String studyId, @Query("userId") String userId, @Query("userName") String userName,
                                                @Query("statusTime") String statusTime, @Query("schoolName") String schoolName,
                                                @Query("dept") String department, @Query("professional") String professional,
                                                @Query("className") String className, @Query("studentNo") String studentNo,
                                                @Query("positionName") String positionName, @Query("postLevel") String postLevel);

    // 添加培训状态  position1Name:培训机构;position4Name:班级
    @POST("addtrainstatusandupdatecircleversion5.json")
    Observable<BaseResponse> addTrainStatus(@Query("userId") String userId, @Query("userName") String userName,
                                            @Query("statusTime") String statusTime,
                                            @Query("position1Name") String position1Name, @Query("position4Name") String position4Name,
                                            @Query("memberId") String memberId, @Query("content") String content);

    // 修改培训状态  position1Name:培训机构;position4Name:班级
    @POST("updatetrainstatusandupdatecircleversion5.json")
    Observable<BaseResponse> updateTrainStatus(@Query("trainId") String trainId, @Query("userId") String userId, @Query("userName") String userName,
                                            @Query("statusTime") String statusTime,
                                            @Query("position1Name") String position1Name, @Query("position4Name") String position4Name,
                                            @Query("memberId") String memberId, @Query("content") String content);

    // 添加全职工作状态
    @POST("addworkfullstatusversion5.json")
    Observable<BaseResponse> addFullTimeJobStatus(@Query("userId") String userId, @Query("userName") String userName,
                                                  @Query("statusTime") String statusTime, @Query("companyName") String companyName,
                                                  @Query("memberId") String memberId, @Query("industry") String industry,
                                                  @Query("dept") String dept, @Query("jobName") String jobName,
                                                  @Query("responsibilities") String responsibilities);

    // 修改全职工作状态
    @POST("updateworkfullstatusversion5.json")
    Observable<BaseResponse> updateFullTimeJobStatus(@Query("workFullId") String workFullId, @Query("userId") String userId, @Query("userName") String userName,
                                                  @Query("statusTime") String statusTime, @Query("companyName") String companyName,
                                                  @Query("memberId") String memberId, @Query("industry") String industry,
                                                  @Query("dept") String dept, @Query("jobName") String jobName,
                                                  @Query("responsibilities") String responsibilities);

    // 添加兼职工作状态
    @POST("addworkpartstatusversion5.json")
    Observable<BaseResponse> addPartTimeJobStatus(@Query("userId") String userId, @Query("userName") String userName,
                                                  @Query("statusTime") String statusTime, @Query("companyName") String companyName,
                                                  @Query("memberId") String memberId, @Query("industry") String industry,
                                                  @Query("dept") String dept, @Query("jobName") String jobName,
                                                  @Query("responsibilities") String responsibilities);

    // 修改兼职工作状态
    @POST("updateworkpartstatusversion5.json")
    Observable<BaseResponse> updatePartTimeJobStatus(@Query("workPartId") String workPartId, @Query("userId") String userId, @Query("userName") String userName,
                                                  @Query("statusTime") String statusTime, @Query("companyName") String companyName,
                                                  @Query("memberId") String memberId, @Query("industry") String industry,
                                                  @Query("dept") String dept, @Query("jobName") String jobName,
                                                  @Query("responsibilities") String responsibilities);

    // 教育状态详情
    @POST("querystudystatusdetails.json")
    Observable<BaseResponse<UserEducationStatusDetails>> loadEducationStatusDetails(@Query("userId") String userId, @Query("studyId") String studyId);

    // 培训状态详情
    @POST("querytrainstatusdetails.json")
    Observable<BaseResponse<UserTrainStatusDetails>> loadTrainStatusDetails(@Query("userId") String userId, @Query("trainId") String trainId);

    // 全职工作状态详情
    @POST("queryworkfullstatusdetails.json")
    Observable<BaseResponse<UserJobStatusDetails>> loadFullTimeJobStatusDetails(@Query("userId") String userId, @Query("workFullId") String workFullId);

    // 兼职工作状态详情
    @POST("queryworkpartstatusdetails.json")
    Observable<BaseResponse<UserJobStatusDetails>> loadPartTimeJobStatusDetails(@Query("userId") String userId, @Query("workPartId") String workPartId);

    // 状态显示开关
    @POST("updatedisplay.json")
    Observable<BaseResponse> statusDisplayToggle(@Query("userId") String userId, @Query("statusId") String statusId, @Query("isDisplay") String isDisplay);

    // 删除状态
    @POST("delstatusbyidversion5.json")
    Observable<BaseResponse> deleteStatusById(@Query("userId") String userId, @Query("userName") String userName, @Query("statusId") String statusId);

    // 我的技能
    @POST("queryskill.json")
    Observable<BaseResponse<List<UserSkillResponse>>> loadUserSkills(@Query("userId") String userId);

    // 添加技能
    @POST("addskill.json")
    Observable<BaseResponse> addSkill(@Query("userId") String userId, @Query("skill") String skill);

    // 删除技能
    @POST("delskill.json")
    Observable<BaseResponse> deleteSkill(@Query("userId") String userId, @Query("skillId") String skillId);

    // 我的爱好
    @POST("queryhobby.json")
    Observable<BaseResponse<List<UserHobbyResponse>>> loadUserHobbies(@Query("userId") String userId);

    // 添加技能
    @POST("addhobby.json")
    Observable<BaseResponse> addHobby(@Query("userId") String userId, @Query("hobby") String hobby);

    // 删除技能
    @POST("delhobby.json")
    Observable<BaseResponse> deleteHobby(@Query("userId") String userId, @Query("hobbyId") String hobbyId);

    // 修改密码
    // TODO 待确定是否User or Account
    @POST("updatenotepwd.json")
    Observable<BaseResponse> alterPassword(@Query("userId") String userId, @Query("pwd") String pwd, @Query("newPwd") String newPwd);

    // 修改账号
    // TODO 待确定是否User or Account
    @POST("updatenoteusername.json")
    Observable<BaseResponse> alterAccoutName(@Query("userId") String userId, @Query("oldUserName") String oldUserName,
                                             @Query("newUserName") String newUserName, @Query("checkCode") String checkCode,
                                             @Query("cert") String cert);

    /**
     * 获取用户工作经验
     * @param userId
     * @param workId
     * @return
     */
    @POST("queryworkexpresslist.json")
    Observable<BaseResponse<List<UserWorkExperienceResponse>>> loadWorkExperienceList(@Query("userId") String userId, @Query("workId") String workId);

    /**
     * 删除工作经验
     * @param userId
     * @param workId
     * @return
     */
    @POST("delworkexpressbyid.json")
    Observable<BaseResponse> deleteWorkExperienceById(@Query("userId") String userId, @Query("expId") String workId);

    /**
     * 新增工作经验
     * @param userId
     * @param workId
     * @param content
     * @param workTime
     * @return
     */
    @POST("addworkexpress.json")
    Observable<BaseResponse> addWorkExperience(@Query("userId") String userId, @Query("workId") String workId, @Query("content") String content,
            @Query("workTime") String workTime);

    /**
     * 更新工作经验
     * @param userId
     * @param expId
     * @param content
     * @param workTime
     * @return
     */
    @POST("updateworkexpress.json")
    Observable<BaseResponse> updateWorkExperience(@Query("userId") String userId, @Query("expId") String expId, @Query("content") String content,
            @Query("workTime") String workTime);


    /**
     * 意见反馈
     * @return
     */
    @POST("addfeedback.json")
    Observable<BaseResponse> feedBack(@Query("userId") String userId, @Query("feedbackContent") String feedbackContent, @Query("phone") String phone,
                                                  @Query("email") String email);


}
