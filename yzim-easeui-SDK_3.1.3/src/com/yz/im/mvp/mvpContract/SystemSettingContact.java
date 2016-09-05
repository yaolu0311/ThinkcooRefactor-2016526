package com.yz.im.mvp.mvpContract;

import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

/**
 * Created by cys on 2016/8/3
 */
public interface SystemSettingContact {

    interface SystemSettingView extends IMMvpView{
        void refreshStatus();
    }

    abstract class SettingPresenter extends IMBasePresenter<SystemSettingView>{
        public abstract void updateSetting(String newMessage, String textingMsg, String unfamiliarMsg);
    }
}
