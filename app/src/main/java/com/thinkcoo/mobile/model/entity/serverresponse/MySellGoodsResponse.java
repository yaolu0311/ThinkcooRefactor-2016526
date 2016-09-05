package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * Created by Robert.yao on 2016/8/9.
 */
public class MySellGoodsResponse {

    private String picPath;
    private String onSaleId;
    private String commodityStatus;
    private String commodityName;
    private String price;
    private String browseCnt;
    private String publishTime;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getOnSaleId() {
        return onSaleId;
    }

    public void setOnSaleId(String onSaleId) {
        this.onSaleId = onSaleId;
    }

    public String getCommodityStatus() {
        return commodityStatus;
    }

    public void setCommodityStatus(String commodityStatus) {
        this.commodityStatus = commodityStatus;
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
}
