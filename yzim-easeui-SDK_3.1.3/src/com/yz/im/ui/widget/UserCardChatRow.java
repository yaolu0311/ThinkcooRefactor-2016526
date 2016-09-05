package com.yz.im.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.publicmodule.util.IdOffsetUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.exceptions.HyphenateException;
import com.yz.im.ui.activity.ChatActivity;
import com.yz.im.utils.GlideRoundTransform;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cys on 2016/6/30.
 */
public class UserCardChatRow extends EaseChatRow {

    private ImageView mUserCardHead;
    private TextView mUserName;
    private TextView mUserId;
    Map<String, String> map;

    private static String[] tags = new String[]{"tag", "userId", "userImage", "userName","hxUserId", "type"};

    private EMTextMessageBody mMessageBody;

    public UserCardChatRow(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_USR_CARD, false)){
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.chat_row_user_card_recv : R.layout.chat_row_user_card_send, this);
        }
    }

    @Override
    protected void onFindViewById() {
        mUserCardHead = (ImageView) findViewById(R.id.iv_user_card);
        mUserName = (TextView) findViewById(R.id.tv_user_name);
        mUserId = (TextView) findViewById(R.id.tv_user_id);
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSetUpView() {
        mMessageBody = (EMTextMessageBody) message.getBody();
        String content = mMessageBody.getMessage();
        createMapFromContent(content);

        Glide.with(context).load(map.get(tags[2])).error(R.drawable.default_avatar).placeholder(R.drawable.default_avatar)
                .transform(new GlideRoundTransform(context, 10)).into(mUserCardHead);
        mUserName.setText(map.get(tags[3]));
        mUserId.setText(map.get(tags[4]));

        handleTextMessage();
    }

    private void createMapFromContent(String content) {
        map = new HashMap<>();
        if (!TextUtils.isEmpty(content)) {
            String[] array = content.split(";");
            int count  = array.length > tags.length ? tags.length : array.length;
            for (int i = 0; i < count; i++) {
                map.put(tags[i], array[i]);
            }
        }
    }

    protected void handleTextMessage() {
        if (message.direct() == EMMessage.Direct.SEND) {
            setMessageSendCallback();
            switch (message.status()) {
                case CREATE:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    // 发送消息
//                sendMsgInBackground(message);
                    break;
                case SUCCESS: // 发送成功
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.GONE);
                    break;
                case FAIL: // 发送失败
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    break;
                case INPROGRESS: // 发送中
                    progressBar.setVisibility(View.VISIBLE);
                    statusView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }else{
            if(!message.isAcked() && message.getChatType() == EMMessage.ChatType.Chat){
                try {
                    EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onBubbleClick() {
        String type = map.get(tags[5]);
        if (TextUtils.isEmpty(type)) {
            return;
        }
        if ("friend".equals(type)) {
            showUserInfo();
        }else if("group".equals(type)){
            showGroupInfo();
        }

    }

    private void showUserInfo() {
        String userId = mUserId.getText().toString().trim();
        if (TextUtils.isEmpty(userId)) {
            return;
        }
        userId = IdOffsetUtil.minusOffset(userId);
        ((ChatActivity) context).mNavigator.navigateToUserInfoPage(context, userId);
    }

    private void showGroupInfo() {
        String groupId = mUserId.getText().toString().trim();
        if (TextUtils.isEmpty(groupId)) {
            return;
        }
        ((ChatActivity) context).mNavigator.navigateToGroupInfoPage(context, groupId, true);
    }

    @Override
    protected List<String> initListItems() {
        String[] array = context.getResources().getStringArray(R.array.user_card_selection_item);
        return Arrays.asList(array);
    }

}
