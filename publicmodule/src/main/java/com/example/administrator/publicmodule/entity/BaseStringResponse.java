package com.example.administrator.publicmodule.entity;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;


/**
 * Created by Robert.yao on 2016/6/22.
 */
public class BaseStringResponse extends Response{

    public static  final String TAG = "BaseStringResponse";

    private String data;

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public <T extends Object> T getDataObject(Type type , T defaultObject){
        Gson gson = new Gson();
        try{
            return  gson.fromJson(getData(),type);
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
            return defaultObject;
        }
    }
    public <T extends Object> List<T> getDataList(Type type){
        Gson gson = new Gson();
        try{
            return  gson.fromJson(getData(),type);
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
            return Collections.emptyList();
        }
    }
}
