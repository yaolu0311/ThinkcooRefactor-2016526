package com.thinkcoo.mobile.model.repository.impl;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.model.repository.LocationRepository;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Robert.yao on 2016/7/6.
 */
public class LocationRepositoryImpl implements LocationRepository{

    private static final String TAG = "LocationRepositoryImpl";
    LocationClient mLocationClient;

    @Inject
    public LocationRepositoryImpl(LocationClient locationClient) {
        mLocationClient = locationClient;
    }

    @Override
    public Observable<Location> requestLocationUseBaiduSdk() {

       return  Observable.create(new Observable.OnSubscribe<Location>() {
           @Override
           public void call(final Subscriber<? super Location> subscriber) {

               BDLocationListener bdLocationListener = new BDLocationListener() {
                   @Override
                   public void onReceiveLocation(BDLocation bdLocation) {
                       if (subscriber.isUnsubscribed()){
                           return;
                       }
                       subscriber.onNext(Location.from(bdLocation));
                       subscriber.onCompleted();
                       mLocationClient.stop();
                       mLocationClient.unRegisterLocationListener(this);
                   }
               };
               mLocationClient.registerLocationListener(bdLocationListener);
               mLocationClient.start();


           }
       }).doOnError(new Action1<Throwable>() {
           @Override
           public void call(Throwable throwable) {
               ThinkcooLog.e(TAG , throwable.getMessage(),throwable);
           }
       });
    }

    @Override
    public Observable upLoadLocation() {
        return null;
    }
}
