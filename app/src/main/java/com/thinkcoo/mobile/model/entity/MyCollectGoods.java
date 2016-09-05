package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectionGoodsResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public class MyCollectGoods extends Goods implements Parcelable{

    private String collectionId;


    public static List<MyCollectGoods> createMyCollectGoodsList(MyCollectionGoodsListResponse myCollectionGoodsListResponse){

        List<MyCollectionGoodsResponse> myCollectionGoodsResponseList = myCollectionGoodsListResponse.getList();
        List<MyCollectGoods> myCollectGoodsList = new ArrayList<>(myCollectionGoodsResponseList.size());
        for (int i = 0; i < myCollectionGoodsResponseList.size(); i++) {
            MyCollectionGoodsResponse myCollectionGoodsResponse = myCollectionGoodsResponseList.get(i);
            MyCollectGoods myCollectGoods = new MyCollectGoods();
            myCollectGoods.setSchoolName(myCollectionGoodsResponse.getSchool());
            myCollectGoods.setIconUrl(myCollectionGoodsResponse.getPicPath());
            myCollectGoods.setUseId(myCollectionGoodsResponse.getUserId());
            myCollectGoods.setCollectionId(myCollectionGoodsResponse.getCollectionId());
            myCollectGoods.setName(myCollectionGoodsResponse.getCommodityName());
            myCollectGoods.setCategory(myCollectionGoodsResponse.getCommodityCategory());
            myCollectGoods.setReleaseDisplayTime(myCollectionGoodsResponse.getPublishTime());
            myCollectGoods.setId(myCollectionGoodsResponse.getCommodityId());
            myCollectGoodsList.add(myCollectGoods);
        }
        return myCollectGoodsList;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.collectionId);
    }

    public MyCollectGoods() {
    }

    protected MyCollectGoods(Parcel in) {
        super(in);
        this.collectionId = in.readString();
    }

    public static final Creator<MyCollectGoods> CREATOR = new Creator<MyCollectGoods>() {
        @Override
        public MyCollectGoods createFromParcel(Parcel source) {
            return new MyCollectGoods(source);
        }

        @Override
        public MyCollectGoods[] newArray(int size) {
            return new MyCollectGoods[size];
        }
    };
}
