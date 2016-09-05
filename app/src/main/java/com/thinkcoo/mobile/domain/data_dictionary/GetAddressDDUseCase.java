package com.thinkcoo.mobile.domain.data_dictionary;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.DataDictionaryRepository;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  11:23
 */
public class GetAddressDDUseCase extends UseCase<Object>{


    DataDictionaryRepository mDataDictionaryRepository;

    @Inject
    public GetAddressDDUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,@Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread , DataDictionaryRepository dataDictionaryRepository) {
        super(mUiThread, mExecutorThread);
        mDataDictionaryRepository = dataDictionaryRepository;
    }
    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mDataDictionaryRepository.getSortedCityList();
    }
}
