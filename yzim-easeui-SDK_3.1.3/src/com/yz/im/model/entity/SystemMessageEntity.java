package com.yz.im.model.entity;

/**
 * Created by cys on 2016/8/11
 */
public class SystemMessageEntity {

    private String description;//消息内容
    private String url;//连接查看详情的地址
    private String title;//连接的标题
    private String imgUrl;//连接图片的地址
    private String msgType;//连接消息的类型
    private String easemobCircleId;

    public String getDescription() {
        return description;
    }

    public SystemMessageEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public SystemMessageEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SystemMessageEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public SystemMessageEntity setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getMsgType() {
        return msgType;
    }

    public SystemMessageEntity setMsgType(String msgType) {
        this.msgType = msgType;
        return this;
    }

    public String getEasemobCircleId() {
        return easemobCircleId;
    }

    public SystemMessageEntity setEasemobCircleId(String easemobCircleId) {
        this.easemobCircleId = easemobCircleId;
        return this;
    }
}
