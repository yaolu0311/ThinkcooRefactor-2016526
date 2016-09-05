package com.thinkcoo.mobile.model.entity;


import com.thinkcoo.mobile.model.entity.serverresponse.ClassResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.EventNoticeEntityResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wyy on 2016/5/19.
 */
public class EventNoticeEntity {


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

    public static List<EventNoticeEntity> fromServerResponse(List<EventNoticeEntityResponse> eventNoticeEntityResponseList , ServerDataConverter serverDataConverter) {

        List<EventNoticeEntity> formatList = new ArrayList<EventNoticeEntity>();
        if(null == eventNoticeEntityResponseList){
            return formatList;
        }
        for (EventNoticeEntityResponse response: eventNoticeEntityResponseList) {
            EventNoticeEntity entity = new EventNoticeEntity();
            entity.setId(response.getId()==null?"0":response.getId());
            entity.setCreateTime(response.getCreateTime()==null?"":response.getCreateTime());
            entity.setNoticeContent(response.getNoticeContent()==null?"":response.getNoticeContent());
            formatList.add(entity);
        }
        return formatList;
    }
}







