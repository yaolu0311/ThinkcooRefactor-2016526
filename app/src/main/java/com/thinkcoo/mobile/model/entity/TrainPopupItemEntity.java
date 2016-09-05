package com.thinkcoo.mobile.model.entity;

/**
 * Created by Leevin
 * CreateTime: 2016/8/20  17:05
 * train search item 实体
 */
public class TrainPopupItemEntity {

    private String name;
    private String code; // 地区code
    private boolean isChecked;

    public TrainPopupItemEntity(String name, String code, boolean isChecked) {
        this.name = name;
        this.code = code;
        this.isChecked = isChecked;
    }

    public TrainPopupItemEntity(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
