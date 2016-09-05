package com.thinkcoo.mobile.model.entity.RequestParam;

import com.thinkcoo.mobile.model.entity.MyGoods;
import com.thinkcoo.mobile.presentation.views.PageMachine;

/**
 * Created by Robert.yao on 2016/8/9.
 */
public class GetMyGoodsRequestParam implements RequestParam {

    PageMachine pageMachine;
    String myGoodsType;
    boolean isUpdateNow;

    public PageMachine getPageMachine() {
        return pageMachine;
    }
    public void setPageMachine(PageMachine pageMachine) {
        this.pageMachine = pageMachine;
    }
    public void setMyGoodsType(String myGoodsType) {
        this.myGoodsType = myGoodsType;
    }
    public boolean isSellType(){
        return String.valueOf(MyGoods.SELL).equals(myGoodsType);
    }
    public boolean isUpdateNow() {
        return isUpdateNow;
    }
    public void setUpdateNow(boolean updateNow) {
        isUpdateNow = updateNow;
    }

    public String getMyGoodsType() {
        return myGoodsType;
    }
}
