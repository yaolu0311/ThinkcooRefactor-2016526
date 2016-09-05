package com.thinkcoo.mobile.presentation.views.component;

import android.app.IntentService;
import android.content.Intent;

import com.thinkcoo.mobile.presentation.views.activitys.LoginActivity;
import com.yz.im.Constant;

/**
 * Created by cys on 2016/7/6
 * in com.thinkcoo.mobile.presentation.views.component
 */
public class AppIntentService extends IntentService{

    public AppIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action  = intent.getAction();
        switch (action) {
            case Constant.ACTION_LOGIN:
                Intent logIntent = new Intent(this, LoginActivity.class);
                logIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logIntent);
                break;
        }
    }
}
