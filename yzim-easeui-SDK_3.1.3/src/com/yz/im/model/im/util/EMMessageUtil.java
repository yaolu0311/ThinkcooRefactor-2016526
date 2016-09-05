package com.yz.im.model.im.util;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.util.IdOffsetUtil;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.yz.im.IMHelper;
import com.yz.im.model.cache.FriendCache;
import com.yz.im.model.cache.GroupCache;
import com.yz.im.model.cache.ShieldCache;
import com.yz.im.model.cache.SystemSettingCache;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.db.entity.Shield;
import com.yz.im.model.entity.MessageExtraAttribute;
import com.yz.im.model.im.MessageWrapUtil;

/**
 * Created by cys on 2016/8/15
 */
public class EMMessageUtil {

    private static final String TAG = "EMMessageUtil";

    private Context mContext;
    private FriendCache friendCache;
    private ShieldCache mShieldCache;
    private SystemSettingCache mSettingCache;

    public EMMessageUtil(Context context) {
        mContext = context;
        friendCache = FriendCache.getInstance(mContext.getApplicationContext());
        mShieldCache = ShieldCache.getInstance(context.getApplicationContext());
        mSettingCache = SystemSettingCache.getInstance(mContext.getApplicationContext());
    }

    public void refreshInfo(EMMessage message) {
        EMMessage.ChatType type = getMessageChatType(message);
        if (EMMessage.ChatType.Chat.equals(type)) {
            refreshUserInfo(message);
        } else if (EMMessage.ChatType.GroupChat.equals(type)) {
            refreshGroupInfo(message);
        }
    }

    /**
     * ====================== 群组信息相关 ======================
     */

    private void refreshGroupInfo(EMMessage message) {
        if (message == null) {
            return;
        }

        if (EMMessage.ChatType.GroupChat.equals(getMessageChatType(message))) {
            Group group = IMHelper.getInstance().getGroupInfo(message.getTo(), true);
            if (group == null) {
                return;
            }
            //消息是否来自圈主
            String senderId = message.getFrom();
            if (!isMessageFromGroupOwner(group, IdOffsetUtil.minusOffset(senderId))) {
                return;
            }
            //圈子头像、名称是否发生改变
            if (isGroupInfoHasChanged(group, message)) {
                GroupCache groupCache = GroupCache.getInstance(mContext);
                groupCache.insert(group);
            }
        }
    }

    private boolean isMessageFromGroupOwner(Group group, String senderId) {
        if (group == null || TextUtils.isEmpty(senderId)) {
            return false;
        }
        String groupOwnerId = group.getOldUserId();
        return senderId.equals(groupOwnerId);
    }

    private boolean isGroupInfoHasChanged(Group group, EMMessage message) {
        if (group == null || message == null) {
            return false;
        }

        String newGroupImage = MessageWrapUtil.getGroupImageExtraAttributes(message);
        String newGroupName = MessageWrapUtil.getGroupNameExtraAttributes(message);

        return judgeGroupImageHasChanged(group, newGroupImage) || judgeGroupNameHasChanged(group, newGroupName);
    }

    private boolean judgeGroupImageHasChanged(Group group, String newImageUri) {
        boolean flag = false;
        if (group == null || TextUtils.isEmpty(newImageUri)) {
            return flag;
        }
        String oldGroupImage = group.getGroupImage();
        flag = newImageUri.equals(oldGroupImage);
        if (!flag) {
            group.setGroupImage(newImageUri);
        }
        return !flag;
    }

    private boolean judgeGroupNameHasChanged(Group group, String newGroupName) {
        boolean flag = false;
        if (group == null || TextUtils.isEmpty(newGroupName)) {
            return flag;
        }
        String oldGroupName = group.getGroupName();
        flag = newGroupName.equals(oldGroupName);
        if (!flag) {
            group.setGroupName(newGroupName);
        }
        return !flag;
    }

    /**
     * ====================== 好友信息相关 ========================
     */

    private void refreshUserInfo(EMMessage message) {
        if (message == null) {
            return;
        }
        if (EMMessage.ChatType.Chat.equals(getMessageChatType(message))) {
            String senderId = IdOffsetUtil.minusOffset(message.getFrom());
            if (friendCache.isFriend(senderId)) {
                Friend friend = IMHelper.getInstance().getFriendInfo(senderId);
                if (friend == null) {
                    return;
                }
                if (isFriendInfoChanged(friend, message)) {
                    friendCache.insert(friend);
                }
            } else {
                Shield shield = mShieldCache.getEntity(senderId);
                if (shield == null) {
                    insertDataIntoShieldDb(message);
                } else {
                    if (isShieldInfoChanged(shield, message)) {
                        mShieldCache.insert(shield);
                    }
                }
            }
        }
    }

    private boolean isFriendInfoChanged(Friend friend, EMMessage message) {
        if (friend == null || message == null) {
            return false;
        }

        String newFriendImage = MessageWrapUtil.getSenderImageFromExtraAttributes(message);
        String newFriendName = MessageWrapUtil.getSenderNameFromExtraAttributes(message);

        return judgeFriendImageHasChanged(friend, newFriendImage) || judgeFriendNameHasChanged(friend, newFriendName);
    }

