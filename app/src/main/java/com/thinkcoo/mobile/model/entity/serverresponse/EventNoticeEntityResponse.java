package com.thinkcoo.mobile.model.entity.serverresponse;


/**
 * Created by Wyy on 2016/5/19.
 */
public class EventNoticeEntityResponse {


    /**
     * id : 公告ID
     * noticeContent : 公告内容
     * createTime : 创建事件
     */

    private String id;
    private String noticeContent;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}







