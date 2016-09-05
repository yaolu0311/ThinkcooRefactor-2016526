package com.yz.im.model.entity;

/**
 * Created by Administrator on 2016/7/14.
 */
public class IMInitFlag {

    public static final int FLAG_LOGIN = 0x0001;
    public static final int FLAG_LOGIN_SUCCEED = 0x0002;
    public static final int FLAG_LOGIN_FAILURE = 0x0003;
    public static final int FLAG_DATA_LOAD = 0x0004;
    public static final int FLAG_DATA_LOAD_SUCCEED = 0x0005;
    public static final int FLAG_DATA_LOAD_FAILURE = 0x0006;

    private static volatile IMInitFlag instance;

    private int flag = FLAG_LOGIN;
    private IMInitFlag(){};

    public static IMInitFlag getInstance(){
        if (instance == null) {
            synchronized (IMInitFlag.class){
                if (instance == null) {
                    instance = new IMInitFlag();
                }
            }
        }
        return instance;
    }

    public void flagToLogin(){
        synchronized (IMInitFlag.this){
            flag = FLAG_LOGIN;

        }
    }
    public void flagToLoginSucceed(){
        synchronized (IMInitFlag.this){
            flag = FLAG_LOGIN_SUCCEED;

        }
    }
    public void flagToLoginFailure(){
        synchronized (IMInitFlag.this){
            flag = FLAG_LOGIN_FAILURE;

        }
    }
    public void flagToDataLoad(){
        synchronized (IMInitFlag.this){
            flag = FLAG_DATA_LOAD;

        }
    }
    public void flagToDataLoadSucceed(){
        synchronized (IMInitFlag.this){
            flag = FLAG_DATA_LOAD_SUCCEED;

        }
    }
    public void flagToDataLoadFailure(){
        synchronized (IMInitFlag.this){
            flag = FLAG_DATA_LOAD_FAILURE;

        }
    }

    public boolean isLogin(){
        return flag == FLAG_LOGIN;
    }
    public boolean isLoginSucceed(){
        return flag == FLAG_LOGIN_SUCCEED;
    }
    public boolean isLoginFailure(){
        return flag == FLAG_LOGIN_FAILURE;
    }
    public boolean isDataLoad(){
        return flag == FLAG_DATA_LOAD;
    }
    public boolean isDataLoadSucceed(){
        return flag == FLAG_DATA_LOAD_SUCCEED;
    }
    public boolean isDataLoadFailure(){
        return flag == FLAG_DATA_LOAD_FAILURE;
    }

    public boolean allDone() {
        return isDataLoadSucceed();
    }
}
