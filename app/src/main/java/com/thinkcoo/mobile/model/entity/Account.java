package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.thinkcoo.mobile.model.entity.nullentities.NullAccount;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.yzkj.android.orm.annotation.Column;
import com.yzkj.android.orm.annotation.Table;


/**
 * Created by Administrator on 2016/5/19.
 */
@Table(name = "account")
public class Account implements Nullable, Parcelable {

    public static final Account NULL_ACCOUNT = new NullAccount();

    @Column(name = "accountName" , isId = true)
    private String accountName;
    @Column(name = "password")
    private String password;
    @Column(name = "isLogin")
    private boolean isLogin;

    public Account() {
    }

    public Account(String accountName, boolean isLogin, String password) {
        this.accountName = accountName;
        this.isLogin = isLogin;
        this.password = password;
    }


    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public String getAccountName() {
        return accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountName);
        dest.writeString(this.password);
        dest.writeByte(this.isLogin ? (byte) 1 : (byte) 0);
    }

    protected Account(Parcel in) {
        this.accountName = in.readString();
        this.password = in.readString();
        this.isLogin = in.readByte() != 0;
    }

}
