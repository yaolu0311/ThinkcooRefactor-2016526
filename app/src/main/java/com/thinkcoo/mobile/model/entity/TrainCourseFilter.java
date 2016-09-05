package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.presentation.views.PageMachine;

/**
 * Created by Leevin
 * CreateTime: 2016/8/20  13:04
 */
public class TrainCourseFilter implements RequestParam{
    /**
     * 搜索课程过滤实体
     */
    private String keyword;
    private String areaCode;
    private String price ;
    private String courseType;
    private String startTime;


    private PageMachine pageMachine;
    private boolean isUpdateNow;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public PageMachine getPageMachine() {
        return pageMachine;
    }

    public void setPageMachine(PageMachine pageMachine) {
        this.pageMachine = pageMachine;
    }

    public boolean isUpdateNow() {
        return isUpdateNow;
    }

    public void setUpdateNow(boolean updateNow) {
        isUpdateNow = updateNow;
    }
}
