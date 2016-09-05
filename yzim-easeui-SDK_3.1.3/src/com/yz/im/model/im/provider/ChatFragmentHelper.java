package com.yz.im.model.im.provider;

import android.content.Context;
import android.view.View;

import com.example.administrator.publicmodule.util.IdOffsetUtil;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.yz.im.IMHelper;
import com.yz.im.model.cache.FriendCache;
import com.yz.im.model.entity.MessageExtraAttribute;
import com.yz.im.model.im.MessageWrapUtil;
import com.yz.im.ui.activity.ChatActivity;
import com.yz.im.ui.fragment.IMChatFragment;
import com.yz.im.ui.widget.CustomChatRowProvider;
import com.yz.im.utils.ToastUtil;

/**
 * Created by cys on 2016/7/1
 */
public class ChatFragmentHelper implements EaseChatFragment.EaseChatFragmentHelper {

    private Context mContext;
    private String mUserId;
    private FriendCache mFriendCache;

    private MessageExtraAttribute mExtraAttribute;

    public ChatFragmentHelper(Context context, String userId, MessageExtraAttribute attribute) {
        mContext = context;
        mUserId = userId;
        mExtraAttribute = attribute;
        mFriendCache = FriendCache.getInstance(context);
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if (mExtraAttribute == null) {
            return;
        }
        MessageWrapUtil.wrapMessage(message, mExtraAttribute);
    }

    @Override
    public void onEnterToChatDetails() {
    }

    @Override
    public void onAvatarClick(String userId) {
        String loginId = IMHelper.getInstance().getLoginUserId();
        userId = IdOffsetUtil.minusOffset(userId);
        if (loginId.equals(userId)) {
            return;
        }
        ((ChatActivity) mContext).mNavigator.navigateToUserInfoPage(mContext, userId);
    }

    @Override
    public void onAvatarLongClick(String username) {
    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        switch (message.getType()) {
            case TXT:
            case IMAGE:
            case LOCATION:
            case VIDEO:
            case VOICE:
            case FILE:
            case CMD:
                return false;
            default:
                ToastUtil.getInstance(mContext).showToast("暂不支持查看 " + message.getType() + " 类型消息");
                break;
        }
        return true;
    }

    @Override
    public boolean onMessageBubbleLongClick(EMMessage message) {
        return false;
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case IMChatFragment.ITEM_TAKE_PICTURE:
            case IMChatFragment.ITEM_PICTURE:
                return false;
            case IMChatFragment.ITEM_LOCATION:
                ((ChatActivity) mContext).mNavigator.navigateToLocalActivity(mContext);
                break;
            case IMChatFragment.ITEM_VIDEO:
                ((ChatActivity) mContext).mNavigator.navigateToVideoActivity(mContext);
                break;
            case IMChatFragment.ITEM_FILE:
                ((ChatActivity) mContext).mNavigator.navigateToSelectFileActivity(mContext);
                break;
            case IMChatFragment.ITEM_VOICE_CALL:
                ((ChatActivity) mContext).mNavigator.navigateToVoiceCallActivity(mContext, mUserId);
                break;
            case IMChatFragment.ITEM_VIDEO_CALL:
                ((ChatActivity) mContext).mNavigator.navigateToVideoCallPage(mContext, mUserId);
                break;
            case IMChatFragment.ITEM_USER_CARD:
                ((ChatActivity) mContext).mNavigator.navigateToFriendCardActivity(mContext);
                break;
            default:
                ToastUtil.getInstance(mContext).showToast("暂不支持该功能");
                break;
        }
        return true;
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider(mContext);
    }
}
