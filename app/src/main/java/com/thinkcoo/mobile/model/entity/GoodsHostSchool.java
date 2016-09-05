package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.presentation.views.component.GoodsFilterView;

/**
 * Created by Robert.yao on 2016/8/2.
 */
public class GoodsHostSchool implements GoodsFilterView.GoodsFilterContent{

    private String schoolName;
    private String schoolCode;
    private boolean isSelected;


    public GoodsHostSchool(String schoolName, String schoolCode, boolean isSelected) {
        this.schoolName = schoolName;
        this.schoolCode = schoolCode;
        this.isSelected = isSelected;
    }

    @Override
    public String getContentName() {
        return schoolName;
    }
    @Override
    public boolean isSelected() {
        return isSelected;
    }
    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public static GoodsHostSchool createDefault() {
        return new GoodsHostSchool("全部","1",true);
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }
}
