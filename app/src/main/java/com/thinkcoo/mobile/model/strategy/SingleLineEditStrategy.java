package com.thinkcoo.mobile.model.strategy;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by Administrator on 2016/6/15.
 */
public interface SingleLineEditStrategy extends Parcelable{

    int getTitle();
    int getOtherInfo();
    int getHint();

    SingleLineEditContent getContent();
    View createEditView(Context context);

}
