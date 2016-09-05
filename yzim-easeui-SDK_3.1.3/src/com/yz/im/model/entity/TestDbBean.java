package com.yz.im.model.entity;

import android.os.Parcel;

import com.example.administrator.publicmodule.entity.nullentities.Nullable;
import com.yzkj.android.orm.annotation.Column;
import com.yzkj.android.orm.annotation.Table;

/**
 * Created by cys on 2016/8/4
 */
@Table(name = "test_db_bean")
public class TestDbBean extends BaseSortEntity implements Nullable{

    public static  TestDbBean create(){
        return new TestDbBean("姚橹",null);
    }

    @Column(name = "_id",isId = true , autoGen = true)
    private int id;
    @Column(name = "Name")
    private String name;
    @Column(name = "sex")
    private String sex;

    public TestDbBean(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public TestDbBean() {

    }

    public int getId() {
        return id;
    }

    public TestDbBean setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TestDbBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public TestDbBean setSex(String sex) {
        this.sex = sex;
        return this;
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
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.sex);
    }

    protected TestDbBean(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.name = in.readString();
        this.sex = in.readString();
    }

    public static final Creator<TestDbBean> CREATOR = new Creator<TestDbBean>() {
        @Override
        public TestDbBean createFromParcel(Parcel source) {
            return new TestDbBean(source);
        }

        @Override
        public TestDbBean[] newArray(int size) {
            return new TestDbBean[size];
        }
    };
}
