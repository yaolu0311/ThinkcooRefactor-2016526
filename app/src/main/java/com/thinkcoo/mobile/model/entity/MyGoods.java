package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.thinkcoo.mobile.model.entity.serverresponse.MyBuyGoodsListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyBuyGoodsResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MySellGoodsListResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MySellGoodsResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Robert.yao on 2016/8/9.
 */
public class MyGoods extends Goods implements Parcelable{

    private final static String PUTAWAYING = "1";

    public static List<MyGoods> createList(MyBuyGoodsListResponse myBuyGoodsListResponse, ServerDataConverter serverDataConverter){
        if (null == myBuyGoodsListResponse || null == myBuyGoodsListResponse.getList() || myBuyGoodsListResponse.getList().isEmpty()){
            return Collections.emptyList();
        }
        List<MyBuyGoodsResponse> list = myBuyGoodsListResponse.getList();
        List<MyGoods> result = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {

            MyBuyGoodsResponse myBuyGoodsResponse = list.get(i);
            MyGoods myGoods = new MyGoods();
            myGoods.setReleaseDisplayTime(myBuyGoodsResponse.getPublishTime());
            myGoods.setId(myBuyGoodsResponse.getBuyId());
            myGoods.setBrowseCount(serverDataConverter.stringToInt(myBuyGoodsResponse.getBrowseCnt(),0));
            myGoods.setSchoolName(myBuyGoodsResponse.getCommodityName());
            myGoods.setStatus(myBuyGoodsResponse.getCommodityStatus());
            myGoods.setPrice(myBuyGoodsResponse.getPrice());
            result.add(myGoods);
        }
        return result;
    }
    public static List<MyGoods> createList(MySellGoodsListResponse mySellGoodsListResponse, ServerDataConverter serverDataConverter){
        if (null == mySellGoodsListResponse || null == mySellGoodsListResponse.getList() || mySellGoodsListResponse.getList().isEmpty()){
            return Collections.emptyList();
        }
        List<MySellGoodsResponse> list = mySellGoodsListResponse.getList();
        List<MyGoods> result = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            MySellGoodsResponse mySellGoodsResponse = list.get(i);
            MyGoods myGoods = new MyGoods();
            myGoods.setReleaseDisplayTime(mySellGoodsResponse.getPublishTime());
            myGoods.setId(mySellGoodsResponse.getOnSaleId());
            myGoods.setBrowseCount(serverDataConverter.stringToInt(mySellGoodsResponse.getBrowseCnt(),0));
            myGoods.setSchoolName(mySellGoodsResponse.getCommodityName());
            myGoods.setStatus(mySellGoodsResponse.getCommodityStatus());
            myGoods.setPrice(mySellGoodsResponse.getPrice());
            myGoods.setIconUrl(mySellGoodsResponse.getPicPath());
            result.add(myGoods);
        }
        return result;
    }

    private String status;
    private int browseCount;
    private String detail;

    private MyGoodsDetail myGoodsDetail;


    @Override
    public void setPrice(String price) {
        if (TextUtils.isEmpty(price)){
            return;
        }
        if (price.startsWith("￥") && price.endsWith("元")){
            setPrice(price.replaceAll("￥","").replaceAll("元","") );
        }else {
            super.setPrice(price);
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }



    public MyGoodsDetail getMyGoodsDetail() {
        return myGoodsDetail;
    }

    public void setMyGoodsDetail(MyGoodsDetail myGoodsDetail) {
        this.myGoodsDetail = myGoodsDetail;
    }

    public boolean isPutawaying() {
        return PUTAWAYING.equals(status);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.status);
        dest.writeInt(this.browseCount);
        dest.writeString(this.detail);
        dest.writeParcelable(this.myGoodsDetail, flags);
    }

    public MyGoods() {
    }

    protected MyGoods(Parcel in) {
        super(in);
        this.status = in.readString();
        this.browseCount = in.readInt();
        this.detail = in.readString();
        this.myGoodsDetail = in.readParcelable(MyGoodsDetail.class.getClassLoader());
    }

    public static final Creator<MyGoods> CREATOR = new Creator<MyGoods>() {
        @Override
        public MyGoods createFromParcel(Parcel source) {
            return new MyGoods(source);
        }

        @Override
        public MyGoods[] newArray(int size) {
            return new MyGoods[size];
        }
    };
}
