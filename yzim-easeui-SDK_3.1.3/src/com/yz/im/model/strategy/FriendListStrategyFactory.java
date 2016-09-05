package com.yz.im.model.strategy;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.hyphenate.chat.EMMessage;
import com.yz.im.ui.widget.FriendContactListView;
import com.yz.im.ui.widget.FriendListCardView;
import com.yz.im.ui.widget.FriendListSendZxingView;
import com.yz.im.ui.widget.FriendListTransferMsgView;

/**
 * Created by cys on 2016/8/13
 */
public class FriendListStrategyFactory {

    public static final int NORMAL_LIST = 0x0001;
    public static final int CARD_LIST = 0x0002;
    public static final int TRANSFER_MSG_LIST = 0x0003;
    public static final int TRANSFER_ZXING_LIST = 0x0004;

    public static FriendListStrategy create(int type) {
        switch (type) {
            case NORMAL_LIST:
                return new FriedNormalListStrategy();
            case CARD_LIST:
                return new FriedCardListStrategy();
            case TRANSFER_MSG_LIST:
                return new FriendListTransferStrategy();
            case TRANSFER_ZXING_LIST:
                return new FriendListZxingStrategy();
            default:
                throw new NullPointerException("can not match contactList type which value is " + type);
        }
    }

    static class FriedNormalListStrategy implements FriendListStrategy, Parcelable {

        @Override
        public FriendContactListView getView(Context context) {
            FriendContactListView view = new FriendContactListView(context);
            view.setStrategy(this);
            return view;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public FriedNormalListStrategy() {
        }

        protected FriedNormalListStrategy(Parcel in) {
        }

        public static final Parcelable.Creator<FriedNormalListStrategy> CREATOR = new Parcelable.Creator<FriedNormalListStrategy>() {
            @Override
            public FriedNormalListStrategy createFromParcel(Parcel source) {
                return new FriedNormalListStrategy(source);
            }

            @Override
            public FriedNormalListStrategy[] newArray(int size) {
                return new FriedNormalListStrategy[size];
            }
        };
    }

    static class FriedCardListStrategy implements FriendListStrategy, Parcelable {

        @Override
        public FriendContactListView getView(Context context) {
            FriendContactListView view = new FriendListCardView(context);
            view.setStrategy(this);
            return view;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public FriedCardListStrategy() {
        }

        protected FriedCardListStrategy(Parcel in) {
        }

        public static final Parcelable.Creator<FriedCardListStrategy> CREATOR = new Parcelable.Creator<FriedCardListStrategy>() {
            @Override
            public FriedCardListStrategy createFromParcel(Parcel source) {
                return new FriedCardListStrategy(source);
            }

            @Override
            public FriedCardListStrategy[] newArray(int size) {
                return new FriedCardListStrategy[size];
            }
        };
    }

    public static class FriendListTransferStrategy implements FriendListStrategy, Parcelable {

        private static EMMessage mMessage;

        @Override
        public FriendContactListView getView(Context context) {
            FriendContactListView view = new FriendListTransferMsgView(context);
            view.setStrategy(this);
            return view;
        }

        public EMMessage getMessage() {
            return mMessage;
        }

        public void setMessage(EMMessage message) {
            mMessage = message;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public FriendListTransferStrategy() {
        }

        protected FriendListTransferStrategy(Parcel in) {
        }

        public static final Parcelable.Creator<FriendListTransferStrategy> CREATOR = new Parcelable.Creator<FriendListTransferStrategy>() {
            @Override
            public FriendListTransferStrategy createFromParcel(Parcel source) {
                return new FriendListTransferStrategy(source);
            }

            @Override
            public FriendListTransferStrategy[] newArray(int size) {
                return new FriendListTransferStrategy[size];
            }
        };
    }

    public static class FriendListZxingStrategy implements FriendListStrategy, Parcelable {

        private static String imageUri;

        @Override
        public FriendContactListView getView(Context context) {
            FriendContactListView view = new FriendListSendZxingView(context);
            view.setStrategy(this);
            return view;
        }

        public String getUri() {
            return imageUri;
        }

        public void setUri(String uri) {
            imageUri = uri;
        }

        public FriendListZxingStrategy() {
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        protected FriendListZxingStrategy(Parcel in) {
        }

        public static final Creator<FriendListZxingStrategy> CREATOR = new Creator<FriendListZxingStrategy>() {
            @Override
            public FriendListZxingStrategy createFromParcel(Parcel source) {
                return new FriendListZxingStrategy(source);
            }

            @Override
            public FriendListZxingStrategy[] newArray(int size) {
                return new FriendListZxingStrategy[size];
            }
        };
    }
}
