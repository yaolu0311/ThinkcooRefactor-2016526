package com.thinkcoo.mobile.domain.user;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.injector.modules.ApplicationModule;
import com.thinkcoo.mobile.model.db.DataDictionaryDao;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  9:32
 */
public class QueryAreaUseCase extends UseCase<Object> {

    DataDictionaryDao mDataDictionaryDao;

    @Inject
    public QueryAreaUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler mUiThread,
                                      @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler mExecutorThread, DataDictionaryDao dataDictionaryDao) {
        super(mUiThread, mExecutorThread);
        mDataDictionaryDao = dataDictionaryDao;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        // TODO: 2016/6/14
//        return mAreaDao.queryAll(AreaInfo.class);
        return null;
    }
}
