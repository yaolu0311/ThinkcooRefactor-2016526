package com.yz.im.model.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrator.publicmodule.entity.nullentities.Nullable;
import com.yz.im.model.entity.nullentities.NullSystemSetting;
import com.yz.im.model.entity.serverresponse.SystemSettingResponse;
import com.yzkj.android.orm.annotation.Column;
import com.yzkj.android.orm.annotation.Table;

/**
 * Created by cys on 2016/7/19
 * 系统设置数据表
 */
@Table(name = "SystemSetingFlage_v2")
public class SystemSetting implements Nullable, Parcelable {

    public static SystemSetting NULL_SETTING = new NullSystemSetting();

    public static SystemSetting fromServerResponse(SystemSettingResponse data) {
        SystemSetting systemSetting = new SystemSetting();
        if (data == null) {
            return systemSetting;
        }

        systemSetting.setIsMessageRemind(data.getIsMessageRemind());
        systemSetting.setIsReceveStranger(data.getIsReceveStranger());
        systemSetting.setIsVerify(data.getIsVerify());
        return systemSetting;
    }

    @Column(name = "id", isId = true)
    private int id;//数据库id
    @Column(name = "isMessageRemind")
    private String isMessageRemind;//新消息提醒  0
    @Column(name = "isReceveStranger")
    private String isReceveStranger;//允许陌生人发消息   1
    @Column(name = "isVerify")
    private String isVerify;//加我为朋友时需要验证 ："0"不需要验证，"1"需要验证

    @Override
    public boolean isEmpty() {
        return false;
    }

    public String getIsMessageRemind() {

        return isMessageRemind;
    }

    public SystemSetting setIsMessageRemind(String isMessageRemind) {
        this.isMessageRemind = isMessageRemind;
        return this;
    }

    public String getIsReceveStranger() {
        return isReceveStranger;
    }

    public SystemSetting setIsReceveStranger(String isReceveStranger) {
        this.isReceveStranger = isReceveStranger;
        return this;
    }

    public String getIsVerify() {
        return isVerify;
    }

    public SystemSetting setIsVerify(String isVerify) {
        this.isVerify = isVerify;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.isMessageRemind);
        dest.writeString(this.isReceveStranger);
        dest.writeString(this.isVerify);
    }

    public SystemSetting() {
    }

    protected SystemSetting(Parcel in) {
        this.id = in.readInt();
        this.isMessageRemind = in.readString();
        this.isReceveStranger = in.readString();
        this.isVerify = in.readString();
    }

    public static final Creator<SystemSetting> CREATOR = new Creator<SystemSetting>() {
        @Override
        public SystemSetting createFromParcel(Parcel source) {
            return new SystemSetting(source);
        }

        @Override
        public SystemSetting[] newArray(int size) {
            return new SystemSetting[size];
        }
    };
}
