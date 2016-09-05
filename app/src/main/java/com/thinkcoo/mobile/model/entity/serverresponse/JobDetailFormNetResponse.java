package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * author ：ml on 2016/7/19
 */
public class JobDetailFormNetResponse {


    /**
     * jobTitle : B2C销售专员
     * salaryRange : 5000-8000元
     * site : 陕西省西安市新城区
     * jobId : 114
     * createTime : 2016-06-26 21:18:33.0
     */

    private String jobTitle;
    private String salaryRange;
    private String site;
    private String jobId;
    private String createTime;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
