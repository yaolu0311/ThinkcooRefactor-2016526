package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * Created by Administrator on 2016/6/23.
 * 学习者添加课程的信息
 */
public class StudentAddCourseInformation {
    /**
     * courseTimeId : 699  //  课程的ID
     * courseId : 654   // 时段的ID
     */

    private String courseTimeId;
    private String courseId;

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




}
