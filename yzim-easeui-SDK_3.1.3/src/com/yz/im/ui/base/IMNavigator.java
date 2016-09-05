package com.yz.im.ui.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.ui.EaseBaiduMapActivity;
import com.hyphenate.easeui.ui.EaseShowBigImageActivity;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import com.yz.im.model.cache.FriendCache;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.entity.serverresponse.FindGroupResponse;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.model.entity.serverresponse.GroupInfoResponse;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;
import com.yz.im.model.strategy.ChatFragmentStrategy;
import com.yz.im.model.strategy.ChatFragmentStrategyFactory;
import com.yz.im.model.strategy.FriendListStrategy;
import com.yz.im.model.strategy.FriendListStrategyFactory;
import com.yz.im.model.strategy.GroupInfoStrategy;
import com.yz.im.model.strategy.GroupInfoStrategyFactory;
import com.yz.im.model.strategy.IMSingleEditTextStrategy;
import com.yz.im.model.strategy.IMSingleEditTextStrategyFactory;
import com.yz.im.model.strategy.SendInviteReasonStrategy;
import com.yz.im.model.strategy.SendInviteReasonStrategyFactory;
import com.yz.im.ui.activity.BlackUserInfoActivity;
import com.yz.im.ui.activity.BlackUserListActivity;
import com.yz.im.ui.activity.ChatActivity;
import com.yz.im.ui.activity.ContactListActivity;
import com.yz.im.ui.activity.CreateGroupActivity;
import com.yz.im.ui.activity.ExceptionActivity;
import com.yz.im.ui.activity.FindGroupListActivity;
import com.yz.im.ui.activity.FindUserExactActivity;
import com.yz.im.ui.activity.FindUserListActivity;
import com.yz.im.ui.activity.FindUserOrGroupActivity;
import com.yz.im.ui.activity.FriendInfoActivity;
import com.yz.im.ui.activity.GroupCardActivity;
import com.yz.im.ui.activity.GroupInfoActivity;
import com.yz.im.ui.activity.GroupListActivity;
import com.yz.im.ui.activity.GroupMemberListActivity;
import com.yz.im.ui.activity.GroupTransferMsgActivity;
import com.yz.im.ui.activity.ImageGridActivity;
import com.yz.im.ui.activity.InviteContactListActivity;
import com.yz.im.ui.activity.NoticeMessageActivity;
import com.yz.im.ui.activity.RecommendUserListActivity;
import com.yz.im.ui.activity.ReportActivity;
import com.yz.im.ui.activity.SendInviteReasonActivity;
import com.yz.im.ui.activity.SingleEditTextActivity;
import com.yz.im.ui.activity.StrangerInfoActivity;
import com.yz.im.ui.activity.SystemSettingActivity;
import com.yz.im.ui.activity.TelephoneContactListActivity;
import com.yz.im.ui.activity.UnJoinGroupInfoActivity;
import com.yz.im.ui.activity.VideoCallActivity;
import com.yz.im.ui.activity.VoiceCallActivity;
import com.yz.im.ui.fragment.IMChatFragment;
import com.yz.im.utils.ImageUtil;
import com.yz.im.utils.ToastUtil;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 */
public class IMNavigator {

    public void navigateToSingleChatActivity(Context context, String userId) {
        ChatFragmentStrategy chatFragmentStrategy = ChatFragmentStrategyFactory.create(context,
                Constant.CHATTYPE_SINGLE);
        Intent intent = ChatActivity.getChatActivityIntent(context, Constant.CHATTYPE_SINGLE, userId,
                chatFragmentStrategy);
        context.startActivity(intent);

    }

    public void navigateToGroupChatActivity(Context context, String groupId) {
        ChatFragmentStrategy chatFragmentStrategy = ChatFragmentStrategyFactory.create(context,
                Constant.CHATTYPE_GROUP);
        Intent intent = ChatActivity.getChatActivityIntent(context, Constant.CHATTYPE_GROUP, groupId,
                chatFragmentStrategy);
        context.startActivity(intent);
    }

