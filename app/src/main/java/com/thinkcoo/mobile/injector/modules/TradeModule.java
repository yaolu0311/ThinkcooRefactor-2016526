package com.thinkcoo.mobile.injector.modules;

import com.thinkcoo.mobile.domain.location.GetLocationUseCase;
import com.thinkcoo.mobile.domain.trade.CollectGoodsUseCase;
import com.thinkcoo.mobile.domain.trade.CreateMyGoodsUseCase;
import com.thinkcoo.mobile.domain.trade.DeleteMyGoodsPhotoUseCase;
import com.thinkcoo.mobile.domain.trade.DeleteMyGoodsUseCase;
import com.thinkcoo.mobile.domain.trade.EditMyGoodsUseCase;
import com.thinkcoo.mobile.domain.trade.GetMyGoodsUseCase;
import com.thinkcoo.mobile.domain.trade.GoodsSearchUseCase;
import com.thinkcoo.mobile.domain.trade.LoadBuyGoodsDetailUseCase;
import com.thinkcoo.mobile.domain.trade.LoadBuyGoodsRecommendUseCase;
import com.thinkcoo.mobile.domain.trade.LoadMyCollectGoodsUseCase;
import com.thinkcoo.mobile.domain.trade.LoadMyGoodsDetailUseCase;
import com.thinkcoo.mobile.domain.trade.LoadSellGoodsDetailUseCase;
import com.thinkcoo.mobile.domain.trade.LoadSellGoodsRecommendUseCase;
import com.thinkcoo.mobile.domain.trade.SoldOutGoodsUseCase;
import com.thinkcoo.mobile.domain.trade.UnCollectGoodsUseCase;
import com.thinkcoo.mobile.domain.train.RefreshGoodsUseCase;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.repository.LocationRepository;
import com.thinkcoo.mobile.model.repository.TradeRepository;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/7/18.
 */
@Module
public class TradeModule {

    @Provides
    @ActivityScope
    PageMachine providePageMachine() {
        return new PageMachine();
    }
    @Provides
    @ActivityScope
    LoadSellGoodsRecommendUseCase provideLoadSellGoodsRecommendUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler workThread, TradeRepository tradeRepository) {
        return new LoadSellGoodsRecommendUseCase(uiThread, workThread, tradeRepository);
    }
    @Provides
    @ActivityScope
    LoadBuyGoodsRecommendUseCase provideLoadBuyGoodsRecommendUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler workThread, TradeRepository tradeRepository) {
        return new LoadBuyGoodsRecommendUseCase(uiThread, workThread, tradeRepository);
    }
    @Provides
    @ActivityScope
    GetLocationUseCase provideGetLocationUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler uiThread, @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler workThread, LocationRepository locationRepository){
        return new GetLocationUseCase(uiThread,workThread,locationRepository);
    }
    @Provides
    @ActivityScope
    GoodsSearchUseCase provideGoodsSearchUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui ,@Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository ){
        return new GoodsSearchUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    GetMyGoodsUseCase provideGetMyGoodsUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui ,@Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work ,TradeRepository tradeRepository){
        return new GetMyGoodsUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    SoldOutGoodsUseCase provideSoldOutGoodsUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new SoldOutGoodsUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    DeleteMyGoodsUseCase provideDeleteMyGoodsUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new DeleteMyGoodsUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    EditMyGoodsUseCase provideRefreshMyGoodsUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new EditMyGoodsUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    RefreshGoodsUseCase provideRefreshGoodsUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new RefreshGoodsUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    CreateMyGoodsUseCase provideCreateMyGoodsUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new CreateMyGoodsUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    LoadMyGoodsDetailUseCase provideLoadMyGoodsDetailUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new LoadMyGoodsDetailUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    DeleteMyGoodsPhotoUseCase provideDeleteMyGoodsPhotoUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new DeleteMyGoodsPhotoUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    LoadMyCollectGoodsUseCase provideLoadMyCollectGoodsUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new LoadMyCollectGoodsUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    LoadBuyGoodsDetailUseCase provideLoadBuyGoodsDetailUsecase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new LoadBuyGoodsDetailUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    LoadSellGoodsDetailUseCase provideLoadSellGoodsDetailUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new LoadSellGoodsDetailUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    CollectGoodsUseCase provideCollectGoodsUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new CollectGoodsUseCase(ui,work,tradeRepository);
    }
    @Provides
    @ActivityScope
    UnCollectGoodsUseCase provideUnCollectGoodsUseCase(@Named(ApplicationModule.UI_THREAD_NAMED) Scheduler ui , @Named(ApplicationModule.EXECUTOR_THREAD_NAMED) Scheduler work , TradeRepository tradeRepository){
        return new UnCollectGoodsUseCase(ui,work,tradeRepository);
    }

}
