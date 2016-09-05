package com.yz.im.mvp.mvpContract;

import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

/**
 * Created by cys on 2016/7/29
 */
public interface BaseInviteContact {

    interface BaseInviteView extends IMMvpView {

    }

    abstract class BaseInvitePresenter extends IMBasePresenter<BaseInviteView> {
//        public abstract void sendInviteReason(String groupId, String friendIdList, String content);
        public abstract void sendInviteReason(String... args);
    }
}
