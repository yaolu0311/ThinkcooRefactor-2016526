package com.thinkcoo.mobile.presentation.mvp.views;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.EventNoticeEntity;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import java.util.List;

/**
 * Created by Robert.yao on 2016/3/22.
 */
public interface NoticeView extends MvpView , BaseHintView , BaseActivityHelpView{
    void setNoticeList(List<EventNoticeEntity> noticeList);
    String getContent();
    Event  getEvent();
}
