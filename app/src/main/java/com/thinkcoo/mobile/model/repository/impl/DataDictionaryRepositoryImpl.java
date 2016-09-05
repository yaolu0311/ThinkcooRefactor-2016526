package com.thinkcoo.mobile.model.repository.impl;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.cache.AddressCache;
import com.thinkcoo.mobile.model.cache.DataDictionaryCache;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.entity.Province;
import com.thinkcoo.mobile.model.entity.SortedCity;
import com.thinkcoo.mobile.model.repository.DataDictionaryRepository;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategy;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  11:26
 */
public class DataDictionaryRepositoryImpl implements DataDictionaryRepository {

    private static final String TAG = "DataDictionaryRepositoryImpl";

    AddressCache mAddressCache;
    DataDictionaryCache mDataDictionaryCache;

    @Inject
    public DataDictionaryRepositoryImpl(AddressCache addressCache, DataDictionaryCache dataDictionaryCache) {
        mAddressCache = addressCache;
        mDataDictionaryCache = dataDictionaryCache;
    }
    @Override
    public Observable<List<Province>> getAddress() {
        return mAddressCache.get();
    }
    @Override
    public Observable<List<DataDictionary>> queryDataDictionary(DataDictionaryStrategy dataDictionaryStrategy) {
        return mDataDictionaryCache.get(dataDictionaryStrategy);
    }
    @Override
    public Observable<List<DataDictionary>> queryDataDictionary(DataDictionaryStrategy dataDictionaryStrategy, String ...searchKey) {
        return mDataDictionaryCache.get(dataDictionaryStrategy, searchKey);
    }
    @Override
    public Observable<List<SortedCity>> getSortedCityList() {
        return getAddress().doOnNext(new Action1<List<Province>>() {
            @Override
            public void call(List<Province> provinceList) {
                Collections.sort(provinceList);
            }
        }).map(new Func1<List<Province>, List<SortedCity>>() {
            @Override
            public List<SortedCity> call(List<Province> provinceList) {
                ThinkcooLog.e(TAG , "=== 获得 :"  + provinceList.size() + " 笔省数据 ===") ;
                return SortedCity.provinceList2SortedCityList(provinceList);
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
