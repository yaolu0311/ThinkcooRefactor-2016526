/************************************************************
 * * EaseMob CONFIDENTIAL
 * __________________
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p/>
 * NOTICE: All information contained herein is, and remains
 * the property of EaseMob Technologies.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from EaseMob Technologies.
 */
package com.hyphenate.easeui.model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.example.administrator.publicmodule.util.IdOffsetUtil;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseSettingsProvider;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.EasyUtils;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import com.yz.im.model.cache.FriendCache;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.im.MessageWrapUtil;
import com.yz.im.model.im.util.EMMessageUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * 新消息提醒class
 * <p/>
 * this class is subject to be inherited and implement the relative APIs
 */
public class EaseNotifier {
    private final static String TAG = "notify";

    private static final String KEY_TYPE = "key_type";
    private static final String KEY_CHAT_ID = "key_chat_id";

    protected final static String[] msg_eng = {"sent a message", "sent a picture", "sent a voice",
            "sent location message", "sent a video", "sent a file", "%1 contacts sent %2 messages",
            "sent user card"
    };
    protected final static String[] msg_ch = {"发来一条消息", "发来一张图片", "发来一段语音", "发来位置信息", "发来一个视频", "发来一个文件",
            "%1个联系人发来%2条消息", "发来一张名片"
    };

    protected static int notifyID = 0525; // start notification id
    protected static String notifyTag = ""; // start notification tag
    protected static int foregroundNotifyID = 0555;

    protected NotificationManager notificationManager = null;

    protected HashSet<String> fromUsers = new HashSet<>();
    protected int notificationNum = 0;

    protected Context appContext;
    protected String packageName;
    protected String[] messages;
    protected long lastNotifyTime;
    protected AudioManager audioManager;
    protected Vibrator vibrator;
    Ringtone ringtone = null;
    protected EaseNotificationInfoProvider notificationInfoProvider;

    public EaseNotifier() {
    }

    /**
     * 开发者可以重载此函数
     * this function can be override
     *
     * @param context
     * @return
     */
    public EaseNotifier init(Context context) {
        appContext = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        packageName = appContext.getApplicationInfo().packageName;
        if (Locale.getDefault().getLanguage().equals("zh")) {
            messages = msg_ch;
        } else {
            messages = msg_eng;
        }

        audioManager = (AudioManager) appContext.getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) appContext.getSystemService(Context.VIBRATOR_SERVICE);

