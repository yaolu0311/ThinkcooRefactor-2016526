package com.thinkcoo.mobile.domain.data_dictionary;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.repository.DataDictionaryRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/11.
 */
public class QuerySchoolCityNameUseCase extends UseCase<String>{

    DataDictionaryRepository mDataDictionaryRepository;

    public QuerySchoolCityNameUseCase(Scheduler mUiThread, Scheduler mExecutorThread, DataDictionaryRepository dataDictionaryRepository) {
        super(mUiThread, mExecutorThread);
        mDataDictionaryRepository = dataDictionaryRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(String... q) {
        return null;
    }
}
