package com.yz.im.mvp.mvpContract;

import com.yz.im.model.db.entity.Group;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

import java.util.List;

/**
 * Created by cys on 2016/7/22
 */
public interface GroupListContract {

    interface GroupListView extends IMMvpView {
        void setData(List<Group> data);
    }

    abstract class GroupListPresenter extends IMBasePresenter<GroupListView> {
        public abstract void loadGroupList();
    }

}
