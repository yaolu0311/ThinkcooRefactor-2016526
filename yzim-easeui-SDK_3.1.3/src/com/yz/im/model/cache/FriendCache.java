package com.yz.im.model.cache;

import android.content.Context;
import android.text.TextUtils;

import com.yz.im.Constant;
import com.yz.im.model.db.FriendDao;
import com.yz.im.model.db.entity.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cys on 2016/7/20
 */
public class FriendCache extends GlobalBaseCache<Friend> {

    public static volatile FriendCache instance;

    public static FriendCache getInstance(Context context) {
        if (instance == null) {
            synchronized (FriendCache.class) {
                if (instance == null) {
                    instance = new FriendCache(context);
                }
            }
        }
        return instance;
    }

    private FriendCache(Context context) {
        super(context);
        mBaseDao = FriendDao.getInstance(context.getApplicationContext());
    }

    @Override
    public Friend getEntity(String id) {
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        return cacheMap.get(id);
    }

    /**
     * 判断是否是好友，包括黑名单用户
     */
    public boolean isFriend(String id) {
        if (TextUtils.isEmpty(id)) {
            return false;
        }
        return cacheMap.containsKey(id);
    }

    /**
     * 判断该好友是否是黑名单用户
     */
    public boolean isBlackUser(String id) {
        if (TextUtils.isEmpty(id)) {
            return false;
        }
        Friend friend = getEntity(id);
        if (friend == null) {
            return false;
        }
        String isBlack = getEntity(id).getBlacklist();
        return Constant.IS_BLACK_USER.equals(isBlack);
    }

    @Override
    public List<Friend> queryAllFromDb() {
        return mBaseDao.queryAll(Friend.class);
    }

    @Override
    public void insert(Friend friend) {
        if (friend == null) {
            return;
        }
        mBaseDao.replace(friend);
        String id = friend.getUserId();
        if (cacheMap.containsKey(id)) {
            cacheMap.remove(id);
        }
        cacheMap.put(id, friend);
    }

    @Override
    public void insertAll(Friend... list) {
        if (list == null) {
            return;
        }
        for (Friend friend : list) {
            insert(friend);
        }
    }

    @Override
    public void delete(Friend friend) {
        if (friend == null) {
            return;
        }
        mBaseDao.deleteById(Friend.class, friend.getUserId());
        String id = friend.getUserId();
        if (cacheMap.containsKey(id)) {
            cacheMap.remove(id);
        }
    }

    @Override
    public void deleteAll(Friend... list) {
        if (list == null) {
            return;
        }
        for (Friend friend : list) {
            delete(friend);
        }
    }

    public List<Friend> getFriendList() {
        List<Friend> resultList = new ArrayList<>();
        List<Friend> friends = getEntityList();
        for (Friend friend : friends) {
            if ("0".equals(friend.getBlacklist())) {
                resultList.add(friend);
            }
        }
        return resultList;
    }

    public List<Friend> getBlackUserList() {
        List<Friend> resultList = new ArrayList<>();
        List<Friend> friends = getEntityList();
        for (Friend friend : friends) {
            if (friend.getBlacklist().equals(Constant.IS_BLACK_USER)) {
                resultList.add(friend);
            }
        }
        return resultList;
    }
}
