package com.thinkcoo.mobile.model.entity.serverresponse;

import com.thinkcoo.mobile.model.entity.EducationDetail;
import com.thinkcoo.mobile.model.entity.UserStatusDetail;

/**
 * Created by Leevin
 * CreateTime: 2016/5/27  14:27
 */
public class UserEducationStatusDetails {

    private String sId;
    private String dept;
    private String professional;
    private String studentNo;
    private String postName;
    private String statusTime;
    private String postLevel;
    private String name;
    private String className;

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public String getPostLevel() {
        return postLevel;
    }

    public void setPostLevel(String postLevel) {
        this.postLevel = postLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "UserEducationStatusDetails{" +
                "sId='" + sId + '\'' +
                ", dept='" + dept + '\'' +
                ", professional='" + professional + '\'' +
                ", studentNo='" + studentNo + '\'' +
                ", postName='" + postName + '\'' +
                ", statusTime='" + statusTime + '\'' +
                ", postLevel='" + postLevel + '\'' +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                '}';
    }

    public static UserStatusDetail fromServerResponse(UserEducationStatusDetails data) {
        EducationDetail educationDetail = new EducationDetail();
        if (null == data) {
            return educationDetail;
        }
        educationDetail.setId(data.getSId());
        educationDetail.setPostName(data.getPostName());
        educationDetail.setClassNumber(data.getClassName());
        educationDetail.setStudentId(data.getStudentNo());
        educationDetail.setMajor(data.getProfessional());
        educationDetail.setDepartment(data.getDept());
        return educationDetail;
    }

}
