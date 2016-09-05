package com.thinkcoo.mobile.domain.data_dictionary;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.repository.DataDictionaryRepository;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/7/26.
 */
public class GetSortedCityListUseCase extends UseCase<RequestParam>{

    DataDictionaryRepository mDataDictionaryRepository;
    public GetSortedCityListUseCase(Scheduler mUiThread, Scheduler mExecutorThread, DataDictionaryRepository dataDictionaryRepository) {
        super(mUiThread, mExecutorThread);
        mDataDictionaryRepository = dataDictionaryRepository;
    }
    @Override
    protected Observable buildUseCaseObservable(RequestParam... q) {
        return mDataDictionaryRepository.getSortedCityList();
    }
}
