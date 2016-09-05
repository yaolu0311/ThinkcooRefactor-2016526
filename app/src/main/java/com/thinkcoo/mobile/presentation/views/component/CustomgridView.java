package com.thinkcoo.mobile.presentation.views.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自定义倒计时button
 */
public class CustomgridView extends GridView {

    public CustomgridView(Context context) {
        super(context);
    }

    public CustomgridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}












