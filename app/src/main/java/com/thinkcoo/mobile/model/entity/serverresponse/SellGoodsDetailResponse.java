package com.thinkcoo.mobile.model.entity.serverresponse;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by Robert.yao on 2016/8/18.
 */
public class SellGoodsDetailResponse {

    private String commodityCategory;
    private String introduce;
    private String realName;
    private String place;
    private String sellId;
    private String commodityName;
    private String price;
    private String school;
    private String browseCnt;
    private String publishTime;
    private String accountId;
    private String collectId;
    private String quality;
    private String address;


    private boolean isMyGoods;

    private List<CimmodityImgListBean> cimmodityImgList;


    public static class CimmodityImgListBean {
        private String filePath;
        private String imgId;

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }
    }


    public String getCommodityCategory() {
        return commodityCategory;
    }

    public void setCommodityCategory(String commodityCategory) {
        this.commodityCategory = commodityCategory;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<CimmodityImgListBean> getCimmodityImgList() {
        return cimmodityImgList;
    }

    public boolean isMyGoods() {
        return isMyGoods;
    }

    public void setMyGoods(boolean myGoods) {
        isMyGoods = myGoods;
    }

    public void setCimmodityImgList(List<CimmodityImgListBean> cimmodityImgList) {
        this.cimmodityImgList = cimmodityImgList;
    }

    public boolean isCoolected(){
        return !TextUtils.isEmpty(collectId);
    }
}
