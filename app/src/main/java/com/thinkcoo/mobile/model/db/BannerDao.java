package com.thinkcoo.mobile.model.db;

import com.example.administrator.publicmodule.model.db.base.BaseDaoImpl;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.entity.Banner;
import com.yzkj.android.orm.DbException;
import com.yzkj.android.orm.DbManager;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/7/19.
 */
public class BannerDao extends BaseDaoImpl<Banner> {

    public static final String TAG = "BannerDao";

    @Inject
    public BannerDao(@Named("public") DbManager dbManager) {
        super(dbManager);
    }
    public Observable<List<Banner>> queryByType(int type) {//暂时不考虑类型
        return Observable.create(new Observable.OnSubscribe<List<Banner>>() {
            @Override
            public void call(Subscriber<? super List<Banner>> subscriber) {
                try {
                    List<Banner> bannerList = mDbManager.findAll(Banner.class);
                    if (null == bannerList){
                        bannerList = Collections.EMPTY_LIST;
                    }
                    subscriber.onNext(bannerList);
                    subscriber.onCompleted();
                } catch (DbException e) {
                    subscriber.onError(e);
                    ThinkcooLog.e(TAG,e.getMessage(),e);
                }
            }
        });
    }
}
