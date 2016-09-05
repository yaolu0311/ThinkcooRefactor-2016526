package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.Banner;

import java.util.List;

/**
 * Created by Robert.yao on 2016/7/18.
 */
public interface BannerView extends MvpView{

    void setBannerList(List<Banner> bannerList);

}
