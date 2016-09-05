package com.yz.im.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

/**
 * ThinkCoo20160718
 * Created by cys on 2016/8/7 0007
 */
public class ImageUtil {

    public static String createImagePath(Context context, String uri) {
        String image_path = context.getFilesDir() + File.separator;
        String name = getImageName(uri);
        return image_path + name;
    }

    private static String getImageName(String uri) {
        if (TextUtils.isEmpty(uri)) {
            return "";
        }
        return uri.substring(uri.lastIndexOf(File.separator) + 1, uri.length());
    }
}
