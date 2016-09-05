package com.thinkcoo.mobile.domain.location;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.LocationRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/7/6.
 */
public class GetLocationUseCase extends UseCase<Void>{

    LocationRepository mLocationRepository;

    @Inject
    public GetLocationUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                              @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread,LocationRepository locationRepository) {
        super(mUiThread, mExecutorThread);
        mLocationRepository = locationRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Void... q) {
        return mLocationRepository.requestLocationUseBaiduSdk();
    }
}
