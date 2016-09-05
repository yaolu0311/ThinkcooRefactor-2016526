package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * author ：ml on 2016/7/20
 */
public class TrainCourseResponse {

    /**
     * ptid;// 培训课程ID
     courseId;//
     courseName;// 培训课程名称
     companyName;// 培训机构名称
     companyId;//机构的id
     classType;// 培训课程班型
     coursePrice;// 培训课程价格
     picPath;// 图片地址
     isOverdue;//过期与否（1过期/0未过期）
     */
    /**
     * courseName : 银行考试笔试班
     * coursePrice : 1000
     * classType : 全日制
     * companyName : 西安优格教育
     * picPath : http://www.thinkcoo.com/yingzi-mobile/upload/a/1.jpg
     * tid : 50
     * courseId : 50
     * companyId : 20065
     * isOverdue : 0
     */

    private String courseName;
    private String coursePrice;
    private String classType;
    private String companyName;
    private String picPath;
    private String tid;
    private String courseId;
    private String companyId;
    private String isOverdue;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue;
    }
}
