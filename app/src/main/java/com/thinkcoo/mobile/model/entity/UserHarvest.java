package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.annotation.FieldEmptyCheck;
import com.thinkcoo.mobile.annotation.FieldPosition;
import com.thinkcoo.mobile.model.entity.nullentities.NullUserHarvest;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.entity.serverresponse.UserHarvestsResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class UserHarvest implements Nullable ,Parcelable {

    public static  final UserHarvest NULL_USER_HARVEST = new NullUserHarvest();

    public static List<UserHarvest> fromServerResponse(UserHarvestsResponse userHarvestsResponse) {
        ArrayList<UserHarvest> localUserHarvests = new ArrayList<UserHarvest>();

        for ( UserHarvestsResponse.ListBean  userHarvestResponse :userHarvestsResponse.getList()) {
            UserHarvest localUserHarvest = new UserHarvest();
            localUserHarvest.setGrantId(userHarvestResponse.getGrantId() == null? "":userHarvestResponse.getGrantId());
            localUserHarvest.setGrantTime(userHarvestResponse.getGrantTime() == null ? "":userHarvestResponse.getGrantTime());
            localUserHarvest.setGrantDepartment(userHarvestResponse.getGrantUnits() == null ? "":userHarvestResponse.getGrantUnits());
            localUserHarvest.setGrantName(userHarvestResponse.getGrantName() == null ? "":userHarvestResponse.getGrantName());
            localUserHarvests.add(localUserHarvest);
        }
        return localUserHarvests;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
    private String grantId;

    @FieldEmptyCheck(R.string.please_fill_harvest_name)
    @FieldPosition(0)
    private String grantName;

    @FieldEmptyCheck(R.string.please_fill_harvest_department)
    @FieldPosition(1)
    private String grantDepartment;

    @FieldEmptyCheck(R.string.please_fill_harvest_time)
    @FieldPosition(2)
    private String grantTime;
    private UserHarvestDetail mUserHarvestDetail;

    public UserHarvestDetail getUserHarvestDetail() {
        return mUserHarvestDetail;
    }

    public void setUserHarvestDetail(UserHarvestDetail userHarvestDetail) {
        mUserHarvestDetail = userHarvestDetail;
    }

    public String getGrantId() {
        return grantId;
    }

    public void setGrantId(String grantId) {
        this.grantId = grantId;
    }

    public String getGrantName() {
        return grantName;
    }

    public void setGrantName(String grantName) {
        this.grantName = grantName;
    }

    public String getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(String grantTime) {
        this.grantTime = grantTime;
    }

    public String getGrantDepartment() {
        return grantDepartment;
    }

    public void setGrantDepartment(String grantDepartment) {
        this.grantDepartment = grantDepartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHarvest that = (UserHarvest) o;

        if (grantId != null ? !grantId.equals(that.grantId) : that.grantId != null) return false;
        if (grantName != null ? !grantName.equals(that.grantName) : that.grantName != null)
            return false;
        if (grantTime != null ? !grantTime.equals(that.grantTime) : that.grantTime != null)
            return false;
        if (grantDepartment != null ? !grantDepartment.equals(that.grantDepartment) : that.grantDepartment != null)
            return false;
        return mUserHarvestDetail != null ? mUserHarvestDetail.equals(that.mUserHarvestDetail) : that.mUserHarvestDetail == null;

    }

    @Override
    public int hashCode() {
        int result = grantId != null ? grantId.hashCode() : 0;
        result = 31 * result + (grantName != null ? grantName.hashCode() : 0);
        result = 31 * result + (grantTime != null ? grantTime.hashCode() : 0);
        result = 31 * result + (grantDepartment != null ? grantDepartment.hashCode() : 0);
        result = 31 * result + (mUserHarvestDetail != null ? mUserHarvestDetail.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.grantId);
        dest.writeString(this.grantName);
        dest.writeString(this.grantTime);
        dest.writeString(this.grantDepartment);
        dest.writeParcelable(this.mUserHarvestDetail, flags);
    }

    public UserHarvest() {
    }

    protected UserHarvest(Parcel in) {
        this.grantId = in.readString();
        this.grantName = in.readString();
        this.grantTime = in.readString();
        this.grantDepartment = in.readString();
        this.mUserHarvestDetail = in.readParcelable(UserHarvestDetail.class.getClassLoader());
    }

    public static final Creator<UserHarvest> CREATOR = new Creator<UserHarvest>() {
        @Override
        public UserHarvest createFromParcel(Parcel source) {
            return new UserHarvest(source);
        }

        @Override
        public UserHarvest[] newArray(int size) {
            return new UserHarvest[size];
        }
    };

}
