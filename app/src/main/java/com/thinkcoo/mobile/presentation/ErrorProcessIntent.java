package com.thinkcoo.mobile.presentation;

import android.content.Intent;

/**
 * Created by Administrator on 2016/8/13.
 */
public class ErrorProcessIntent {

    public static final byte INTENT_TYPE_START_ACTIVITY = 0x01;
    public static final byte INTENT_TYPE_SEND_BROCAST = 0x02;

    byte intentType = INTENT_TYPE_START_ACTIVITY;
    Intent realIntent;

    public ErrorProcessIntent(byte intentType, Intent realIntent) {
        this.intentType = intentType;
        this.realIntent = realIntent;
    }

   public boolean isStartActivityType(){
       return intentType == INTENT_TYPE_START_ACTIVITY;
   }
   public  boolean isBrocastType(){
        return intentType == INTENT_TYPE_SEND_BROCAST;
    }
    public void setIntentType(byte intentType) {
        this.intentType = intentType;
    }

    public Intent getRealIntent() {
        return realIntent;
    }

    public void setRealIntent(Intent realIntent) {
        this.realIntent = realIntent;
    }
}
