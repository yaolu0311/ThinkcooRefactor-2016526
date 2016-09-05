package com.thinkcoo.mobile.model.repository.impl;

import com.thinkcoo.mobile.model.db.base.DataBaseCompat;
import com.thinkcoo.mobile.model.exception.applaunch.CompatOldVersionDbException;
import com.thinkcoo.mobile.model.exception.applaunch.CopyDDDataBaseException;
import com.thinkcoo.mobile.model.repository.AppLaunchRepository;
import com.thinkcoo.mobile.utils.AssetsUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by robert on 2016/5/22.
 */
@Singleton
public class AppLaunchRepositoryImpl implements AppLaunchRepository{

    AssetsUtils assetsUtils;
    DataBaseCompat dbCompat;

    @Inject
    public AppLaunchRepositoryImpl(AssetsUtils assetsUtils, DataBaseCompat dbCompat) {
        this.assetsUtils = assetsUtils;
        this.dbCompat = dbCompat;
    }

    @Override
    public Observable copyDataDictionaryDbFile() {

       return Observable.create(new Observable.OnSubscribe<Void>(){

            @Override
            public void call(Subscriber subscriber) {
                try{
                    assetsUtils.copyDataDictionaryDbFile();
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(new CopyDDDataBaseException());
                }
            }
        });
    }

    @Override
    public Observable compatOldVersionDb() {
       return Observable.create(new Observable.OnSubscribe<Boolean>(){
           @Override
           public void call(Subscriber<? super Boolean> subscriber) {
               try{
                   dbCompat.tryCompatOldVersion();
                   subscriber.onCompleted();
               }catch (Exception e){
                   subscriber.onError(new CompatOldVersionDbException());
               }
           }
       });
    }
}
