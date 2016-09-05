package com.yz.im.model.strategy;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.yz.im.mvp.IMBasePresenter;
import com.yz.im.mvp.presenter.SendInviteFriendToGroupReasonPresenter;
import com.yz.im.mvp.presenter.SendInviteUserReasonPresenter;
import com.yz.im.mvp.presenter.SendJoinGroupReasonPresenter;

/**
 * Created by cys on 2016/8/2
 */
public class SendInviteReasonStrategyFactory {

    public static int APPLY_JOIN_GROUP = 0x0001;
    public static int APPLY_ADD_USER = 0x0002;
    public static int APPLY_ADD_FRIEND_TO_GROUP = 0x0003;

    public static SendInviteReasonStrategy create(Context context, int type, String uid, String groupId) {
        if (APPLY_JOIN_GROUP == type) {
            return new JoinGroupStrategy(context, groupId);
        } else if (APPLY_ADD_USER == type) {
            return new AddFriendStrategy(context, uid);
        } else if (APPLY_ADD_FRIEND_TO_GROUP == type) {
            return new AddFriendToGroupStrategy(context, uid, groupId);
        } else {
            throw new NullPointerException("SendInviteReasonStrategy is null");
        }
    }

    public static class JoinGroupStrategy implements SendInviteReasonStrategy, Parcelable {

        private Context mContext;
        private String applyId;

        public JoinGroupStrategy(Context context, String id) {
            mContext = context;
            applyId = id;
        }

        @Override
        public IMBasePresenter getPresenter() {
            return new SendJoinGroupReasonPresenter(mContext);
        }

        @Override
        public String getUserId() {
            return "";
        }

        @Override
        public String getGroupId() {
            return applyId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.applyId);
        }

        protected JoinGroupStrategy(Parcel in) {
            this.applyId = in.readString();
        }

        public static final Parcelable.Creator<JoinGroupStrategy> CREATOR = new Parcelable.Creator<JoinGroupStrategy>() {
            @Override
            public JoinGroupStrategy createFromParcel(Parcel source) {
                return new JoinGroupStrategy(source);
            }

            @Override
            public JoinGroupStrategy[] newArray(int size) {
                return new JoinGroupStrategy[size];
            }
        };
    }

    public static class AddFriendStrategy implements SendInviteReasonStrategy, Parcelable {

        private Context mContext;
        private String applyId;

        public AddFriendStrategy(Context context, String id) {
            mContext = context;
            applyId = id;
        }

        @Override
        public IMBasePresenter getPresenter() {
            return new SendInviteUserReasonPresenter(mContext);
        }

        @Override
        public String getUserId() {
            return applyId;
        }

        @Override
        public String getGroupId() {
            return "";
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.applyId);
        }

        protected AddFriendStrategy(Parcel in) {
            this.applyId = in.readString();
        }

        public static final Parcelable.Creator<AddFriendStrategy> CREATOR = new Parcelable.Creator<AddFriendStrategy>() {
            @Override
            public AddFriendStrategy createFromParcel(Parcel source) {
                return new AddFriendStrategy(source);
            }

            @Override
            public AddFriendStrategy[] newArray(int size) {
                return new AddFriendStrategy[size];
            }
        };
    }

    public static class AddFriendToGroupStrategy implements SendInviteReasonStrategy {

        private Context mContext;
        private String userId;
        private String groupId;

        public AddFriendToGroupStrategy(Context context, String userId, String groupId) {
            mContext = context;
            this.userId = userId;
            this.groupId = groupId;
        }

        @Override
        public IMBasePresenter getPresenter() {
            return new SendInviteFriendToGroupReasonPresenter(mContext);
        }

        @Override
        public String getUserId() {
            return userId;
        }

        @Override
        public String getGroupId() {
            return groupId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.userId);
            dest.writeString(this.groupId);
        }

        protected AddFriendToGroupStrategy(Parcel in) {
            this.userId = in.readString();
            this.groupId = in.readString();
        }

        public static final Creator<AddFriendToGroupStrategy> CREATOR = new Creator<AddFriendToGroupStrategy>() {
            @Override
            public AddFriendToGroupStrategy createFromParcel(Parcel source) {
                return new AddFriendToGroupStrategy(source);
            }

            @Override
            public AddFriendToGroupStrategy[] newArray(int size) {
                return new AddFriendToGroupStrategy[size];
            }
        };
    }
}
