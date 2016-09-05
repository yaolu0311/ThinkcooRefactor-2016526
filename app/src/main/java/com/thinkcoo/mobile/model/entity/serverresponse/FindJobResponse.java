package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * author ：ml on 2016/7/19
 */
public class FindJobResponse {



    /**
     * jobTitle : B2C销售专员
     * salaryRange : 5000-8000元
     * site : 陕西省西安市新城区
     * companyName : 西安浩华亚商贸
     * jobId : 114
     * createTime : 2016-06-26 21:18:33.0
     * companyId : 20155
     */

    private String jobTitle;
    private String salaryRange;
    private String site;
    private String companyName;
    private String jobId;
    private String createTime;
    private String companyId;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
