package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.annotation.FieldEmptyCheck;
import com.thinkcoo.mobile.annotation.FieldPosition;
import com.thinkcoo.mobile.model.entity.nullentities.NullTrainDetail;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class TrainDetail extends UserStatusDetail implements Nullable, Parcelable {

    public static  final TrainDetail NULL_TRAIN_DETAIL = new NullTrainDetail();

    @Override
    public boolean isEmpty() {
        return false;
    }

    @FieldEmptyCheck(R.string.student_id_is_empty)
    @FieldPosition(0)
    private String studentId;
    private String classNumber;

    public String getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.studentId);
        dest.writeString(this.classNumber);
    }

    public TrainDetail() {
    }

    protected TrainDetail(Parcel in) {
        this.studentId = in.readString();
        this.classNumber = in.readString();
    }

    public static final Parcelable.Creator<TrainDetail> CREATOR = new Parcelable.Creator<TrainDetail>() {
        @Override
        public TrainDetail createFromParcel(Parcel source) {
            return new TrainDetail(source);
        }

        @Override
        public TrainDetail[] newArray(int size) {
            return new TrainDetail[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainDetail that = (TrainDetail) o;

        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null)
            return false;
        return classNumber != null ? classNumber.equals(that.classNumber) : that.classNumber == null;

    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + (classNumber != null ? classNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TrainDetail{" +
                "studentId='" + studentId + '\'' +
                ", classNumber='" + classNumber + '\'' +
                '}';
    }
}
