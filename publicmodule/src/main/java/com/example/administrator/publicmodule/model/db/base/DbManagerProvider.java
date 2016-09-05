package com.example.administrator.publicmodule.model.db.base;

import android.os.Environment;
import android.text.TextUtils;

import com.yzkj.android.orm.DbManager;
import com.yzkj.android.orm.x;

import java.io.File;


/**
 * 该类负责配置数据库实体，以及创建DbManager
 * Created by robert on 2016/5/21.
 */
public class DbManagerProvider {

    public static final String DD_DB_FILE_DIR = Environment.getDataDirectory().getAbsolutePath() + File.separator +"data" + File.separator + "com.thinkcoo.mobile" +  File.separator  + "databases" + File.separator ;
    public static final String DD_DB_NAME = "external.db";

    public static  final int PUBLIC_DB_VERSION_CODE = 1;
    public static  final int USER_DB_VERSION = 1;
    public static  final String USER_DB_NAME = "tc_user_db.db";
    public static  final String PUBLIC_DB_NAME = "tc_public_db.db";
    public static  final String PUBLIC_DB_DIR_PATH = Environment.getDataDirectory().getAbsolutePath() + File.separator + "tc_db_dir" + File.separator;

    private static DbManagerProvider instance;

    private DbManager mDataDictionaryDbManager;
    private DbManager mPublicDbManager;
    private DbManager mUserDbManager;


    private String mUserDbName; //生成用户数据库

    public static DbManagerProvider getInstance(){
        if (instance == null) {
           instance = new DbManagerProvider();
        }
        return instance;
    }

    private DbManagerProvider() {

    }

    public DbManager getDataDictionaryDbManager() {
        createDataDictionaryDbManager();
        return mDataDictionaryDbManager;
    }

    public DbManager getPublicDbManager() {

        if (null == mPublicDbManager){
            createPublicDbManager();
        }
        return mPublicDbManager;
    }

    public DbManager getUserDbManager() {
        checkUserDbDir();
        if (null == mUserDbManager || dirIsChanged()){
            createUserDbManager();
        }
        return mUserDbManager;
    }

    public void setUserDbName(String userDbName) {
        mUserDbName = userDbName;
    }

    private void checkUserDbDir() {
        if (TextUtils.isEmpty(mUserDbName)){
            throw  new IllegalArgumentException("You must be set userDbDir");
        }
    }

    private void createDataDictionaryDbManager() {

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
        daoConfig.setAllowTransaction(true);
        daoConfig.setDbName(DD_DB_NAME);
        daoConfig.setDbVersion(1);
        mDataDictionaryDbManager = x.getDb(daoConfig);

        //TODO 数据改变

    }
    private void createPublicDbManager() {

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbVersion(PUBLIC_DB_VERSION_CODE);
        daoConfig.setDbName(PUBLIC_DB_NAME);
        daoConfig.setAllowTransaction(true);
        mPublicDbManager = x.getDb(daoConfig);
        //TODO daoConfig.setDbUpgradeListener();

    }
    private void createUserDbManager() {

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbVersion(USER_DB_VERSION);
        daoConfig.setDbName(mUserDbName);
        daoConfig.setAllowTransaction(true);
        mUserDbManager = x.getDb(daoConfig);

        //TODO 数据改变

    }
    private boolean dirIsChanged() {
        String nowUserDbName = mUserDbManager.getDaoConfig().getDbName();
        return !mUserDbName.equals(nowUserDbName);
    }

}
