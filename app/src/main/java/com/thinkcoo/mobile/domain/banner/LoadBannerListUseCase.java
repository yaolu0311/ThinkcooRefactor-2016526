package com.thinkcoo.mobile.domain.banner;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.repository.BannerRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/7/19.
 */
public class LoadBannerListUseCase extends UseCase<Integer>{

    BannerRepository mBannerRepository;

    @Inject
    public LoadBannerListUseCase(Scheduler mUiThread, Scheduler mExecutorThread,BannerRepository bannerRepository) {
        super(mUiThread, mExecutorThread);
        mBannerRepository = bannerRepository;
    }
    @Override
    protected Observable buildUseCaseObservable(Integer... q) {
        return mBannerRepository.loadBannerListByType(q[0]);
    }
}
