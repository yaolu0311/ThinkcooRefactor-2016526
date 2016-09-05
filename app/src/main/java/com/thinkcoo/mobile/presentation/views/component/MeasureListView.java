package com.thinkcoo.mobile.presentation.views.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Leevin
 * CreateTime: 2016/7/21  10:18
 */
public class MeasureListView extends ListView{

    public MeasureListView(Context context) {
        super(context);
    }

    public MeasureListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
