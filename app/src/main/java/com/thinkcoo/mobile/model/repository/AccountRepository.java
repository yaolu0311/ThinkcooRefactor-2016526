package com.thinkcoo.mobile.model.repository;

import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.model.entity.CheckVcode;
import com.thinkcoo.mobile.model.entity.License;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.model.entity.Vcode;

import rx.Observable;

/**
 * Created by Administrator on 2016/5/19.
 */
public interface AccountRepository {

    Observable<Account> getLoggedAccount();
    Observable<User> login(Account account);
    Observable autoLogin(Account account);
    Observable logout();
    Observable register(Account account,Vcode vcode, License license);
    Observable gotPassword(Account account,Vcode vcode);
    Observable changePassword(Account account);
    Observable changeAccountName(Account account,Vcode vcode,String oldPhoneNumber);
    Observable<Vcode> requestRegisterVcode(Account account);
    Observable completeRegister(Account account, String areaCode, String userName);
    Observable completeFindPassword(CheckVcode checkVcode, String newPassword);
    Observable userEnvironmentInit(User user);
    Observable modifyPassword(String odlPwd, String newPwd);

}
