package com.yz.im.mvp.mvpContract;

import com.yz.im.model.entity.serverresponse.NoticeMessageResponse;
import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.IMMvpView;

import java.util.List;

/**
 * Created by cys on 2016/8/5
 */
public interface MessageNoticeContact {

    interface NoticeView extends IMMvpView{
        void setVerifyMsgList(List<NoticeMessageResponse> list);
        void setNoticeMsgList(List<NoticeMessageResponse> list);
        void refreshVerifyMessageData();
        void refreshOtherMessageData();
    }

    abstract class NoticePresenter extends IMBasePresenter<NoticeView> {
        public abstract void loadMessage(String type);
        public abstract void doWithMessage(NoticeMessageResponse  response, String operationType);
    }
}