package com.thinkcoo.mobile.model.exception.account;

/**
 * Created by Robert.yao on 2016/3/28.
 */
public class PhoneNumberOrPasswordErrorException extends Exception {

    @Override
    public String getMessage() {
        return "用户名或密码错误";
    }
}
