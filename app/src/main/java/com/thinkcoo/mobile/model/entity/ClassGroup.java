package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.model.entity.nullentities.NullClassGroup;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.entity.serverresponse.ClassResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Wyy on 2016/5/19.
 */
public class ClassGroup implements Nullable, Parcelable {

    public static final ClassGroup NULL_OBJECT = new NullClassGroup();

    static {
        NULL_OBJECT.setGroupId("");
    }

    public static List<ClassGroup> fromServerResponse(List<ClassResponse> classResponseList , ServerDataConverter serverDataConverter) {

        List<ClassGroup> formatList = new ArrayList<ClassGroup>();
        if(null == classResponseList){
            return formatList;
        }
        for (ClassResponse response: classResponseList) {
            ClassGroup entity =new ClassGroup();
            entity.setGroupId(response.getGroupId()==null?"0":response.getGroupId());
            entity.setSchoolName(response.getSchoolName()==null?"":response.getSchoolName());
            entity.setGroupName(response.getGroupName()==null?"":response.getGroupName());
            formatList.add(entity);
        }
        return formatList;
    }

    public static final ClassGroup NULL_ACCOUNT = new NullClassGroup();
    private String groupId;
    private String schoolName;
    private String groupName;
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
        dest.writeString(this.groupId);
        dest.writeString(this.schoolName);
        dest.writeString(this.groupName);
    }

    public ClassGroup() {
    }

    protected ClassGroup(Parcel in) {
        this.groupId = in.readString();
        this.schoolName = in.readString();
        this.groupName = in.readString();
    }

    public static final Parcelable.Creator<ClassGroup> CREATOR = new Parcelable.Creator<ClassGroup>() {
        @Override
        public ClassGroup createFromParcel(Parcel source) {
            return new ClassGroup(source);
        }

        @Override
        public ClassGroup[] newArray(int size) {
            return new ClassGroup[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassGroup)) return false;

        ClassGroup that = (ClassGroup) o;

        return groupId.equals(that.groupId);

    }

    @Override
    public int hashCode() {
        return groupId.hashCode();
    }
}







