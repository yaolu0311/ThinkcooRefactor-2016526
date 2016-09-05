package com.yz.im.mvp.mvpContract;

import com.yz.im.model.db.entity.Friend;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

import java.util.List;

/**
 * Created by cys on 2016/8/3
 */
public interface BlackListContact {

    interface BlackListView extends IMMvpView {
        void setData(List<Friend> list);
    }

    abstract class BlackUserListPresenter extends IMBasePresenter<BlackListView> {
        public abstract void loadBlackUserList(String userType);
    }
}
