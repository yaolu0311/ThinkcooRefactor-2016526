package com.thinkcoo.mobile.model.entity.serverresponse;

import java.io.Serializable;

/**
 * <p>Title: HomeImageBean.java</p>
 * <p>首页轮播图片bean</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: 西安影子</p>
 * <p>CreateTime: 2016-1-11 下午6:34:00</p>
 *
 * @author 自己填写
 * @version V1.0
 * @since JDK1.7
 * @CheckItem 自己填写
 */
public class HomeImageBean implements Serializable {

    /** 自己填写 */
    private static final long serialVersionUID = 1L;

    /**
     * 图片路径
     */
    private String picPath;
    /**
     * 图片id
     */
    private String adCode;
    /**
     * 点击指向连接
     */
    private String linkUrl;
    /**
     * @return the picPath
     */
    public String getPicPath() {
        return picPath;
    }
    /**
     * @param picPath the picPath to set
     */
    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
    /**
     * @return the adCode
     */
    public String getAdCode() {
        return adCode;
    }
    /**
     * @param adCode the adCode to set
     */
    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }
    /**
     * @return the linkUrl
     */
    public String getLinkUrl() {
        return linkUrl;
    }
    /**
     * @param linkUrl the linkUrl to set
     */
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }



}
