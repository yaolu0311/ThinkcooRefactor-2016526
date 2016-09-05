package com.yz.im.model.entity.serverresponse;

/**
 * Created by cys on 2016/7/15
 */
public class SystemSettingResponse {

    /**
     * isVerify : 1
     * isMessageRemind : 1
     * isReceveStranger : 1
     */

    private String isVerify;
    private String isMessageRemind;
    private String isReceveStranger;

    public String getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(String isVerify) {
        this.isVerify = isVerify;
    }

    public String getIsMessageRemind() {
        return isMessageRemind;
    }

    public void setIsMessageRemind(String isMessageRemind) {
        this.isMessageRemind = isMessageRemind;
    }

    public String getIsReceveStranger() {
        return isReceveStranger;
    }

    public void setIsReceveStranger(String isReceveStranger) {
        this.isReceveStranger = isReceveStranger;
    }
}
