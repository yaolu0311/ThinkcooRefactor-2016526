package com.thinkcoo.mobile.model.rest.apis;

import com.example.administrator.publicmodule.entity.BaseResponse;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Robert.yao on 2016/6/23.
 * 软课表
 */
public interface TodoApi {

    /**
     * 登录后获取最新的课程信息
     *
     * @param userId
     * @param reqDate // 每周周一的时间
     */
    @POST("courselist.json")
    Observable<BaseResponse> loadCourselist(@Query("userId") String userId, @Query("reqDate") String reqDate);


    /**
     * 学习者添加课程信息
     *
     * @param userId
     * @param teacherName   // 学习者添加课程信息的时候 姓名直接 传“”
     * @param courseName    课程名称
     * @param startTime     上课开始时间
     * @param timeStart     下课结束时间
     * @param weeks         周
     * @param courseNoFirst 开始课程节数
     * @param courseNoLast  结束的节数
     * @param weekNo        单双周
     * @param classRoom     教室
     * @param color         背景颜色
     */
    @POST("addstudycourseandtime.json")
    Observable<BaseResponse> addstudycourseandtime(@Query("userId") String userId, @Query("teacherName") String teacherName, @Query("courseName") String courseName,
                                                   @Query("startTime") String startTime, @Query("timeStart") String timeStart, @Query("timeEnd") String timeEnd,
                                                   @Query("weeks") String weeks, @Query("courseNoFirst") String courseNoFirst, @Query("courseNoLast") String courseNoLast,
                                                   @Query("weekNo") String weekNo, @Query("classRoom") String classRoom, @Query("color") String color);

    /**
     * 学习者修改课程基本信息
     * *
     *
     * @param userId
     * @param teacherName   学习者添加课程信息的时候 姓名直接 传“”
     * @param courseId      课程的Id
     * @param courseName    课程的信息
     * @param startTime
     * @param timeStart     上课开始时间
     * @param timeEnd       下课结束时间  //
     * @param timeId        课程时段id
     * @param weeks         周
     * @param courseNoFirst 开始课程节数
     * @param courseNoLast  结束的节数
     * @param weekNo        单双周
     * @param classRoom     教室
     * @param color         背景颜色
     */
    @POST("updatestudycourseandtime.json")
    Observable<BaseResponse> updatestudycourseandtime(@Query("userId") String userId, @Query("courseId") String courseId, @Query("teacherName") String teacherName,
                                                      @Query("courseName") String courseName, @Query("startTime") String startTime, @Query("timeStart") String timeStart, @Query("timeEnd") String timeEnd,
                                                      @Query("timeId") String timeId, @Query("weeks") String weeks, @Query("courseNoFirst") String courseNoFirst, @Query("courseNoLast") String courseNoLast,
                                                      @Query("weekNo") String weekNo, @Query("classRoom") String classRoom, @Query("color") String color);

    /**
     * 学习者添加更多的时段的课程信息
     *
     * @param userId
     * @param courseId      课程的Id
     * @param courseTimeId  时段的ID   添加更多时段课程信息 传空  修改时段信息的时候 传courseTimeId
     * @param timeStart     上课开始时间
     * @param timeEnd       下课结束时间
     * @param weeks         周
     * @param courseNoFirst 开始课程节数
     * @param courseNoLast  开始课程节数
     * @param weekNo        单双周
     * @param classRoom     教室
     */
    @POST("editcoursetime.json")
    Observable<BaseResponse> editcoursetime(@Query("userId") String userId, @Query("courseId") String courseId, @Query("courseTimeId") String courseTimeId,
                                            @Query("startTime") String startTime, @Query("timeStart") String timeStart, @Query("timeEnd") String timeEnd, @Query("weeks") String weeks,
                                            @Query("courseNoFirst") String courseNoFirst, @Query("courseNoLast") String courseNoLast, @Query("weekNo") String weekNo, @Query("classRoom") String classRoom);

    /**
     * 授课者   添加课程信息
     *
     * @param userId=10032,
     * @param statusId=73,                                                           学校的ID
     * @param courseName=数学建模                                                        课程名字
     * @param startTime=2016-06-23                                                   // 开课时间
     * @param classGroup=1602,                                                       // 班组
     * @param timeStart=15:30,                                                       上课的开始时间
     * @param timeEnd=16:00                                                          下课结束时间
     * @param weeks=4,                                                               周
     * @param courseNoFirst=6,                                                       开始课程节数
     * @param courseNoLast=7,                                                        开始课程节数
     * @param weekNo=1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24, 单双周 或者全选
     * @param classRoom=301                                                          教室
     * @param color=91c8f6                                                           颜色
     */
    @POST("addteachercourse.json")
    Observable<BaseResponse> addteachercourse(@Query("userId") String userId, @Query("statusId") String statusId, @Query("courseName") String courseName,
                                              @Query("startTime") String startTime, @Query("classGroup") String classGroup, @Query("timeStart") String timeStart,
                                              @Query("timeEnd") String timeEnd, @Query("weeks") String weeks, @Query("courseNoFirst") String courseNoFirst, @Query("courseNoLast") String courseNoLast,
                                              @Query("weekNo") String weekNo, @Query("classRoom") String classRoom, @Query("color") String color);

    /** 授课程者 添加时段或者修改课程信息
     * @POST("editcoursetime.json") 和学习者添加的更多的授课信息一致
     *
     */

    /**
     * 课程详情
     *
     * @param userId
     * @param courseId 课程的ID
     */
    @POST("querycoursebyid.json")
    Observable<BaseResponse> querycoursebyid(@Query("userId") String userId, @Query("courseId") String courseId);

    /**
     * 删除时段
     *
     * @param userId
     * @param courseTimeId 时段ID
     */
    @POST("delcoursetimebyid.json")
    Observable<BaseResponse> delcoursetimebyid(@Query("userId") String userId, @Query("courseTimeId") String courseTimeId);

    /**
     * 授课者修改课程的基本信息
     *
     * @param userId=20022,
     * @param courseId=4224,                                                         courseId
     * @param statusId=221,                                                          学校的ID
     * @param courseName=Android,                                                    课程名字
     * @param startTime=2016-06-28,                                                  上课的开始日期
     * @param classGroup=0627,0628,                                                  班组的名称
     * @param color=f895a4,                                                          颜色
     * @param timeId=4325,                                                           课程时段id
     * @param timeStart=08:30,                                                       上课的开始时间
     * @param timeEnd=09:00,                                                         结束时间
     * @param weeks=2,                                                               周
     * @param courseNoFirst=5,                                                       上课开始节数
     * @param courseNoLast=6,                                                        结束节数
     * @param weekNo=1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24, 单双周 全选
     * @param classRoom=101                                                          教室
     */
    @POST("updateteachcourseandtime.json")
    Observable<BaseResponse> updateteachcourseandtime(@Query("userId") String userId, @Query("courseId") String courseId, @Query("statusId") String statusId, @Query("courseName") String courseName, @Query("startTime") String startTime,
                                                      @Query("classGroup") String classGroup, @Query("color") String color, @Query("timeId") String timeId, @Query("timeStart") String timeStart, @Query("timeEnd") String timeEnd, @Query("weeks") String weeks,
                                                      @Query("courseNoFirst") String courseNoFirst, @Query("courseNoLast") String courseNoLast,
                                                      @Query("weekNo") String weekNo, @Query("classRoom") String classRoom);


}
