package com.yz.im.model.im;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.yz.im.model.entity.MessageExtraAttribute;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by cys on 2016/8/8
 */
public class MessageWrapUtil {

    private static final String TAG = "MessageWrapUtil";

    public static EMMessage wrapMessage(EMMessage message, MessageExtraAttribute extraAttribute) {
        if (message == null) {
            throw new NullPointerException("message is null which is to be wrap");
        }

        if (extraAttribute == null) {
            return message;
        }

        Map<String, String> map = extraAttribute.getMap();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            message.setAttribute(key, map.get(key));
        }
        return message;
    }

    public static String getSenderIdFromExtraAttributes(EMMessage message) {
        String senderId = "";
        if (message == null) {
            return senderId;
        }
        try {
            senderId = message.getStringAttribute(MessageExtraAttribute.KEY_SENDER_ID);
        } catch (HyphenateException e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
        return senderId;
    }

    public static String getSenderNameFromExtraAttributes(EMMessage message) {
        String name = "";
        if (message == null) {
            name = "";
        }
        try {
            name = message.getStringAttribute(MessageExtraAttribute.KEY_SENDER_NAME);
        } catch (HyphenateException e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
        return name;
    }

    public static String getSenderImageFromExtraAttributes(EMMessage message) {
        String image = "";
        if (message == null) {
            return image;
        }
        try {
            image = message.getStringAttribute(MessageExtraAttribute.KEY_SENDER_IMAGE);
        } catch (HyphenateException e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
        return image;
    }

    public static String getReceiverIdExtraAttributes(EMMessage message) {
        String receiverId = "";
        if (message == null) {
            return receiverId;
        }
        try {
            receiverId = message.getStringAttribute(MessageExtraAttribute.KEY_RECEIVER_ID);
        } catch (HyphenateException e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
        return receiverId;
    }

    public static String getGroupNameExtraAttributes(EMMessage message) {
        String groupName = "";
        if (message == null) {
            return groupName;
        }
        try {
            groupName = message.getStringAttribute(MessageExtraAttribute.KEY_GROUP_NAME);
        } catch (HyphenateException e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
        return groupName;
    }

    public static String getGroupImageExtraAttributes(EMMessage message) {
        String image = "";
        if (message == null) {
            return image;
        }
        try {
            image = message.getStringAttribute(MessageExtraAttribute.KEY_GROUP_IMAGE);
        } catch (HyphenateException e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
        return image;
    }

    public static String getGroupNickNameExtraAttributes(EMMessage message) {
        String nickName = "";
        if (message == null) {
            return nickName;
        }
        try {
            nickName = message.getStringAttribute(MessageExtraAttribute.KEY_SENDER_GROUP_REMARK);
        } catch (HyphenateException e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
        return nickName;
    }

}
