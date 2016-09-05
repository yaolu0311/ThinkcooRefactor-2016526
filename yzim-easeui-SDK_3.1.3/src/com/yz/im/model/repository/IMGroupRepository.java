package com.yz.im.model.repository;

import com.yz.im.model.db.entity.Group;
import com.yz.im.model.entity.serverresponse.FindGroupResponse;
import com.yz.im.model.entity.serverresponse.GroupInfoResponse;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by cys on 2016/7/22
 */
public interface IMGroupRepository {

    Observable<List<Group>> queryGroupList();

    Observable<GroupInfoResponse> loadGroupInfo(String groupId);

    Observable updateGroupToggleStatus(String groupId, String msgUp, String msgDisturb);

    Observable quitGroup(String groupId, String friendId, String type);

    Observable updateSingleEditContent(String friendId, String groupId, String content, String editType);

    Observable reportFriendOrGroup(String friendId, String groupId, String reportReason, String context);

    Observable<List<GroupMemberResponse>> loadGroupMember(String groupId);

    Observable transferGroup(String groupId, String newUserId);

    Observable sendInviteReason(String groupId, String friendIdList, String content);

    Observable<List<FindGroupResponse>> findGroup(String groupValue, String groupType, String pageNumber, String pageSize);

    Observable<List<FindGroupResponse>> findInterestGroup(String groupValue, String groupType, String pageNo, String pageSize);

    Observable sendInviteReason(String circleId, String context);

    Observable<Group> createGroup(String groupName, String isPublic, String isApproval, String circleType);
}
