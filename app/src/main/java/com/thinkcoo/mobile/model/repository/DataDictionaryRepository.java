package com.thinkcoo.mobile.model.repository;

import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.entity.Province;
import com.thinkcoo.mobile.model.entity.SortedCity;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategy;

import java.util.List;

import rx.Observable;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  11:25
 */
public interface DataDictionaryRepository {

    Observable<List<Province>> getAddress();
    Observable<List<DataDictionary>> queryDataDictionary(DataDictionaryStrategy dataDictionaryStrategy);
    Observable<List<DataDictionary>> queryDataDictionary(DataDictionaryStrategy dataDictionaryStrategy,String... searchKey);
    Observable<List<SortedCity>> getSortedCityList();
}
