package com.yz.im.mvp.mvpContract;

import com.yz.im.model.db.entity.Group;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

/**
 * Created by cys on 2016/8/4
 */
public interface CreateGroupContact {

    interface CreateGroupView extends IMMvpView{
        void goToGroupInfoPage(Group group);
    }

    abstract class CreatePresenter extends IMBasePresenter<CreateGroupView>{
            public abstract void createGroup(String groupName, String isPublic, String isApproval, String circleType);
    }
}
