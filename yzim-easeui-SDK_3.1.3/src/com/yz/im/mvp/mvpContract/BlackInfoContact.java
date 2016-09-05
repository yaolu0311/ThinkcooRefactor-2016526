package com.yz.im.mvp.mvpContract;

import com.yz.im.model.entity.serverresponse.FriendInfoResponse;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

/**
 * Created by cys on 2016/7/28
 */
public interface BlackInfoContact {

    interface BlackInfoView extends IMMvpView{
        void setData(FriendInfoResponse response);
        void changeToggleStatus();
    }

    abstract class BlackInfoPresenter extends IMBasePresenter<BlackInfoContact.BlackInfoView>{
        public abstract void loadBlackFriendInfo(String userId);
        public abstract void deleteFriend(String friendId);
        public abstract void reliefBlack(String friendId);
        public abstract void addFriendToBlack(String friendId, String popMsg, String disturbMsg, String blackList);
    }
}
