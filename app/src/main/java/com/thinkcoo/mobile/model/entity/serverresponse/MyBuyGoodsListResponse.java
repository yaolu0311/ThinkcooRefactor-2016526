package com.thinkcoo.mobile.model.entity.serverresponse;

import java.util.List;

/**
 * Created by Robert.yao on 2016/8/9.
 */
public class MyBuyGoodsListResponse {

    private List<MyBuyGoodsResponse> list;

    public List<MyBuyGoodsResponse> getList() {
        return list;
    }
    public void setList(List<MyBuyGoodsResponse> list) {
        this.list = list;
    }
    public boolean isEmpty(){
        return null == list || list.isEmpty();
    }
}
