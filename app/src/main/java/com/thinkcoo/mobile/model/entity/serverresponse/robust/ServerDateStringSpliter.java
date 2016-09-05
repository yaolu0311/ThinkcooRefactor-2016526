package com.thinkcoo.mobile.model.entity.serverresponse.robust;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/5/31.
 */
public class ServerDateStringSpliter {

    public static  final String TAG = "ServerDateStringSpliter";

    private String year;
    private String month;
    private String day;

    public static ServerDateStringSpliter createByServerDateString(String serverDateString){
        return new ServerDateStringSpliter(serverDateString);
    }

    private ServerDateStringSpliter(String serverDateString) {
        useCurrentTimeInitYearMonthDay();
        doSplit(serverDateString);
    }

    private void useCurrentTimeInitYearMonthDay() {
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH));
        day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void doSplit(String serverDateString) {
        try{
            String [] dateTime = serverDateString.split(" ");
            String [] yearMonthDay = dateTime[0].split("-");
            this.year = yearMonthDay[0];
            this.month = yearMonthDay[1];
            this.day = yearMonthDay[2];
        }catch (Exception e){
            ThinkcooLog.e(TAG,e.getMessage(),e);
        }
    }

    public String getYear() {
        return year;
    }
    public String getDay() {
        return day;
    }
    public String getMonth() {
        return month;
    }

}
