package com.yz.im.model.strategy;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.yz.im.Constant;

/**
 * Created by cys on 2016/8/4
 */
public class GroupInfoStrategyFactory {

    public static GroupInfoStrategy create(Context context, String type, boolean isOwner) {
        if (Constant.TYPE_EVENT_GROUP.equals(type)) {
            return new EventGroupStrategy(isOwner);
        } else if (Constant.TYPE_INITIATIVE_GROUP.equals(type)) {
            return new InitiativeGroupStrategy(isOwner);
        } else {
            throw new NullPointerException("can't create a GroupInfoStrategy");
        }
    }

    public static class InitiativeGroupStrategy implements GroupInfoStrategy, Parcelable {

        private boolean isOwner;

        public InitiativeGroupStrategy(boolean isOwner) {
            this.isOwner = isOwner;
        }

        @Override
        public int isShowName() {
            return getVisibleValue(isOwner);
        }

        @Override
        public int isShowHead() {
            return getVisibleValue(isOwner);
        }

        @Override
        public int isShowIntroduction() {
            return View.VISIBLE;
        }

        @Override
        public int isShowGroupMember() {
            return View.VISIBLE;
        }

        @Override
        public int isShowRemarkName() {
            return View.VISIBLE;
        }

        @Override
        public int isShowTransfer() {
            return getVisibleValue(isOwner);
        }

        @Override
        public int isMsgTop() {
            return View.VISIBLE;
        }

        @Override
        public int isMsgDisturb() {
            return View.VISIBLE;
        }

        @Override
        public int isReportGroup() {
            return View.VISIBLE;
        }

        @Override
        public int isSupportChat() {
            return View.VISIBLE;
        }

        @Override
        public int isSupportQuit() {
            return View.VISIBLE;
        }

        @Override
        public boolean isOwner() {
            return isOwner;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.isOwner ? (byte) 1 : (byte) 0);
        }

        protected InitiativeGroupStrategy(Parcel in) {
            this.isOwner = in.readByte() != 0;
        }

        public static final Parcelable.Creator<InitiativeGroupStrategy> CREATOR = new Parcelable.Creator<InitiativeGroupStrategy>() {
            @Override
            public InitiativeGroupStrategy createFromParcel(Parcel source) {
                return new InitiativeGroupStrategy(source);
            }

            @Override
            public InitiativeGroupStrategy[] newArray(int size) {
                return new InitiativeGroupStrategy[size];
            }
        };
    }

    static class EventGroupStrategy implements GroupInfoStrategy, Parcelable {

        private boolean isOwner;

        public EventGroupStrategy(boolean isOwner) {
            this.isOwner = isOwner;
        }

        @Override
        public int isShowName() {
            return View.GONE;
        }

        @Override
        public int isShowHead() {
            return getVisibleValue(isOwner);
        }

        @Override
        public int isShowIntroduction() {
            return View.VISIBLE;
        }

        @Override
        public int isShowGroupMember() {
            return View.VISIBLE;
        }

        @Override
        public int isShowRemarkName() {
            return View.VISIBLE;
        }

        @Override
        public int isShowTransfer() {
            return View.GONE;
        }

        @Override
        public int isMsgTop() {
            return View.VISIBLE;
        }

        @Override
        public int isMsgDisturb() {
            return View.VISIBLE;
        }

        @Override
        public int isReportGroup() {
            return View.VISIBLE;
        }

        @Override
        public int isSupportChat() {
            return View.VISIBLE;
        }

        @Override
        public int isSupportQuit() {
            return View.GONE;
        }

        @Override
        public boolean isOwner() {
            return isOwner;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.isOwner ? (byte) 1 : (byte) 0);
        }

        protected EventGroupStrategy(Parcel in) {
            this.isOwner = in.readByte() != 0;
        }

        public static final Parcelable.Creator<EventGroupStrategy> CREATOR = new Parcelable.Creator<EventGroupStrategy>() {
            @Override
            public EventGroupStrategy createFromParcel(Parcel source) {
                return new EventGroupStrategy(source);
            }

            @Override
            public EventGroupStrategy[] newArray(int size) {
                return new EventGroupStrategy[size];
            }
        };
    }

    private static int getVisibleValue(boolean flag) {
        return flag ? View.VISIBLE : View.GONE;
    }
}
