package com.yz.im.model.db;

import android.content.Context;

import com.example.administrator.publicmodule.model.db.base.BaseDaoImpl;
import com.example.administrator.publicmodule.model.db.base.DbManagerProvider;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.yz.im.model.db.entity.SystemSetting;
import com.yzkj.android.orm.DbManager;

/**
 * Created by cys on 2016/7/19
 */
public class SystemSettingDao extends BaseDaoImpl<SystemSetting>{

    public static volatile SystemSettingDao instance;

    public static SystemSettingDao getInstance(Context context){
        if (instance == null) {
            synchronized (SystemSettingDao.class){
                if (instance == null) {
                    DbManager dbManager = DbManagerProvider.getInstance().getUserDbManager();
                    instance = new SystemSettingDao(dbManager);
                }
            }
        }
        return instance;
    }

    public SystemSettingDao(DbManager dbManager) {
        super(dbManager);
    }

    public void replaceAll(SystemSetting... settings){
        if (settings == null || settings.length == 0 ) {
            return;
        }
        try {
            deleteAll(SystemSetting.class);
            insert(settings);
        } catch (Exception e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
    }
}
