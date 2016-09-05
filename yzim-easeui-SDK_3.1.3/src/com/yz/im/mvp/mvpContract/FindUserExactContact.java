package com.yz.im.mvp.mvpContract;

import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

import java.util.List;

/**
 * Created by cys on 2016/8/2
 * 精确查找好友
 */
public interface FindUserExactContact {

    interface FindUserExactView extends IMMvpView {
        void goToUserListPage(List<FindUserResponse> list);
    }

    abstract class FindUserExactPresenter extends IMBasePresenter<FindUserExactView> {
        public abstract void loadUserList(String areaCode, String school, String department, String professional, String realName, String pageNo, String pageSize);
    }
}
