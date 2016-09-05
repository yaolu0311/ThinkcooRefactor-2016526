package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * author ：ml on 2016/7/20
 */
public class CooperationCollectionResponse {


    /**
     * companyName : 西安影子信息科技有限责任公司
     * projectId : 6
     * projectName : 影子科技大学生创新基金
     * budget : 5000~8000
     * releaseTime : 2016-07-20 16:42:23.0
     * collectId : 56
     * companyId : 20042
     * isOverdue : 0
     */

    private String companyName;
    private int projectId;
    private String projectName;
    private String budget;
    private String releaseTime;
    private String collectId;
    private int companyId;
    private String isOverdue;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue;
    }
}
