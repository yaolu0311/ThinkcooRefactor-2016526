package com.thinkcoo.mobile.domain.location;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.LocationRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Administrator on 2016/7/6 15:03
 */
public class UpLoadLocationCase extends UseCase<Object>{
    LocationRepository mLocationRepository;
    @Inject
    protected UpLoadLocationCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                 @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, LocationRepository mLocationRepository) {
        super(mUiThread, mExecutorThread);
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mLocationRepository.upLoadLocation();
    }
}
