package com.thinkcoo.mobile.model.db;


import com.example.administrator.publicmodule.model.db.base.BaseDaoImpl;
import com.thinkcoo.mobile.model.entity.User;
import com.yzkj.android.orm.DbManager;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;


/**
 * Created by Robert.yao on 2016/3/25.
 */
@Singleton
public class UserDao extends BaseDaoImpl<User> {
    @Inject
    public UserDao(@Named("user") DbManager dbManager) {
        super(dbManager);
    }
}
