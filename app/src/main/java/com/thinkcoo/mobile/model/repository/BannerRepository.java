package com.thinkcoo.mobile.model.repository;

import com.thinkcoo.mobile.model.entity.Banner;

import java.util.List;

import rx.Observable;

/**
 * Created by Robert.yao on 2016/7/19.
 */
public interface BannerRepository {
    Observable<List<Banner>> loadBannerListByType(Integer integer);
}
