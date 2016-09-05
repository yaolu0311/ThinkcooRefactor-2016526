package com.yz.im.model.im.listener;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.google.gson.Gson;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import com.yz.im.model.entity.RockEntity;
import com.yz.im.model.im.util.EMMessageUtil;
import com.yz.im.utils.PreferenceManager;

import java.util.List;

/**
 * Created by cys on 2016/7/1
 * in com.yz.im.model.im.listener
 */
public class MessageListener implements EMMessageListener {

    private static final String TAG = "MessageListener";

    private Context mContext;
    private IMHelper mIMHelper;
    private EMMessageUtil mMessageUtil;

    public MessageListener(Context context) {
        mContext = context.getApplicationContext();
        mIMHelper = IMHelper.getInstance().getInstance();
    }

    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        createMessageUtil();
        EaseAtMessageHelper.get().parseMessages(messages);
        for (EMMessage message : messages) {
            if (mMessageUtil.filterMessage(message)) {
                mMessageUtil.deleteMessage(message);
            } else {
                mMessageUtil.refreshInfo(message);
                getNotifier().onNewMsg(message);
            }
        }
    }

    private void createMessageUtil() {
        if (mMessageUtil == null) {
            mMessageUtil = new EMMessageUtil(mContext.getApplicationContext());
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        for (EMMessage message : messages) {
            EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
            if (isRockCmdMessage(cmdMsgBody)) {
                sendBroadCastByMessage(cmdMsgBody);
            } else {
                PreferenceManager.getInstance().setNewNotification(true);
                mIMHelper.broadcastManager.sendBroadcast(new Intent(Constant.ACTION_NEW_NOTICE_RECEIVE));
                mIMHelper.getNotifier().variableAndPlayTone(message);
            }
        }
    }

    private boolean isRockCmdMessage(EMCmdMessageBody body) {
        boolean flag = false;
        if (body == null) {
            return flag;
        }
        boolean flagType = body.toString().contains("type");
        boolean flagUuid = body.toString().contains("uuid");
        boolean flagMsg = body.toString().contains("msg");
        flag = flagType && flagUuid && flagMsg;

        return flag;
    }

    private void sendBroadCastByMessage(EMCmdMessageBody body) {
        if (body == null) {
            return;
        }
        try {
            String action = body.action();
            Gson gson = new Gson();
            RockEntity entity = gson.fromJson(action, RockEntity.class);
            String msg = entity.getMsg();
            switch (msg) {
                case "摇一摇点名":
                    Intent intent = new Intent("yzke.action.uploadLocation");
                    intent.putExtra("uuid", entity.getUuid());
                    mContext.sendBroadcast(intent);
                    break;
            }
        } catch (Exception e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
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

    private EaseNotifier getNotifier() {
        return mIMHelper.getInstance().easeUI.getNotifier();
    }
}

