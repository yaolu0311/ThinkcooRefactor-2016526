package com.thinkcoo.mobile.presentation.views.service;

import android.app.IntentService;
import android.content.Intent;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.ThinkcooApp;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.model.repository.LocationRepository;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import rx.observers.Subscribers;
import rx.schedulers.Schedulers;

/**
 * Created by Robert.yao on 2016/8/13.
 */
public class UploadLocationOnRockService extends IntentService{

    public static final String TAG = "UploadLocationOnRockService";
    public static final String ACTION = "yzke.action.uploadLocation";
    public static final String UUID = "uuid";

    @Inject
    ScheduleRepository mScheduleRepository;
    @Inject
    LocationRepository mLocationRepository;

    public UploadLocationOnRockService() {
        super(TAG);
        initInject();
    }

    private void initInject() {
        ThinkcooApp.getsAppInstance().getApplicationComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent.hasExtra(UUID)){
            ThinkcooLog.e(TAG,"=== 收到点名上传位置请求，准备上传位置 ===");
            final String uuid = intent.getStringExtra(UUID);
            mLocationRepository.requestLocationUseBaiduSdk().flatMap(new Func1<Location, Observable<?>>() {
                @Override
                public Observable<?> call(Location location) {
                    return mScheduleRepository.memberInsertEvent("","","",location.getLonString(),location.getLatString(),uuid).subscribeOn(Schedulers.io());
                }
            }).subscribeOn(Schedulers.io()).subscribe(Subscribers.empty());
        }
    }
}
