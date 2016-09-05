package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.model.entity.nullentities.Nullable;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  11:09
 */
public class Address implements Nullable, Parcelable {

    private String name;
    private int code;
    private String baiduCode;
    // 当服务器端返回String code为null ,或""时,本地默转化为 int ERROR_CODE
    public static final int ERROR_CODE = -1;

    @Override
    public boolean isEmpty() {
        return false;
    }

    public Address() {
    }

    public Address(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    // 处理与服务器端类型转化,默认值的问题
    public String getStringCode() {
        if (code == ERROR_CODE) {
            return "";
        } else {
            return String.valueOf(code);
        }
    }

    public void setCode(int code) {
        this.code = code;
    }



    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", code=" + code +
                '}';
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        return name;
    }

    public String getBaiduCode() {
        return baiduCode;
    }

    public void setBaiduCode(String baiduCode) {
        this.baiduCode = baiduCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.code);
        dest.writeString(this.baiduCode);
    }

    protected Address(Parcel in) {
        this.name = in.readString();
        this.code = in.readInt();
        this.baiduCode = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
