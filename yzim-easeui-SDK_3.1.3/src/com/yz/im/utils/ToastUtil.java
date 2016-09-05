package com.yz.im.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil {

    private Context mContext;
    private Toast mToast;
    private static ToastUtil instance;

    private ToastUtil(Context mContext) {
        super();
        this.mContext = mContext;
        if(mContext!=null){
            this.mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }

    }

    public static ToastUtil getInstance(Context context) {
        if (instance == null) {
            if(context!=null){
                instance = new ToastUtil(context);
            }

        }
        return instance;
    }

    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void showToastById(int resId) {
        String content = mContext.getString(resId);
        if (TextUtils.isEmpty(content)) {
            return;
        }
        showToast(content);
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
