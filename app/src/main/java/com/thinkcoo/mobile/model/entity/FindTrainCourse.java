package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

/**
 * author ：ml on 2016/8/1
 */
public class FindTrainCourse implements Nullable {
    /**
     *  课程列表的bean
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return false;
    }
    private String tid;// 培训课程ID
    private String courseId;
    private String courseName;// 培训课程名称
    private String companyName;// 培训机构名称
    private String companyId;//机构的id
    private String classType;// 培训课程班型
    private String coursePrice;// 培训课程价格
    private String picPath;// 图片地址
    private String isOverdue;//过期与否（1过期/0未过期）

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue;
    }
}
