package com.yz.im.model.strategy;

import android.os.Parcelable;

import com.yz.im.mvp.IMBasePresenter;

/**
 * Created by cys on 2016/8/2
 */
public interface SendInviteReasonStrategy extends Parcelable{

    IMBasePresenter getPresenter();
    String getUserId();
    String getGroupId();
}
