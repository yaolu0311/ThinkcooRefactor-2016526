package com.thinkcoo.mobile.presentation.views.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.thinkcoo.mobile.presentation.views.service.UploadLocationOnRockService;

/**
 * author ：ml on 2016/8/17
 */
public class UploadLocationBroadCast extends BroadcastReceiver{

    public static final String ACTION = "yzke.action.uploadLocation";
    public static final String UUID = "uuid";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction()) && intent.hasExtra(UUID)){
            Intent serviceIntent = new Intent(context, UploadLocationOnRockService.class);
            Bundle bundle = new Bundle();
            bundle.putString(UUID,intent.getStringExtra(UUID));
            serviceIntent.putExtras(bundle);
            context.startService(serviceIntent);
        }
    }
}
