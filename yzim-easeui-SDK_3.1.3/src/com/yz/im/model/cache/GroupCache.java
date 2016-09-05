package com.yz.im.model.cache;

import android.content.Context;
import android.text.TextUtils;

import com.yz.im.model.db.GroupDao;
import com.yz.im.model.db.entity.Group;

import java.util.Iterator;
import java.util.List;

/**
 * Created by cys on 2016/7/20
 */
public class GroupCache extends GlobalBaseCache<Group> {

    public static volatile GroupCache instance;

    public static GroupCache getInstance(Context context) {
        if (instance == null) {
            synchronized (GroupCache.class) {
                if (instance == null) {
                    instance = new GroupCache(context);
                }
            }
        }
        return instance;
    }

    private GroupCache(Context context) {
        super(context);
        mBaseDao = GroupDao.getInstance(context.getApplicationContext());
    }

    @Override
    public Group getEntity(String id) {
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        return cacheMap.get(id);
    }

    @Override
    public List<Group> queryAllFromDb() {
        return mBaseDao.queryAll(Group.class);
    }

    @Override
    public void insert(Group group) {
        if (group == null) {
            return;
        }

        mBaseDao.replace(group);
        String id = group.getGroupId();
        if (cacheMap.containsKey(id)) {
            cacheMap.remove(id);
        }
        cacheMap.put(id, group);
    }

    @Override
    public void insertAll(Group... list) {
        if (list == null) {
            return;
        }
        for (Group group : list) {
            insert(group);
        }
    }

    @Override
    public void delete(Group group) {
        if (group == null) {
            return;
        }
        mBaseDao.deleteById(Group.class, group.getGroupId());
        String id = group.getGroupId();
        if (cacheMap.containsKey(id)) {
            cacheMap.remove(id);
        }
    }

    @Override
    public void deleteAll(Group... list) {
        if (list == null) {
            return;
        }
        for (Group group: list) {
            delete(group);
        }
    }

    public Group getEntityByDiffId(String id, boolean isHxId) {
        if (TextUtils.isEmpty(id)) {
            return null;
        }

        if (isHxId) {
            return getEntityByHxId(id);
        }else {
            return getEntity(id);
        }
    }

    private Group getEntityByHxId(String hxId) {
        Iterator<Group> iterator = cacheMap.values().iterator();
        while (iterator.hasNext()){
            Group group = iterator.next();
            if(hxId.equals(group.getEasemobGroupId())){
                return group;
            }
        }
        return null;
    }
}
