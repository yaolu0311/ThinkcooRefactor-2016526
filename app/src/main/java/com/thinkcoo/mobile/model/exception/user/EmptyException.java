package com.thinkcoo.mobile.model.exception.user;

/**
 * Created by Leevin
 * CreateTime: 2016/6/4  17:51
 */
public class EmptyException extends Throwable {

    @Override
    public String getMessage() {
        return "返回的数据为空";
    }
}
