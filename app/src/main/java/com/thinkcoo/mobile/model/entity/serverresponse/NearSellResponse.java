package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * author ：ml on 2016/7/18
 */
public class NearSellResponse {

    /**
     * school : 西安外事学院
     * picPath : http://www.thinkcoo.com/yingzi-mobile/upload/trade/picture/20160712090132475.jpg
     * userId : 20068
     * distance : 610.4
     * commodityName : 驱蚊水
     * price : 8
     * commodityCategory : 生活用品
     * publishTime : 2016-07-12 09:09:43.0
     * commodityId : 8826
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
