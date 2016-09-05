package com.yz.im.model.entity.base;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;

/**
 * Created by Robert.yao on 2016/6/3.
 */
public class ServerDataConverter {

    public static  final String TAG = "ServerDataConverter";

    public static volatile ServerDataConverter instance;

    public static ServerDataConverter getInstance(){
        if (instance == null) {
            synchronized (ServerDataConverter.class){
                if (instance == null) {
                    instance = new ServerDataConverter();
                }
            }
        }
        return instance;
    }

    private ServerDataConverter(){}

    public int stringToInt(String serverString , int defaultValue){
        try{
            return Integer.valueOf(serverString);
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
        }
        return defaultValue;
    }

    public long stringToLong(String serverString , long defaultValue){
        try{
            return Long.valueOf(serverString);
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
        }
        return defaultValue;
    }

    public boolean stringToBoolean(String serverString){
        try {
            return serverString.equals("1");
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
            return false;
        }
    }

    public String stringReplace(String target , String regularExpression , String replacement){
        if (null == target){
            return "";
        }
        return target.replaceAll(regularExpression,replacement);
    }

}
