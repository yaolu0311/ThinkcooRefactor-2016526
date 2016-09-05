package com.yz.im.mvp.mvpContract;

import com.yz.im.model.entity.serverresponse.StrangerInfoResponse;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

/**
 * Created by cys on 2016/7/28
 */
public interface StrangerInfoContact {

    interface StrangerInfoView extends IMMvpView{
        void setData(StrangerInfoResponse response);
        void toggleButtonStatus();
    }

    abstract class StrangerInfoPresenter extends IMBasePresenter<StrangerInfoView>{
        public abstract void loadStrangerInfo(String userId);
        public abstract void updateShieldStatus(String userId, String type);
    }
}
