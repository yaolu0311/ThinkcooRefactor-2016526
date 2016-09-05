package com.yz.im.mvp.mvpContract;

import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

import java.util.List;

/**
 * Created by cys on 2016/8/2
 */
public interface TelephoneContact {

    interface TelephoneView extends IMMvpView{
        void setData(List<FindUserResponse> list);
    }

    abstract class TelephonePresenter extends IMBasePresenter<TelephoneView>{
        public abstract void loadTelephoneContact();
    }
}
