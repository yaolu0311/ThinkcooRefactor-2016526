package com.thinkcoo.mobile.model.entity;

import android.os.Environment;

/**
 * Created by Robert.yao on 2016/6/3.
 */
public class UserSpace implements Cloneable{

    public static  String DB_DIR_NAME = "tc_db_dir";
    public static String DATA_DATA_DIR_PATH = Environment.getDataDirectory().getAbsolutePath();
    public static String SDCARD_DIR_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 不能在ui线程调用
     */
    public static UserSpace createByUserId(String userId){

        UserSpace userSpace = new UserSpace();

        userSpace.setUserDbDir(createDbDir(userId));
        userSpace.setUserLogDir(createLogDir(userId));
        userSpace.setUserPhotoDir(createPhotoDir(userId));

        return userSpace;
    }

    private static String createPhotoDir(String userId) {
        return "";
    }
    private static String createLogDir(String userId) {
        return "";
    }
    private static String createDbDir(String userId) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(userId);
        stringBuilder.append(".db");

        return stringBuilder.toString();
    }

    public UserSpace(){}

    String userDbDir;
    String userLogDir;
    String userPhotoDir;


    public String getUserPhotoDir() {
        return userPhotoDir;
    }
    public void setUserPhotoDir(String userPhotoDir) {
        this.userPhotoDir = userPhotoDir;
    }
    public String getUserLogDir() {
        return userLogDir;
    }
    public void setUserLogDir(String userLogDir) {
        this.userLogDir = userLogDir;
    }
    public String getUserDbDir() {
        return userDbDir;
    }
    public void setUserDbDir(String userDbDir) {
        this.userDbDir = userDbDir;
    }


    @Override
    protected UserSpace clone() throws CloneNotSupportedException {
        UserSpace userSpace = new UserSpace();
        userSpace.setUserDbDir(this.userDbDir);
        userSpace.setUserLogDir(this.userLogDir);
        userSpace.setUserPhotoDir(this.userPhotoDir);
        return userSpace;
    }
}
