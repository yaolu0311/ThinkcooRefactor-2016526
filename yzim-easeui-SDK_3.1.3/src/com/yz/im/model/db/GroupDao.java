package com.yz.im.model.db;

import android.content.Context;

import com.example.administrator.publicmodule.model.db.base.BaseDaoImpl;
import com.example.administrator.publicmodule.model.db.base.DbManagerProvider;
import com.yz.im.model.db.entity.Group;
import com.yzkj.android.orm.DbManager;

/**
 * Created by cys on 2016/7/19
 */
public class GroupDao extends BaseDaoImpl<Group> {

    public static volatile GroupDao instance;

    public static GroupDao getInstance(Context context) {
        if (instance == null) {
            synchronized (GroupDao.class) {
                if (instance == null) {
                    DbManager dbManager = DbManagerProvider.getInstance().getUserDbManager();
                    instance = new GroupDao(dbManager);
                }
            }
        }
        return instance;
    }

    private GroupDao(DbManager dbManager) {
        super(dbManager);
    }
}
