package com.yz.im.model.im.listener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import com.yz.im.IMModel;

/**
 * Created by cys on 2016/7/1
 * in com.yz.im.model.im.listener
 */
public class ConnectionListener implements EMConnectionListener {

    private static final String TAG = "ConnectionListener";

    private Context mContext;
    private IMModel mIMModel;

    public ConnectionListener(Context context) {
        mContext = context;
        mIMModel = new IMModel(context);
    }

    @Override
    public void onConnected() {
//        if (mIMHelper.isGroupsSyncedWithServer && mIMHelper.isContactsSyncedWithServer) {
//            ThinkcooLog.d(TAG, "group and contact already synced with server");
//        } else {
//            if (!mIMHelper.isGroupsSyncedWithServer) {
//                asyncFetchGroupsFromServer(null);
//            }
//
//            if (!mIMHelper.isContactsSyncedWithServer) {
//                asyncFetchContactsFromServer(null);
//            }
//
//            if (!mIMHelper.isBlackListSyncedWithServer) {
//                asyncFetchBlackListFromServer(null);
//            }
//        }
    }

    @Override
    public void onDisconnected(int error) {
        String action = getAction(error);
        if (TextUtils.isEmpty(action)) {
            return;
        }
        IMHelper.getInstance().getNavigator().sendRemoveLoginBroadCast(mContext, getAction(error));
    }

