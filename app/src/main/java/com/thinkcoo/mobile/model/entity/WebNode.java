package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.serverresponse.LoginResponse;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public class WebNode implements Cloneable{


    public static WebNode fromServerResponse(LoginResponse.ModulesBean modulesBean){

        WebNode webNode = new WebNode();

        webNode.setName(modulesBean.getName());
        webNode.setSymbol(modulesBean.getSymbol());
        webNode.setId(modulesBean.getId());
        webNode.setApiUrl(modulesBean.getApiUrl());

        return  webNode;
    }

    private String apiUrl;
    private String symbol;
    private String name;
    private int id;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    protected WebNode clone(){

        WebNode webNode = new WebNode();
        webNode.setName(this.name);
        webNode.setApiUrl(this.apiUrl);
        webNode.setId(this.id);
        webNode.setSymbol(this.symbol);
        return webNode;
    }
}
