package com.thinkcoo.mobile.presentation.mvp.presenters;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.domain.data_dictionary.SearchDataDictionaryUseCase;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategy;
import com.thinkcoo.mobile.presentation.mvp.views.SingleLineEditAndAutoHintView;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/15.
 */
public class EditAndAutoHinPresenter extends MvpBasePresenter<SingleLineEditAndAutoHintView>{


    SearchDataDictionaryUseCase mSearchDataDictionaryUseCase;

    @Inject
    public EditAndAutoHinPresenter(SearchDataDictionaryUseCase searchDataDictionaryUseCase) {
        mSearchDataDictionaryUseCase = searchDataDictionaryUseCase;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }

    public void startSearch(DataDictionaryStrategy dataDictionaryStrategy, String ...input) {
        mSearchDataDictionaryUseCase.execute(getSub(),dataDictionaryStrategy,input);
    }

    private Subscriber getSub() {

        return new Subscriber<List<DataDictionary>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(List<DataDictionary> searchResult) {
                getView().setSearchResult(searchResult);
            }
        };
    }

}
