package com.thinkcoo.mobile.model.db;

import com.example.administrator.publicmodule.model.db.base.BaseDaoImpl;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.entity.Account;
import com.yzkj.android.orm.DbManager;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/5/20.
 */
@Singleton
public class AccountDao extends BaseDaoImpl<Account> {

    public static final String TAG = "AccountDao";

    @Inject
    public AccountDao(@Named("public") DbManager dbManager) {
        super(dbManager);
    }

    public Account getLoggedAccount(){

        Account account = null;
        try{
            account = mDbManager.selector(Account.class).findFirst();
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
        }
        if (null == account){
            account = Account.NULL_ACCOUNT;
        }
        return  account;
    }
}
