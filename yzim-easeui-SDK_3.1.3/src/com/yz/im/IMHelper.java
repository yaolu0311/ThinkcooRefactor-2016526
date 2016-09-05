package com.yz.im;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.example.administrator.publicmodule.util.PinyinHelper;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.yz.im.model.cache.FriendCache;
import com.yz.im.model.cache.GroupCache;
import com.yz.im.model.cache.ShieldCache;
import com.yz.im.model.cache.SystemSettingCache;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.db.entity.Shield;
import com.yz.im.model.entity.serverresponse.UserInfoResponse;
import com.yz.im.model.im.listener.ConnectionListener;
import com.yz.im.model.im.listener.ContactListener;
import com.yz.im.model.im.listener.GroupChangeListener;
import com.yz.im.model.im.listener.MessageListener;
import com.yz.im.model.im.provider.SettingProvider;
import com.yz.im.model.im.provider.UserProfileProvider;
import com.yz.im.model.im.receiver.CallReceiver;
import com.yz.im.parse.UserProfileManager;
import com.yz.im.ui.base.IMNavigator;
import com.yz.im.ui.fragment.IMConversationListFragment;
import com.yz.im.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class IMHelper {

    protected static final String TAG = "IMHelper";

    private static final String MI_PUSH_KEY = "2882303761517463853";
    private static final String MI_PUSH_VALUE = "5741746354853";

    public boolean isSyncingGroupsWithServer = false;
    public boolean isSyncingContactsWithServer = false;
    public boolean isSyncingBlackListWithServer = false;
    public boolean isGroupsSyncedWithServer = false;
    public boolean isContactsSyncedWithServer = false;
    public boolean isBlackListSyncedWithServer = false;
    public boolean isGroupAndContactListenerRegister;

    public boolean isVoiceCalling;
    public boolean isVideoCalling;

    public EaseUI easeUI;

    private FriendCache mFriendCache;
    private GroupCache mGroupCache;
    private ShieldCache mShieldCache;
    private SystemSettingCache mSettingCache;
    private UserInfoResponse mInfoResponse;

    public LocalBroadcastManager broadcastManager;
    private UserProfileManager userProManager;

    private String loginUserId;

    /**
     * sync groups status listener
     */
    private List<DataSyncListener> syncGroupsListeners;
    /**
     * sync contacts status listener
     */
    private List<DataSyncListener> syncContactsListeners;
    /**
     * sync blacklist status listener
     */
    private List<DataSyncListener> syncBlackListListeners;

    private Context appContext;
    private IMModel mIMModel;
    private String username;
    private CallReceiver callReceiver;

    private IMNavigator mNavigator;
    private PinyinHelper mPinyinHelper;

    private String currentChatId;

    private IMHelper() {
        mNavigator = new IMNavigator();
    }

    private static IMHelper instance = null;

    public synchronized static IMHelper getInstance() {
        if (instance == null) {
            synchronized (IMHelper.class) {
                if (instance == null) {
                    instance = new IMHelper();
                }
            }
        }
        return instance;
    }

    public void init(Context context, String appKey) {
        mIMModel = new IMModel(context);
        EMOptions options = initChatOptions(appKey);
        if (EaseUI.getInstance().init(context, options)) {
            appContext = context;
            EMClient.getInstance().setDebugMode(true);
            easeUI = EaseUI.getInstance();
            setEaseUIProviders();
            registerCallFilter();
            setGlobalListeners();

            PreferenceManager.init(context);
            getUserProfileManager().init(context);
            EMClient.getInstance().callManager().getVideoCallHelper().setAdaptiveVideoFlag
                    (getModel().isAdaptiveVideoEncode());
        }
        //init other
        broadcastManager = LocalBroadcastManager.getInstance(appContext);
        mPinyinHelper = PinyinHelper.getInstance();
    }


    private EMOptions initChatOptions(String appKey) {
        EMOptions options = new EMOptions();
        options.setAppKey(appKey);
        options.setAcceptInvitationAlways(false);
        options.setRequireAck(true);
        options.setRequireDeliveryAck(false);
        options.setMipushConfig(MI_PUSH_KEY, MI_PUSH_VALUE);
        options.allowChatroomOwnerLeave(getModel().isChatroomOwnerLeaveAllowed());
        options.setDeleteMessagesAsExitGroup(getModel().isDeleteMessagesAsExitGroup());
        options.setAutoAcceptGroupInvitation(getModel().isAutoAcceptGroupInvitation());
        return options;
    }

    protected void setEaseUIProviders() {
        easeUI.setUserProfileProvider(new UserProfileProvider(appContext));
        easeUI.setSettingsProvider(new SettingProvider(appContext));
//        easeUI.setEmojiconInfoProvider(new EmojiconProvider());
//        easeUI.getNotifier().setNotificationInfoProvider(new NotificationProvider());
    }

    private void registerCallFilter() {
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager()
                .getIncomingCallBroadcastAction());
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }
        appContext.registerReceiver(callReceiver, callFilter);
    }

    protected void setGlobalListeners() {
        syncGroupsListeners = new ArrayList<>();
        syncContactsListeners = new ArrayList<>();
        syncBlackListListeners = new ArrayList<>();
        isGroupsSyncedWithServer = mIMModel.isGroupsSynced();
        isContactsSyncedWithServer = mIMModel.isContactSynced();
        isBlackListSyncedWithServer = mIMModel.isBacklistSynced();
        registerGroupAndContactListener();
    }

    /**
     * register group and contact listener, you need register when login
     */
    public void registerGroupAndContactListener() {
        EMClient.getInstance().addConnectionListener(new ConnectionListener(appContext));
        EMClient.getInstance().chatManager().addMessageListener(new MessageListener(appContext));
        if (!isGroupAndContactListenerRegister) {
            EMClient.getInstance().groupManager().addGroupChangeListener(new GroupChangeListener
                    (appContext));
            EMClient.getInstance().contactManager().setContactListener(new ContactListener
                    (appContext));
            isGroupAndContactListenerRegister = true;
        }
    }

    public void initCache() {
        mFriendCache = FriendCache.getInstance(appContext);
        mGroupCache = GroupCache.getInstance(appContext);
        mShieldCache = ShieldCache.getInstance(appContext);
        mSettingCache = SystemSettingCache.getInstance(appContext);
    }

    /**userId为思扣id*/
    public Friend getFriendInfo(String userId){
        if (mFriendCache == null) {
            mFriendCache = FriendCache.getInstance(appContext);
        }
        return mFriendCache.getEntity(userId);
    }

    public Shield getShieldInfo(String userId){
        if (mShieldCache == null) {
            mShieldCache = ShieldCache.getInstance(appContext);
        }
        return mShieldCache.getEntity(userId);
    }

    public Group getGroupInfo(String groupId, boolean isHxGroupId) {
        if (mGroupCache == null) {
            mGroupCache = GroupCache.getInstance(appContext);
        }
        Group group = mGroupCache.getEntityByDiffId(groupId, isHxGroupId);
        return group;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public FriendCache getFriendCache() {
        if (mFriendCache == null) {
            mFriendCache = FriendCache.getInstance(appContext);
        }
        return mFriendCache;
    }

    public GroupCache getGroupCache() {
        if (mGroupCache == null) {
            mGroupCache = GroupCache.getInstance(appContext);
        }
        return mGroupCache;
    }

    public ShieldCache getShieldCache() {
        if (mShieldCache == null) {
            mShieldCache = ShieldCache.getInstance(appContext);
        }
        return mShieldCache;
    }

    public SystemSettingCache getSettingCache() {
        if (mSettingCache == null) {
            mSettingCache = SystemSettingCache.getInstance(appContext);
        }
        return mSettingCache;
    }

    /**
     * if ever logged in
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    public void logout(boolean unbindDeviceToken) {
        endCall();
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {
            @Override
            public void onSuccess() {
                reset();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String error) {
                reset();
            }
        });
    }

    protected void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            ThinkcooLog.e(TAG, e.getMessage(), e);
        }
    }

    public String getCurrentChatId() {
        return currentChatId;
    }

    public IMHelper setCurrentChatId(String currentChatId) {
        this.currentChatId = currentChatId;
        return this;
    }

    public EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }

    public IMModel getModel() {
        return mIMModel;
    }

    public PinyinHelper getPinyinHelper() {
        return mPinyinHelper;
    }

    public UserProfileManager getUserProfileManager() {
        if (userProManager == null) {
            userProManager = new UserProfileManager();
        }
        return userProManager;
    }

    public IMNavigator getNavigator() {
        return mNavigator;
    }

    public EaseUI getEaseUI() {
        return easeUI;
    }

    public void addSyncListener(DataSyncListener listener, SyncListenerType type) {
        if (listener == null) {
            return;
        }
        switch (type) {
            case CONTACT_LIST:
                if (!syncContactsListeners.contains(listener)) {
                    syncContactsListeners.add(listener);
                }
                break;
            case BLACK_LIST:
                if (!syncBlackListListeners.contains(listener)) {
                    syncBlackListListeners.add(listener);
                }
                break;
            case GROUP_LIST:
                if (!syncGroupsListeners.contains(listener)) {
                    syncGroupsListeners.add(listener);
                }
                break;
        }
    }

    public void removeSyncListener(DataSyncListener listener, SyncListenerType type) {
        if (listener == null) {
            return;
        }
        switch (type) {
            case CONTACT_LIST:
                if (syncContactsListeners.contains(listener)) {
                    syncContactsListeners.remove(listener);
                }
                break;
            case BLACK_LIST:
                if (syncBlackListListeners.contains(listener)) {
                    syncBlackListListeners.remove(listener);
                }
                break;
            case GROUP_LIST:
                if (syncGroupsListeners.contains(listener)) {
                    syncGroupsListeners.remove(listener);
                }
                break;
        }
    }

    public void notifySyncListeners(boolean success, SyncListenerType type) {
        switch (type) {
            case CONTACT_LIST:
                for (DataSyncListener listener : syncContactsListeners) {
                    listener.onSyncComplete(success);
                }
                break;
            case BLACK_LIST:
                for (DataSyncListener listener : syncBlackListListeners) {
                    listener.onSyncComplete(success);
                }
                break;
            case GROUP_LIST:
                for (DataSyncListener listener : syncGroupsListeners) {
                    listener.onSyncComplete(success);
                }
                break;
        }
    }

    public boolean isSyncingGroupsWithServer() {
        return isSyncingGroupsWithServer;
    }

    public boolean isSyncingContactsWithServer() {
        return isSyncingContactsWithServer;
    }

    public boolean isSyncingBlackListWithServer() {
        return isSyncingBlackListWithServer;
    }

    public boolean isGroupsSyncedWithServer() {
        return isGroupsSyncedWithServer;
    }

    public boolean isContactsSyncedWithServer() {
        return isContactsSyncedWithServer;
    }

    public boolean isBlackListSyncedWithServer() {
        return isBlackListSyncedWithServer;
    }

    public void pushActivity(Activity activity) {
        easeUI.pushActivity(activity);
    }

    public void popActivity(Activity activity) {
        easeUI.popActivity(activity);
    }

    public EaseConversationListFragment createConversationListFragment() {
        return new IMConversationListFragment();
    }

    protected synchronized void reset() {
        isSyncingGroupsWithServer = false;
        isSyncingContactsWithServer = false;
        isSyncingBlackListWithServer = false;

        mIMModel.setGroupsSynced(false);
        mIMModel.setContactSynced(false);
        mIMModel.setBlacklistSynced(false);

        isGroupsSyncedWithServer = false;
        isContactsSyncedWithServer = false;
        isBlackListSyncedWithServer = false;

        isGroupAndContactListenerRegister = false;

        getUserProfileManager().reset();
    }

    public UserInfoResponse getInfoResponse() {
        return mInfoResponse;
    }

    public void setInfoResponse(UserInfoResponse infoResponse) {
        mInfoResponse = infoResponse;
    }

    public void setLoginuserId(String userId) {
        this.loginUserId = userId;
    }

    public enum SyncListenerType {
        CONTACT_LIST,
        BLACK_LIST,
        GROUP_LIST;
    }

    public interface DataSyncListener {
        /**
         * sync complete
         *
         * @param success true：data sync successful，false: failed to sync data
         */
        void onSyncComplete(boolean success);
    }
}
