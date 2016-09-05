package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.presentation.mvp.views.BaseLceView;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import java.util.List;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/7/25.
 */
public abstract class BaseLcePagedPresenter<D,V extends BaseLceView<D>> extends BaseLcePresenter<D,V>{

    PageMachine mPageMachine;

    public BaseLcePagedPresenter(UseCase useCase) {
        super(useCase);
        mPageMachine = new PageMachine();
    }
    public void loadFirstPageData(boolean pullToRefresh){
        mPageMachine.first();
        mUseCase.execute(getLoadSub(pullToRefresh),buildLcePagedParams(pullToRefresh,mPageMachine));
    }

    private Subscriber<List<D>> getLoadSub(final boolean pullToRefresh) {

        return new Subscriber<List<D>>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()){
                    return;
                }
                getView().showError(e, pullToRefresh);
            }
            @Override
            public void onNext(List<D> ds) {
                if (!isViewAttached()){
                    return;
                }
                getView().setDataList(ds);
            }
        };
    }
    public void loadMore(boolean refresh){
        mPageMachine.next();
        mUseCase.execute(getLoadSub(refresh),buildLcePagedParams(refresh,mPageMachine));
    }
    public boolean hasLoadedAllItems(){
        return mPageMachine.hasLoadedAllItems();
    }
    public int getPageContent(){
        return mPageMachine.getPageContentCount();
    }
    protected abstract RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine);
    @Override
    protected RequestParam buildLceLoadDataParams(boolean pullToRefresh) {
        return null;
    }
}
