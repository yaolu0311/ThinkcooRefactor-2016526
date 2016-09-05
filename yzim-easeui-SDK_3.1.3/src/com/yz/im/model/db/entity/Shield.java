package com.yz.im.model.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrator.publicmodule.entity.nullentities.Nullable;
import com.example.administrator.publicmodule.util.IdOffsetUtil;
import com.yz.im.model.entity.nullentities.NullShield;
import com.yz.im.model.entity.serverresponse.ShieldResponse;
import com.yzkj.android.orm.annotation.Column;
import com.yzkj.android.orm.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cys on 2016/7/19
 * 屏蔽人数据表
 */
@Table(name = "MayAcquaintalBean_v2")
public class Shield implements Nullable, Parcelable {

    public static Shield NULL_SHIELD = new NullShield();

    public static List<Shield> fromServerResponse(List<ShieldResponse> data) {
        List<Shield> shieldList = new ArrayList<>();
        if (data == null) {
            return shieldList;
        }
        for (ShieldResponse response : data) {
            Shield shield = new Shield();
            shield.setShield(response.getIsShield());
            shield.setImager(response.getHeadPortrait());
            shield.setUserId(IdOffsetUtil.minusOffset(response.getEasemobUserName()+""));
            shield.setName(response.getRealName());
            shieldList.add(shield);
        }
        return shieldList;
    }

    @Column(name = "imager")
    private String imager;
    @Column(name = "isVerify")
    private String isVerify;
    @Column(name = "userId", isId = true, autoGen = false)
    private String userId;
    @Column(name = "school")
    private String school;
    @Column(name = "sex")
    private String sex;
    @Column(name = "shield")
    private String shield;
    @Column(name = "name")
    private String name;
    @Column(name = "userName")
    private String userName;

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
        dest.writeString(this.imager);
        dest.writeString(this.isVerify);
        dest.writeString(this.userId);
        dest.writeString(this.school);
        dest.writeString(this.sex);
        dest.writeString(this.shield);
        dest.writeString(this.name);
        dest.writeString(this.userName);
    }

    public Shield() {
    }

    protected Shield(Parcel in) {
        this.imager = in.readString();
        this.isVerify = in.readString();
        this.userId = in.readString();
        this.school = in.readString();
        this.sex = in.readString();
        this.shield = in.readString();
        this.name = in.readString();
        this.userName = in.readString();
    }

    public static final Parcelable.Creator<Shield> CREATOR = new Parcelable.Creator<Shield>() {
        @Override
        public Shield createFromParcel(Parcel source) {
            return new Shield(source);
        }

        @Override
        public Shield[] newArray(int size) {
            return new Shield[size];
        }
    };

    public String getImager() {
        return imager;
    }

    public void setImager(String imager) {
        this.imager = imager;
    }

    public String getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(String isVerify) {
        this.isVerify = isVerify;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getShield() {
        return shield;
    }

    public void setShield(String shield) {
        this.shield = shield;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
