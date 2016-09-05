package com.yz.im.model.strategy;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by cys on 2016/7/25
 */
public interface IMSingleEditTextStrategy extends Parcelable{

    int getTitle();
    int getRightText();
    int getHint();
    int getEditType();

    View createView(Context context);
    String getOldContent();

}
