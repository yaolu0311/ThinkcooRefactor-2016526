package com.thinkcoo.mobile.model.entity.RequestParam;

/**
 * Created by Robert.yao on 2016/8/12.
 */
public class LoadSchoolBaiduAddressParam implements RequestParam {

    private String schoolName;
    private boolean isUpdateNow;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public boolean isUpdateNow() {
        return isUpdateNow;
    }

    public void setUpdateNow(boolean updateNow) {
        isUpdateNow = updateNow;
    }
}
