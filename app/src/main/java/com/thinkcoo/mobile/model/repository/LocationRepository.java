package com.thinkcoo.mobile.model.repository;

import com.thinkcoo.mobile.model.entity.Location;
import rx.Observable;

/**
 * Created by Robert.yao on 2016/7/6.
 */
public interface LocationRepository {
    Observable<Location> requestLocationUseBaiduSdk();
    Observable upLoadLocation();
}
