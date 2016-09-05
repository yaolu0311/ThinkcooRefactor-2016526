package com.thinkcoo.mobile.utils;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/3/24.
 */
public class FileUtils {

    @Inject
    public FileUtils(){}

    public static String readTextFromStream(InputStream inputStream) {
        if (inputStream == null) {
            throw new NullPointerException("inputStream cannot be null ");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String buffer ;
            while ((buffer = br.readLine()) != null) {
                sb.append(buffer);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.safeIoClose(br);
        }
        return null;
    }


    public static boolean isFileExist(String path) {
            if (TextUtils.isEmpty(path)) {
                return false;
            }
        try {
            File file = new File(path);
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteFile(String path) {
        return deleteFile(new File(path));
    }

    public static boolean deleteFile(File file) {
        if (file.isDirectory())
            return false;
        return file.delete();
    }

    public static boolean createFile(String path, String fileName) {
            return createFile(path + File.separator +fileName);
    }

    public static boolean createFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
