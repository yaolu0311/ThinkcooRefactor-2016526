package com.thinkcoo.mobile.model.cache;

import com.thinkcoo.mobile.model.entity.User;

import rx.Observable;

/**
 * Created by Robert.yao on 2016/3/24.
 */
public interface LoginUserCache {

    Observable<User> get();
    boolean put(User user);
    boolean destroy();

}
