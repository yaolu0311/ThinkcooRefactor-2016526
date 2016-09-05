package com.yz.im.mvp.mvpContract;

import com.yz.im.model.entity.serverresponse.GroupMemberResponse;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

import java.util.List;

/**
 * Created by cys on 2016/7/27
 */
public interface GroupMemberContact {

    interface GroupMemberView extends IMMvpView{
        void setData(List<GroupMemberResponse> list);
    }

    abstract class GroupMemberPresenter extends IMBasePresenter<GroupMemberView> {
        public abstract void loadGroupMemberList(String groupId);
        public abstract void transferGroup(String groupId, String newUserId);
    }
}
