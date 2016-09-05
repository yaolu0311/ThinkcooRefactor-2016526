package com.thinkcoo.mobile.injector.components;

import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.injector.modules.GoodsSearchModule;
import com.thinkcoo.mobile.presentation.views.activitys.GoodsSearchActivity;
import com.thinkcoo.mobile.presentation.views.activitys.GoodsSearchAndFilterActivity;
import com.thinkcoo.mobile.presentation.views.component.GoodsFilterView;
import dagger.Component;

/**
 * Created by Robert.yao on 2016/8/3.
 */
@Component(dependencies = ApplicationComponent.class , modules = {GoodsSearchModule.class})
@ActivityScope
public interface GoodsSearchComponent {
    void inject(GoodsSearchActivity goodsSearchActivity);
    void inject(GoodsSearchAndFilterActivity goodsSearchAndFilterActivity);
}
