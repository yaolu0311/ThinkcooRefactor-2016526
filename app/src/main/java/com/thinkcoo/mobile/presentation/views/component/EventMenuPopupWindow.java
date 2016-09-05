package com.thinkcoo.mobile.presentation.views.component;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.thinkcoo.mobile.R;
import com.yz.im.IMHelper;

/**
 * ThinkCoo20160718
 * Created by cys on 2016/7/30 0030
 */
public class EventMenuPopupWindow extends PopupWindow {

    private Context mContext;

    private View rootView;
    private LinearLayout editeLayout;
    private LinearLayout deleteLayout;

    private IMHelper mHelper;

    public EventMenuPopupWindow(Context context) {
        this(context, null);
    }

    public EventMenuPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setContentView();
        initView();
        mHelper = IMHelper.getInstance();
    }

    private void setContentView() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.menu_event, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(params);
        setContentView(rootView);
        setAttribute();
    }

    private void initView() {
        editeLayout = (LinearLayout) rootView.findViewById(R.id.edite);
        deleteLayout = (LinearLayout) rootView.findViewById(R.id.delete);

    }

    private void setAttribute() {
        setWidth((int) mContext.getResources().getDimension(R.dimen.px_350));
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
    }

    public LinearLayout getEditeLayout() {
        return editeLayout;
    }

    public void setEditeLayout(LinearLayout editeLayout) {
        this.editeLayout = editeLayout;
    }

    public LinearLayout getDeleteLayout() {
        return deleteLayout;
    }

    public void setDeleteLayout(LinearLayout deleteLayout) {
        this.deleteLayout = deleteLayout;
    }
}
