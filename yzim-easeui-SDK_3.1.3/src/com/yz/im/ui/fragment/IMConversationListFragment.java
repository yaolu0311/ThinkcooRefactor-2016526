package com.yz.im.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.publicmodule.util.IdOffsetUtil;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import com.yz.im.ui.component.MenuPopupWindow;
import com.yz.im.utils.PreferenceManager;
import com.yz.im.utils.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/6/29.
 */
public class IMConversationListFragment extends EaseConversationListFragment implements View
        .OnClickListener {

    private TextView errorText;
    private View headView;
    private LinearLayout recommendLlayout;
    private RelativeLayout msgNotice;
    private TextView redIconNotice;
    private IMHelper mHelper;
    private MenuPopupWindow mPopupWindow;

    private LocalBroadcastManager mBroadcastManager;
    private ConversationReceiver mReceiver;

    private EMMessageListener mListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerBroadCast();
    }

    private void registerBroadCast() {
        mBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_NEW_NOTICE_RECEIVE);
        mReceiver = new ConversationReceiver();
        mBroadcastManager.registerReceiver(mReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation_list, container, false);
        mHelper = IMHelper.getInstance();
        mPopupWindow = new MenuPopupWindow(getContext());
        return view;
    }

    @Override
    protected void initView() {
        super.initView();
        inflateErrorNoticeLayout();
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        addHeadViewAndEvent();
        setTitleLayout();
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position - 1);
                gotoChatActivity(conversation);
            }
        });
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())) {
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PreferenceManager.getInstance().getNewNotification()) {
            redIconNotice.setVisibility(View.VISIBLE);
        } else {
            redIconNotice.setVisibility(View.GONE);
        }
        if (!isConflict) {
            updateUnreadCount();
        }
        // unregister this event listener when this activity enters the background
        IMHelper.getInstance().pushActivity(getActivity());
        EMClient.getInstance().chatManager().addMessageListener(mListener);
    }

    private void addHeadViewAndEvent() {
        headView = LayoutInflater.from(getContext()).inflate(R.layout.head_conversation_list, null);
        conversationListView.addHeaderView(headView);
        recommendLlayout = (LinearLayout) headView.findViewById(R.id.layout_recommend_user);
        msgNotice = (RelativeLayout) headView.findViewById(R.id.layout_msg_notice);
        redIconNotice = (TextView) headView.findViewById(R.id.tv_new_message_notice);

        msgNotice.setOnClickListener(this);
        recommendLlayout.setOnClickListener(this);
    }

    @Override
    public void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(mListener);
        IMHelper.getInstance().popActivity(getActivity());
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBroadcastManager.unregisterReceiver(mReceiver);
    }

    /**
     * 无网络等情况的提示
     */
    private void inflateErrorNoticeLayout() {
        View errorView = View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
    }

    private void setTitleLayout() {
        titleBar.setLeftImageResource(R.drawable.icon_contacts);
        titleBar.setRightImageResource(R.drawable.conversation_list_menu);
        titleBar.setTitle(getString(R.string.hx_title));
        titleBar.setLeftLayoutClickListener(this);
        titleBar.setRightLayoutClickListener(this);
    }

    private void gotoChatActivity(EMConversation conversation) {
        String chatId = conversation.getUserName();
        if (chatId.equals(EMClient.getInstance().getCurrentUser())) {
            ToastUtil.getInstance(getContext()).showToastById(R.string.Cant_chat_with_yourself);
            return;
        }
        int chatType = Constant.CHATTYPE_SINGLE;
        if (conversation.isGroup()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom) {
                chatType = Constant.CHATTYPE_CHATROOM;
            } else {
                chatType = Constant.CHATTYPE_GROUP;
            }
            IMHelper.getInstance().getNavigator().navigateToGroupChatActivity(getContext(), chatId);
        } else {
            chatId = IdOffsetUtil.minusOffset(chatId);
            IMHelper.getInstance().getNavigator().navigateToSingleChatActivity(getContext(), chatId);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.left_layout == id) {
            IMHelper.getInstance().getNavigator().navigateToFriendListActivity(getContext());
        } else if (R.id.right_layout == id) {
            mPopupWindow.showAsDropDown(titleBar.getRightImage());
        } else if (R.id.layout_recommend_user == id) {
            mHelper.getNavigator().navigateToRecommendUserListActivity(getContext());
        } else if (R.id.layout_msg_notice == id) {
            mHelper.getNavigator().navigateToNoticeMsgList(getContext());
        }
    }

    private void refreshUIWithMessage() {
        getActivity().runOnUiThread(new Runnable() {
                                        public void run() {
                                            updateUnreadCount();
                                            refresh();
                                        }
                                    }

        );
    }

    private void updateUnreadCount() {
        int count = getUnreadMsgCountTotal();
        Intent intent = new Intent(Constant.ACTION_REFRESH_UNREAD_MESSAGE_COUNT);
        if (count > 0) {
            intent.putExtra(Constant.KEY_UNREAD_MESSAGE, count);
        } else {
            intent.putExtra(Constant.KEY_UNREAD_MESSAGE, 0);
        }
        getActivity().sendBroadcast(intent);
    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal;
        int chatRoomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        for (EMConversation conversation : EMClient.getInstance().chatManager()
                .getAllConversations().values()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatRoomUnreadMsgCount = chatRoomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatRoomUnreadMsgCount;
    }

    class ConversationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Constant.ACTION_NEW_NOTICE_RECEIVE.equals(action)) {
                redIconNotice.setVisibility(View.VISIBLE);
            }
        }
    }

}
