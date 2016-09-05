package com.thinkcoo.mobile.injector.modules;

import com.thinkcoo.mobile.domain.data_dictionary.GetSortedCityListUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.DataDictionaryRepository;
import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/7/28.
 */
@Module
public class DataDictionaryModule {

    @Provides
    @ActivityScope
    GetSortedCityListUseCase provideGetSortedCityListUsecase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work, DataDictionaryRepository dataDictionaryRepository){
        return new GetSortedCityListUseCase(ui,work,dataDictionaryRepository);
    }

}
