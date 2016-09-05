package com.thinkcoo.mobile.model.repository;

import com.thinkcoo.mobile.model.entity.SchoolLocation;
import java.util.List;
import rx.Observable;

/**
 * Created by Robert.yao on 2016/8/11.
 */
public interface BaiduRepository {
    Observable<List<SchoolLocation>> loadSchoolLocationList(String school);
}
