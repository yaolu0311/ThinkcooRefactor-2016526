package com.thinkcoo.mobile.presentation.views.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/6/1.
 */
public class GlobalTransparentPopupWindow extends PopupWindow implements View.OnKeyListener{


    public GlobalTransparentPopupWindow(Context context) {
        this(context, null);
    }

    public GlobalTransparentPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        View v = createTransparentBackGroupView(context);
        setContentView(v);
        setAttributes(context);
    }

    private View createTransparentBackGroupView(Context context) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0,0);
        relativeLayout.setLayoutParams(params);
        return relativeLayout;
    }

    private void setAttributes(Context context){
        setHeight(2000);
        setWidth(2000);
        this.setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
    }

    public void showPopupWindow(View underView){
        this.showAsDropDown(underView);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}
