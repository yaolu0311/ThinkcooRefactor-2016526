package com.thinkcoo.mobile.model.entity.serverresponse;

import android.text.TextUtils;

/**
 * Created by Robert.yao on 2016/8/18.
 */
public class BuyGoodsDetailResponse {


    private String userId;
    private String commodityCategory;
    private String realName;
    private String commodityDetails;
    private String place;
    private String commodityName;
    private String price;
    private String school;
    private String buyId;
    private String browseCnt;
    private String publishTime;
    private String collectId;
    private String address;

    private boolean isMyGoods;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommodityCategory() {
        return commodityCategory;
    }

    public void setCommodityCategory(String commodityCategory) {
        this.commodityCategory = commodityCategory;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCommodityDetails() {
        return commodityDetails;
    }

    public void setCommodityDetails(String commodityDetails) {
        this.commodityDetails = commodityDetails;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public String getBrowseCnt() {
        return browseCnt;
    }

    public void setBrowseCnt(String browseCnt) {
        this.browseCnt = browseCnt;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isMyGoods() {
        return isMyGoods;
    }
    public void setMyGoods(boolean myGoods) {
        isMyGoods = myGoods;
    }

    public boolean isCoolected(){
        return !TextUtils.isEmpty(collectId);
    }


}
