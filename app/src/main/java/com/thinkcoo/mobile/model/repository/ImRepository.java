package com.thinkcoo.mobile.model.repository;

import rx.Observable;

/**
 * Created by Administrator on 2016/6/27.
 */
public interface ImRepository {
    Observable imLogin(String userId,String passWord);
}
