package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * author ：ml on 2016/7/19
 */
public class StudyStatusResponse {


    /**
     * logTime : null
     * statusCategory : null
     * vitaeId : 221
     * vitaeTypeId : 1
     * statusTime : 2016-05-12 00:00:00.0
     * position1name : 西北政法大学
     * logContent : null
     * displayConfig : null
     */

    private String logTime;
    private String statusCategory;
    private String logContent;
    private String displayConfig;


    public String getDisplayConfig() {
        return displayConfig;
    }

    public void setDisplayConfig(String displayConfig) {
        this.displayConfig = displayConfig;
    }

    private String vitaeId;
    private String vitaeTypeId;
    private String statusTime;
    private String position1name;


    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getStatusCategory() {
        return statusCategory;
    }

    public void setStatusCategory(String statusCategory) {
        this.statusCategory = statusCategory;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getVitaeId() {
        return vitaeId;
    }

    public void setVitaeId(String vitaeId) {
        this.vitaeId = vitaeId;
    }

    public String getVitaeTypeId() {
        return vitaeTypeId;
    }

    public void setVitaeTypeId(String vitaeTypeId) {
        this.vitaeTypeId = vitaeTypeId;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public String getPosition1name() {
        return position1name;
    }

    public void setPosition1name(String position1name) {
        this.position1name = position1name;
    }


}
