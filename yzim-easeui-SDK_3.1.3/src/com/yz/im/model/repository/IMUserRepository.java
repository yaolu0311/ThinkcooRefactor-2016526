package com.yz.im.model.repository;

import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.model.entity.serverresponse.FriendInfoResponse;
import com.yz.im.model.entity.serverresponse.StrangerInfoResponse;
import com.yz.im.model.entity.serverresponse.UserInfoResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by cys on 2016/7/19
 */
public interface IMUserRepository {

    Observable<UserInfoResponse> loadUserInfo();

    Observable<FriendInfoResponse> loadFriendInfo(String friendId);

    Observable updateFriendToggleStatus(String friendId, String msgTop, String msgDisturb, String blackList);

    Observable deleteFriend(String friendId, String type);

    Observable<StrangerInfoResponse> loadStrangerInfo(String userId);

    Observable updateStrangerShieldStatus(String userId, String type);

    Observable reliefBlackFriend(String friendId);

    Observable<List<FindUserResponse>> findUserByNumber(String searchValue, String findUserType, String pageNo, String pageSize);

    Observable<List<FindUserResponse>> findUserByName(String areaCode, String school, String department, String professional, String realName, String pageNo, String pageSize);

    Observable sendInviteReason(String friendIdList, String content);

    Observable<List<FindUserResponse>> getContactList();
}
