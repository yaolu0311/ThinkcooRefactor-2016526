package com.yz.im.model.entity;

/**
 * Created by cys on 2016/7/26
 */
public class ReportEntity {

    private String title;
    private String index;
    private boolean isChecked;

    public ReportEntity(String title, String index, boolean isChecked) {
        this.title = title;
        this.index = index;
        this.isChecked = isChecked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
