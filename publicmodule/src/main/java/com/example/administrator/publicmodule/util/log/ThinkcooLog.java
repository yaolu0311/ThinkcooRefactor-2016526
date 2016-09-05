package com.example.administrator.publicmodule.util.log;


/**
 * Created by robert on 2016/5/22.
 */
public class ThinkcooLog {

    private static volatile TcLog tcLog;

    public static final void d(String tag,String msg){
        instanceTcLog();
        tcLog.d(tag,msg);
    }
    public static final void e(String tag,String msg){
        instanceTcLog();
        tcLog.e(tag,msg);
    }
    public static final void e(String tag,String msg,Throwable e){
        instanceTcLog();
        tcLog.e(tag,msg,e);
    }

    public static final void sd(String tag,String msg){
        instanceTcLog();
        tcLog.sd(tag,msg);
    }

    private static void instanceTcLog() {
        if (null == tcLog){
            synchronized (ThinkcooLog.class){
                if (null == tcLog){
                    tcLog = new TcLogImpl();
                }
            }
        }
    }
}