    @NonNull
    private String getAction(int error) {
        String action = null;
        switch (error) {
            case EMError.USER_REMOVED:
                action = Constant.ACTION_USER_REMOVED;
                break;
            case EMError.USER_LOGIN_ANOTHER_DEVICE:
                action = Constant.ACTION_LOGIN_ANOTHER_DEVICE;
                break;
        }
        return action;
    }

//    private boolean isLoggedIn() {
//        return EMClient.getInstance().isLoggedInBefore();
//    }
//
//    //同步群组
//    public synchronized void asyncFetchGroupsFromServer(final EMCallBack callback) {
//        if (mIMHelper.isSyncingGroupsWithServer) {
//            return;
//        }
//        new Thread(new AsyncFetchGroupsFromServer(callback)).start();
//    }
//
//    //// TODO: 2016/7/1  同步用好友列表
//    private void asyncFetchContactsFromServer(EMValueCallBack<List<String>> callback) {
//        if (mIMHelper.isSyncingContactsWithServer) {
//            return;
//        }
//        mIMHelper.isSyncingContactsWithServer = true;
//        new Thread(new AsyncFetchContactsFromServer(callback)).start();
//    }
//
//    //同步黑名单
//    private void asyncFetchBlackListFromServer(EMValueCallBack<List<String>> callback) {
//        if (mIMHelper.isSyncingBlackListWithServer) {
//            return;
//        }
//        mIMHelper.isSyncingBlackListWithServer = true;
//        new Thread(new AsyncFetchBlackListFromServer(callback)).start();
//    }
//
//    class AsyncFetchGroupsFromServer implements Runnable {
//
//        private EMCallBack mEMCallBack;
//
//        public AsyncFetchGroupsFromServer(EMCallBack EMCallBack) {
//            mEMCallBack = EMCallBack;
//        }
//
//        @Override
//        public void run() {
//            try {
//                EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
//                // in case that logout already before server returns, we should return immediately
//                if (!isLoggedIn()) {
//                    setGroupsSyncStatus(false);
//                    return;
//                }
//                mIMModel.setGroupsSynced(true);
//                setGroupsSyncStatus(true);
//
//                if (mEMCallBack != null) {
//                    mEMCallBack.onSuccess();
//                }
//            } catch (HyphenateException e) {
//                mIMModel.setGroupsSynced(false);
//                setGroupsSyncStatus(false);
//                if (mEMCallBack != null) {
//                    mEMCallBack.onError(e.getErrorCode(), e.toString());
//                }
//                EMLog.e(TAG, e.getLocalizedMessage());
//            }
//        }
//
//        private void setGroupsSyncStatus(boolean isSuccess) {
//            mIMHelper.isGroupsSyncedWithServer = isSuccess;
//            mIMHelper.isSyncingGroupsWithServer = isSuccess;
//            mIMHelper.notifySyncListeners(isSuccess, IMHelper.SyncListenerType.GROUP_LIST);
//        }
//    }
//
//    class AsyncFetchContactsFromServer implements Runnable {
//
//        private EMValueCallBack<List<String>> callback;
//
//        public AsyncFetchContactsFromServer(EMValueCallBack<List<String>> callback) {
//            this.callback = callback;
//        }
//
//        @Override
//        public void run() {
//            List<String> userList;
//            try {
//                userList = EMClient.getInstance().contactManager().getAllContactsFromServer();
//                // in case that logout already before server returns, we should return immediately
//                if (!isLoggedIn()) {
//                    setContactsSyncStatus(false);
//                    return;
//                }
//
//                Map<String, EaseUser> list = getUsersMap(userList);
//                saveContactToCache(list);
//                saveContactToDb(list);
//
//                mIMModel.setContactSynced(true);
//                setContactsSyncStatus(true);
//                mIMHelper.getUserProfileManager().asyncFetchContactInfosFromServer(userList, new
//                        EMValueCallBack<List<EaseUser>>() {
//
//                    @Override
//                    public void onSuccess(List<EaseUser> uList) {
////                        mIMHelper.updateContactList(uList);
//                        mIMHelper.getUserProfileManager().notifyContactInfosSyncListener(true);
//                    }
//
//                    @Override
//                    public void onError(int error, String errorMsg) {
//                    }
//                });
//                if (callback != null) {
//                    callback.onSuccess(userList);
//                }
//            } catch (HyphenateException e) {
//                mIMModel.setContactSynced(false);
//                setContactsSyncStatus(false);
//                if (callback != null) {
//                    callback.onError(e.getErrorCode(), e.toString());
//                }
//                EMLog.e(TAG, e.getLocalizedMessage());
//            }
//        }
//
//        private void saveContactToCache(Map<String, EaseUser> list) {
//            if (null == list) {
//                return;
//            }
////            mIMHelper.getContactList().clear();
////            mIMHelper.getContactList().putAll(list);
//        }
//
//        private void saveContactToDb(Map<String, EaseUser> list) {
////            UserDao dao = new UserDao(appContext);
////            List<EaseUser> users = new ArrayList<EaseUser>(list.values());
////            dao.saveContactList(users);
//        }
//
//        @NonNull
//        private Map<String, EaseUser> getUsersMap(List<String> userList) {
//            Map<String, EaseUser> users = new HashMap<>();
//            if (null == userList) {
//                return users;
//            }
//            for (String username : userList) {
//                EaseUser user = new EaseUser(username);
//                EaseCommonUtils.setUserInitialLetter(user);
//                users.put(username, user);
//            }
//            return users;
//        }
//
//        private void setContactsSyncStatus(boolean isSuccess) {
//            mIMHelper.isContactsSyncedWithServer = isSuccess;
//            mIMHelper.isSyncingContactsWithServer = isSuccess;
//            mIMHelper.notifySyncListeners(isSuccess, IMHelper.SyncListenerType.CONTACT_LIST);
//        }
//    }
//
//    class AsyncFetchBlackListFromServer implements Runnable {
//
//        EMValueCallBack<List<String>> callback;
//
//        public AsyncFetchBlackListFromServer(EMValueCallBack<List<String>> callback) {
//            this.callback = callback;
//        }
//
//        @Override
//        public void run() {
//            try {
//                List<String> blackUsers = EMClient.getInstance().contactManager()
//                        .getBlackListFromServer();
//                // in case that logout already before server returns, we should return immediately
//                if (!isLoggedIn()) {
//                    setBlackListSyncStatus(false);
//                    return;
//                }
//
//                mIMModel.setBlacklistSynced(true);
//                setBlackListSyncStatus(true);
//
//                if (callback != null) {
//                    callback.onSuccess(blackUsers);
//                }
//            } catch (HyphenateException e) {
//                mIMModel.setBlacklistSynced(false);
//                setBlackListSyncStatus(false);
//                if (callback != null) {
//                    callback.onError(e.getErrorCode(), e.toString());
//                }
//            }
//        }
//
//        private void setBlackListSyncStatus(boolean isSuccess) {
//            mIMHelper.isBlackListSyncedWithServer = isSuccess;
//            mIMHelper.isSyncingBlackListWithServer = isSuccess;
//            mIMHelper.notifySyncListeners(isSuccess, IMHelper.SyncListenerType.BLACK_LIST);
//        }
//    }
}
