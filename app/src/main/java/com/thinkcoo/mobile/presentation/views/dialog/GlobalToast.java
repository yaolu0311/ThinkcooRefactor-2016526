package com.thinkcoo.mobile.presentation.views.dialog;

import android.content.Context;
import android.widget.Toast;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/3/24.
 */
public class GlobalToast {

    private Context appContext;

    @Inject
    public GlobalToast(Context context) {
        appContext = context.getApplicationContext();
    }

    public void showShortToast(int stringtResID){
        Toast.makeText(appContext,stringtResID,Toast.LENGTH_SHORT).show();
    }
    public void showShortToast(String stringMsg){
        Toast.makeText(appContext,stringMsg,Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(int stringtResID){
        Toast.makeText(appContext,stringtResID,Toast.LENGTH_LONG).show();
    }
    public void showLongToast(String stringMsg){
        Toast.makeText(appContext,stringMsg,Toast.LENGTH_LONG).show();
    }
}
