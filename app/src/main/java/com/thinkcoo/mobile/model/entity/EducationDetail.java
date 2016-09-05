package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.annotation.FieldEmptyCheck;
import com.thinkcoo.mobile.annotation.FieldPosition;
import com.thinkcoo.mobile.model.entity.nullentities.NullEducationDetail;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class EducationDetail extends UserStatusDetail implements Nullable, Parcelable {

    public static final EducationDetail NULL_EDUCATION_DETAIL = new NullEducationDetail();

    @Override
    public boolean isEmpty() {
        return false;
    }

    private String department;
    private String major;
    private String classNumber;
    @FieldEmptyCheck(R.string.student_id_is_empty)
    @FieldPosition(0)
    private String studentId;
    private String postName;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.department);
        dest.writeString(this.major);
        dest.writeString(this.classNumber);
        dest.writeString(this.studentId);
        dest.writeString(this.postName);
    }

    public EducationDetail() {
    }

    protected EducationDetail(Parcel in) {
        this.department = in.readString();
        this.major = in.readString();
        this.classNumber = in.readString();
        this.studentId = in.readString();
        this.postName = in.readString();
    }

    public static final Parcelable.Creator<EducationDetail> CREATOR = new Parcelable.Creator<EducationDetail>() {
        @Override
        public EducationDetail createFromParcel(Parcel source) {
            return new EducationDetail(source);
        }

        @Override
        public EducationDetail[] newArray(int size) {
            return new EducationDetail[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EducationDetail that = (EducationDetail) o;

        if (department != null ? !department.equals(that.department) : that.department != null)
            return false;
        if (major != null ? !major.equals(that.major) : that.major != null) return false;
        if (classNumber != null ? !classNumber.equals(that.classNumber) : that.classNumber != null)
            return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null)
            return false;
        return postName != null ? postName.equals(that.postName) : that.postName == null;

    }

    @Override
    public int hashCode() {
        int result = department != null ? department.hashCode() : 0;
        result = 31 * result + (major != null ? major.hashCode() : 0);
        result = 31 * result + (classNumber != null ? classNumber.hashCode() : 0);
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        result = 31 * result + (postName != null ? postName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EducationDetail{" +
                "department='" + department + '\'' +
                ", major='" + major + '\'' +
                ", classNumber='" + classNumber + '\'' +
                ", studentId='" + studentId + '\'' +
                ", postName='" + postName + '\'' +
                '}';
    }
}
