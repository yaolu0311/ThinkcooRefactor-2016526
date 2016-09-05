package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.annotation.FieldEmptyCheck;
import com.thinkcoo.mobile.annotation.FieldPosition;
import com.thinkcoo.mobile.model.entity.nullentities.NullPartTimeJobDetail;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class PartTimeJobDetail extends UserStatusDetail implements Nullable, Parcelable {

    public static final PartTimeJobDetail NULL_PART_TIME_JOB_DETAIL = new NullPartTimeJobDetail();

    @Override
    public boolean isEmpty() {
        return false;
    }

    @FieldEmptyCheck(R.string.employer_id_is_empty)
    @FieldPosition(0)
    private String employerId;
    /**行业方向*/
    @FieldEmptyCheck(R.string.industry_direction_is_empty)
    @FieldPosition(1)
    private String industry_direction;
    /**部门*/
    private String branchName;
    /**职责*/
    private String responsibility;

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public String getIndustry_direction() {
        return industry_direction;
    }

    public void setIndustry_direction(String industry_direction) {
        this.industry_direction = industry_direction;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.employerId);
        dest.writeString(this.industry_direction);
        dest.writeString(this.branchName);
        dest.writeString(this.responsibility);
    }

    public PartTimeJobDetail() {
    }

    protected PartTimeJobDetail(Parcel in) {
        this.employerId = in.readString();
        this.industry_direction = in.readString();
        this.branchName = in.readString();
        this.responsibility = in.readString();
    }

    public static final Parcelable.Creator<PartTimeJobDetail> CREATOR = new Parcelable.Creator<PartTimeJobDetail>() {
        @Override
        public PartTimeJobDetail createFromParcel(Parcel source) {
            return new PartTimeJobDetail(source);
        }

        @Override
        public PartTimeJobDetail[] newArray(int size) {
            return new PartTimeJobDetail[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartTimeJobDetail that = (PartTimeJobDetail) o;

        if (employerId != null ? !employerId.equals(that.employerId) : that.employerId != null)
            return false;
        if (industry_direction != null ? !industry_direction.equals(that.industry_direction) : that.industry_direction != null)
            return false;
        if (branchName != null ? !branchName.equals(that.branchName) : that.branchName != null)
            return false;
        return responsibility != null ? responsibility.equals(that.responsibility) : that.responsibility == null;

    }

    @Override
    public int hashCode() {
        int result = employerId != null ? employerId.hashCode() : 0;
        result = 31 * result + (industry_direction != null ? industry_direction.hashCode() : 0);
        result = 31 * result + (branchName != null ? branchName.hashCode() : 0);
        result = 31 * result + (responsibility != null ? responsibility.hashCode() : 0);
        return result;
    }
}
