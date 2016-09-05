package com.thinkcoo.mobile.model.cache;

import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategy;

import java.util.List;
import rx.Observable;

/**
 * Created by Robert.yao on 2016/6/15.
 */
public interface DataDictionaryCache {
    Observable<List<DataDictionary>> get(DataDictionaryStrategy dataDictionaryStrategy);
    Observable<List<DataDictionary>> get(DataDictionaryStrategy dataDictionaryStrategy,String ...searchKey);
    void destroy();
}
