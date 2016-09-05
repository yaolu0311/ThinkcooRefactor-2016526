package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * author ：ml on 2016/7/14
 *                          我的收藏--出售
 */
public class MyCollectionGoodsResponse {


    /**
     * school : 西安医学院
     * picPath : http://www.thinkcoo.com/yingzi-mobile/upload/trade/picture/20160712085515849.jpeg
     * userId : 20188
     * collectionId : 136
     * commodityName : 笔
     * price : 10
     * commodityCategory : 图书/音像
     * publishTime : 2016-07-12 09:02:07.0
     * commodityId : 8807
     */

    private String school;
    private String picPath;
    private String userId;
    private String collectionId;
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

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
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
