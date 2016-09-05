package com.yz.im.mvp.mvpContract;

import com.yz.im.model.entity.serverresponse.FriendInfoResponse;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

/**
 * Created by cys on 2016/7/11
 * in com.yz.im.mvp.mvpContract
 * @function  个人信息页面
 */
public interface ProfileContract {

    interface ProfileView extends IMMvpView{
        void setData(FriendInfoResponse response);
        void refreshToggleStatus();
    }

    abstract class ProfilePresenter extends IMBasePresenter<ProfileView> {
        public abstract void loadFriendInfo(String friendId);


    }
}
