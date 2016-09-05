package com.thinkcoo.mobile.annotation;

/**
 * 注解常量
 */
public interface AnnotationConstant {

    String PHONE_FORMAT_CHECK = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0,5-9])|(18[0-9])|(19[0-9]))\\d{8}$";
    String EMAIL_FORMAT_CHECK = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    String PERSON_ID_FORMAT_CHECK = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
}
