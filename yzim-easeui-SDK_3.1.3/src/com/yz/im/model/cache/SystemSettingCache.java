package com.yz.im.model.cache;

import android.content.Context;
import android.text.TextUtils;

import com.yz.im.Constant;
import com.yz.im.model.db.SystemSettingDao;
import com.yz.im.model.db.entity.SystemSetting;

import java.util.List;

/**
 * Created by cys on 2016/7/20
 */
public class SystemSettingCache extends GlobalBaseCache<SystemSetting> {

    public static volatile SystemSettingCache instance;

    public static SystemSettingCache getInstance(Context context) {
        if (instance == null) {
            synchronized (SystemSettingCache.class) {
                if (instance == null) {
                    instance = new SystemSettingCache(context);
                }
            }
        }
        return instance;
    }

    public static String KEY_SINGLE = "0";

    public SystemSettingCache(Context context) {
        super(context);
        mBaseDao = SystemSettingDao.getInstance(context.getApplicationContext());
    }

    @Override
    public SystemSetting getEntity(String id) {
        if (TextUtils.isEmpty(id)) {
            return getDefaultEntity();
        }
        SystemSetting setting = cacheMap.get(id);
        return setting == null ? getDefaultEntity() : setting;
    }

    private SystemSetting getDefaultEntity() {
        SystemSetting systemSetting = new SystemSetting();
        systemSetting.setIsMessageRemind(Constant.OPEN_SETTING);
        systemSetting.setIsVerify(Constant.OPEN_SETTING);
        systemSetting.setIsReceveStranger(Constant.OPEN_SETTING);
        return systemSetting;
    }

    @Override
    public List<SystemSetting> queryAllFromDb() {
        return mBaseDao.queryAll(SystemSetting.class);
    }

    @Override
    public void insert(SystemSetting setting) {
        if (setting == null) {
            return;
        }
        mBaseDao.deleteAll(SystemSetting.class);
        mBaseDao.replace(setting);
        cacheMap.clear();
        cacheMap.put(KEY_SINGLE, setting);
    }

    @Override
    public void insertAll(SystemSetting... list) {
        if (list == null) {
            return;
        }
        for (SystemSetting setting : list) {
            insert(setting);
        }
    }

    @Override
    public void delete(SystemSetting setting) {
        if (setting == null) {
            return;
        }
        mBaseDao.deleteAll(SystemSetting.class);
        cacheMap.clear();
    }

    @Override
    public void deleteAll(SystemSetting... list) {
        if (list == null) {
            return;
        }
        for (SystemSetting setting : list) {
            delete(setting);
        }
    }

    public boolean allowVoiceNotice() {
        String flag = getEntity(SystemSettingCache.KEY_SINGLE).getIsMessageRemind();
        if (TextUtils.isEmpty(flag)) {
            return true;
        }
        return Constant.OPEN_SETTING.equals(flag);
    }

    public boolean allowReceiveStrangerMsg() {
        String flag = getEntity(SystemSettingCache.KEY_SINGLE).getIsReceveStranger();
        if (TextUtils.isEmpty(flag)) {
            return true;
        }
        return Constant.OPEN_SETTING.equals(flag);
    }

    public boolean needVefiryWhenAddFriend() {
        String flag = getEntity(SystemSettingCache.KEY_SINGLE).getIsVerify();
        if (TextUtils.isEmpty(flag)) {
            return true;
        }
        return Constant.OPEN_SETTING.equals(flag);
    }
}
