package com.yz.im.model.db;

import android.content.Context;

import com.example.administrator.publicmodule.model.db.base.BaseDaoImpl;
import com.example.administrator.publicmodule.model.db.base.DbManagerProvider;
import com.yz.im.model.db.entity.Shield;
import com.yzkj.android.orm.DbManager;

/**
 * Created by cys on 2016/7/19
 */
public class ShieldDao extends BaseDaoImpl<Shield>{

    public static volatile ShieldDao instance;

    public static ShieldDao getInstance(Context context){
        if (instance == null) {
            synchronized (ShieldDao.class){
                if (instance == null) {
                    DbManager dbManager = DbManagerProvider.getInstance().getUserDbManager();
                    instance = new ShieldDao(dbManager);
                }
            }
        }
        return instance;
    }

    public ShieldDao(DbManager dbManager) {
        super(dbManager);
    }
}
