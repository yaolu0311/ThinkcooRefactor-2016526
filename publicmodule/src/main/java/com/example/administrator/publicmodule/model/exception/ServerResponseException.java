package com.example.administrator.publicmodule.model.exception;


import android.text.TextUtils;

/**
 * Created by admin on 2016/5/24.
 */
public class ServerResponseException  extends Exception{

    public static ServerResponseException createByResponseMsg(String msg ){
        if (TextUtils.isEmpty(msg)){
            msg = "未知响应消息";
        }
        return  new ServerResponseException(msg);
    }
    public ServerResponseException(String detailMessage) {
        super(detailMessage);
    }
}
