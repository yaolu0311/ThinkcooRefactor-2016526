package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.serverresponse.QuerySellInformationResponse;

import java.util.List;

/**
 * Created by Robert.yao on 2016/8/2.
 */
public class QuerySellInformationResponseList {

    private List<QuerySellInformationResponse> list;

    public List<QuerySellInformationResponse> getList() {
        return list;
    }
    public void setList(List<QuerySellInformationResponse> list) {
        this.list = list;
    }
}
