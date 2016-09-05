package com.thinkcoo.mobile.model.cache;


import com.bumptech.glide.util.LruCache;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.db.DataDictionaryDao;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategy;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Robert.yao on 2016/6/15.
 */
@Singleton
public class DataDictionaryCacheImpl implements DataDictionaryCache {

    public static final String TAG = "DataDictionaryCacheImpl";

    public static final int MAX_CACHE_SIZE = 1024 * 100;

    DataDictionaryDao mDataDictionaryDao;
    LruCache<String , List<DataDictionary>> mCache;

    @Inject
    public DataDictionaryCacheImpl(DataDictionaryDao dataDictionaryDao) {
        mDataDictionaryDao = dataDictionaryDao;
        initCache();
    }

    private void initCache() {
        mCache = new LruCache<>(MAX_CACHE_SIZE);
    }

    @Override
    public Observable<List<DataDictionary>> get(DataDictionaryStrategy dataDictionaryStrategy) {
        return get(dataDictionaryStrategy,"");
    }

    @Override
    public Observable<List<DataDictionary>> get(DataDictionaryStrategy dataDictionaryStrategy, String ...searchKey) {
        String tableName = dataDictionaryStrategy.getDataDictionaryTableName();
        List<DataDictionary> dataDictionaryList =  mCache.get(tableName + searchKey);
        if (null == dataDictionaryList || dataDictionaryList.size() == 0){
            dataDictionaryList = queryFromDb(dataDictionaryStrategy,searchKey);
            mCache.put(tableName + searchKey ,dataDictionaryList);
        }else {
            ThinkcooLog.d(TAG,"=== 从缓存中获取数据字典列表 name : " + tableName + " ===");
        }
        return Observable.just(dataDictionaryList);
    }

    private List<DataDictionary> queryFromDb(DataDictionaryStrategy dataDictionaryStrategy,String ...searchKey) {
        return mDataDictionaryDao.queryDataDictionary(dataDictionaryStrategy,searchKey);
    }


    /*
    private List<DataDictionary> getFakeDD(String tableName) {

        List<DataDictionary> dataDictionaryList = new ArrayList<>();

        if (tableName.equals(DataDictionaryDialogProvideFactory.EDUCATION_DD_TABLE_NAME)){

            DataDictionary dataDictionary01 = new DataDictionary();
            dataDictionary01.setCode("1");
            dataDictionary01.setDisplayName("幼儿园");

            DataDictionary dataDictionary02 = new DataDictionary();
            dataDictionary02.setCode("2");
            dataDictionary02.setDisplayName("小学");

            DataDictionary dataDictionary03 = new DataDictionary();
            dataDictionary03.setCode("3");
            dataDictionary03.setDisplayName("初中");

            DataDictionary dataDictionary04 = new DataDictionary();
            dataDictionary04.setCode("4");
            dataDictionary04.setDisplayName("高中");

            DataDictionary dataDictionary05 = new DataDictionary();
            dataDictionary05.setCode("5");
            dataDictionary05.setDisplayName("大学");


            dataDictionaryList.add(dataDictionary01);
            dataDictionaryList.add(dataDictionary02);
            dataDictionaryList.add(dataDictionary03);
            dataDictionaryList.add(dataDictionary04);
            dataDictionaryList.add(dataDictionary05);


        }else if (tableName.equals(DataDictionaryDialogProvideFactory.NATION_DD_TABLE_NAME)){

            DataDictionary dataDictionary01 = new DataDictionary();
            dataDictionary01.setCode("1");
            dataDictionary01.setDisplayName("汉族");

            DataDictionary dataDictionary02 = new DataDictionary();
            dataDictionary02.setCode("2");
            dataDictionary02.setDisplayName("苗族");

            DataDictionary dataDictionary03 = new DataDictionary();
            dataDictionary03.setCode("3");
            dataDictionary03.setDisplayName("侗族");

            DataDictionary dataDictionary04 = new DataDictionary();
            dataDictionary04.setCode("4");
            dataDictionary04.setDisplayName("土家族");

            DataDictionary dataDictionary05 = new DataDictionary();
            dataDictionary05.setCode("5");
            dataDictionary05.setDisplayName("水族");


            dataDictionaryList.add(dataDictionary01);
            dataDictionaryList.add(dataDictionary02);
            dataDictionaryList.add(dataDictionary03);
            dataDictionaryList.add(dataDictionary04);
            dataDictionaryList.add(dataDictionary05);

        }else if (tableName.equals(DataDictionaryDialogProvideFactory.POLITICS_DD_TABLE_NAME)){

            DataDictionary dataDictionary01 = new DataDictionary();
            dataDictionary01.setCode("1");
            dataDictionary01.setDisplayName("少先队员");

            DataDictionary dataDictionary02 = new DataDictionary();
            dataDictionary02.setCode("2");
            dataDictionary02.setDisplayName("团员");

            DataDictionary dataDictionary03 = new DataDictionary();
            dataDictionary03.setCode("3");
            dataDictionary03.setDisplayName("党员");

            DataDictionary dataDictionary04 = new DataDictionary();
            dataDictionary04.setCode("4");
            dataDictionary04.setDisplayName("华侨");

            DataDictionary dataDictionary05 = new DataDictionary();
            dataDictionary05.setCode("5");
            dataDictionary05.setDisplayName("无党派人士");


            dataDictionaryList.add(dataDictionary01);
            dataDictionaryList.add(dataDictionary02);
            dataDictionaryList.add(dataDictionary03);
            dataDictionaryList.add(dataDictionary04);
            dataDictionaryList.add(dataDictionary05);

        }else if (tableName.equals(DataDictionaryDialogProvideFactory.POLITICS_DD_TABLE_NAME)){

            DataDictionary dataDictionary01 = new DataDictionary();
            dataDictionary01.setCode("1");
            dataDictionary01.setDisplayName("身份证");

            DataDictionary dataDictionary02 = new DataDictionary();
            dataDictionary02.setCode("2");
            dataDictionary02.setDisplayName("护照");

            DataDictionary dataDictionary03 = new DataDictionary();
            dataDictionary03.setCode("3");
            dataDictionary03.setDisplayName("港澳身份证");

            DataDictionary dataDictionary04 = new DataDictionary();
            dataDictionary04.setCode("4");
            dataDictionary04.setDisplayName("其它");

            dataDictionaryList.add(dataDictionary01);
            dataDictionaryList.add(dataDictionary02);
            dataDictionaryList.add(dataDictionary03);
            dataDictionaryList.add(dataDictionary04);

        }
        return  dataDictionaryList;
    }*/

    @Override
    public void destroy() {
        mCache.clearMemory();
    }
}
