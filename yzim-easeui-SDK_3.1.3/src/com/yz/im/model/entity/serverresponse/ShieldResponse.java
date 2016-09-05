package com.yz.im.model.entity.serverresponse;

/**
 * Created by cys on 2016/7/15
 */
public class ShieldResponse {

    /**
     * isShield : 1
     * headPortrait : http://117.34.109.231/yingzi-mobile/upload/thinkcoo-mobile/headIcon/default_logo.png
     * easemobUserName : 110027
     * realName : 郑
     */

    private String isShield;
    private String headPortrait;
    private int easemobUserName;
    private String realName;

    public String getIsShield() {
        return isShield;
    }

    public void setIsShield(String isShield) {
        this.isShield = isShield;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public int getEasemobUserName() {
        return easemobUserName;
    }

    public void setEasemobUserName(int easemobUserName) {
        this.easemobUserName = easemobUserName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
