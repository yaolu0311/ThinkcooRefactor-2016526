package com.yz.im.model.repository.impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.publicmodule.model.rest.ApiFactory;
import com.example.administrator.publicmodule.model.rest.ApiFactoryImpl;
import com.example.administrator.publicmodule.util.PinyinHelper;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.yz.im.IMHelper;
import com.yz.im.model.cache.FriendCache;
import com.yz.im.model.cache.GroupCache;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.entity.serverresponse.BaseResponse;
import com.yz.im.model.entity.serverresponse.CreateGroupResponse;
import com.yz.im.model.entity.serverresponse.FindGroupResponse;
import com.yz.im.model.entity.serverresponse.GroupInfoResponse;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;
import com.yz.im.model.entity.serverresponse.GroupResponse;
import com.yz.im.model.repository.IMGroupRepository;
import com.yz.im.model.rest.apis.HxApi;
import com.yz.im.model.rest.apis.HxGroupApi;
import com.yz.im.model.strategy.IMSingleEditTextStrategyFactory;

import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by cys on 2016/7/22
 */
public class IMGroupRepositoryImpl implements IMGroupRepository {

    private static String TAG = "IMGroupRepositoryImpl";

    private HxGroupApi mHxGroupApi;
    private HxApi mHxApi;
    private GroupCache mGroupCache;
    private FriendCache mFriendCache;
    private IMHelper mHelper;

    public IMGroupRepositoryImpl(Context context) {
        mGroupCache = GroupCache.getInstance(context);
        mFriendCache = FriendCache.getInstance(context);
        mHxGroupApi = ApiFactoryImpl.getInstance(context).createApiByClass(HxGroupApi.class, ApiFactory.QECHART_PREFIX);
        mHxApi = ApiFactoryImpl.getInstance(context).createApiByClass(HxApi.class, ApiFactory.QECHART_PREFIX);
        mHelper = IMHelper.getInstance();
    }

