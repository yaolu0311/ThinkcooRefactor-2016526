package com.yz.im.mvp.mvpContract;

import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

/**
 * ThinkCoo20160718
 * Created by cys on 2016/7/23
 */
public interface ReportContact {

    interface ReportView extends IMMvpView{
    }

    abstract class ReportPresenter extends IMBasePresenter<ReportView> {
        public abstract void uploadReport(String friendId,String groupId, String reportReason, String context);
    }
}
