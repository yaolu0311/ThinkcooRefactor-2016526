package com.yz.im.ui.widget;

import android.content.Context;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.yz.im.model.im.util.EMMessageUtil;
import com.yz.im.ui.fragment.IMChatFragment;

/**
 * Created by Administrator on 2016/6/30.
 */
public class CustomChatRowProvider implements EaseCustomChatRowProvider {

    private static int typeCount = 3;

    private Context mContext;

    public CustomChatRowProvider(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCustomChatRowTypeCount() {
        return typeCount;
    }

    @Override
    public int getCustomChatRowType(EMMessage message) {
        if (message.getType() == EMMessage.Type.TXT) {
            if (EMMessageUtil.isUserCard(message)) {
                return message.direct() == EMMessage.Direct.RECEIVE ? IMChatFragment.MESSAGE_TYPE_RECV_USER_CARD : IMChatFragment.MESSAGE_TYPE_SENT_USER_CARD;
            }else if(EMMessageUtil.isSystemMessage(message)){
                return IMChatFragment.MESSAGE_TYPE_SYSTEM_MSG;
            }
        }
        return 0;
    }

    @Override
    public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
        if (message.getType() == EMMessage.Type.TXT) {
            if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_USR_CARD, false)) {
                return new UserCardChatRow(mContext, message, position, adapter);
            }else if(EMMessageUtil.isSystemMessage(message)){
                return new SystemNoticeChatRow(mContext, message, position, adapter);
            }
        }
        return null;
    }
}
