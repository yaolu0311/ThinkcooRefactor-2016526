package com.thinkcoo.mobile.model.entity.RequestParam;

import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.presentation.views.PageMachine;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public class LoadMyCollectGoodsParam implements RequestParam{

    private boolean isUpdateNow;
    private PageMachine mPageMachine;
    private int queryType;

    public boolean isUpdateNow() {
        return isUpdateNow;
    }

    public void setUpdateNow(boolean updateNow) {
        isUpdateNow = updateNow;
    }

    public PageMachine getPageMachine() {
        return mPageMachine;
    }

    public void setPageMachine(PageMachine pageMachine) {
        mPageMachine = pageMachine;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public boolean isBuyType(){
        return queryType == Goods.BUY;
    }
    public boolean isSellType(){
        return queryType == Goods.SELL;
    }
}
