package com.yz.im.parse;

import android.content.Context;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.yz.im.IMHelper;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class UserProfileManager {

	/**
	 * init flag: test if the sdk has been inited before, we don't need to init again
	 */
	private boolean sdkInit = false;

	/**
	 * HuanXin sync contact nick and avatar listener
	 */
	private List<IMHelper.DataSyncListener> syncContactInfoListeners;

	private boolean isSyncingContactInfoWithServer = false;

	private Friend currentUser;

	public UserProfileManager() {}

	public synchronized boolean init(Context context) {
		if (sdkInit) {
			return true;
		}
		ParseManager.getInstance().onInit(context);
		syncContactInfoListeners = new ArrayList<>();
		sdkInit = true;
		return true;
	}

	public void addSyncContactInfoListener(IMHelper.DataSyncListener listener) {
		if (listener == null) {
			return;
		}
		if (!syncContactInfoListeners.contains(listener)) {
			syncContactInfoListeners.add(listener);
		}
	}

	public void removeSyncContactInfoListener(IMHelper.DataSyncListener listener) {
		if (listener == null) {
			return;
		}
		if (syncContactInfoListeners.contains(listener)) {
			syncContactInfoListeners.remove(listener);
		}
	}

	public void asyncFetchContactInfosFromServer(List<String> usernames, final EMValueCallBack<List<EaseUser>> callback) {
		if (isSyncingContactInfoWithServer) {
			return;
		}
		isSyncingContactInfoWithServer = true;
		ParseManager.getInstance().getContactInfos(usernames, new EMValueCallBack<List<EaseUser>>() {

			@Override
			public void onSuccess(List<EaseUser> value) {
				isSyncingContactInfoWithServer = false;
				if (!IMHelper.getInstance().isLoggedIn()) {
					return;
				}
				if (callback != null) {
					callback.onSuccess(value);
				}
			}

			@Override
			public void onError(int error, String errorMsg) {
				isSyncingContactInfoWithServer = false;
				if (callback != null) {
					callback.onError(error, errorMsg);
				}
			}

		});

	}

	public void notifyContactInfosSyncListener(boolean success) {
		for (IMHelper.DataSyncListener listener : syncContactInfoListeners) {
			listener.onSyncComplete(success);
		}
	}

	public boolean isSyncingContactInfoWithServer() {
		return isSyncingContactInfoWithServer;
	}

	public synchronized void reset() {
		isSyncingContactInfoWithServer = false;
		currentUser = null;
		PreferenceManager.getInstance().removeCurrentUserInfo();
	}

	public synchronized Friend getCurrentUserInfo() {
		if (currentUser == null) {
			String username = EMClient.getInstance().getCurrentUser();
			currentUser = new Friend();
			String nick = getCurrentUserNick();
			currentUser.setNickName((nick != null) ? nick : username);
			currentUser.setImage(getCurrentUserAvatar());
		}
		return currentUser;
	}

	public boolean updateCurrentUserNickName(final String nickname) {
		boolean isSuccess = ParseManager.getInstance().updateParseNickName(nickname);
		if (isSuccess) {
			setCurrentUserNick(nickname);
		}
		return isSuccess;
	}

	public String uploadUserAvatar(byte[] data) {
		String avatarUrl = ParseManager.getInstance().uploadParseAvatar(data);
		if (avatarUrl != null) {
			setCurrentUserAvatar(avatarUrl);
		}
		return avatarUrl;
	}

	public void asyncGetCurrentUserInfo() {
		ParseManager.getInstance().asyncGetCurrentUserInfo(new EMValueCallBack<EaseUser>() {

			@Override
			public void onSuccess(EaseUser value) {
			    if(value != null){
    				setCurrentUserNick(value.getNick());
    				setCurrentUserAvatar(value.getAvatar());
			    }
			}

			@Override
			public void onError(int error, String errorMsg) {

			}
		});

	}
	public void asyncGetUserInfo(final String username,final EMValueCallBack<EaseUser> callback){
		ParseManager.getInstance().asyncGetUserInfo(username, callback);
	}
	private void setCurrentUserNick(String nickname) {
		getCurrentUserInfo().setNickName(nickname);
		PreferenceManager.getInstance().setCurrentUserNick(nickname);
	}

	private void setCurrentUserAvatar(String avatar) {
		getCurrentUserInfo().setImage(avatar);
		PreferenceManager.getInstance().setCurrentUserAvatar(avatar);
	}

	private String getCurrentUserNick() {
		return PreferenceManager.getInstance().getCurrentUserNick();
	}

	private String getCurrentUserAvatar() {
		return PreferenceManager.getInstance().getCurrentUserAvatar();
	}

}
