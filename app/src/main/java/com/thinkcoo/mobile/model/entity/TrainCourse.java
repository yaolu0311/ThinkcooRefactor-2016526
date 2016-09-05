package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.serverresponse.TrainCourseResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/8/18  13:36
 */
public class TrainCourse {

    public static List<TrainCourse> fromServerResponse(List<TrainCourseResponse> serverData) {
        if (serverData == null || serverData.size() == 0) {
            return Collections.emptyList();
        }
        List<TrainCourse> localData = new ArrayList<>();
        for (TrainCourseResponse trainCourseResponse : serverData) {
            localData.add(createTrainCourse(trainCourseResponse));
        }
        return localData;
    }

    public static TrainCourse createTrainCourse(TrainCourseResponse trainCourseResponse) {
        TrainCourse trainCourse = new TrainCourse();
        trainCourse.settId(trainCourseResponse.getTid());
        trainCourse.setCourseId(trainCourseResponse.getCourseId());
        trainCourse.setCourseName(trainCourseResponse.getCourseName());
        trainCourse.setCompanyName(trainCourseResponse.getCompanyName());
        trainCourse.setCompanyId(trainCourseResponse.getCompanyId());
        trainCourse.setCourseType(trainCourseResponse.getClassType());
        trainCourse.setCoursePrice(trainCourseResponse.getCoursePrice());
        trainCourse.setPicPath(trainCourseResponse.getPicPath());
        trainCourse.setOverdue("1".equals(trainCourseResponse.getIsOverdue()));
        return trainCourse;
    }

    private String tId;// 1.0 遗留,暂时好像没用
    private String courseId;//培训课程名称
    private String courseName;// 培训课程名称
    private String companyName;// 培训机构名称
    private String companyId;//机构的id
    private String courseType;// 培训课程班型
    private String coursePrice;// 培训课程价格
    private String picPath;// 图片地址
    private boolean isOverdue;//过期与否（1过期/0未过期）

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

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
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

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

}
