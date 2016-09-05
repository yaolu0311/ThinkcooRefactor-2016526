package com.thinkcoo.mobile.model.rest.apis;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.CompanyDetailsResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.FindJobListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.JobDetailFormNetResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.JobDetailforActivity;
import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectJobListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.PersonBasicInfo;
import com.thinkcoo.mobile.model.entity.serverresponse.PersonJobIntention;
import com.thinkcoo.mobile.model.entity.serverresponse.ProViewResumeResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.QueryRequestJobListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyShieldCompanyListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.SearchShieldCompanyListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.SearchShieldCompanyResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.StudyStatusResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.WorkStatusResponse;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author ：ml on 2016/7/18
 * 找工作
 */
public interface GetJobApi {
    /**
     * 找工作- 我的收藏 查询界面
     */
    @POST("/querycollection.json")
    Observable<BaseResponse<MyCollectJobListResponse>> queryJobcollection(@Query("userId") String userId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    /**
     * 网络获取职位详情 activity
     *
     * @param userId
     * @param jobId  职位ID
     * @return
     */
    @POST("/jobdetails.json")
    Observable<BaseResponse<JobDetailforActivity>> jobdetails(@Query("userId") String userId, @Query("jobId") String jobId);

    /**
     * 网络获取职位详情 activity   添加收藏/添加申请
     *
     * @param userId
     * @param jobId         职位ID
     * @param operationType 操作类型(操作类型2 添加收藏 3职位申请)
     * @return
     */
    @POST("addjoborcollection.json")
    Observable<BaseResponse> addjoborcollection(@Query("userId") String userId, @Query("jobId") String jobId, @Query("operationType") String operationType);

    /**
     * 网络获取职位详情 activity  取消收藏/取消申请
     *
     * @param userId
     * @param jobId
     * @param operationType 操作类型(操作类型 2取消收藏 3取消申请)
     * @return
     */
    @POST("deljoborcollection.json")
    Observable<BaseResponse> deljoborcollection(@Query("userId") String userId, @Query("jobId") String jobId, @Query("operationType") String operationType);

    /**
     * 职位详情activity onResume()中 获取个人基本信息
     *
     * @param userId
     * @return
     */
    @POST("/querypersonalinformation.json")
    Observable<BaseResponse<PersonBasicInfo>> querypersonalinformation(@Query("userId") String userId);

    /**
     * 职位详情activity onResume()中 获取个人求职意向
     *
     * @param userId
     * @return
     */
    @POST("/queryjobintention.json.json")
    Observable<BaseResponse<PersonJobIntention>> queryjobintention(@Query("userId") String userId);

    /**
     * 修改求职意向
     *
     * @param userId
     * @param intentionId    求职意向(也就是jobid)
     * @param jobType        工作类型
     * @param money          薪资
     * @param place          地区code
     * @param industry       行业类型code
     * @param professionType 职业类型
     * @return
     */
    @POST("editjobintention.json")
    Observable<BaseResponse> editjobintention(@Query("userId") String userId, @Query("intentionId") String intentionId, @Query("jobType") String jobType, @Query("place") String place, @Query("money") String money, @Query("industry") String industry, @Query("professionType") String professionType);

    /**
     * 找工作列表
     *
     * @param keyWord     关键字
     * @param site        地区code
     * @param jobCategory 类型code
     * @param industry    行业code
     * @param updateTime  时间code
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @POST("/searchjob.json")
    Observable<BaseResponse<FindJobListResponse>> searchjob(@Query("keyWord") String keyWord, @Query("site") String site, @Query("jobCategory") String jobCategory, @Query("industry") String industry, @Query("updateTime") String updateTime, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    /**
     * 查看屏蔽的公司
     *
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @POST("/queryshield.json")
    Observable<BaseResponse<MyShieldCompanyListResponse>> loadMyShieldCompany(@Query("userId") String userId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    /**
     * 取消屏蔽的公司
     *
     * @param userId
     * @param companyId 公司的ID
     * @return
     */
    @POST("delshield.json")
    Observable<BaseResponse> delshield(@Query("userId") String userId, @Query("companyId") String companyId);

    /**
     * 搜索屏蔽的公司
     *
     * @param userId
     * @param companyName 公司名称
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @POST("/searchcompany.json")
    Observable<BaseResponse<SearchShieldCompanyListResponse>> searchCompany(@Query("userId") String userId, @Query("companyName") String companyName, @Query("pageIndex") String pageIndex, @Query("pageSize") String pageSize);

    /**
     * 添加屏蔽的公司
     *
     * @param userId
     * @param companyId 公司的ID
     * @return
     */
    @POST("addshield.json")
    Observable<BaseResponse> addshield(@Query("userId") String userId, @Query("companyId") String companyId);

    /**
     * 网络详情 查看网络获取公司发布职位列表
     *
     * @param companyId 公司的ID
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @POST("companyjob.json")
    Observable<BaseResponse<List<JobDetailFormNetResponse>>> companyjob(@Query("companyId") String companyId, @Query("pageIndex") String pageIndex, @Query("pageSize") String pageSize);

    /**
     * 网络查看  职位申请列表
     *
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @POST("/queryjobapplication.json")
    Observable<BaseResponse<QueryRequestJobListResponse>> queryRequestJobListResponse(@Query("userId") String userId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    /**
     * 查询学习状态---查询自信模块
     *
     * @param userId
     * @return
     */
    @POST("/querystudyvitae.json")
    Observable<BaseResponse<List<StudyStatusResponse>>> querystudyvitae(@Query("userId") String userId);

    /**
     * 查询工作状态---查询自信模块
     *
     * @param userId
     * @return
     */

    @POST("/queryworkvitae.json")
    Observable<BaseResponse<List<WorkStatusResponse>>> queryworkStatus(@Query("userId") String userId);

    /** 这里还有三个接口  编辑个人简历时候使用 自信接口定义好的
     *
     * 1：  查询获得个人技能列表的网络请求
     * 2：  查询获得个人爱好列表的网络请求
     * 3：  查询获取我的收获列表
     */

    /**
     * 修改性别、身份网络请求
     *
     * @param userId
     * @param fullName 用户姓名
     * @param sex      性别
     * @return
     */
    @POST("updatepersonmsg.json")
    Observable<BaseResponse> updatepersonmsg(@Query("userId") String userId, @Query("fullName") String fullName, @Query("sex") String sex);

    /**
     * 刷新我的简历
     */
    @POST("refreshjob.json")
    Observable<BaseResponse> refreshjob(@Query("userId") String userId);

    /**
     * 预览简历--获取简历的信息
     */
    @POST("queryownresume.json")
    Observable<BaseResponse<ProViewResumeResponse>> queryownresume(@Query("userId") String userId);

    /**
     * @param userId
     * @param isOpen 是否公开简历  0不公开 1公开
     * @return
     */
    @POST("isopenjob.json")
    Observable<BaseResponse> isopenjob(@Query("userId") String userId, @Query("isOpen") String isOpen);

    /**
     * fragment 公司详情网络请求
     *
     * @param companyId 公司的ID
     * @return
     */
    @POST("/companydetails.json")
    Observable<BaseResponse<List<CompanyDetailsResponse>>> companydetails(@Query("companyId") String companyId);


}
