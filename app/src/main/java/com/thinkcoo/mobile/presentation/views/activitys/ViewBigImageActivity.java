package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.thinkcoo.mobile.model.entity.ThinkCooPhoto;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;

/**
 * Created by Robert.yao on 2016/8/10.
 */
public class ViewBigImageActivity extends BaseSimpleActivity{

    public static final String EXTRA_KEY_TCPS = "ThinkCooPhotos";

    ThinkCooPhoto [] mThinkCooPhotos;

    public static Intent getJumpIntent(Context context, ThinkCooPhoto... item) {
        if (null == item){
            throw new IllegalArgumentException("photoArray is null");
        }
        Intent intent = new Intent(context,ViewBigImageActivity.class);
        intent.putExtra(EXTRA_KEY_TCPS,item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initThinkCooPhotosFromIntentExtra();
    }

    private void initThinkCooPhotosFromIntentExtra() {
        mThinkCooPhotos = (ThinkCooPhoto[]) getIntent().getParcelableArrayExtra(EXTRA_KEY_TCPS);
    }

}
