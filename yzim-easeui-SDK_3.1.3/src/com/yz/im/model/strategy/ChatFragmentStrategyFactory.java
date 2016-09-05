package com.yz.im.model.strategy;

import android.content.Context;
import android.os.Parcel;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.yz.im.Constant;
import com.yz.im.model.cache.FriendCache;
import com.yz.im.model.cache.GroupCache;
import com.yz.im.model.db.entity.Group;
import com.yz.im.ui.activity.ChatActivity;
import com.yz.im.ui.base.IMNavigator;
import com.yz.im.ui.fragment.IMChatFragment;

/**
 * Created by cys on 2016/6/30.
 */
public class ChatFragmentStrategyFactory {

    public static ChatFragmentStrategy create(Context context, int chatType) {
        switch (chatType) {
            case Constant.CHATTYPE_SINGLE:
                return new PrivateChatFragment(context);
            case Constant.CHATTYPE_GROUP:
                return new GroupChatFragment(context);
            default:
                throw new IllegalArgumentException("=== 聊天类型参数错误，无法返回相应的chatFragment ===");
        }
    }

    private static class GroupChatFragment implements ChatFragmentStrategy {

        private static Context mContext;
        private static IMNavigator mNavigator;

        public GroupChatFragment(Context context) {
            mContext = context;
            mNavigator = new IMNavigator();
        }

        @Override
        public void registerExtendMenuItem(EaseChatInputMenu inputMenu, EaseChatExtendMenu.EaseChatExtendMenuItemClickListener clickListener) {
            inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, IMChatFragment.ITEM_VIDEO, clickListener);
            inputMenu.registerExtendMenuItem(R.string.attach_files, R.drawable.em_chat_file_selector, IMChatFragment.ITEM_FILE, clickListener);
            inputMenu.registerExtendMenuItem(R.string.attach_user_card, R.drawable.em_chat_user_card_selector, IMChatFragment.ITEM_USER_CARD, clickListener);
        }

        @Override
        public void gotoNextPage(Context context, String id) {
            GroupCache groupCache = GroupCache.getInstance(context);
            Group group = groupCache.getEntity(id);
            mNavigator.navigateToGroupInfoPage(context, id , false);
        }

        @Override
        public int getRightImage() {
            return R.drawable.icon_group_chat;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        protected GroupChatFragment(Parcel in) {
        }

        public static final Creator<GroupChatFragment> CREATOR = new Creator<GroupChatFragment>() {
            @Override
            public GroupChatFragment createFromParcel(Parcel source) {
                return new GroupChatFragment(source);
            }

            @Override
            public GroupChatFragment[] newArray(int size) {
                return new GroupChatFragment[size];
            }
        };
    }

    private static class PrivateChatFragment implements ChatFragmentStrategy {

        private static Context mContext;
        private static IMNavigator mNavigator;
        private static FriendCache mFriendCache;

        public PrivateChatFragment(Context context) {
            mContext = context;
            mNavigator = new IMNavigator();
            mFriendCache = FriendCache.getInstance(context);
        }

        @Override
        public void registerExtendMenuItem(EaseChatInputMenu inputMenu, EaseChatExtendMenu.EaseChatExtendMenuItemClickListener clickListener) {
            inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, IMChatFragment.ITEM_VIDEO, clickListener);
            inputMenu.registerExtendMenuItem(R.string.attach_files, R.drawable.em_chat_file_selector, IMChatFragment.ITEM_FILE, clickListener);
            inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.em_chat_voice_call_selector, IMChatFragment.ITEM_VOICE_CALL, clickListener);
            inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.em_chat_video_call_selector, IMChatFragment.ITEM_VIDEO_CALL, clickListener);
            inputMenu.registerExtendMenuItem(R.string.attach_user_card, R.drawable.em_chat_user_card_selector, IMChatFragment.ITEM_USER_CARD, clickListener);
        }

        @Override
        public void gotoNextPage(Context context, String id) {
            ((ChatActivity) context).mNavigator.navigateToUserInfoPage(context, id);
        }

        @Override
        public int getRightImage() {
            return R.drawable.icon_private_chat;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        protected PrivateChatFragment(Parcel in) {
        }

        public static final Creator<PrivateChatFragment> CREATOR = new Creator<PrivateChatFragment>() {
            @Override
            public PrivateChatFragment createFromParcel(Parcel source) {
                return new PrivateChatFragment(source);
            }

            @Override
            public PrivateChatFragment[] newArray(int size) {
                return new PrivateChatFragment[size];
            }
        };
    }
}
