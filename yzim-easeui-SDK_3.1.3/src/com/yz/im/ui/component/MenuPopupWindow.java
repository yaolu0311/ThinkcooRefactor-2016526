package com.yz.im.ui.component;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.hyphenate.easeui.R;
import com.yz.im.IMHelper;

/**
 * ThinkCoo20160718
 * Created by cys on 2016/7/30 0030
 */
public class MenuPopupWindow extends PopupWindow implements View.OnClickListener {

    private Context mContext;

    private View rootView;
    private LinearLayout findUserOrGroup;
    private LinearLayout createGroup;
    private LinearLayout saveCollection;
    private LinearLayout setting;

    private IMHelper mHelper;

    public MenuPopupWindow(Context context) {
        this(context, null);
    }

    public MenuPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setContentView();
        initView();
        mHelper = IMHelper.getInstance();
    }

    private void setContentView() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.menu_more, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(params);
        setContentView(rootView);
        setAttribute();
    }

    private void initView() {
        findUserOrGroup = (LinearLayout) rootView.findViewById(R.id.item_find_user_of_group);
        createGroup = (LinearLayout) rootView.findViewById(R.id.item_create_group);
        saveCollection = (LinearLayout) rootView.findViewById(R.id.item_menu_collection);
        setting = (LinearLayout) rootView.findViewById(R.id.item_menu_setting);

        findUserOrGroup.setOnClickListener(this);
        createGroup.setOnClickListener(this);
        saveCollection.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    private void setAttribute() {
        setWidth((int) mContext.getResources().getDimension(R.dimen.px_350));
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.item_find_user_of_group) {
            mHelper.getNavigator().navigateToFindUserOrGroupActivity(mContext);
        } else if (id == R.id.item_create_group) {
            mHelper.getNavigator().navigateToCreateGroupActivity(mContext);
        } else if (id == R.id.item_menu_collection) {
            mHelper.getNavigator().navigateToCollectionListActivity(mContext);
        } else if (id == R.id.item_menu_setting) {
            mHelper.getNavigator().navigateToSettingActivity(mContext);
        }
        this.dismiss();
    }
}
