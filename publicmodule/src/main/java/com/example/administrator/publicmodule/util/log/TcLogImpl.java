package com.example.administrator.publicmodule.util.log;

import android.util.Log;

/**
 * Created by robert on 2016/5/22.
 */
public class TcLogImpl implements TcLog{

    @Override
    public void d(String tag, String msg) {
        Log.d(tag,msg);
    }
    @Override
    public void e(String tag, String msg) {
        Log.e(tag, msg);
    }
    @Override
    public void e(String tag, String msg, Throwable e) {
        Log.e(tag,msg,e);
    }
    @Override
    public void sd(String tag, String msg) {
        //TODO 将日志写入文件
    }
}
