package com.yz.im.model.db;

import android.content.Context;

import com.example.administrator.publicmodule.model.db.base.BaseDaoImpl;
import com.example.administrator.publicmodule.model.db.base.DbManagerProvider;
import com.yz.im.model.db.entity.Friend;
import com.yzkj.android.orm.DbManager;

/**
 * Created by cys on 2016/7/19
 */
public class FriendDao extends BaseDaoImpl<Friend> {

    public static volatile FriendDao instance;

    public static FriendDao getInstance(Context context){
        if (instance == null) {
            synchronized (FriendDao.class){
                if (instance == null) {
                    DbManager dbManager = DbManagerProvider.getInstance().getUserDbManager();
                    instance = new FriendDao(dbManager);
                }
            }
        }
        return instance;
    }

    private FriendDao(DbManager manager) {
        super(manager);
    }
}
