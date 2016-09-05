package com.thinkcoo.mobile.model.entity.serverresponse;

import com.thinkcoo.mobile.model.entity.FullTimeJobDetail;
import com.thinkcoo.mobile.model.entity.UserStatusDetail;

/**
 * Created by Leevin
 * CreateTime: 2016/5/27  14:29
 */
public class UserJobStatusDetails {

    private String memberId;
    private String sId;
    private String dept;
    private String workName;
    private String statusTime;
    private String companyName;
    private String industry;
    private String responsibilities;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

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

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    @Override
    public String toString() {
        return "UserJobStatusDetails{" +
                "memberId='" + memberId + '\'' +
                ", sId='" + sId + '\'' +
                ", dept='" + dept + '\'' +
                ", workName='" + workName + '\'' +
                ", statusTime='" + statusTime + '\'' +
                ", companyName='" + companyName + '\'' +
                ", industry='" + industry + '\'' +
                ", responsibilities='" + responsibilities + '\'' +
                '}';
    }

    public static UserStatusDetail fromServerResponse(UserJobStatusDetails data) {
        FullTimeJobDetail fullTimeJobDetail = new FullTimeJobDetail();
        if (null == fullTimeJobDetail) {
            return fullTimeJobDetail;
        }
        fullTimeJobDetail.setId(data.getSId());
        fullTimeJobDetail.setBranchName(data.getDept());
        fullTimeJobDetail.setIndustry_direction(data.getIndustry());
        fullTimeJobDetail.setEmployerId(data.getMemberId());
        fullTimeJobDetail.setResponsibility(data.getResponsibilities());
        return fullTimeJobDetail;
    }
}
