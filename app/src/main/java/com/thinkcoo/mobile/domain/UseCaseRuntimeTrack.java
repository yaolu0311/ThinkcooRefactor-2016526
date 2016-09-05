package com.thinkcoo.mobile.domain;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/5/26.
 */
public class UseCaseRuntimeTrack {

    private long firstUseCaseStartTime;
    private long lastUseCaseEndTime;

    @Inject
    public UseCaseRuntimeTrack() {}

    public void firstUseCaseStart(){
        firstUseCaseStartTime = System.currentTimeMillis();
    }
    public void lastUseCaseEnd(){
        lastUseCaseEndTime = System.currentTimeMillis();
    }
    private long getAllUseCaseRunTimeInMillisecond(){
        return (firstUseCaseStartTime - lastUseCaseEndTime) / 1000;
    }
    public long getWelcomePageHoldMillisecond(){
        long caseRunTime = getAllUseCaseRunTimeInMillisecond();
        long remain = caseRunTime - 2000;
        if (remain <= 0){
            return 0;
        }else {
            return 2000 - caseRunTime;
        }
    }
}
