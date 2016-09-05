/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.thinkcoo.mobile.injector.modules;
import android.app.Activity;
import com.thinkcoo.mobile.injector.ActivityScope;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategyFactory;
import com.thinkcoo.mobile.presentation.mvp.presenters.DataDictionaryDialogPresenter;
import com.thinkcoo.mobile.presentation.views.component.DataDictionaryDialog;

import javax.inject.Named;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides @ActivityScope Activity activity() {
        return this.activity;
    }

    @Provides
    @ActivityScope
    DataDictionaryStrategyFactory provideDataDictionaryStrategyFactory(){
        return new DataDictionaryStrategyFactory();
    }

    /** 滚轮dialog **/
    @Provides
    @ActivityScope
    @Named("Politics")
    DataDictionaryDialog providePoliticsDDDialog(Activity activity, DataDictionaryDialogPresenter dataDictionaryDialogPresenter,DataDictionaryStrategyFactory dataDictionaryStrategyFactory){
        return new DataDictionaryDialog(activity, dataDictionaryStrategyFactory.create(DataDictionaryStrategyFactory.DD_TYPE_POLITICS),dataDictionaryDialogPresenter);
    }

    @Provides
    @ActivityScope
    @Named("Nation")
    DataDictionaryDialog provideNationDDDialog(Activity activity, DataDictionaryDialogPresenter dataDictionaryDialogPresenter,DataDictionaryStrategyFactory dataDictionaryStrategyFactory){
        return new DataDictionaryDialog(activity, dataDictionaryStrategyFactory.create(DataDictionaryStrategyFactory.DD_TYPE_NATION),dataDictionaryDialogPresenter);
    }

    @Provides
    @ActivityScope
    @Named("Education")
    DataDictionaryDialog provideEducationDDDialog(Activity activity, DataDictionaryDialogPresenter dataDictionaryDialogPresenter,DataDictionaryStrategyFactory dataDictionaryStrategyFactory){
        return new DataDictionaryDialog(activity, dataDictionaryStrategyFactory.create(DataDictionaryStrategyFactory.DD_TYPE_EDUCATION),dataDictionaryDialogPresenter);
    }

    @Provides
    @ActivityScope
    @Named("Certificate")
    DataDictionaryDialog provideCertificateDDDialog(Activity activity, DataDictionaryDialogPresenter dataDictionaryDialogPresenter,DataDictionaryStrategyFactory dataDictionaryStrategyFactory){
        return new DataDictionaryDialog(activity,dataDictionaryStrategyFactory.create(DataDictionaryStrategyFactory.DD_TYPE_CERTIFICATE),dataDictionaryDialogPresenter);
    }

    @Provides
    @ActivityScope
    @Named("GoodsCategory")
    DataDictionaryDialog provideGoodsCategoryDDDialog(Activity activity, DataDictionaryDialogPresenter dataDictionaryDialogPresenter,DataDictionaryStrategyFactory dataDictionaryStrategyFactory){
        return new DataDictionaryDialog(activity,dataDictionaryStrategyFactory.create(DataDictionaryStrategyFactory.DD_TYPE_GOODS_CATEGORY),dataDictionaryDialogPresenter);
    }

    @Provides
    @ActivityScope
    @Named("quality")
    DataDictionaryDialog provideQualityDDDialog(Activity activity, DataDictionaryDialogPresenter dataDictionaryDialogPresenter,DataDictionaryStrategyFactory dataDictionaryStrategyFactory){
        return new DataDictionaryDialog(activity,dataDictionaryStrategyFactory.create(DataDictionaryStrategyFactory.DD_TYPE_QUALITY),dataDictionaryDialogPresenter);
    }

}
