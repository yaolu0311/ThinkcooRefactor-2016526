package com.yz.im.model.repository;

import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.db.entity.Shield;
import com.yz.im.model.db.entity.SystemSetting;
import com.yz.im.model.entity.serverresponse.NoticeMessageResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by cys on 2016/7/12
 */
public interface IMRepository {

    Observable<SystemSetting> loadSystemSetting(String userId);

    Observable<List<Friend>> loadFriendList(String userId, String type);

    Observable<List<Group>> loadGroupList(String userId);

    Observable<List<Shield>> loadShieldList(String userId);

    Observable updateSetting(String newMessage, String textingMsg, String unfamiliarMsg);

    Observable<List<NoticeMessageResponse>> loadNoticeMessage(String type);

    Observable dealWithNoticeMessage(String messageId, String operationType);

}
