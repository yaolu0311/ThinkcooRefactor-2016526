package com.yz.im.ui.fragment;

import com.yz.im.ui.activity.NoticeMessageActivity;

/**
 * Created by cys on 2016/8/5
 */
public class NoticeOtherFragment extends BaseNoticeMessageFragment {

    @Override
    protected String setMessageType() {
        return NoticeMessageActivity.TYPE_OTHER_MSG;
    }

    @Override
    protected void lazyLoad() {
        ((NoticeMessageActivity)getContext()).refreshOtherMessageData();
    }

    @Override
    protected String getDeleteMsgType() {
        return NoticeMessageActivity.ACTION_DELETE_OTHRE_MSG;
    }

}
