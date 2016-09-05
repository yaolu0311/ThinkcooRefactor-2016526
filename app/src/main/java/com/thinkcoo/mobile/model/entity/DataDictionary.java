package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.thinkcoo.mobile.model.entity.nullentities.NullDataDictionary;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.strategy.SingleLineEditContent;

/**
 * Created by Robert.yao on 2016/6/14.
 */
public class DataDictionary implements Nullable ,SingleLineEditContent,Parcelable {

    public static final DataDictionary NULL_DATA_DICTIONARY = new NullDataDictionary();


    private int type;
    private int id;
    private String code;
    private String displayName;
    private String parentCode;

    private String data1;
    private String data2;
    private String data3;
    private String data4;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public String getData4() {
        return data4;
    }

    public void setData4(String data4) {
        this.data4 = data4;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataDictionary that = (DataDictionary) o;

        if (type != that.type) return false;
        if (id != that.id) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null)
            return false;
        if (parentCode != null ? !parentCode.equals(that.parentCode) : that.parentCode != null)
            return false;
        if (data1 != null ? !data1.equals(that.data1) : that.data1 != null) return false;
        if (data2 != null ? !data2.equals(that.data2) : that.data2 != null) return false;
        if (data3 != null ? !data3.equals(that.data3) : that.data3 != null) return false;
        return data4 != null ? data4.equals(that.data4) : that.data4 == null;

    }

    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (parentCode != null ? parentCode.hashCode() : 0);
        result = 31 * result + (data1 != null ? data1.hashCode() : 0);
        result = 31 * result + (data2 != null ? data2.hashCode() : 0);
        result = 31 * result + (data3 != null ? data3.hashCode() : 0);
        result = 31 * result + (data4 != null ? data4.hashCode() : 0);
        return result;
    }

    /**
     * 协议方法
     */
    public String getPickerViewText() {
        return displayName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeInt(this.id);
        dest.writeString(this.code);
        dest.writeString(this.displayName);
        dest.writeString(this.parentCode);
        dest.writeString(this.data1);
        dest.writeString(this.data2);
        dest.writeString(this.data3);
        dest.writeString(this.data4);
    }

    public DataDictionary() {
    }

    protected DataDictionary(Parcel in) {
        this.type = in.readInt();
        this.id = in.readInt();
        this.code = in.readString();
        this.displayName = in.readString();
        this.parentCode = in.readString();
        this.data1 = in.readString();
        this.data2 = in.readString();
        this.data3 = in.readString();
        this.data4 = in.readString();
    }

    public static final Creator<DataDictionary> CREATOR = new Creator<DataDictionary>() {
        @Override
        public DataDictionary createFromParcel(Parcel source) {
            return new DataDictionary(source);
        }

        @Override
        public DataDictionary[] newArray(int size) {
            return new DataDictionary[size];
        }
    };
}
