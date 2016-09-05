package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.thinkcoo.mobile.model.entity.nullentities.NullUserStatus;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.entity.serverresponse.UserStatusInfoResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class UserStatus implements Nullable, Parcelable {

    public static final UserStatus NULL_USER_STATUS  = new NullUserStatus();

    public static List<UserStatus> fromServerResponse(List<UserStatusInfoResponse> statusInfoResponses, ServerDataConverter serverDataConverter) {
        List<UserStatus> userStatusList = new ArrayList<>();
        if (null == userStatusList) {
            return userStatusList;
        }
        for (UserStatusInfoResponse response : statusInfoResponses) {
            UserStatus userStatus = createNewUserStatus(response,serverDataConverter);
            userStatusList.add(userStatus);
        }
        return userStatusList;
    }

    private static UserStatus createNewUserStatus(UserStatusInfoResponse response , ServerDataConverter serverDataConverter) {
        UserStatus userStatus = new UserStatus();
        userStatus.setStartTime(serverDataConverter.stringReplace(response.getStatuTime()," 00:00:00.0",""));
        userStatus.setTitle(response.getName());
        userStatus.setExtraInfo(response.getLogContent());
        userStatus.setId(response.getSId());
        userStatus.setType(serverDataConverter.stringToInt(response.getStatusCategory(), 1));
        userStatus.setOpen(serverDataConverter.stringToBoolean(response.getDisplayConfig()));
        userStatus.setDeleteFlag(response.getDeleteFlag());
        return userStatus;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private String id;
    private String startTime;
    private String endTime;
    private String editTime;
    private String title;
    private String ExtraInfo;
    private boolean isOpen;
    private String deleteFlag;
    private int type;

    private UserStatusDetail mUserStatusDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtraInfo() {
        return ExtraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        ExtraInfo = extraInfo;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public UserStatusDetail getUserStatusDetail() {
        return mUserStatusDetail;
    }

    public void setUserStatusDetail(UserStatusDetail userStatusDetail) {
        mUserStatusDetail = userStatusDetail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.editTime);
        dest.writeString(this.title);
        dest.writeString(this.ExtraInfo);
        dest.writeByte(this.isOpen ? (byte) 1 : (byte) 0);
        dest.writeString(this.deleteFlag);
        dest.writeInt(this.type);
        dest.writeParcelable((Parcelable) this.mUserStatusDetail, flags);
    }

    public UserStatus() {
    }

    protected UserStatus(Parcel in) {
        this.id = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.editTime = in.readString();
        this.title = in.readString();
        this.ExtraInfo = in.readString();
        this.isOpen = in.readByte() != 0;
        this.deleteFlag = in.readString();
        this.type = in.readInt();
        this.mUserStatusDetail = in.readParcelable(UserStatusDetail.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserStatus> CREATOR = new Parcelable.Creator<UserStatus>() {
        @Override
        public UserStatus createFromParcel(Parcel source) {
            return new UserStatus(source);
        }

        @Override
        public UserStatus[] newArray(int size) {
            return new UserStatus[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserStatus that = (UserStatus) o;

        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (ExtraInfo != null ? !ExtraInfo.equals(that.ExtraInfo) : that.ExtraInfo != null)
            return false;
        return mUserStatusDetail != null ? mUserStatusDetail.equals(that.mUserStatusDetail) : that.mUserStatusDetail == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (ExtraInfo != null ? ExtraInfo.hashCode() : 0);
        result = 31 * result + (mUserStatusDetail != null ? mUserStatusDetail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "id='" + id + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", editTime='" + editTime + '\'' +
                ", title='" + title + '\'' +
                ", ExtraInfo='" + ExtraInfo + '\'' +
                ", isOpen=" + isOpen +
                ", deleteFlag='" + deleteFlag + '\'' +
                ", type=" + type +
                ", mUserStatusDetail=" + mUserStatusDetail +
                '}';
    }
}
