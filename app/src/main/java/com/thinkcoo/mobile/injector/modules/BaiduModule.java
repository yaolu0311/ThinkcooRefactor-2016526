package com.thinkcoo.mobile.injector.modules;

import com.thinkcoo.mobile.domain.baidu.LoadSchoolBaiduAddressUseCase;
import com.thinkcoo.mobile.domain.data_dictionary.QuerySchoolCityNameUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.repository.BaiduRepository;
import com.thinkcoo.mobile.model.repository.DataDictionaryRepository;

import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/11.
 */
@Module
public class BaiduModule {

    @Provides
    @ActivityScope
    LoadSchoolBaiduAddressUseCase provideLoadSchoolBaiduAddressUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , BaiduRepository baiduRepository){
        return new LoadSchoolBaiduAddressUseCase(ui,work,baiduRepository);
    }
    @Provides
    @ActivityScope
    QuerySchoolCityNameUseCase provideQuerySchoolCityNameUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , DataDictionaryRepository dataDictionaryRepository){
        return new QuerySchoolCityNameUseCase(ui,work, dataDictionaryRepository);
    }

}
