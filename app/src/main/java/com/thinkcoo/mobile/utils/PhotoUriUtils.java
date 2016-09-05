package com.thinkcoo.mobile.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/5.
 */
public class PhotoUriUtils {

    public static final String TAG = "PhotoUriUtils";

    public static final String USER_PHOTO_TEMP_DIR_PATH = Environment.getExternalStorageDirectory() + File.separator + "thinkoo-mobile" + File.separator + "user_photo" + File.separator +"%s.jpg";

    @Inject
    public PhotoUriUtils (){}

    public Uri createUsePhotoUri(){
        File file=new File(String.format(USER_PHOTO_TEMP_DIR_PATH, DateUtils.millisToString3(System.currentTimeMillis())));
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        return Uri.fromFile(file);
    }


    public static String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "thinkcoo");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        return file.getAbsolutePath();
    }



}
