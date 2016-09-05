package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.thinkcoo.mobile.model.entity.serverresponse.GoodsSearchResultResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.QueryBuynformationResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.QuerySellInformationResponse;

import java.text.DecimalFormat;

/**
 * Created by Robert.yao on 2016/7/21.
 */
public class Goods implements Parcelable{

    public static byte SELL = 0x0001;
    public static byte BUY = 0x0002;


    public static Goods create(QueryBuynformationResponse queryBuynformationResponse){
        Goods goods = new Goods();
        goods.setId(queryBuynformationResponse.getCommodityId());
        goods.setType(Goods.BUY);
        goods.setCategory(queryBuynformationResponse.getCommodityCategory());
        goods.setDistance(queryBuynformationResponse.getDistance());
        goods.setIconUrl(queryBuynformationResponse.getPicPath());
        goods.setName(queryBuynformationResponse.getCommodityName());
        goods.setPrice(queryBuynformationResponse.getPrice());
        goods.setReleaseDisplayTime(queryBuynformationResponse.getPublishTime());
        goods.setSchoolName(queryBuynformationResponse.getSchool());
        goods.setUseId(queryBuynformationResponse.getUserId());
        return goods;
    }

    public static Goods create(QuerySellInformationResponse querySellInformationResponse){
        Goods goods = new Goods();
        goods.setId(querySellInformationResponse.getCommodityId());
        goods.setType(Goods.SELL);
        goods.setCategory(querySellInformationResponse.getCommodityCategory());
        goods.setDistance(querySellInformationResponse.getDistance());
        goods.setIconUrl(querySellInformationResponse.getPicPath());
        goods.setName(querySellInformationResponse.getCommodityName());
        goods.setPrice(querySellInformationResponse.getPrice());
        goods.setReleaseDisplayTime(querySellInformationResponse.getPublishTime());
        goods.setSchoolName(querySellInformationResponse.getSchool());
        goods.setUseId(querySellInformationResponse.getUserId());
        return goods;
    }

    public static Goods create(GoodsSearchResultResponse goodsSearchResultResponse,int type) {

        Goods goods = new Goods();

        goods.setId(goodsSearchResultResponse.getCommodityId());
        goods.setType(type);
        goods.setCategory(goodsSearchResultResponse.getCommodityCategory());
        goods.setDistance(goodsSearchResultResponse.getDistance());
        goods.setIconUrl(goodsSearchResultResponse.getPicPath());
        goods.setName(goodsSearchResultResponse.getCommodityName());
        goods.setPrice(goodsSearchResultResponse.getPrice());
        goods.setReleaseDisplayTime(goodsSearchResultResponse.getPublishTime());
        goods.setSchoolName(goodsSearchResultResponse.getSchool());
        goods.setUseId(goodsSearchResultResponse.getUserId());

        return goods;
    }

    private String id;
    private String useId;
    private String distance;
    private String category;
    private String name;
    private String schoolName;
    private String releaseDisplayTime;
    private long releaseTime;
    private String iconUrl;
    private String price;
    private int type;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReleaseDisplayTime() {
        return releaseDisplayTime;
    }

    public void setReleaseDisplayTime(String releaseDisplayTime) {
        this.releaseDisplayTime = releaseDisplayTime;
    }
    public long getReleaseTime() {
        return releaseTime;
    }
    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean typeIsBuy() {
        return getType() == BUY;
    }

    public String getDisplayPrice(){
        return "￥"+ makePriceString(getPrice()) +"元";
    }

    public Goods() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.useId);
        dest.writeString(this.distance);
        dest.writeString(this.category);
        dest.writeString(this.name);
        dest.writeString(this.schoolName);
        dest.writeString(this.releaseDisplayTime);
        dest.writeLong(this.releaseTime);
        dest.writeString(this.iconUrl);
        dest.writeString(this.price);
        dest.writeInt(this.type);
    }

    protected Goods(Parcel in) {
        this.id = in.readString();
        this.useId = in.readString();
        this.distance = in.readString();
        this.category = in.readString();
        this.name = in.readString();
        this.schoolName = in.readString();
        this.releaseDisplayTime = in.readString();
        this.releaseTime = in.readLong();
        this.iconUrl = in.readString();
        this.price = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel source) {
            return new Goods(source);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public String makePriceString(String goodsPrice){
        if (goodsPrice != null && !TextUtils.isEmpty(goodsPrice) ) {
            try{
                DecimalFormat myFormatter = new DecimalFormat(
                        "###,###.##");
                String output = myFormatter.format(Double
                        .parseDouble(goodsPrice));
                return output;
            }catch(Exception e){
                return "0";
            }
        }
        return "0";
    }
}
