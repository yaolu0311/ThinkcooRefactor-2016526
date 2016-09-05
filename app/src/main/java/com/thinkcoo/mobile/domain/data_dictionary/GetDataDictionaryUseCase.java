package com.thinkcoo.mobile.domain.data_dictionary;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.repository.DataDictionaryRepository;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategy;

import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/6/15.
 */
public class GetDataDictionaryUseCase extends UseCase<DataDictionaryStrategy>{

    DataDictionaryRepository mDataDictionaryRepository;

    @Inject
    public GetDataDictionaryUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, DataDictionaryRepository dataDictionaryRepository) {
        super(mUiThread, mExecutorThread);
        mDataDictionaryRepository = dataDictionaryRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(DataDictionaryStrategy... q) {
        return mDataDictionaryRepository.queryDataDictionary(q[0]);
    }
}
