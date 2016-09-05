package com.example.administrator.publicmodule.model.exception;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public class HttpExceptionFactory {


    public final static String SERVER_ERROR = "HTTP 503 Service Unavailable";

    public static Exception create(Throwable throwable){
        return new Exception(SERVER_ERROR);
    }

}
