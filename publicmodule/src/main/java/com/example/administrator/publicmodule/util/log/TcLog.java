package com.example.administrator.publicmodule.util.log;

/**
 * Created by robert on 2016/5/22.
 */
public interface TcLog {

    //调试级别
    void d(String tag, String msg);
    //错误级别
    void e(String tag, String msg);
    void e(String tag, String msg, Throwable e);
    //sdcard 级别
    void sd(String tag, String msg);

}
