package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.support.v7.app.AppCompatActivity;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/3/24.
 */
public class ActivityHistoryStack {

    List<WeakReference<AppCompatActivity>> mActivityLinkedList = new LinkedList<>();

    @Inject
    public ActivityHistoryStack() {
    }

    public void inStack(AppCompatActivity activity){
        if (null == activity) {
            return;
        }
        mActivityLinkedList.add(packWeakReference(activity));
    }

    private WeakReference<AppCompatActivity> packWeakReference(AppCompatActivity activity) {
        return  new WeakReference<>(activity);
    }

    public void outStack(AppCompatActivity activity){
        if (activity == null) {
            return;
        }
        Iterator<WeakReference<AppCompatActivity>> iterator = mActivityLinkedList.iterator();
        while(iterator.hasNext()){
            WeakReference<AppCompatActivity> activityWr  = (WeakReference<AppCompatActivity>)iterator.next();
            if (null != activityWr){
                AppCompatActivity activityFromWeakReference = getActivityFromWeakReference(activityWr);
                if (activity.equals(activityFromWeakReference)){
                    iterator.remove();
                }
            }
        }
    }

    private AppCompatActivity getActivityFromWeakReference(WeakReference<AppCompatActivity> activityWr) {
        if (null == activityWr){
            return  null;
        }
        return activityWr.get();
    }

    public void allFinish(){
        for (int i = 0 ; i < mActivityLinkedList.size() ; i ++) {
            WeakReference<AppCompatActivity> activityWr = mActivityLinkedList.get(i);
            AppCompatActivity activity = getActivityFromWeakReference(activityWr);
            if (null != activity && !activity.isFinishing()){
                activity.finish();
            }
        }
    }

    public int size(){
        return  mActivityLinkedList.size();
    }

}
