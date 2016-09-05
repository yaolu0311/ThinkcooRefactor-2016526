package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.presentation.mvp.views.BaseLceView;
import java.util.List;
import rx.Subscriber;

/**
 * Created by Robert.yao on 2016/7/25.
 */
public abstract class BaseLcePresenter<D,V extends BaseLceView<D>> extends MvpBasePresenter<V> {

    private static final String TAG = "BaseLcePresenter";

    UseCase mUseCase;

    public BaseLcePresenter(UseCase useCase) {
        mUseCase = useCase;
    }

    public void startLoadData(boolean pullToRefresh) {
        mUseCase.execute(getLoadSub(pullToRefresh),buildLceLoadDataParams(pullToRefresh));
    }

    private Subscriber<List<D>> getLoadSub(final boolean pullToRefresh) {

       return new Subscriber<List<D>>() {
           @Override
           public void onCompleted() {
           }
           @Override
           public void onError(Throwable e) {
               ThinkcooLog.e(TAG , e.getMessage(),e);
               getView().showError(e,pullToRefresh);
           }

           @Override
           public void onNext(List<D> ds) {
               ThinkcooLog.e(TAG , "=== 获得 : " + ds.size() + " 笔数据 ===");
               getView().setDataList(ds);
           }
       };
    }

    protected abstract RequestParam buildLceLoadDataParams(boolean pullToRefresh);
}
