package com.thinkcoo.mobile.model.cache;

import com.thinkcoo.mobile.model.db.DataDictionaryDao;
import com.thinkcoo.mobile.model.entity.Province;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  10:51
 */
public class AddressCacheImpl implements AddressCache {


    DataDictionaryDao mDataDictionaryDao;
    WeakReference<List<Province>> mProvinceListWeakReference;

    @Inject
    public AddressCacheImpl(DataDictionaryDao dataDictionaryDao) {
        mDataDictionaryDao = dataDictionaryDao;
    }

    @Override
    public Observable<List<Province>> get() {

        List<Province> provinceListFromMemory = getFromMemory();
        if (null == provinceListFromMemory || provinceListFromMemory.size() == 0){
            provinceListFromMemory = queryFromDb();
            putToMemory(provinceListFromMemory);
        }
        return Observable.just(provinceListFromMemory);
    }

    private List<Province> queryFromDb() {
        return mDataDictionaryDao.queryAddress();
    }

    private List<Province> getFromMemory() {
        if (null == mProvinceListWeakReference){
            return null;
        }
        return mProvinceListWeakReference.get();
    }

    private void putToMemory(List<Province> provinceList){
        mProvinceListWeakReference = new WeakReference<>(provinceList);
    }

}
