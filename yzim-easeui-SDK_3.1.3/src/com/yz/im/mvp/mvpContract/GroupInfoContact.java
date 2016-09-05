package com.yz.im.mvp.mvpContract;

import com.yz.im.model.entity.serverresponse.GroupInfoResponse;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

/**
 * ThinkCoo20160718
 * Created by cys on 2016/7/23
 */
public interface GroupInfoContact {

    interface GroupInfoView extends IMMvpView{
        void setData(GroupInfoResponse response);
        void changeToggleStatus();
    }

    abstract class GroupInfoPresenter extends IMBasePresenter<GroupInfoView> {
        public abstract void loadGroupInfo(String groupId);
    }
}
