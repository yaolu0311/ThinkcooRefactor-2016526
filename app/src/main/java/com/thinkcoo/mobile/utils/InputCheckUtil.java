package com.thinkcoo.mobile.utils;

import android.text.TextUtils;

import com.thinkcoo.mobile.injector.ActivityScope;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/3/24.
 */
@ActivityScope
public class InputCheckUtil {

    @Inject
    public InputCheckUtil(){}

    public boolean checkPhoneNumber(String phoneNumber) {

        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        } else {
            String regex = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0,5-9])|(18[0-9])|(19[0-9]))\\d{8}$";
            return match(regex, phoneNumber);
        }
    }

    public boolean checkPersonID(String phoneNumber) {

        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        } else {
            String regex = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
            return match(regex, phoneNumber);
        }
    }

    public boolean checkEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            String regex = "^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$";
            return match(regex, email);
        }
    }

    public boolean checkVerifyCode(String verifyCode) {
        return !TextUtils.isEmpty(verifyCode) && TextUtils.isDigitsOnly(verifyCode) && verifyCode.trim().length() == 4;
    }

    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public boolean checkPassword(String password) {
        return false;
    }
    /**
     * 验证身份证号是否符合规则
     * @param text 身份证号
     * @return
     */
    public boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    /**
     * 判断护照号码
     *
     * @param
     * @return
     */
    public boolean ishzId(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return false;
        } else {
            String idCards = "^1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$";
            Pattern patterns = Pattern.compile(idCards);
            Matcher matchers = patterns.matcher(idCard);
            boolean flag = matchers.matches();
            return flag;
        }
    }
    /**
     * 判断港澳台身份证合法
     *
     * @param
     * @return
     */
    public  boolean IsGAId(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return false;
        } else {
            String idCards = "^((s?[A-Za-z])|([A-Za-z]{2}))d{6}((([0-9aA]))|([0-9aA]))$";
            Pattern patterns = Pattern.compile(idCards);
            Matcher matchers = patterns.matcher(idCard);
            boolean flag = matchers.matches();
            return flag;
        }
    }
}
