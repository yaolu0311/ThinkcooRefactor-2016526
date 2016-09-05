package com.example.administrator.publicmodule.model.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.HashMap;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public class WebNodeMap extends HashMap<String,String>{

    private static final String KEY_WEB_NODE_MAP = "WEB_NODE_MAP";

    SharedPreferences mSharedPreferences;

    public WebNodeMap(Context context) {
        mSharedPreferences = context.getSharedPreferences(KEY_WEB_NODE_MAP,Context.MODE_APPEND);
    }
    @Override
    public String put(String key, String value) {
        mSharedPreferences.edit().putString(key,value).commit();
        return super.put(key, value);
    }
    @Override
    public String get(Object key) {
        String result = super.get(key);
        if (TextUtils.isEmpty(result)){
            result = mSharedPreferences.getString((String)key,"");
        }
        return result;
    }
}
