package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.entity.serverresponse.StudentCheckResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.StudentResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Wyy on 2016/5/19.
 */
public class Student implements Nullable, Parcelable {

    private String className;
    private String eventRosterId;
    private String headPortrait;
    private String studentNo;
    private String realName;
    private String accountId;
    private boolean isCheck;
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getEventRosterId() {
        return eventRosterId;
    }

    public void setEventRosterId(String eventRosterId) {
        this.eventRosterId = eventRosterId;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
        dest.writeString(this.className);
        dest.writeString(this.eventRosterId);
        dest.writeString(this.headPortrait);
        dest.writeString(this.studentNo);
        dest.writeString(this.realName);
        dest.writeString(this.accountId);
    }

    public Student() {
    }

    protected Student(Parcel in) {
        this.className = in.readString();
        this.eventRosterId = in.readString();
        this.headPortrait = in.readString();
        this.studentNo = in.readString();
        this.realName = in.readString();
        this.accountId = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
        public static List<Student> fromServerResponse(StudentResponse studentResponseList , ServerDataConverter serverDataConverter) {

        List<Student> formatList = new ArrayList<Student>();
        if(null == studentResponseList){
            return formatList;
        }
        for (StudentResponse.ListBean response: studentResponseList.getList()) {

            Student entity = new Student();
            entity.setAccountId(response.getAccountId()==null?"0":response.getAccountId());
            entity.setEventRosterId(response.getEventRosterId()==null?"0":response.getEventRosterId());
            entity.setStudentNo(response.getStudentNo()==null?"":response.getStudentNo());
            entity.setRealName(response.getRealName()==null?"":response.getRealName());
            entity.setHeadPortrait(response.getHeadPortrait()==null?"":response.getHeadPortrait());
            entity.setClassName(response.getClassName()==null?"":response.getClassName());
            entity.setCheck(true);
            formatList.add(entity);
        }
        return formatList;
    }
    public static List<Student> checkFromServerResponse(List<StudentCheckResponse> studentResponseList , ServerDataConverter serverDataConverter) {

        List<Student> formatList = new ArrayList<Student>();
        if(null == studentResponseList){
            return formatList;
        }
        for (StudentCheckResponse response: studentResponseList) {

            Student entity = new Student();
            entity.setAccountId(response.getAccountId()==null?"0":response.getAccountId());
            entity.setEventRosterId(response.getEventRosterId()==null?"0":response.getEventRosterId());
            entity.setStudentNo(response.getStudentNo()==null?"":response.getStudentNo());
            entity.setRealName(response.getRealName()==null?"":response.getRealName());
            entity.setHeadPortrait(response.getHeadPortrait()==null?"":response.getHeadPortrait());
//            entity.setClassName(response.getClassName()==null?"":response.getClassName());
            entity.setCheck(true);
            formatList.add(entity);
        }
        return formatList;
    }
    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
