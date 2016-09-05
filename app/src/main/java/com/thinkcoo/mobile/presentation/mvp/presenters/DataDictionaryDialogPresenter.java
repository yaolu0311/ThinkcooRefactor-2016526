package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.domain.data_dictionary.GetDataDictionaryUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategy;
import com.thinkcoo.mobile.presentation.mvp.views.DataDictionaryDialogView;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/6/15.
 */
@ActivityScope
public class DataDictionaryDialogPresenter extends MvpBasePresenter<DataDictionaryDialogView>{

    GetDataDictionaryUseCase mGetDataDictionaryUseCase;

    @Inject
    public DataDictionaryDialogPresenter(GetDataDictionaryUseCase getDataDictionaryUseCase) {
        mGetDataDictionaryUseCase = getDataDictionaryUseCase;
    }

    public void queryDataDictionary(DataDictionaryStrategy dataDictionaryStrategy){
        mGetDataDictionaryUseCase.execute(getSub(),dataDictionaryStrategy);
    }

    private Subscriber<List<DataDictionary>> getSub(){

        return new Subscriber<List<DataDictionary>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                detachView(false);
            }

            @Override
            public void onNext(List<DataDictionary> dataDictionaries) {
                getView().showDialogByDataDictionaryList(dataDictionaries);
                getView().selectDefaultDataDictionaryIfNeed();
            }
        };
    }

}
