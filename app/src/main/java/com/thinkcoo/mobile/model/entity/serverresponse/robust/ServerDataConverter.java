package com.thinkcoo.mobile.model.entity.serverresponse.robust;

import android.text.TextUtils;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Robert.yao on 2016/6/3.
 */
@Singleton
public class ServerDataConverter {

    public static  final String TAG = "ServerDataConverter";

    @Inject
    public ServerDataConverter(){}

    public int stringToInt(String serverString , int defaultValue){
            if (TextUtils.isEmpty(serverString)) {
                return defaultValue;
            }
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
