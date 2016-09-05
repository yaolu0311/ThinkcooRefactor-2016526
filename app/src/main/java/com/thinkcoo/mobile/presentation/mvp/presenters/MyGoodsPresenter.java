package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.trade.DeleteMyGoodsUseCase;
import com.thinkcoo.mobile.domain.trade.GetMyGoodsUseCase;
import com.thinkcoo.mobile.domain.trade.SoldOutGoodsUseCase;
import com.thinkcoo.mobile.model.entity.MyGoods;
import com.thinkcoo.mobile.model.entity.RequestParam.GetMyGoodsRequestParam;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.presentation.mvp.views.MyGoodsView;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/8/9.
 */
public class MyGoodsPresenter extends BaseLcePagedPresenter<MyGoods, MyGoodsView<MyGoods>>{

    SoldOutGoodsUseCase mSoldOutGoodsUseCase;
    DeleteMyGoodsUseCase mDeleteMyGoodsUseCase;


    @Inject
    public MyGoodsPresenter(GetMyGoodsUseCase getMyGoodsUseCase, SoldOutGoodsUseCase soldOutGoodsUseCase, DeleteMyGoodsUseCase deleteMyGoodsUseCase) {
        super(getMyGoodsUseCase);
        mSoldOutGoodsUseCase = soldOutGoodsUseCase;
        mDeleteMyGoodsUseCase = deleteMyGoodsUseCase;
    }

    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {
        GetMyGoodsRequestParam getMyGoodsRequestParam = new GetMyGoodsRequestParam();
        getMyGoodsRequestParam.setMyGoodsType(getView().getMyGoodsType());
        getMyGoodsRequestParam.setPageMachine(pageMachine);
        getMyGoodsRequestParam.setUpdateNow(pullToRefresh);
        return getMyGoodsRequestParam;
    }

    public void soldOut(final MyGoods bindGoods) {

        getView().showProgressDialog(R.string.loading);
        mSoldOutGoodsUseCase.execute(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()){
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                if (!isViewAttached()){
                    return;
                }
                getView().refreshItem(bindGoods);
                getView().hideProgressDialogIfShowing();

            }
        },bindGoods);
    }
    public void delete(final MyGoods bindGoods) {
        getView().showProgressDialog(R.string.loading);
        mDeleteMyGoodsUseCase.execute(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()){
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                if (!isViewAttached()){
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().deleteItem(bindGoods);
            }
        },bindGoods);

    }

    public void refresh(MyGoods bindGoods) {
        //TODO
    }
}
