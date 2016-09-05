package com.thinkcoo.mobile.model.entity.nullentities;

import android.os.Parcel;

import com.thinkcoo.mobile.model.entity.Account;

/**
 * Created by Administrator on 2016/5/19.
 */
public class NullAccount extends Account {

    public NullAccount() {
        super("",false, "");
    }

    @Override
    public boolean isEmpty() {
        return true;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected NullAccount(Parcel in) {
        super(in);
    }

    public static final Creator<NullAccount> CREATOR = new Creator<NullAccount>() {
        @Override
        public NullAccount createFromParcel(Parcel source) {
            return new NullAccount(source);
        }

        @Override
        public NullAccount[] newArray(int size) {
            return new NullAccount[size];
        }
    };
}
