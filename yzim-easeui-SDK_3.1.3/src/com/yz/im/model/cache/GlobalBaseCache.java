package com.yz.im.model.cache;

import android.content.Context;

import com.example.administrator.publicmodule.model.db.base.BaseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by cys on 2016/7/20
 */
public abstract class GlobalBaseCache<T> implements GlobalInitCache<T> {

    protected static String TAG = "GlobalBaseCache";

    protected Context mContext;

    protected Map<String, T> cacheMap;

    protected BaseDao<T> mBaseDao;

    protected GlobalBaseCache(Context context) {
        mContext = context;
        cacheMap = new HashMap<>();
    }

    @Override
    public void clear() {
        cacheMap.clear();
    }

    public List<T> getEntityList(){
        List<T> list = new ArrayList<>();
        Iterator iterator = cacheMap.values().iterator();
        while (iterator.hasNext()){
            list.add((T) iterator.next());
        }

        if (list.size()<=0) {
            List<T> dbList = queryAllFromDb();
            if (dbList != null) {
                list.addAll(dbList);
            }
        }

        return list;
    }

    public abstract T getEntity(String id);

    public abstract List<T> queryAllFromDb();

}