    private void navigateToFriendInfoActivity(Context context, String userId) {
        Intent intent = FriendInfoActivity.getFriendInfoActivityIntent(context, userId);
        context.startActivity(intent);
    }

    public void navigateToLocalActivity(Context context) {
        Intent intent = new Intent(context, EaseBaiduMapActivity.class);
        ((ChatActivity) context).startActivityForResult(intent, IMChatFragment.REQUEST_CODE_MAP);
    }

    public void navigateToVideoActivity(Context context) {
        Intent intent = new Intent(context, ImageGridActivity.class);
        ((ChatActivity) context).startActivityForResult(intent, IMChatFragment.REQUEST_CODE_SELECT_VIDEO);
    }

    public void navigateToSelectFileActivity(Context context) {
        Intent intent;
//        if (Build.VERSION.SDK_INT < 19) {
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

//        } else {
//            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media
//                    .EXTERNAL_CONTENT_URI);
//        }
        ((ChatActivity) context).startActivityForResult(intent, IMChatFragment
                .REQUEST_CODE_SELECT_FILE);
    }

    public void navigateToVoiceCallActivity(Context context, String userId) {
        if (!EMClient.getInstance().isConnected()) {
            ToastUtil.getInstance(context).showToastById(R.string.not_connect_to_server);
        } else {
            context.startActivity(new Intent(context, VoiceCallActivity.class)
                    .putExtra("username", userId)
                    .putExtra("isComingCall", false));
//            inputMenu.hideExtendMenuContainer();
        }
    }

    public void navigateToVideoCallPage(Context context, String userId) {
        if (!EMClient.getInstance().isConnected()) {
            ToastUtil.getInstance(context).showToastById(R.string.not_connect_to_server);
            return;
        } else {
            context.startActivity(new Intent(context, VideoCallActivity.class)
                    .putExtra("username", userId)
                    .putExtra("isComingCall", false));
        }
    }

    public void navigateToFriendListActivity(Context context) {
        FriendListStrategy strategy = FriendListStrategyFactory.create(FriendListStrategyFactory.NORMAL_LIST);
        Intent intent = ContactListActivity.getContactListActivityIntent(context, strategy);
        context.startActivity(intent);
    }

    public void navigateToFriendCardActivity(Context context) {
        FriendListStrategy strategy = FriendListStrategyFactory.create(FriendListStrategyFactory.CARD_LIST);
        Intent intent = ContactListActivity.getContactListActivityIntent(context, strategy);
        ((ChatActivity) context).startActivityForResult(intent, IMChatFragment.REQUEST_CODE_USER_CARD);
    }

    public void navigateToFriendTransferActivity(Context context, EMMessage message) {
        FriendListStrategy strategy = FriendListStrategyFactory.create(FriendListStrategyFactory.TRANSFER_MSG_LIST);
        if (strategy instanceof FriendListStrategyFactory.FriendListTransferStrategy) {
            ((FriendListStrategyFactory.FriendListTransferStrategy) strategy).setMessage(message);
        }
        Intent intent = ContactListActivity.getContactListActivityIntent(context, strategy);
        context.startActivity(intent);
    }

    public void navigateToFriendTransferZxingActivity(Context context, String uri) {
        FriendListStrategy strategy = FriendListStrategyFactory.create(FriendListStrategyFactory.TRANSFER_ZXING_LIST);
        if (strategy instanceof FriendListStrategyFactory.FriendListZxingStrategy) {
            ((FriendListStrategyFactory.FriendListZxingStrategy) strategy).setUri(uri);
        }
        Intent intent = ContactListActivity.getContactListActivityIntent(context, strategy);
        context.startActivity(intent);
    }

    /**
     * 当异地登录或用户被删除时发送广播
     */
    public void sendRemoveLoginBroadCast(Context context, String action) {
        Intent intent = new Intent(action);
        context.sendBroadcast(intent);
    }

    public void sendIntentService(Context context, String action) {
        Intent intent = new Intent(action);
        context.startService(intent);
    }

    public void navigateToGroupListActivity(Context context) {
        Intent intent = GroupListActivity.getGroupActivityIntent(context);
        context.startActivity(intent);
    }

