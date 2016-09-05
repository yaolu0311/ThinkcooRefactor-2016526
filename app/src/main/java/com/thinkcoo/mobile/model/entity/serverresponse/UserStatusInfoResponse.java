package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * Created by Leevin
 * CreateTime: 2016/5/27  10:59
 */
public class UserStatusInfoResponse {


    /**
     * sId : 224
     * statuTime : 2016-05-23 00:00:00.0
     * deleteFlag : 1
     * statusCategory : 1
     * logContent : 初中
     * displayConfig : 1
     * name : 山西医科大学
     */

    private String sId;
    private String statuTime;
    private String deleteFlag;
    private String statusCategory;
    private String logContent;
    private String displayConfig;
    private String name;

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public String getStatuTime() {
        return statuTime;
    }

    public void setStatuTime(String statuTime) {
        this.statuTime = statuTime;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public String getDisplayConfig() {
        return displayConfig;
    }

    public void setDisplayConfig(String displayConfig) {
        this.displayConfig = displayConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
