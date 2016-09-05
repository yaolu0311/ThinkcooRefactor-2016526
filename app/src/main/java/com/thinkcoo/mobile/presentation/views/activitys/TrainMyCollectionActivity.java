package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import com.thinkcoo.mobile.presentation.mvp.presenters.TrainMyCollectionPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;
import javax.inject.Inject;

public class TrainMyCollectionActivity extends BaseSimpleActivity{


    public static Intent getJumpIntent(Context context) {
        return new Intent(context, TrainMyCollectionActivity.class);
    }

    @Inject
    TrainMyCollectionPresenter mTrainMyCollectionPresenter;



}
