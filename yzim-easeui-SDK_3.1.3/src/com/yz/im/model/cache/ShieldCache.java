package com.yz.im.model.cache;

import android.content.Context;
import android.text.TextUtils;

import com.yz.im.Constant;
import com.yz.im.model.db.ShieldDao;
import com.yz.im.model.db.entity.Shield;

import java.util.List;

/**
 * Created by cys on 2016/7/20
 */
public class ShieldCache extends GlobalBaseCache<Shield> {

    public static volatile ShieldCache instance;

    public static ShieldCache getInstance(Context context) {
        if (instance == null) {
            synchronized (ShieldCache.class) {
                if (instance == null) {
                    instance = new ShieldCache(context);
                }
            }
        }
        return instance;
    }

    private ShieldCache(Context context) {
        super(context);
        mBaseDao = ShieldDao.getInstance(context.getApplicationContext());
    }

    @Override
    public Shield getEntity(String id) {
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        return cacheMap.get(id);
    }

    @Override
    public List<Shield> queryAllFromDb() {
        return mBaseDao.queryAll(Shield.class);
    }

    @Override
    public void insert(Shield shield) {
        if (shield == null) {
            return;
        }
        mBaseDao.replace(shield);
        String id = shield.getUserId();
        if (cacheMap.containsKey(id)) {
            cacheMap.remove(id);
        }

        cacheMap.put(id, shield);
    }

    @Override
    public void insertAll(Shield... list) {
        if (list == null) {
            return;
        }
        for (Shield shield : list) {
            insert(shield);
        }
    }

    @Override
    public void delete(Shield shield) {
        if (shield == null) {
            return;
        }
        mBaseDao.delete(shield);
        String id = shield.getUserId();
        if (cacheMap.containsKey(id)) {
            cacheMap.remove(id);
        }
    }

    @Override
    public void deleteAll(Shield... list) {
        if (list == null) {
            return;
        }
        for (Shield shield: list) {
            delete(shield);
        }
    }

    /**
     * 判断陌生人是否被屏蔽
     */
    public boolean isShield(String id) {
        if (TextUtils.isEmpty(id)) {
            return false;
        }
        Shield shield = getEntity(id);
        if (shield == null) {
            return false;
        }
        String isShield = shield.getShield();
        return Constant.IS_SHIELD.equals(isShield);
    }
}