    @Override
    public Observable<List<Group>> queryGroupList() {
        return Observable.just(mGroupCache.getEntityList()).flatMap(new Func1<List<Group>, Observable<List<Group>>>() {
            @Override
            public Observable<List<Group>> call(List<Group> list) {
                if (list != null && list.size() > 0) {
                    return Observable.just(list);
                }
                return mHxApi.loadGroupList(mHelper.getInfoResponse().getUserId()).flatMap(new Func1<BaseResponse<List<GroupResponse>>, Observable<List<Group>>>() {
                    @Override
                    public Observable<List<Group>> call(BaseResponse<List<GroupResponse>> groupListResponseBaseResponse) {
                        if (!groupListResponseBaseResponse.isSuccess()) {
                            Observable.error(new Throwable(groupListResponseBaseResponse.getMsg()));
                        }
                        return Observable.just(Group.fromServerResponse(groupListResponseBaseResponse.getData()));
                    }
                });
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<GroupInfoResponse> loadGroupInfo(String groupId) {
        return mHxGroupApi.loadGroupInfo(mHelper.getInfoResponse().getUserId(), groupId).flatMap(new Func1<BaseResponse<GroupInfoResponse>, Observable<GroupInfoResponse>>() {
            @Override
            public Observable<GroupInfoResponse> call(BaseResponse<GroupInfoResponse> friendInfoResponseBaseResponse) {
                if (!friendInfoResponseBaseResponse.isSuccess()) {
                    return Observable.error(new Throwable(friendInfoResponseBaseResponse.getMsg()));
                }
                return Observable.just(friendInfoResponseBaseResponse.getData());
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable updateGroupToggleStatus(final String groupId, final String msgUp, final String msgDisturb) {
        return mHxGroupApi.updateGroupToggleStatus(mHelper.getInfoResponse().getUserId(), groupId, msgUp, msgDisturb).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new Throwable(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                Group group = mGroupCache.getEntity(groupId);
                if (group == null) {
                    return;
                }
                group.setDisturb(msgDisturb);
                group.setStick(msgUp);
                mGroupCache.insert(group);
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable quitGroup(final String groupId, String friendId, String type) {
        return mHxGroupApi.quitGroup(mHelper.getInfoResponse().getUserId(), groupId, friendId, type).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new Throwable(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                Group group = mGroupCache.getEntity(groupId);
                if (group == null) {
                    return;
                }
                mGroupCache.delete(group);
                EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(groupId);
                if (emConversation != null) {
                    emConversation.clearAllMessages();
                }

            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable updateSingleEditContent(final String friendId, final String groupId, final String content, final String editType) {
        return mHxGroupApi.updateRemarkInfo(mHelper.getInfoResponse().getUserId(), friendId, groupId, content, editType)
                .flatMap(new Func1<BaseResponse, Observable<?>>() {
                    @Override
                    public Observable<?> call(BaseResponse baseResponse) {
                        if (!baseResponse.isSuccess()) {
                            return Observable.error(new Throwable(baseResponse.getMsg()));
                        }
                        return Observable.empty();
                    }
                }).doOnCompleted(new Action0() {
                    @Override
                    public void call() {

                        switch (Integer.valueOf(editType)) {
                            case IMSingleEditTextStrategyFactory.TYPE_MODIFY_FRIEND_REMARK_NAME:
                                refreshFriendRemarkName(friendId, content);
                                break;
                            case IMSingleEditTextStrategyFactory.TYPE_MODIFY_GROUP_INTRODUCTION:
                                refreshGroupInfo(groupId, content, null, null);
                                break;
                            case IMSingleEditTextStrategyFactory.TYPE_MODIFY_GROUP_NAME:
                                refreshGroupInfo(groupId, null, content, null);
                                break;
                            case IMSingleEditTextStrategyFactory.TYPE_MODIFY_GROUP_REMARK_NAME:
                                refreshGroupInfo(groupId, null, null, content);
                                break;
                        }
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                    }
                });
    }

    private void refreshFriendRemarkName(String friendId, String newValue) {
        PinyinHelper pinyinHelper = IMHelper.getInstance().getPinyinHelper();
        Friend friend = mFriendCache.getEntity(friendId);
        if (friend == null) {
            return;
        }
        friend.setRemarkName(newValue);
        friend.setSortLetters(pinyinHelper.getFirstUpCaseLetter(newValue));
        friend.setFirstLetter(pinyinHelper.getFirstUpCaseLetter(newValue));
        friend.setNameLetters(pinyinHelper.getPinyins(newValue).toLowerCase());
        mFriendCache.insert(friend);
    }

    private void refreshGroupInfo(String groupId, String introduction, String groupName, String remarkName) {
        Group group = mGroupCache.getEntity(groupId);
        if (group == null) {
            return;
        }
        if (!TextUtils.isEmpty(introduction)) {
            group.setCircleIntroduce(introduction);
        }
        if (!TextUtils.isEmpty(groupName)) {
            group.setGroupName(groupName);
        }
        if (!TextUtils.isEmpty(remarkName)) {
            group.setRealName(remarkName);
        }
        mGroupCache.insert(group);
    }

    @Override
    public Observable reportFriendOrGroup(String friendId, String groupId, String reportReason, String context) {
        return mHxGroupApi.reportFriendOrGroup(mHelper.getInfoResponse().getUserId(), friendId, groupId, reportReason, context).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new Throwable(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<List<GroupMemberResponse>> loadGroupMember(String groupId) {
        return mHxGroupApi.loadGroupMember(mHelper.getInfoResponse().getUserId(), groupId).flatMap(new Func1<BaseResponse<List<GroupMemberResponse>>, Observable<List<GroupMemberResponse>>>() {
            @Override
            public Observable<List<GroupMemberResponse>> call(BaseResponse<List<GroupMemberResponse>> listBaseResponse) {
                if (!listBaseResponse.isSuccess()) {
                    return Observable.error(new Throwable(listBaseResponse.getMsg()));
                }
                return Observable.just(GroupMemberResponse.fromServerResponse(listBaseResponse.getData()));
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable transferGroup(String groupId, String newUserId) {
        return mHxGroupApi.transferGroup(mHelper.getInfoResponse().getUserId(), groupId, newUserId).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new Throwable(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    /**
     * 邀请好友至组
     */
    @Override
    public Observable sendInviteReason(String groupId, String friendIdList, String content) {
        return mHxGroupApi.sendInviteReason(mHelper.getInfoResponse().getUserId(), groupId, friendIdList, content).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new Throwable(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<List<FindGroupResponse>> findGroup(String groupValue, String groupType, String pageNumber, String pageSize) {
        return mHxGroupApi.findGroup(mHelper.getInfoResponse().getUserId(), groupValue, groupType, pageNumber, pageSize).flatMap(new Func1<BaseResponse<List<FindGroupResponse>>, Observable<List<FindGroupResponse>>>() {
            @Override
            public Observable<List<FindGroupResponse>> call(BaseResponse<List<FindGroupResponse>> listBaseResponse) {
                if (!listBaseResponse.isSuccess()) {
                    return Observable.error(new Throwable(listBaseResponse.getMsg()));
                }
                return Observable.just(listBaseResponse.getData());
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<List<FindGroupResponse>> findInterestGroup(String groupValue, String groupType, String pageNo, String pageSize) {
        return mHxGroupApi.findGroup(mHelper.getInfoResponse().getUserId(), groupValue, groupType, pageNo, pageSize).flatMap(new Func1<BaseResponse<List<FindGroupResponse>>, Observable<List<FindGroupResponse>>>() {
            @Override
            public Observable<List<FindGroupResponse>> call(BaseResponse<List<FindGroupResponse>> listBaseResponse) {
                if (!listBaseResponse.isSuccess()) {
                    return Observable.error(new Throwable(listBaseResponse.getMsg()));
                }
                return Observable.just(listBaseResponse.getData());
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    /**
     * 申请加入群组
     */
    @Override
    public Observable sendInviteReason(String circleId, String context) {
        return mHxGroupApi.applyJoinGroup(mHelper.getInfoResponse().getUserId(), circleId, context).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new Throwable(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public Observable<Group> createGroup(String groupName, String isPublic, String isApproval, final String circleType) {
        return mHxGroupApi.createGroup(mHelper.getInfoResponse().getUserId(), groupName, isPublic, isApproval, circleType)
                .flatMap(new Func1<BaseResponse<CreateGroupResponse>, Observable<CreateGroupResponse>>() {
                    @Override
                    public Observable<CreateGroupResponse> call(BaseResponse<CreateGroupResponse> createGroupResponseBaseResponse) {
                        if (!createGroupResponseBaseResponse.isSuccess()) {
                            return Observable.error(new Throwable(createGroupResponseBaseResponse.getMsg()));
                        }
                        return Observable.just(createGroupResponseBaseResponse.getData());
                    }
                }).flatMap(new Func1<CreateGroupResponse, Observable<Group>>() {
                    @Override
                    public Observable<Group> call(CreateGroupResponse createGroupResponse) {
                        if (createGroupResponse == null) {
                            return Observable.error(new Throwable("创建圈子失败"));
                        }
                        Group group = new Group();
                        group.setGroupId(createGroupResponse.getGroupId() + "");
                        group.setEasemobGroupId(createGroupResponse.getEasemobGroupId());
                        group.setOldUserId(mHelper.getInfoResponse().getUserId());
                        group.setGroupType(circleType);
                        return Observable.just(group);
                    }
                }).doOnNext(new Action1<Group>() {
                    @Override
                    public void call(Group group) {
                        mGroupCache.insert(group);
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
                    }
                });
    }
}
