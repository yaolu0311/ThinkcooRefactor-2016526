package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * author ：ml on 2016/7/15
 */
public class NearBuyResponse {

    /**
     * school : 西安外事学院
     * picPath : null
     * userId : 20068
     * distance : 604.62
     * commodityName : 登山包
     * price : 200
     * commodityCategory : 文体户外
     * publishTime : 2016-07-12 09:10:57.0
     * commodityId : 8488
     */

    private String school;
    private String picPath;
    private String userId;
    private String distance;
    private String commodityName;
    private String price;
    private String commodityCategory;
    private String publishTime;
    private String commodityId;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCommodityCategory() {
        return commodityCategory;
    }

    public void setCommodityCategory(String commodityCategory) {
        this.commodityCategory = commodityCategory;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }
}
