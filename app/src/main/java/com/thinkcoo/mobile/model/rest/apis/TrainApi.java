package com.thinkcoo.mobile.model.rest.apis;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.AppointmentCompanyDetailResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.AppointmentCourseDetailResponese;
import com.thinkcoo.mobile.model.entity.serverresponse.TrainCourseResponse;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author ：ml on 2016/7/19
 * 找培训
 */
public interface TrainApi {
    /**
     *   找培训搜索界面--搜索
     * @param userId
     * @param keyWord  搜索的关键字
     * @param city         placeCode地区
     * @param coursePrice  课程价格
     * @param classType   课程分类
     * @param classTime   开课时间
     * @param pageNow       当前的页数
     * @param pageSize   自己定义返回的条目数
     * @return
     */
    @POST("searchtrain.json")
    Observable<BaseResponse<List<TrainCourseResponse>>> searchTrainCourse(@Query("userId") String userId, @Query("keyWord") String keyWord, @Query("city") String city, @Query("coursePrice") String coursePrice, @Query("classType") String classType, @Query("classTime") String classTime, @Query("pageNow") int pageNow, @Query("pageSize") int pageSize);

    /**
     *   我的收藏
     * @param userId
     * @param pageNow
     * @param pageSize
     * @return
     */
    @POST("querycollect.json")
    Observable<BaseResponse<List<TrainCourseResponse>>> querycollect(@Query("userId") String userId, @Query("pageNow") String pageNow, @Query("pageSize") String pageSize);

    /**
     *   我的预约
     * @param userId
     * @param pageNow
     * @param pageSize
     * @return
     */
    @POST("queryenroll.json")
    Observable<BaseResponse<List<TrainCourseResponse>>> queryenroll(@Query("userId") String userId, @Query("pageNow") String pageNow, @Query("pageSize") String pageSize);


    /**
     *   取消预约- 收藏
     * @param userId
     * @param oId, honorid 看成预约和收藏的ID
     * @return
     */
    @POST("deloperate.json")
    Observable<BaseResponse> deloperate(@Query("userId") String userId,@Query("oId") String oId);

    /**
     *   删除收藏或者预约
     */
    @POST("deloperate.json")
    Observable<BaseResponse> deleteAppointmentOrCollection(@Query("userId") String userId, @Query("oId") String oId);

    /**
     * 查看我的预约课程的详情信息
     * @param userId
     * @param courseId  课程的ID
     * @return
     */
    @POST("querycoursedetails.json")
    Observable<BaseResponse<AppointmentCourseDetailResponese>> querycoursedetails(@Query("userId") String userId, @Query("courseId") String courseId);

    /**
     *   添加收藏或者预约
     * @param userId
     * @param courseId   课程的ID
     * @param type  操作类型(2 添加收藏 3添加预约)
     * @return
     */
    @POST("addoperate.json")
    Observable<BaseResponse> addAppointmentOrCollection(@Query("userId") String userId, @Query("courseId") String courseId,@Query("type") String type);

    /**
     *
     * @param companyId 公司的ID
     * @return
     */
    @POST("companydetails.json")
    Observable<BaseResponse<AppointmentCompanyDetailResponse>> companydetails(@Query("companyId") String companyId);












}
