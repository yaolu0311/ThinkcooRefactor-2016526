package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.domain.banner.LoadBannerListUseCase;
import com.thinkcoo.mobile.model.entity.Banner;
import com.thinkcoo.mobile.presentation.mvp.views.BannerView;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/7/18.
 */
public class BannerPresenter extends MvpBasePresenter<BannerView>{

    private LoadBannerListUseCase mLoadBannerListUseCase;

    @Inject
    public BannerPresenter(LoadBannerListUseCase loadBannerListUseCase) {
        mLoadBannerListUseCase = loadBannerListUseCase;
    }

    public void loadBannerList(int bannerType){
        mLoadBannerListUseCase.execute(getSub(),bannerType);
    }

    private Subscriber<List<Banner>> getSub() {

        return new Subscriber<List<Banner>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Banner> banners) {
                if (!isViewAttached()){
                    return;
                }
                getView().setBannerList(banners);
            }
        };
    }

}
