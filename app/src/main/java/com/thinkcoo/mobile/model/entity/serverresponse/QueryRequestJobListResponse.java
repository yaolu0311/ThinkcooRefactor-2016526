package com.thinkcoo.mobile.model.entity.serverresponse;

import java.util.List;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class QueryRequestJobListResponse {

    private List<QueryRequestJobResponse> list;

    public List<QueryRequestJobResponse> getList() {
        return list;
    }

    public void setList(List<QueryRequestJobResponse> list) {
        this.list = list;
    }
}
