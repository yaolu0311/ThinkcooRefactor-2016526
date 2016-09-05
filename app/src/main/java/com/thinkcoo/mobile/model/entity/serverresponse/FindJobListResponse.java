package com.thinkcoo.mobile.model.entity.serverresponse;

import java.util.List;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class FindJobListResponse {

    private List<FindJobResponse> list;

    public List<FindJobResponse> getList() {
        return list;
    }
    public void setList(List<FindJobResponse> list) {
        this.list = list;
    }
}
