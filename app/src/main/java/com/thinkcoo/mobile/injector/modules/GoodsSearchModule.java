package com.thinkcoo.mobile.injector.modules;

import android.app.Activity;

import com.thinkcoo.mobile.domain.trade.ClearGoodsSearchHistoryUseCase;
import com.thinkcoo.mobile.domain.trade.GetGoodsSearchHistoryByTypeUseCase;
import com.thinkcoo.mobile.domain.trade.SaveGoodsSearchHistoryUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.TradeRepository;
import com.thinkcoo.mobile.presentation.mvp.presenters.GoodsFilterPresenter;
import com.thinkcoo.mobile.presentation.views.component.GoodsFilterView;
import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/3.
 */
@Module
public class GoodsSearchModule {
    private Activity mActivity;
    public GoodsSearchModule(Activity activity) {
        mActivity = activity;
    }
    @Provides
    @ActivityScope
    GetGoodsSearchHistoryByTypeUseCase provideGetGoodsSearchHistoryByTypeUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui,@Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new GetGoodsSearchHistoryByTypeUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    GoodsFilterView provideGoodsFilterView(GoodsFilterPresenter goodsFilterPresenter){
        return new GoodsFilterView(mActivity,goodsFilterPresenter);
    }
    @Provides
    @ActivityScope
    ClearGoodsSearchHistoryUseCase provideClearGoodsSearchHistoryUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository ){
        return new ClearGoodsSearchHistoryUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    SaveGoodsSearchHistoryUseCase provideSaveGoodsSearchHistoryUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository ){
        return new SaveGoodsSearchHistoryUseCase(ui,work,tradeRepository);
    }
}