        return this;
    }

    /**
     * 开发者可以重载此函数
     * this function can be override
     */
    public void reset() {
        resetNotificationCount();
        cancelNotification();
    }

    void resetNotificationCount() {
        notificationNum = 0;
        fromUsers.clear();
    }

    void cancelNotification() {
        if (notificationManager != null)
            notificationManager.cancel(notifyID);
    }

    /**
     * 处理新收到的消息，然后发送通知
     * <p/>
     * 开发者可以重载此函数
     * this function can be override
     *
     * @param message
     */
    public void onNewMsg(EMMessage message) {
        if (EMClient.getInstance().chatManager().isSlientMessage(message)) {
            return;
        }

        EaseSettingsProvider settingsProvider = EaseUI.getInstance().getSettingsProvider();
        if (!settingsProvider.isMsgNotifyAllowed(message)) {
            return;
        }

        if (!isCurrentChatMessage(message)) {
            // 判断app是否在后台
//            if (!EasyUtils.isAppRunningForeground(appContext)) {
            ThinkcooLog.e(TAG, "app is running in backGround");
            sendNotification(message, false);
//            } else {
//                sendNotification(message, true);
//
//            }
            variableAndPlayTone(message);
        }
    }

    private boolean isCurrentChatMessage(EMMessage message) {
        String currentId = IMHelper.getInstance().getCurrentChatId();
        if (TextUtils.isEmpty(currentId)) {
            return false;
        }
        EMMessage.ChatType type = message.getChatType();
        String messageFrom = null;
        if (EMMessage.ChatType.Chat.equals(type)) {
            messageFrom = message.getFrom();
        } else if (EMMessage.ChatType.GroupChat.equals(type)) {
            messageFrom = message.getTo();
        }
        if (TextUtils.isEmpty(messageFrom)) {
            return false;
        }
        return currentId.equals(messageFrom);
    }

    public synchronized void onNewMesg(List<EMMessage> messages) {
        if (EMClient.getInstance().chatManager().isSlientMessage(messages.get(messages.size() - 1))) {
            return;
        }
        EaseSettingsProvider settingsProvider = EaseUI.getInstance().getSettingsProvider();
        if (!settingsProvider.isMsgNotifyAllowed(null)) {
            return;
        }
        // 判断app是否在后台
        if (!EasyUtils.isAppRunningForeground(appContext)) {
            EMLog.d(TAG, "app is running in background");
            sendNotification(messages, false);
        } else {
            sendNotification(messages, true);
        }
        variableAndPlayTone(messages.get(messages.size() - 1));
    }

    /**
     * 发送通知栏提示
     * This can be override by subclass to provide customer implementation
     *
     * @param messages
     * @param isForeground
     */
    protected void sendNotification(List<EMMessage> messages, boolean isForeground) {
        for (EMMessage message : messages) {
            if (!isForeground) {
                notificationNum++;
                fromUsers.add(message.getFrom());
            }
        }
        sendNotification(messages.get(messages.size() - 1), isForeground, false);
    }

    protected void sendNotification(EMMessage message, boolean isForeground) {
        sendNotification(message, isForeground, true);
    }

    /**
     * 发送通知栏提示
     * This can be override by subclass to provide customer implementation
     *
     * @param message
     */
    protected void sendNotification(EMMessage message, boolean isForeground, boolean numIncrease) {
        String username = getChatName(message);
        notifyTag = String.valueOf(System.currentTimeMillis());
        try {
            String notifyText = username + " ";
            switch (message.getType()) {
                case TXT:
                    if (EMMessageUtil.isSystemMessage(message)) {
                        return;
                    }
                    if (EMMessageUtil.isUserCard(message)) {
                        notifyText += messages[7];
                    } else {
                        EMTextMessageBody body = (EMTextMessageBody) message.getBody();
                        String content = body.getMessage();
                        notifyText += TextUtils.isEmpty(content) ? messages[0] : " : " + content;
                    }
                    break;
                case IMAGE:
                    notifyText += messages[1];
                    break;
                case VOICE:
                    notifyText += messages[2];
                    break;
                case LOCATION:
                    notifyText += messages[3];
                    break;
                case VIDEO:
                    notifyText += messages[4];
                    break;
                case FILE:
                    notifyText += messages[5];
                    break;
            }

            PackageManager packageManager = appContext.getPackageManager();
            String appName = (String) packageManager.getApplicationLabel(appContext.getApplicationInfo());

            // notification title
            String contentTitle = appName;
            if (notificationInfoProvider != null) {
                String customNotifyText = notificationInfoProvider.getDisplayedText(message);
                String customContentTitle = notificationInfoProvider.getTitle(message);
                if (customNotifyText != null) {
                    // 设置自定义的状态栏提示内容
                    notifyText = customNotifyText;
                }

                if (customContentTitle != null) {
                    // 设置自定义的通知栏标题
                    contentTitle = customContentTitle;
                }
            }

            // create and send notification
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(appContext)
                    .setSmallIcon(appContext.getApplicationInfo().icon)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true);

//            Intent intent = new Intent(appContext, ChatActivity.class);
            Intent intent = new Intent(Constant.ACTION_NOTIFICATION_CLICK);
            int flag = Constant.CHATTYPE_SINGLE;
            EMMessage.ChatType type = message.getChatType();
            if (EMMessage.ChatType.Chat.equals(type)) {
                flag =Constant.CHATTYPE_SINGLE;
            }else if(EMMessage.ChatType.GroupChat.equals(type)){
                flag =Constant.CHATTYPE_GROUP;
            }
            intent.putExtra(KEY_TYPE, flag);
            intent.putExtra(KEY_CHAT_ID, EMMessageUtil.getChatId(message));
            if (notificationInfoProvider != null) {
                // 设置自定义的notification点击跳转intent
                intent = notificationInfoProvider.getLaunchIntent(message);
            }

            PendingIntent pendingIntent = PendingIntent.getBroadcast(appContext, notifyID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (numIncrease) {
                // prepare latest event info section
                if (!isForeground) {
                    notificationNum++;
                    fromUsers.add(message.getFrom());
                }
            }

            int fromUsersNum = fromUsers.size();
            String summaryBody = messages[6].replaceFirst("%1", Integer.toString(fromUsersNum)).replaceFirst("%2", Integer.toString(notificationNum));

            if (notificationInfoProvider != null) {
                // latest text
                String customSummaryBody = notificationInfoProvider.getLatestText(message, fromUsersNum, notificationNum);
                if (customSummaryBody != null) {
                    summaryBody = customSummaryBody;
                }

                // small icon
                int smallIcon = notificationInfoProvider.getSmallIcon(message);
                if (smallIcon != 0) {
                    mBuilder.setSmallIcon(smallIcon);
                }
            }

            mBuilder.setContentTitle(contentTitle);
            mBuilder.setTicker(notifyText);
            mBuilder.setContentText(summaryBody);
            mBuilder.setContentIntent(pendingIntent);
            Notification notification = mBuilder.build();

            if (isForeground) {
                notificationManager.notify(foregroundNotifyID, notification);
                notificationManager.cancel(foregroundNotifyID);
            } else {
                notificationManager.notify(notifyID, notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getChatName(EMMessage message) {
        String name = "";
        EMMessage.ChatType type = message.getChatType();
        if (EMMessage.ChatType.Chat.equals(type)) {
            name = getUserName(message);
        } else if (EMMessage.ChatType.GroupChat.equals(type)) {
            name = MessageWrapUtil.getGroupNameExtraAttributes(message);
        }
        return name;
    }

    private String getUserName(EMMessage message) {
        String name = "";
        String senderId = IdOffsetUtil.minusOffset(message.getFrom());
        if (getFriendCache().isFriend(senderId)) {
            Friend friend = getFriendCache().getEntity(senderId);
            name = friend.getShowName();
        } else {
            name = MessageWrapUtil.getSenderNameFromExtraAttributes(message);
        }
        return name;
    }

    private FriendCache mFriendCache;

    private FriendCache getFriendCache() {
        if (mFriendCache == null) {
            mFriendCache = IMHelper.getInstance().getFriendCache();
        }
        return mFriendCache;
    }


    /**
     * 手机震动和声音提示
     */
    public void variableAndPlayTone(EMMessage message) {
        if (message != null) {
            if (EMClient.getInstance().chatManager().isSlientMessage(message)) {
                return;
            }
        }

        if (System.currentTimeMillis() - lastNotifyTime < 1000) {
            return;
        }

        try {
            lastNotifyTime = System.currentTimeMillis();

            // 判断是否处于静音模式
            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                ThinkcooLog.e(TAG, "device in silent mode now");
                return;
            }
            EaseSettingsProvider settingsProvider = EaseUI.getInstance().getSettingsProvider();
            if (settingsProvider.isMsgVibrateAllowed(message)) {
                long[] pattern = new long[]{0, 180, 80, 120};
                vibrator.vibrate(pattern, -1);
            }

            if (settingsProvider.isMsgSoundAllowed(message)) {
                if (ringtone == null) {
                    Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    ringtone = RingtoneManager.getRingtone(appContext, notificationUri);
                    if (ringtone == null) {
                        ThinkcooLog.e(TAG, "cant find ringtone at:" + notificationUri.getPath());
                        return;
                    }
                }

                if (!ringtone.isPlaying()) {
                    String vendor = Build.MANUFACTURER;
                    ringtone.play();
                    // for samsung S3, we meet a bug that the phone will
                    // continue ringtone without stop
                    // so add below special handler to stop it after 3s if
                    // needed
                    if (vendor != null && vendor.toLowerCase().contains("samsung")) {
                        Thread ctlThread = new Thread() {
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                    if (ringtone.isPlaying()) {
                                        ringtone.stop();
                                    }
                                } catch (Exception e) {
                                }
                            }
                        };
                        ctlThread.run();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置通知栏消息Provider
     *
     * @param provider
     */
    public void setNotificationInfoProvider(EaseNotificationInfoProvider provider) {
        notificationInfoProvider = provider;
    }

    public interface EaseNotificationInfoProvider {
        /**
         * 设置发送notification时状态栏提示新消息的内容(比如Xxx发来了一条图片消息)
         *
         * @param message 接收到的消息
         * @return null为使用默认
         */
        String getDisplayedText(EMMessage message);

        /**
         * 设置notification持续显示的新消息提示(比如2个联系人发来了5条消息)
         *
         * @param message      接收到的消息
         * @param fromUsersNum 发送人的数量
         * @param messageNum   消息数量
         * @return null为使用默认
         */
        String getLatestText(EMMessage message, int fromUsersNum, int messageNum);

        /**
         * 设置notification标题
         *
         * @param message
         * @return null为使用默认
         */
        String getTitle(EMMessage message);

        /**
         * 设置小图标
         *
         * @param message
         * @return 0使用默认图标
         */
        int getSmallIcon(EMMessage message);

        /**
         * 设置notification点击时的跳转intent
         *
         * @param message 显示在notification上最近的一条消息
         * @return null为使用默认
         */
        Intent getLaunchIntent(EMMessage message);
    }
}
