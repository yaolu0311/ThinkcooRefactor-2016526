package com.thinkcoo.mobile.model.cache;

import com.thinkcoo.mobile.model.entity.Province;

import java.util.List;

import rx.Observable;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  10:49
 */
public interface AddressCache {
    Observable<List<Province>> get();
}
