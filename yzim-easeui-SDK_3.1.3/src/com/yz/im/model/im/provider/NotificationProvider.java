package com.yz.im.model.im.provider;

import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import com.yz.im.ui.activity.ChatActivity;
import com.yz.im.ui.activity.VideoCallActivity;
import com.yz.im.ui.activity.VoiceCallActivity;

/**
 * Created by cys on 2016/7/2
 */
public class NotificationProvider implements NotificationProviderInterface {

    private Context mContext;
    private IMHelper mHelper;

    @Override
    public String getTitle(EMMessage message) {
        //you can update title here
        return null;
    }

    @Override
    public int getSmallIcon(EMMessage message) {
        //you can update icon here
        return 0;
    }

    @Override
    public String getDisplayedText(EMMessage message) {
        // be used on notification bar, different text according the message type.
        String ticker = EaseCommonUtils.getMessageDigest(message, mContext);
        if (message.getType() == EMMessage.Type.TXT) {
            ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
        }
//        EaseUser user = mHelper.getUserInfo(message.getFrom());
//        if (user != null) {
//            if (EaseAtMessageHelper.get().isAtMeMsg(message)) {
//                return String.format(mContext.getString(R.string.at_your_in_group), user.getNick());
//            }
//            return user.getNick() + ": " + ticker;
//        } else {
//            if (EaseAtMessageHelper.get().isAtMeMsg(message)) {
//                return String.format(mContext.getString(R.string.at_your_in_group), message.getFrom());
//            }
//            return message.getFrom() + ": " + ticker;
//        }
        return "";
    }

    @Override
    public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
        // here you can customize the text.
        // return fromUsersNum + "contacts send " + messageNum + "messages to you";
        return null;
    }

    @Override
    public Intent getLaunchIntent(EMMessage message) {
        // you can set what activity you want display when user click the notification
        Intent intent = new Intent(mContext, ChatActivity.class);
        // open calling activity if there is call
        if (mHelper.isVideoCalling) {
            intent = new Intent(mContext, VideoCallActivity.class);
        } else if (mHelper.isVoiceCalling) {
            intent = new Intent(mContext, VoiceCallActivity.class);
        } else {
            EMMessage.ChatType chatType = message.getChatType();
            if (chatType == EMMessage.ChatType.Chat) { // single chat message
                intent.putExtra("userId", message.getFrom());
                intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
            } else { // group chat message
                // message.getTo() is the group id
                intent.putExtra("userId", message.getTo());
                if (chatType == EMMessage.ChatType.GroupChat) {
                    intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                } else {
                    intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
                }

            }
        }
        return null;
    }
}
