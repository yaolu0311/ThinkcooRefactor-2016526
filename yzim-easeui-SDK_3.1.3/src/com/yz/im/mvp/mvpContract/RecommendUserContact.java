package com.yz.im.mvp.mvpContract;

import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

import java.util.List;

/**
 * Created by cys on 2016/8/2
 */
public interface RecommendUserContact {

    interface RecommendUserView extends IMMvpView{
        void setList(List<FindUserResponse> list);
    }

    abstract class RecommendPresenter extends IMBasePresenter<RecommendUserView>{
        public abstract void loadRecommendUser(String searchValue, String findUserType, String pageNo, String pageSize);
    }
}
