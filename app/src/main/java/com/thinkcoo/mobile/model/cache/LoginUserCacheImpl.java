package com.thinkcoo.mobile.model.cache;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.db.UserDao;
import com.thinkcoo.mobile.model.entity.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/3/24.
 */
@Singleton
public class LoginUserCacheImpl implements LoginUserCache {

    public static final String TAG = "LoginUserCacheImpl";

    User mUser = User.NULL_USER;
    @Inject
    Lazy<UserDao> mUserDao;

    @Override
    public Observable<User> get() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                if (mUser.isEmpty()){
                    mUser = tryGetFromDb();
                }
                subscriber.onNext(cloneUser(mUser));
                subscriber.onCompleted();
            }
        });
    }

    private User cloneUser(User user){
        try {
            User cloneUser =  user.clone();
            return cloneUser;
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
        }
        return copyUser(user);
    }

    private User copyUser(User user) {
        //TODO
        return null;
    }

    private User tryGetFromDb() {
        User user = User.NULL_USER;
        try {
            List<User> userList = getUserDao().queryAll(User.class);
            if (null != userList && userList.size() != 0){
                user = userList.get(0);
            }
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
        }
        return  user;
    }

    @Override
    public boolean put(User user) {
        //if (saveToDb(user)){
            setUserToMemory(user);
            return true;
       //}
        //return true;
    }

    public void setUserToMemory(User user){
        mUser = user;
    }

    public void removeMemoryUser(){
        mUser = null;
    }

    @Override
    public boolean destroy() {
        removeFromDb(mUser);
        removeMemoryUser();
        return true;
    }
    private boolean saveToDb(User user) {
        try {
            getUserDao().insert(user);
            return true;
        } catch (Exception e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
            return false;
        }
    }
    private void removeFromDb(User user) {
       try {
           getUserDao().delete(user);
       }catch (Exception e){
           ThinkcooLog.e(TAG,e.getMessage(),e);
       }
    }

    public UserDao getUserDao() {
        return mUserDao.get();
    }
}
