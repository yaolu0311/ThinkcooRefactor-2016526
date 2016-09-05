package com.yz.im.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.easeui.R;

public class ExceptionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception);
    }

    public static Intent getExceptionIntent(Context context) {
        return new Intent(context, ExceptionActivity.class);
    }
}
