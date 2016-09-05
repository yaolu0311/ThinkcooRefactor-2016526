package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.entity.serverresponse.CheckVcodeResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.UserBasicInfoResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;

/**
 * Created by Robert.yao on 2016/5/26.
 */
public class CheckVcode  implements Nullable, Parcelable {

    public static CheckVcode fromServerResponse(CheckVcodeResponse checkVcodeResponse , ServerDataConverter serverDataConverter) {

        CheckVcode checkVcode = new CheckVcode();
        checkVcode.setUserId(checkVcodeResponse.getUserId());
        checkVcode.setEncryptStr(checkVcodeResponse.getEncryptStr());
;
        return checkVcode;
    }
    private String encryptStr;
    private String userId;

    public String getEncryptStr() {
        return encryptStr;
    }

    public void setEncryptStr(String encryptStr) {
        this.encryptStr = encryptStr;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.encryptStr);
        dest.writeString(this.userId);
    }

    public CheckVcode() {
    }

    protected CheckVcode(Parcel in) {
        this.encryptStr = in.readString();
        this.userId = in.readString();
    }

    public static final Parcelable.Creator<CheckVcode> CREATOR = new Parcelable.Creator<CheckVcode>() {
        @Override
        public CheckVcode createFromParcel(Parcel source) {
            return new CheckVcode(source);
        }

        @Override
        public CheckVcode[] newArray(int size) {
            return new CheckVcode[size];
        }
    };

}
