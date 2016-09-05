package com.thinkcoo.mobile.presentation.views;

import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;

/**
 * Created by Robert.yao on 2016/7/21.
 */
public class PageMachine implements RequestParam{


    public static final String TAG = "PageMachine";

    public static final int DEFAULT_PAGE_CONTENT_COUNT = 10;

    private int pageIndex;
    private int totalPage;
    private int pageContentCount = DEFAULT_PAGE_CONTENT_COUNT;

    public void next(){
        if (pageIndex >= totalPage){
            return;
        }
        synchronized (this){
            pageIndex ++;
        }
    }
    public void prev(){
        if (pageIndex == 0){
            return;
        }
        synchronized (this){
            pageIndex --;
        }
    }
    public boolean hasLoadedAllItems(){
        return pageIndex == totalPage;
    }
    public void setPageContentCount(int pageContentCount) {
        this.pageContentCount = pageContentCount;
    }
    public void reset(){
        synchronized (this){
            pageIndex = 0;
            totalPage = 0;
        }
    }
    public void first(){
        synchronized (this){
            pageIndex = 1;
            totalPage = 0;
        }
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getPageContentCount() {
        return pageContentCount;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
