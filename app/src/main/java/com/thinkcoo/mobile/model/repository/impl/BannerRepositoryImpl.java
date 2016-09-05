package com.thinkcoo.mobile.model.repository.impl;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.model.db.BannerDao;
import com.thinkcoo.mobile.model.entity.Banner;
import com.thinkcoo.mobile.model.entity.serverresponse.HomeImageBean;
import com.thinkcoo.mobile.model.repository.BannerRepository;
import com.thinkcoo.mobile.model.rest.apis.BannerApi;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Robert.yao on 2016/7/19.
 */
public class BannerRepositoryImpl implements BannerRepository {

    public static final String TAG = "BannerRepositoryImpl";

    BannerApi mBannerApi;
    BannerDao mBannerDao;

    @Inject
    public BannerRepositoryImpl(BannerApi bannerApi,BannerDao bannerDao) {
        mBannerApi = bannerApi;
        mBannerDao = bannerDao;
    }

    @Override
    public Observable<List<Banner>> loadBannerListByType(final Integer integer) {
        return mBannerDao.queryByType(integer).concatWith(mBannerApi.loadBannerList().map(new Func1<BaseResponse<List<HomeImageBean>>, List<Banner>>() {
            @Override
            public List<Banner> call(BaseResponse<List<HomeImageBean>> listBaseResponse) {
                ThinkcooLog.e(TAG,"==== start get banner ===");
                List<HomeImageBean> homeImageBeanList = listBaseResponse.getData();
                List<Banner> bannerList = new ArrayList<>();
                for (HomeImageBean ho : homeImageBeanList) {
                    bannerList.add(Banner.create(ho));
                }
                return bannerList;
            }
        })).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG,throwable.getMessage(),throwable);
            }
        });
    }

}
