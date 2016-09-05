package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectionGoodsResponse;
import java.util.List;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public class MyCollectionGoodsListResponse {

    private List<MyCollectionGoodsResponse> list;

    public List<MyCollectionGoodsResponse> getList() {
        return list;
    }
    public void setList(List<MyCollectionGoodsResponse> list) {
        this.list = list;
    }
}
