package com.thinkcoo.mobile.model.entity.serverresponse;

import java.util.List;

/**
 * author ：ml on 2016/7/14
 */
public class LookGoodsDetailResponse {

    /**
     * latitude : 34.039385
     * longitude : 108.773114
     * place : 西安市
     * introduce : 有需要者，速联系
     * realName : 高军
     * school : 西北工业大学
     * accountId : 20044
     * headPortrait : http://www.thinkcoo.com/yingzi-mobile/upload/personal/headportrait/20160623090442156.jpg
     * commodityName : 一对对讲机
     * price : 150
     * commodityCategory : 3
     * browseCnt : 15
     * publishTime : 2016-07-13 09:02:40.0
     * collectId : null
     * cimmodityImgList : [{"filePath":"http://www.thinkcoo.com/yingzi-mobile/upload/trade/picture/20160713085550768.jpg","imgId":"9819"}]
     * sellId : 9146
     * address : 陕西省西安长安区东祥路1号
     * quality : 9成新
     */

    /**
     * filePath : http://www.thinkcoo.com/yingzi-mobile/upload/trade/picture/20160713085550768.jpg
     * imgId : 9819
     */


    private String latitude;
    private String longitude;
    private String place;
    private String introduce;
    private String realName;
    private String school;
    private String accountId;
    private String headPortrait;
    private String commodityName;
    private String price;
    private String commodityCategory;
    private String browseCnt;
    private String publishTime;
    private Object collectId;
    private String sellId;
    private String address;
    private String quality;

    private List<CimmodityImgListBean> cimmodityImgList;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
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

    public Object getCollectId() {
        return collectId;
    }

    public void setCollectId(Object collectId) {
        this.collectId = collectId;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public List<CimmodityImgListBean> getCimmodityImgList() {
        return cimmodityImgList;
    }

    public void setCimmodityImgList(List<CimmodityImgListBean> cimmodityImgList) {
        this.cimmodityImgList = cimmodityImgList;
    }

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
}