    public void navigateToGroupListCardActivity(Context context) {
        Intent intent = GroupCardActivity.getGroupCardActivityIntent(context);
        ((ContactListActivity) context).startActivityForResult(intent, IMChatFragment.REQUEST_CODE_USER_CARD);
    }

    public void navigateToGroupListTransferMsgActivity(Context context, EMMessage message) {
        Intent intent = GroupTransferMsgActivity.getGroupTransferMsgActivityIntent(context, message);
        ((ContactListActivity) context).startActivityForResult(intent, ContactListActivity.REQUEST_CODE_GROUP_TRANSFER_MSG);
    }

    public void navigateToGroupInfoPage(Context context, String groupId, boolean isHxId) {
        Group group = IMHelper.getInstance().getGroupInfo(groupId, isHxId);
        if (group == null) {
            navigateToUnJoinGroupInfoActivity(context, null);  //// TODO: 2016/8/13 如何获取
        } else {
            navigateToGroupInfoActivity(context, group);
        }
    }

    private void navigateToGroupInfoActivity(Context context, Group group) {
        String groupId = group.getGroupId();
        String groupOwnerId = group.getOldUserId();
        String type = group.getGroupType();
        String loginUserId = IMHelper.getInstance().getInfoResponse().getUserId();
        if (TextUtils.isEmpty(groupId) || TextUtils.isEmpty(groupOwnerId) || TextUtils.isEmpty(type)) {
            throw new NullPointerException("argument is null");
        }

        boolean isOwner = groupOwnerId.equals(loginUserId);

        GroupInfoStrategy groupInfoStrategy = GroupInfoStrategyFactory.create(context, type, isOwner);
        Intent intent = GroupInfoActivity.getGroupInfoActivityIntent(context, groupId, groupInfoStrategy);
        context.startActivity(intent);
    }

    public void navigateToUnJoinGroupInfoActivity(Context context, FindGroupResponse response) {
        Intent intent = UnJoinGroupInfoActivity.getUnJoinGroupInfoIntent(context, response);
        context.startActivity(intent);
    }

    public void navigateToExceptionActivity(Context context) {
        context.startActivity(ExceptionActivity.getExceptionIntent(context));
    }

    /**
     * 修改好友名片
     */
    public void navigateToUpdateFriendRemarkActivity(Context context, String friendId, String nickName) {
        IMSingleEditTextStrategy strategy = IMSingleEditTextStrategyFactory.create(IMSingleEditTextStrategyFactory.TYPE_MODIFY_FRIEND_REMARK_NAME, nickName);
        Intent intent = SingleEditTextActivity.getSingleEditTextIntent(context, friendId, strategy);
        ((FriendInfoActivity) context).startActivityForResult(intent, FriendInfoActivity.REQUEST_FRIEND_REMARK_NAME);
    }

    /**
     * 修改黑名单用户的备注
     */
    public void navigateToUpdateBlackFriendRemarkActivity(Context context, String friendId, String nickName) {
        IMSingleEditTextStrategy strategy = IMSingleEditTextStrategyFactory.create(IMSingleEditTextStrategyFactory.TYPE_MODIFY_FRIEND_REMARK_NAME, nickName);
        Intent intent = SingleEditTextActivity.getSingleEditTextIntent(context, friendId, strategy);
        ((BlackUserInfoActivity) context).startActivityForResult(intent, BlackUserInfoActivity.REQUEST_FRIEND_REMARK_NAME);
    }

    /**
     * 修改圈子名片
     */
    public void navigateToUpdateGroupRemarkActivity(Context context, String friendId, String nickName) {
        IMSingleEditTextStrategy strategy = IMSingleEditTextStrategyFactory.create(IMSingleEditTextStrategyFactory.TYPE_MODIFY_GROUP_REMARK_NAME, nickName);
        Intent intent = SingleEditTextActivity.getSingleEditTextIntent(context, friendId, strategy);
        ((GroupInfoActivity) context).startActivityForResult(intent, GroupInfoActivity.REQUEST_GROUP_REMARK_NAME);
    }

