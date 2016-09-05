package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * Created by Robert.yao on 2016/5/26.
 */
public class CheckVcodeResponse {


    private String encryptStr;
    private String userId;

    public String getEncryptStr() {
        return encryptStr;
    }

    public void setEncryptStr(String encryptStr) {
        this.encryptStr = encryptStr;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
