package com.yz.im.model.cache;

import android.content.Context;
import android.text.TextUtils;

import com.yz.im.model.db.NoticeMessageDao;
import com.yz.im.model.db.entity.NoticeMessageDb;
import com.yz.im.model.db.entity.SystemSetting;

import java.util.List;

/**
 * Created by cys on 2016/7/20
 */
public class NoticeMessageCache extends GlobalBaseCache<NoticeMessageDb> {

    public static volatile NoticeMessageCache instance;

    public static NoticeMessageCache getInstance(Context context) {
        if (instance == null) {
            synchronized (NoticeMessageCache.class) {
                if (instance == null) {
                    instance = new NoticeMessageCache(context);
                }
            }
        }
        return instance;
    }

    public static String KEY_SINGLE = "0";

    public NoticeMessageCache(Context context) {
        super(context);
        mBaseDao = NoticeMessageDao.getInstance(context.getApplicationContext());
    }

    @Override
    public NoticeMessageDb getEntity(String id) {
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        return cacheMap.get(id);
    }

    @Override
    public List<NoticeMessageDb> queryAllFromDb() {
        return mBaseDao.queryAll(NoticeMessageDb.class);
    }

    @Override
    public void insert(NoticeMessageDb setting) {
        if (setting == null) {
            return;
        }
        mBaseDao.deleteAll(SystemSetting.class);
        mBaseDao.replace(setting);
        cacheMap.clear();
        cacheMap.put(KEY_SINGLE, setting);
    }

    @Override
    public void insertAll(NoticeMessageDb... list) {
        if (list == null) {
            return;
        }
        for (NoticeMessageDb setting : list) {
            insert(setting);
        }
    }

    @Override
    public void delete(NoticeMessageDb setting) {
        if (setting == null) {
            return;
        }
        mBaseDao.deleteAll(SystemSetting.class);
        cacheMap.clear();
    }

    @Override
    public void deleteAll(NoticeMessageDb... list) {
        if (list == null) {
            return;
        }
        for (NoticeMessageDb setting : list) {
            delete(setting);
        }
    }

    public boolean hasNewNoticeMessage() {
        return cacheMap.size() > 0 ? true : false;
    }
}
