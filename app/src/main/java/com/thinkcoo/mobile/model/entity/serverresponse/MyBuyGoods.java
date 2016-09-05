package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * author ：ml on 2016/7/15
 */
public class MyBuyGoods {

    /**
     * buyId : 9349
     * commodityStatus : 1
     * commodityName : 二手电脑
     * price : 1000
     * browseCnt : 0
     * publishTime : 2016-07-14 17:29:38.0
     */

    private String buyId;
    private String commodityStatus;
    private String commodityName;
    private String price;
    private String browseCnt;
    private String publishTime;

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
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
