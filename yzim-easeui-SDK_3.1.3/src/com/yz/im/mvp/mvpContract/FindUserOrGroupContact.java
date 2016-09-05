package com.yz.im.mvp.mvpContract;

import com.yz.im.model.entity.serverresponse.FindGroupResponse;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

import java.util.List;

/**
 * Created by cys on 2016/8/1
 */
public interface FindUserOrGroupContact {

    interface FindUserGroupView extends IMMvpView{

        void gotoUserInfoPage(FindUserResponse response);
        void gotoGroupListPage(List<FindGroupResponse> list);
        void setGroupListData(List<FindGroupResponse> list);

    }

    abstract class FindUserGroupPresenter extends IMBasePresenter<FindUserGroupView>{
        public abstract void findUserByNumber(String searchValue, String findUserType, String pageNo, String pageSize);
//        public abstract void findUserByName(String areaCode, String school, String department, String professional, String realName, String pageNo, String pageSize);
        public abstract void loadInterestGroup(String groupValue, String groupType, String pageNo, String pageSize);
        public abstract void findGroup(String groupValue, String groupType, String pageNumber, String pageSize);
    }
}
