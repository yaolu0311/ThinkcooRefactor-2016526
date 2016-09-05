package com.thinkcoo.mobile.model.repository.impl;

import com.thinkcoo.mobile.model.db.DataDictionaryDao;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.entity.PoiAddress;
import com.thinkcoo.mobile.model.entity.SchoolLocation;
import com.thinkcoo.mobile.model.exception.ServerResponseException;
import com.thinkcoo.mobile.model.repository.BaiduRepository;
import com.thinkcoo.mobile.model.rest.apis.BaiduApi;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Robert.yao on 2016/8/11.
 */
public class BaiduRepositoryImpl implements BaiduRepository {

    BaiduApi mBaiduApi;
    DataDictionaryDao mDataDictionaryDao;

    @Inject
    public BaiduRepositoryImpl(BaiduApi baiduApi, DataDictionaryDao dataDictionaryDao) {
        mBaiduApi = baiduApi;
        mDataDictionaryDao = dataDictionaryDao;
    }

    @Override
    public Observable<List<SchoolLocation>> loadSchoolLocationList(final String school) {

        return mDataDictionaryDao.queryCityNameBuSchoolName(school).flatMap(new Func1<String, Observable<List<SchoolLocation>>>() {
            @Override
            public Observable<List<SchoolLocation>> call(String s) {
                return mBaiduApi.querySchoolAddress(school,"json","MTVh2o3w87NkgIHUP9dIRtsT","20",s).flatMap(new Func1<PoiAddress, Observable<List<SchoolLocation>>>() {
                    @Override
                    public Observable<List<SchoolLocation>> call(PoiAddress poiAddress) {
                        if (!poiAddress.isSuccess()){
                            return Observable.error(new ServerResponseException(poiAddress.getMessage()));
                        }
                        return Observable.just(SchoolLocation.create(poiAddress));
                    }
                });
            }
        });


    }
}
