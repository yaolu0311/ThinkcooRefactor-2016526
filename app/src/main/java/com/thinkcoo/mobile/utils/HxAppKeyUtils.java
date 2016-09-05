package com.thinkcoo.mobile.utils;

import android.nfc.tech.IsoDep;

/**
 * Created by Administrator on 2016/6/29.
 */
public class HxAppKeyUtils {

    /**
     * 根据登陆的环境地址动态获得app key
     * @return
     */
    public static String getAppKeyByLoginApiAddress(String loginAddress){

        if (loginAddress.startsWith("http://117.34.109.231/")){
            return "yingzitech#thinkcoo-mobile";
        }else if (loginAddress.startsWith("http://www.thinkcoo.com/")){
            return "yingzitech#thinkcoo-mobile1";
        }else if (loginAddress.startsWith("http://10.21.4.43:8080/")){
            return "yingzitech#thinkcoo-mobile3";
        } else if (loginAddress.startsWith("http://10.21.4.115:8082/")) {
            return "yingzitech#thinkcoo-mobile3";
        } else if (loginAddress.startsWith("http://10.21.4.108:8081/")){
            return "yingzitech#thinkcoo-mobile3";
        }
        throw  new IllegalArgumentException("没有匹配到登陆环境，我找不到环信appkey");
    }

}