    private boolean judgeFriendImageHasChanged(Friend friend, String newImageUri) {
        boolean flag = false;
        if (friend == null || TextUtils.isEmpty(newImageUri)) {
            return flag;
        }
        String oldFriendImage = friend.getImage();
        flag = newImageUri.equals(oldFriendImage);
        if (!flag) {
            friend.setImage(newImageUri);
        }
        return !flag;
    }

    private boolean judgeFriendNameHasChanged(Friend friend, String newFriendName) {
        boolean flag = false;
        if (friend == null || TextUtils.isEmpty(newFriendName)) {
            return flag;
        }
        String oldFriendName = friend.getRealName();
        flag = newFriendName.equals(oldFriendName);
        if (!flag) {
            friend.setRealName(newFriendName);
            //更新昵称
            String showName = friend.getShowName();
            if (oldFriendName.equals(showName)) {
                Friend.setName(friend, newFriendName);
            }
        }
        return !flag;
    }

    private boolean isShieldInfoChanged(Shield shield, EMMessage message) {
        if (shield == null && message == null) {
            return false;
        }

        String newFriendImage = MessageWrapUtil.getSenderImageFromExtraAttributes(message);
        String newFriendName = MessageWrapUtil.getSenderNameFromExtraAttributes(message);

        return judgeShieldImageHasChanged(shield, newFriendImage) || judgeShieldNameHasChanged(shield, newFriendName);
    }

    private boolean judgeShieldImageHasChanged(Shield shield, String newImageUri) {
        boolean flag = false;
        if (shield == null || TextUtils.isEmpty(newImageUri)) {
            return flag;
        }
        String oldFriendImage = shield.getImager();
        flag = newImageUri.equals(oldFriendImage);
        if (!flag) {
            shield.setImager(newImageUri);
        }
        return !flag;
    }

    private boolean judgeShieldNameHasChanged(Shield shield, String newUserName) {
        boolean flag = false;
        if (shield == null || TextUtils.isEmpty(newUserName)) {
            return flag;
        }
        String oldUserName = shield.getName();
        flag = newUserName.equals(oldUserName);
        if (!flag) {
            shield.setName(newUserName);
        }
        return !flag;
    }

    private void insertDataIntoShieldDb(EMMessage message) {
        Shield shield;
        try {
            shield = new Shield();
            shield.setShield("0");
            shield.setUserName(message.getStringAttribute(MessageExtraAttribute.KEY_SENDER_NAME));
            shield.setName(shield.getUserName());
            shield.setImager(message.getStringAttribute(MessageExtraAttribute.KEY_SENDER_IMAGE));
            shield.setUserId(message.getStringAttribute(MessageExtraAttribute.KEY_SENDER_ID));
            ShieldCache shieldCache = ShieldCache.getInstance(mContext);
            shieldCache.insert(shield);
        } catch (HyphenateException e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * ====================== 消息过滤相关 ========================
     */

    public boolean filterMessage(EMMessage message) {
        boolean flag = false;
        if (message == null) {
            flag = true;
        }
        if (EMMessage.ChatType.Chat.equals(getMessageChatType(message))) {
            FriendCache friendCache = FriendCache.getInstance(mContext);
            String senderId = IdOffsetUtil.minusOffset(message.getFrom());
            if (friendCache.isFriend(senderId)) {
                if (friendCache.isBlackUser(senderId)) {
                    flag = true;
                }
            } else {
                if (!mSettingCache.allowReceiveStrangerMsg()) {
                    flag = true;
                } else {
                    if (isShieldStranger(message, senderId)) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    private boolean isShieldStranger(EMMessage message, String senderId) {
        boolean flag = false;
        if (EMMessage.ChatType.Chat.equals(getMessageChatType(message))) {
            ShieldCache shieldCache = ShieldCache.getInstance(mContext);
            flag = shieldCache.isShield(senderId);
        }
        return flag;
    }

    /**
     * ====================== 其他 ========================
     */
    public EMMessage.ChatType getMessageChatType(EMMessage message) {
        return message.getChatType();
    }

    public static boolean isSystemMessage(EMMessage message) {
        boolean flag = false;
        EMMessage.Type type = message.getType();
        switch (type) {
            case TXT:
                EMTextMessageBody body = (EMTextMessageBody) message.getBody();
                String content = body.getMessage();
                if (!TextUtils.isEmpty(content)) {
                    flag = body.getMessage().startsWith("xtxx@xtxx");
                }
                break;
        }
        return flag;
    }

    public static boolean isUserCard(EMMessage message) {
        boolean flag = false;
        EMMessage.Type type = message.getType();
        switch (type) {
            case TXT:
                flag = message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_USR_CARD, false);
                break;
        }
        return flag;
    }

    public static String getChatId(EMMessage message) {
        String chatId = null;
        if (message == null) {
            return chatId;
        }
        EMMessage.ChatType type = message.getChatType();
        if (EMMessage.ChatType.Chat.equals(type)) {
            chatId = IdOffsetUtil.minusOffset(message.getFrom());
        } else if (EMMessage.ChatType.GroupChat.equals(type)) {
            chatId = message.getTo();
        }
        return chatId;
    }

    public void deleteMessage(EMMessage message) {
        String id = null;
        EMMessage.ChatType type = getMessageChatType(message);
        if (EMMessage.ChatType.Chat.equals(type)) {
            id = message.getFrom();
        } else if (EMMessage.ChatType.GroupChat.equals(type)) {
            id = message.getTo();
        }
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(id);
        if (conversation != null) {
            conversation.removeMessage(message.getMsgId());
        }
    }
}
