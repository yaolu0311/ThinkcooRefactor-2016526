package com.yz.im.model.db;

import android.content.Context;

import com.example.administrator.publicmodule.model.db.base.BaseDaoImpl;
import com.example.administrator.publicmodule.model.db.base.DbManagerProvider;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.yz.im.model.db.entity.NoticeMessageDb;
import com.yz.im.model.db.entity.SystemSetting;
import com.yzkj.android.orm.DbManager;

/**
 * Created by cys on 2016/7/19
 */
public class NoticeMessageDao extends BaseDaoImpl<NoticeMessageDb>{

    public static volatile NoticeMessageDao instance;

    public static NoticeMessageDao getInstance(Context context){
        if (instance == null) {
            synchronized (NoticeMessageDao.class){
                if (instance == null) {
                    DbManager dbManager = DbManagerProvider.getInstance().getUserDbManager();
                    instance = new NoticeMessageDao(dbManager);
                }
            }
        }
        return instance;
    }

    public NoticeMessageDao(DbManager dbManager) {
        super(dbManager);
    }

    public void replaceAll(NoticeMessageDb... settings){
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
