package com.yz.im.model.strategy;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.hyphenate.easeui.R;

/**
 * Created by cys on 2016/7/25
 */
public class IMSingleEditTextStrategyFactory {

    public static final int TYPE_MODIFY_GROUP_NAME = 0X0000;
    public static final int TYPE_MODIFY_GROUP_REMARK_NAME = 0X0001;
    public static final int TYPE_MODIFY_FRIEND_REMARK_NAME = 0X0002;
    public static final int TYPE_MODIFY_GROUP_INTRODUCTION = 0X0003;

    public static IMSingleEditTextStrategy create(int type, String oldContent) {
        if (type == TYPE_MODIFY_FRIEND_REMARK_NAME) {
            return new FriendRemarkStrategy(oldContent);
        } else if (type == TYPE_MODIFY_GROUP_REMARK_NAME) {
            return new GroupRemarkStrategy(oldContent);
        } else if (type == TYPE_MODIFY_GROUP_NAME) {
            return new GroupNameStrategy(oldContent);
        } else if (type == TYPE_MODIFY_GROUP_INTRODUCTION) {
            return new GroupIntroductionStrategy(oldContent);
        }
        throw new IllegalArgumentException("can't create a IMSingleEditTextStrategy");
    }

    public static class FriendRemarkStrategy implements IMSingleEditTextStrategy, Parcelable {

        private String oldContent;

        public FriendRemarkStrategy(String oldContent) {
            this.oldContent = oldContent;
        }

        public int getTitle() {
            return R.string.remark;
        }

        @Override
        public int getRightText() {
            return R.string.save;
        }

        @Override
        public int getHint() {
            return R.string.hint_remark;
        }

        @Override
        public int getEditType() {
            return TYPE_MODIFY_FRIEND_REMARK_NAME;
        }

        @Override
        public View createView(Context context) {
            return LayoutInflater.from(context).inflate(R.layout.single_edit_text, null);
        }

        @Override
        public String getOldContent() {
            if (TextUtils.isEmpty(oldContent)) {
                oldContent = "";
            }
            return oldContent;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.oldContent);
        }

        protected FriendRemarkStrategy(Parcel in) {
            this.oldContent = in.readString();
        }

        public static final Parcelable.Creator<FriendRemarkStrategy> CREATOR = new Parcelable.Creator<FriendRemarkStrategy>() {
            @Override
            public FriendRemarkStrategy createFromParcel(Parcel source) {
                return new FriendRemarkStrategy(source);
            }

            @Override
            public FriendRemarkStrategy[] newArray(int size) {
                return new FriendRemarkStrategy[size];
            }
        };
    }

    public static class GroupRemarkStrategy implements IMSingleEditTextStrategy, Parcelable {

        private String oldContent;

        public GroupRemarkStrategy(String oldContent) {
            this.oldContent = oldContent;
        }

        @Override
        public int getTitle() {
            return R.string.group_remark;
        }

        @Override
        public int getRightText() {
            return R.string.save;
        }

        @Override
        public int getHint() {
            return R.string.hint_remark;
        }

        @Override
        public int getEditType() {
            return TYPE_MODIFY_GROUP_REMARK_NAME;
        }

        @Override
        public View createView(Context context) {
            return LayoutInflater.from(context).inflate(R.layout.single_edit_text, null);
        }

        @Override
        public String getOldContent() {
            if (TextUtils.isEmpty(oldContent)) {
                oldContent = "";
            }
            return oldContent;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.oldContent);
        }

        protected GroupRemarkStrategy(Parcel in) {
            this.oldContent = in.readString();
        }

        public static final Parcelable.Creator<GroupRemarkStrategy> CREATOR = new Parcelable.Creator<GroupRemarkStrategy>() {
            @Override
            public GroupRemarkStrategy createFromParcel(Parcel source) {
                return new GroupRemarkStrategy(source);
            }

            @Override
            public GroupRemarkStrategy[] newArray(int size) {
                return new GroupRemarkStrategy[size];
            }
        };
    }

    public static class GroupNameStrategy implements IMSingleEditTextStrategy, Parcelable {

        private String oldContent;

        public GroupNameStrategy(String oldContent) {
            this.oldContent = oldContent;
        }

        @Override
        public int getTitle() {
            return R.string.name_group;
        }

        @Override
        public int getRightText() {
            return R.string.save;
        }

        @Override
        public int getHint() {
            return R.string.hint_group_name;
        }

        @Override
        public int getEditType() {
            return TYPE_MODIFY_GROUP_NAME;
        }

        @Override
        public View createView(Context context) {
            return LayoutInflater.from(context).inflate(R.layout.single_edit_text, null);
        }

        @Override
        public String getOldContent() {
            return oldContent;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.oldContent);
        }

        protected GroupNameStrategy(Parcel in) {
            this.oldContent = in.readString();
        }

        public static final Creator<GroupNameStrategy> CREATOR = new Creator<GroupNameStrategy>() {
            @Override
            public GroupNameStrategy createFromParcel(Parcel source) {
                return new GroupNameStrategy(source);
            }

            @Override
            public GroupNameStrategy[] newArray(int size) {
                return new GroupNameStrategy[size];
            }
        };
    }

    public static class GroupIntroductionStrategy implements IMSingleEditTextStrategy, Parcelable {

        private String oldContent;

        public GroupIntroductionStrategy(String oldContent) {
            this.oldContent = oldContent;
        }

        @Override
        public int getTitle() {
            return R.string.group_info;
        }

        @Override
        public int getRightText() {
            return R.string.save;
        }

        @Override
        public int getHint() {
            return R.string.hint_group_introduction;
        }

        @Override
        public int getEditType() {
            return TYPE_MODIFY_GROUP_INTRODUCTION;
        }

        @Override
        public View createView(Context context) {
            return LayoutInflater.from(context).inflate(R.layout.single_edit_text, null);
        }

        @Override
        public String getOldContent() {
            return oldContent;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.oldContent);
        }

        protected GroupIntroductionStrategy(Parcel in) {
            this.oldContent = in.readString();
        }

        public static final Creator<GroupIntroductionStrategy> CREATOR = new Creator<GroupIntroductionStrategy>() {
            @Override
            public GroupIntroductionStrategy createFromParcel(Parcel source) {
                return new GroupIntroductionStrategy(source);
            }

            @Override
            public GroupIntroductionStrategy[] newArray(int size) {
                return new GroupIntroductionStrategy[size];
            }
        };
    }
}