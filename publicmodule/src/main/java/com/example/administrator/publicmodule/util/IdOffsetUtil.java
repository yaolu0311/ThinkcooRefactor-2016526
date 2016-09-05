package com.example.administrator.publicmodule.util;

import android.text.TextUtils;

/**
 * Created by cys on 2016/7/21
 */
public class IdOffsetUtil {

    private static int OFFSET = 100000;

    public static String addOffset(String id){
        if (TextUtils.isEmpty(id)) {
            throw new NullPointerException("id is null when add offset");
        }
        int userId = Integer.valueOf(id) + OFFSET;
        return String.valueOf(userId);
    }

    public static String minusOffset(String id){
        if (TextUtils.isEmpty(id)) {
            throw new NullPointerException("id is null when add minus offset");
        }
        int userId = Integer.valueOf(id) - OFFSET;
        return String.valueOf(userId);
    }
}
