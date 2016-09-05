package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.domain.trade.ClearGoodsSearchHistoryUseCase;
import com.thinkcoo.mobile.domain.trade.GetGoodsSearchHistoryByTypeUseCase;
import com.thinkcoo.mobile.domain.trade.SaveGoodsSearchHistoryUseCase;
import com.thinkcoo.mobile.model.entity.GoodsSearchHistory;
import com.thinkcoo.mobile.presentation.mvp.views.GoodsSearchView;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/8/2.
 */
public class GoodsSearchPresenter extends MvpBasePresenter<GoodsSearchView>{

    GetGoodsSearchHistoryByTypeUseCase mGetGoodsSearchHistoryByTypeUseCase;
    SaveGoodsSearchHistoryUseCase mSaveGoodsSearchHistoryUseCase;
    ClearGoodsSearchHistoryUseCase mClearGoodsSearchHistoryUseCase;

    @Inject
    public GoodsSearchPresenter( GetGoodsSearchHistoryByTypeUseCase getGoodsSearchHistoryByTypeUseCase , SaveGoodsSearchHistoryUseCase saveGoodsSearchHistoryUseCase ,ClearGoodsSearchHistoryUseCase clearGoodsSearchHistoryUseCase ) {
        mGetGoodsSearchHistoryByTypeUseCase = getGoodsSearchHistoryByTypeUseCase;
        mSaveGoodsSearchHistoryUseCase = saveGoodsSearchHistoryUseCase;
        mClearGoodsSearchHistoryUseCase = clearGoodsSearchHistoryUseCase;

    }

    public void loadSearchHistory(int goodsType) {

        mGetGoodsSearchHistoryByTypeUseCase.execute(new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(List<String> strings) {
                getView().setSearchHistoryList(strings);
            }
        },goodsType);

    }
    public void clearSearchHistory(int goodsType) {

        mClearGoodsSearchHistoryUseCase.execute(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        },goodsType);

    }
    public void addSearchHistory(String s,int goodsType) {


        GoodsSearchHistory goodsSearchHistory = new GoodsSearchHistory();
        goodsSearchHistory.setContent(s);
        goodsSearchHistory.setGoodsType(goodsType);

        mSaveGoodsSearchHistoryUseCase.execute(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                if (!isViewAttached()){
                    return;
                }
                getView().setSearchHistoryList(Collections.EMPTY_LIST);
            }
        },goodsSearchHistory);

    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mClearGoodsSearchHistoryUseCase.unSubscribe();
        mSaveGoodsSearchHistoryUseCase.unSubscribe();
        mClearGoodsSearchHistoryUseCase.unSubscribe();
    }
}
