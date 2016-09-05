package com.thinkcoo.mobile.presentation.mvp.presenters;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.domain.trade.CollectGoodsUseCase;
import com.thinkcoo.mobile.domain.trade.UnCollectGoodsUseCase;
import com.thinkcoo.mobile.presentation.mvp.views.GoodsDetailView;
import javax.inject.Inject;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public abstract class GoodsDetailPresenter<T> extends MvpBasePresenter<GoodsDetailView<T>> {

    UseCase mLoadUseCase;
    //@Inject
    CollectGoodsUseCase mCollectGoodsUseCase;
    //@Inject
    UnCollectGoodsUseCase mUnCollectGoodsUseCase;

    public GoodsDetailPresenter(UseCase loadUseCase) {
        mLoadUseCase = loadUseCase;
    }

    public void loadDetail(String goodsId){
        getView().showLoading(false);
        mLoadUseCase.execute(getLoadDetailSub(),goodsId);
    }

    public void collect(String typeId, String goodsId) {
        getView().showProgressDialog(R.string.Is_sending_a_request);
        mCollectGoodsUseCase.execute(getCollectSub(),goodsId,typeId);
    }

    public void unCollect(String collectId){
        getView().showProgressDialog(R.string.Is_sending_a_request);
        mUnCollectGoodsUseCase.execute(getUnCollectSub(),collectId);
    }

    private Subscriber getUnCollectSub() {
        return new Subscriber() {
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
                mLoadUseCase.execute(getBackGroundLoadSub(),getView().getGoodsId());
                getView().showToast("已取消收藏");
            }
        };
    }

    private Subscriber<T> getLoadDetailSub() {

        return new Subscriber<T>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()){
                    return;
                }
                getView().showError(e,false);
            }

            @Override
            public void onNext(T t) {
                if (!isViewAttached()){
                    return;
                }
                getView().setData(t);
                getView().showContent();
            }
        };
    }

    private Subscriber getCollectSub() {

        return new Subscriber() {
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
                mLoadUseCase.execute(getBackGroundLoadSub(),getView().getGoodsId());
                getView().showToast("已添加至我的收藏");
            }
        };
    }

    private Subscriber<T> getBackGroundLoadSub() {

        return new Subscriber<T>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()){
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().closeSelf();
            }

            @Override
            public void onNext(T t) {
                if (!isViewAttached()){
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().setData(t);
            }
        };
    }
}
