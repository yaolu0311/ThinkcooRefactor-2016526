package com.thinkcoo.mobile.model.entity.serverresponse;

import java.util.List;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class SearchShieldCompanyListResponse {

    private List<SearchShieldCompanyResponse> list;

    public List<SearchShieldCompanyResponse> getList() {
        return list;
    }
    public void setList(List<SearchShieldCompanyResponse> list) {
        this.list = list;
    }
}