    /**
     * 圈主修改圈名
     */
    public void navigateToUpdateGroupNameActivity(Context context, String friendId, String nickName) {
        IMSingleEditTextStrategy strategy = IMSingleEditTextStrategyFactory.create(IMSingleEditTextStrategyFactory.TYPE_MODIFY_GROUP_NAME, nickName);
        Intent intent = SingleEditTextActivity.getSingleEditTextIntent(context, friendId, strategy);
        ((GroupInfoActivity) context).startActivityForResult(intent, GroupInfoActivity.REQUEST_GROUP_NAME);
    }

    /**
     * 修改圈子简介
     */
    public void navigateToUpdateGroupIntroductionActivity(Context context, String friendId, String nickName) {
        IMSingleEditTextStrategy strategy = IMSingleEditTextStrategyFactory.create(IMSingleEditTextStrategyFactory.TYPE_MODIFY_GROUP_INTRODUCTION, nickName);
        Intent intent = SingleEditTextActivity.getSingleEditTextIntent(context, friendId, strategy);
        ((GroupInfoActivity) context).startActivityForResult(intent, GroupInfoActivity.REQUEST_GROUP_INTRODUCE);
    }

    public void navigateToReportFriendActivity(Context context, String id) {
        Intent intent = ReportActivity.getReportActivityIntent(context, id, ReportActivity.FRIEND);
        context.startActivity(intent);
    }

    public void navigateToReportGroupActivity(Context context, String id) {
        Intent intent = ReportActivity.getReportActivityIntent(context, id, ReportActivity.GROUP);
        context.startActivity(intent);
    }

    /**
     * 圈主转让圈子
     */
    public void navigateToGroupTransferActivity(Context context, GroupInfoResponse response) {
        Intent intent = GroupMemberListActivity.getGroupMemberListActivityIntent(context, GroupMemberListActivity.GROUP_TRANSFER, response);
        context.startActivity(intent);
    }

    /**
     * 圈子成员列表
     */
    public void navigateToGroupMemberActivity(Context context, GroupInfoResponse response) {
        Intent intent = GroupMemberListActivity.getGroupMemberListActivityIntent(context, GroupMemberListActivity.GROUP_MEMBERS, response);
        context.startActivity(intent);
    }

    /**
     * 陌生人资料详情页
     *
     * @param context
     * @param userId
     */
    private void navigateToStrangerInfoActivity(Context context, String userId) {
        Intent intent = StrangerInfoActivity.getStrangerInfoActivityIntent(context, userId);
        context.startActivity(intent);
    }

    public void navigateToUserInfoPage(Context context, String userId) {
        FriendCache friendCache = FriendCache.getInstance(context);
        Friend friend = friendCache.getEntity(userId);
        if (friend == null) {
            navigateToStrangerInfoActivity(context, userId);
        } else {
            showFriendInfo(context, userId, friend);
        }
    }

    private void showFriendInfo(Context context, String userId, Friend friend) {
        boolean isBlack = friend.getBlacklist().equals(Constant.IS_BLACK_USER) ? true : false;
        if (isBlack) {
            navigateToBlackInfoPage(context, userId);
        } else {
            navigateToFriendInfoActivity(context, userId);
        }
    }

    /**
     * 从群成员页面跳转到黑名单用户详情页
     */
    public void navigateToBlackInfoPageFromGroupMember(Context context, String userId) {
        Intent intent = BlackUserInfoActivity.getBlackInfoActivityIntent(context, userId);
        ((GroupMemberListActivity) context).startActivityForResult(intent, BlackUserInfoActivity.REQUEST_CODE);
    }

    /**
     * 从黑名单列表跳转到黑名单用户详情页
     */
    public void navigateToBlackInfoPageFromBlackList(Context context, String userId) {
        Intent intent = BlackUserInfoActivity.getBlackInfoActivityIntent(context, userId);
        ((BlackUserListActivity) context).startActivityForResult(intent, BlackUserInfoActivity.REQUEST_CODE);
    }

    private void navigateToBlackInfoPage(Context context, String userId) {
        Intent intent = BlackUserInfoActivity.getBlackInfoActivityIntent(context, userId);
        context.startActivity(intent);
    }

