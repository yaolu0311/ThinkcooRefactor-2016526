package com.yz.im.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.publicmodule.util.EasemobConstantsUtils;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yz.im.IMHelper;
import com.yz.im.domain.LoadFriendListUseCase;
import com.yz.im.domain.LoadGroupListUseCase;
import com.yz.im.domain.LoadShieldListUseCase;
import com.yz.im.domain.LoadSystemSettingUseCase;
import com.yz.im.domain.LoadUserInfoUseCase;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.db.entity.Shield;
import com.yz.im.model.db.entity.SystemSetting;
import com.yz.im.model.entity.IMInitFlag;
import com.yz.im.model.entity.serverresponse.UserInfoResponse;
import com.yz.im.utils.MD5Tools;

import java.util.List;

import rx.Subscriber;

public class BackgroundInitService extends IntentService {

    private static final String TAG = "BackgroundInitService";

    public static final String KEY_USER_ID = "key_user_id";
    public static final String KEY_NICK_NAME = "key_nike_name";
    public static final String KEY_SHOW_PROGRESS = "key_show_progress";

    private static final String ACTION_INIT = "com.yz.im.services.action.INIT";
    public static final String FRIEND_ALL = "2";

    private IMHelper mHelper;
    private static Context mContext;
    private LoadSystemSettingUseCase mSystemSettingUseCase;
    private LoadFriendListUseCase mFriendListUseCase;
    private LoadGroupListUseCase mGroupListUseCase;
    private LoadShieldListUseCase mShieldListUseCase;
    private LoadUserInfoUseCase mUserInfoUseCase;

    public BackgroundInitService() {
        super("BackgroundInitService");
        initUseCase();
        mHelper = IMHelper.getInstance();
    }

    private void initUseCase() {
        mSystemSettingUseCase = new LoadSystemSettingUseCase(mContext);
        mFriendListUseCase = new LoadFriendListUseCase(mContext);
        mGroupListUseCase = new LoadGroupListUseCase(mContext);
        mShieldListUseCase = new LoadShieldListUseCase(mContext);
        mUserInfoUseCase = new LoadUserInfoUseCase(mContext);
    }

    public static void startInit(Context context, String userId) {
        mContext = context;
        Intent intent = new Intent(context, BackgroundInitService.class);
        intent.setAction(ACTION_INIT);
        intent.putExtra(KEY_USER_ID, userId);
        context.startService(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                final String userId = intent.getStringExtra(KEY_USER_ID);
                handleInitAction(userId);
            }
        }
    }

    private void handleInitAction(String userId) {
        login(userId);
    }

    private void login(final String userId) {
//        DBManager.getInstance(this).closeDB();
//        IMHelper.getInstance().setCurrentUserName(userId);

        ThinkcooLog.e(TAG,"=== 开始登录环信 ===");

        EasemobConstantsUtils constantsUtils =new EasemobConstantsUtils();
        String hxUserId = String.valueOf(constantsUtils.getEasemobUserName(Long.parseLong(userId)));
        MD5Tools md5 = new MD5Tools();
        String secretId = md5.getMD5ofStr(hxUserId).toLowerCase();
        IMInitFlag.getInstance().flagToLogin();
        EMClient.getInstance().login(hxUserId, secretId, new EMCallBack() {

            @Override
            public void onSuccess() {
                ThinkcooLog.e(TAG,"=== 登录环信成功开始初始化缓存 ===");
                IMHelper.getInstance().initCache();
                ThinkcooLog.e(TAG,"=== 初始化缓存成功开始获取圈子跟会话列表 ===");
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                ThinkcooLog.e(TAG,"=== 获取圈子跟会话列表成功，开始设置登录的userid并获取用户信息 ===");
                IMInitFlag.getInstance().flagToLoginSucceed();
                mHelper.setLoginuserId(userId);
                getUserInfo(userId);
            }

            @Override
            public void onError(int i, String s) {
                IMInitFlag.getInstance().flagToLoginFailure();
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }

    private void getUserInfo(String userId){
        IMInitFlag.getInstance().flagToDataLoad();
        mUserInfoUseCase.execute(getUserInfoSub(userId), userId);
    }

    private Subscriber getUserInfoSub(final String userId) {
        return new Subscriber<UserInfoResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                IMInitFlag.getInstance().flagToDataLoadFailure();
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(UserInfoResponse response) {
                ThinkcooLog.e(TAG,"=== HX获取用户信息成功开始获取系统设置 ===");
                getSystemSetting(userId);
            }
        };
    }

    private void getSystemSetting(String userId) {
        mSystemSettingUseCase.execute(getSystemSettingSub(userId), userId);
    }

    private Subscriber getSystemSettingSub(final String userId) {
        return new Subscriber<SystemSetting>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                IMInitFlag.getInstance().flagToDataLoadFailure();
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(SystemSetting objects) {
                ThinkcooLog.e(TAG,"=== 获取系统设置成功开始获取好友列表 ===");
                getFriendList(userId);
            }
        };
    }

    private void getFriendList(String userId) {
        mFriendListUseCase.execute(getFriendListSub(userId), userId, FRIEND_ALL);
    }

    private Subscriber getFriendListSub(final String userId) {
        return new Subscriber<List<Friend>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                IMInitFlag.getInstance().flagToDataLoadFailure();
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(List<Friend> objects) {
                ThinkcooLog.e(TAG,"=== 获取好友列表成功开始获取圈子列表 ===");
                getGroupList(userId);
            }
        };
    }

    private void getGroupList(String userId) {
        mGroupListUseCase.execute(getGroupListSub(userId), userId);
    }

    private Subscriber getGroupListSub(final String userId) {
        return new Subscriber<List<Group>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                IMInitFlag.getInstance().flagToDataLoadFailure();
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(List<Group> objects) {
                ThinkcooLog.e(TAG,"=== 获取圈子列表成功开始获取黑白明白 ===");
                getShieldList(userId);
            }
        };
    }

    private void getShieldList(String userId) {
        mShieldListUseCase.execute(getShieldListSub(userId), userId);
    }

    private Subscriber getShieldListSub(String userId) {
        return new Subscriber<List<Shield>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                IMInitFlag.getInstance().flagToDataLoadFailure();
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(List<Shield> objects) {
                ThinkcooLog.e(TAG,"=== 获取黑白名单成功，环信初始化完成 !!!!!!!!!! ===");
                IMInitFlag.getInstance().flagToDataLoadSucceed();
            }
        };
    }

}
