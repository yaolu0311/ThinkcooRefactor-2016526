package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.GoodsHostSchool;

import java.util.List;

/**
 * Created by Robert.yao on 2016/8/2.
 */
public interface IGoodsFilterView extends MvpView{
    void showSchoolPop(List<GoodsHostSchool> goodsHostSchoolList);
}