    /**
     * 邀请好友加入群组
     */
    public void navigateToInviteContactActivity(Context context, String groupId, List<GroupMemberResponse> contactList) {
        Intent intent = InviteContactListActivity.getContactListActivityIntent(context, groupId, contactList);
        context.startActivity(intent);
    }

    public void navigateToSendInviteFriendToGroupReasonPage(Context context, String groupId, String userIds) {
        SendInviteReasonStrategy strategy = SendInviteReasonStrategyFactory.create(context, SendInviteReasonStrategyFactory.APPLY_ADD_FRIEND_TO_GROUP, userIds, groupId);
        Intent intent = SendInviteReasonActivity.getInviteReasonIntent(context, strategy);
        context.startActivity(intent);
    }

    public void navigateToSendInviteUserReasonPage(Context context, String userIds) {
        SendInviteReasonStrategy strategy = SendInviteReasonStrategyFactory.create(context, SendInviteReasonStrategyFactory.APPLY_ADD_USER, userIds, "");
        Intent intent = SendInviteReasonActivity.getInviteReasonIntent(context, strategy);
        context.startActivity(intent);
    }

    public void navigateToSendInviteJoinGroupReasonPage(Context context, String groupId) {
        SendInviteReasonStrategy strategy = SendInviteReasonStrategyFactory.create(context, SendInviteReasonStrategyFactory.APPLY_JOIN_GROUP, "", groupId);
        Intent intent = SendInviteReasonActivity.getInviteReasonIntent(context, strategy);
        context.startActivity(intent);
    }

    public void navigateToNoticeMsgList(Context context) {
        Intent intent = NoticeMessageActivity.getMessageNoticeIntent(context);
        context.startActivity(intent);
    }

    public void navigateToFindUserOrGroupActivity(Context context) {
        Intent intent = FindUserOrGroupActivity.getFindUserIntent(context);
        context.startActivity(intent);
    }

    public void navigateToCreateGroupActivity(Context context) {
        Intent intent = CreateGroupActivity.getCreateGroupIntent(context);
        context.startActivity(intent);
    }

    public void navigateToCollectionListActivity(Context context) {
        ToastUtil.getInstance(context).showToast("收藏");
    }

    public void navigateToSettingActivity(Context context) {
        Intent intent = SystemSettingActivity.getSettingIntent(context);
        context.startActivity(intent);
    }

    /**
     * 搜索圈子返回的圈子列表
     *
     * @param context
     */
    public void navigateToFindGroupActivity(Context context, List<FindGroupResponse> list) {
        Intent intent = FindGroupListActivity.getFindGroupIntent(context, list);
        context.startActivity(intent);
    }

    /**
     * 显示搜索到的用户列表
     */
    public void navigateToUserListActivity(Context context, List<FindUserResponse> list) {
        Intent intent = FindUserListActivity.getUserListIntent(context, list);
        context.startActivity(intent);
    }

    public void navigateToRecommendUserListActivity(Context context) {
        Intent intent = RecommendUserListActivity.getRecommendUserListIntent(context);
        context.startActivity(intent);
    }

    public void navigateToExactFindUserPage(Context context) {
        Intent intent = FindUserExactActivity.getFindUserExactIntent(context);
        context.startActivity(intent);
    }

    /**
     * 手机通讯录
     *
     * @param context
     */
    public void navigateToTelephoneContactActivity(Context context) {
        Intent intent = TelephoneContactListActivity.getTelephoneContactListIntent(context);
        context.startActivity(intent);
    }

    public void navigateToBlackUserListActivity(Context context) {
        Intent intent = BlackUserListActivity.getBlackUserListIntent(context);
        context.startActivity(intent);
    }

    public void navigateToShowBIgImageActivity(Context context, String path) {
        Intent intent = new Intent(context, EaseShowBigImageActivity.class);
        String localPath = ImageUtil.createImagePath(context, path);
        File file = new File(localPath);
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            intent.putExtra("uri", uri);
        } else {
            intent.putExtra("secret", "");
            intent.putExtra("remotepath", path);
            intent.putExtra("localUrl", localPath);
        }
        context.startActivity(intent);
    }
}
