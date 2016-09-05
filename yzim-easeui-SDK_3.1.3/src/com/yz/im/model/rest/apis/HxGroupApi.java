package com.yz.im.model.rest.apis;

import com.yz.im.model.entity.serverresponse.BaseResponse;
import com.yz.im.model.entity.serverresponse.CreateGroupResponse;
import com.yz.im.model.entity.serverresponse.FindGroupResponse;
import com.yz.im.model.entity.serverresponse.GroupInfoResponse;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by cys on 2016/7/28
 */
public interface HxGroupApi {

    @POST("groupinfo.json")
    Observable<BaseResponse<GroupInfoResponse>> loadGroupInfo(@Query("userId") String userId, @Query("groupId") String groupId);

    @POST("updategroupinfo.json")
    Observable<BaseResponse> updateGroupToggleStatus(@Query("userId") String userId, @Query("groupId") String groupId, @Query("chartTop") String msgUp, @Query("chartMsgDisturb") String msgDisturb);

    /**
     * friendId  type =1 时需要
     * type 0表示主动退出圈子  1表示被管理员移除
     */
    @POST("exitcirle.json")
    Observable<BaseResponse> quitGroup(@Query("userId") String userId, @Query("groupId") String groupId, @Query("friendId") String friendId, @Query("type") String type);

    /**
     * 0表示圈主修改圈子名称   1表示修改圈子备注  2表示修改好友备注  3表示修改圈子简介
     *
     * @param type
     * @return
     */
    @POST("editfriends.json")
    Observable<BaseResponse> updateRemarkInfo(@Query("userId") String userId, @Query("friendId") String friendId, @Query("groupId") String groupId, @Query("content") String content, @Query("editType") String type);

    @POST("reportfriends.json")
    Observable<BaseResponse> reportFriendOrGroup(@Query("userId") String userId, @Query("friendId") String friendId, @Query("groupId") String groupId, @Query("reportReason") String reportReason, @Query("context") String context);

    @POST("groupmemberlist.json")
    Observable<BaseResponse<List<GroupMemberResponse>>> loadGroupMember(@Query("userId") String userId, @Query("groupId") String groupId);

    @POST("transfergroup.json")
    Observable<BaseResponse> transferGroup(@Query("userId") String userId, @Query("groupId") String groupId, @Query("newUserId") String newUserId);

    @POST("invitenewfriends.json")
    Observable<BaseResponse> sendInviteReason(@Query("userId") String userId, @Query("groupId") String groupId, @Query("friendIdList") String friendIdList, @Query("content") String content);

    /**
     * 搜索圈子
     */
    @POST("searchgroup.json")
    Observable<BaseResponse<List<FindGroupResponse>>> findGroup(@Query("userId") String userId, @Query("groupValue") String groupValue, @Query("groupType") String groupType,
                                                                @Query("pageNumber") String pageNumber, @Query("pageSize") String pageSize);

    /**
     * 感兴趣的圈子
     */
    @POST("searchgroup.json")
    Observable<BaseResponse<List<FindGroupResponse>>> findInterestGroup(@Query("userId") String userId, @Query("groupValue") String groupValue, @Query("groupType") String groupType,
                                                                @Query("pageNo") String pageNo, @Query("pageSize") String pageSize);

    @POST("applyjoingroup.json")
    Observable<BaseResponse> applyJoinGroup(@Query("userId") String userId, @Query("circleId") String circleId, @Query("context") String context);

    @POST("creategroup.json")
    Observable<BaseResponse<CreateGroupResponse>> createGroup(@Query("userId") String userId, @Query("groupName") String groupName, @Query("isPublic") String isPublic,
                                                              @Query("isApproval") String isApproval, @Query("circleType") String circleType);
}
