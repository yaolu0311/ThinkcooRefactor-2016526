package com.thinkcoo.mobile.model.entity.serverresponse;

import java.util.List;

/**
 * Created by Robert.yao on 2016/8/9.
 */
public class MySellGoodsListResponse {

    private List<MySellGoodsResponse> list;

    public List<MySellGoodsResponse> getList() {
        return list;
    }
    public void setList(List<MySellGoodsResponse> list) {
        this.list = list;
    }
    public boolean isEmpty(){
        return null == list || list.isEmpty();
    }
}
