package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.Address;

import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/8/18  13:31
 */
public interface TrainSearchView extends MvpView{

    void setArea(List<Address> addresses);
}
