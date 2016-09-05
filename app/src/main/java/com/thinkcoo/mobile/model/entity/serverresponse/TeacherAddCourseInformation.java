package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * Created by Administrator on 2016/6/24.
 * 老师添加课程的信息
 */
public class TeacherAddCourseInformation {


    private String courseTimeId;   // 时段id
    private String easemobGroupId;  // 圈子Id(环信系统)
    private String courseId;   // 课程ID
    private String circleId;  //圈子id

    public String getEasemobGroupId() {
        return easemobGroupId;
    }

    public void setEasemobGroupId(String easemobGroupId) {
        this.easemobGroupId = easemobGroupId;
    }

    public String getCourseTimeId() {
        return courseTimeId;
    }

    public void setCourseTimeId(String courseTimeId) {
        this.courseTimeId = courseTimeId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }




}
