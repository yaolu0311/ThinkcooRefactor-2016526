package com.thinkcoo.mobile.presentation.views.component.viewBean;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.entity.UserStatusDetail;
import com.thinkcoo.mobile.model.strategy.SingleLineEditContent;
import com.thinkcoo.mobile.model.strategy.StringSingleLineEditContent;
import com.thinkcoo.mobile.presentation.views.activitys.UserStatusDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.SingleLineEditActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseDetailActivity;
import com.thinkcoo.mobile.presentation.views.component.TimePickDialog;
import com.thinkcoo.mobile.utils.DateUtils;

import java.util.Date;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/2.
 */
public abstract class UserStatusBaseContentView {

    public static final String EXTRA_CHANGE_ITEM_DATA = "extra_item_data";

    public Context mContext;
    protected View mRootView;
    protected int mActivityType;
    protected int mActivityMode;

    protected TimePickDialog mTimePickDialog;

    public UserStatusBaseContentView(Context mContext, TimePickDialog timePickDialog) {
        this.mContext = mContext;
        initRootView();
        ButterKnife.bind(this, mRootView);
        mActivityMode = ((UserStatusDetailActivity) mContext).getMode();
        if (BaseDetailActivity.VIEW_MODE == mActivityMode) {
            changeViewByMode();
        }
        this.mTimePickDialog = timePickDialog;
    }

    public View getRootView() {
        return mRootView;
    }

    protected SingleLineEditContent getItemContentFromIntent(Intent intent) {
        if (null == intent || !intent.hasExtra(SingleLineEditActivity.KEY_CALL_BACK_CONTENT)) {
            return newEmptySingleLineEditContent();
        }
        SingleLineEditContent singleLineEditContent = intent.getParcelableExtra(SingleLineEditActivity.KEY_CALL_BACK_CONTENT);
        return singleLineEditContent;
    }

    private SingleLineEditContent newEmptySingleLineEditContent() {
        return new StringSingleLineEditContent("");
    }

    protected abstract void initRootView();

    protected abstract void changeViewByMode();

    public abstract UserStatus getUserStatus();

    public abstract void showBasicInfo(UserStatus userStatus);

    public abstract void showDetailsInfo(UserStatusDetail userStatusDetail);

    public abstract void handActivityResult(Intent intent, int requestCode, int resultCode);

    protected void showSelectDateDialog(Context context, final TextView textView) {
        mTimePickDialog.setDefaultDate(DateUtils.stringToDate(textView.getText().toString().trim(), DateUtils.YEAR_MONTH_DAY));
        mTimePickDialog.show();
        mTimePickDialog.setOnTimeSelectedListener(new TimePickDialog.OnTimeSelectedListener(){

            @Override
            public void onTimeSelected(Date date) {
                textView.setText(DateUtils.dateToString(date, DateUtils.YEAR_MONTH_DAY));
            }
        });
    }

    protected void setEditTextSelection(EditText editText){
        if (null == editText) {
            return;
        }
        editText.setSelection(editText.getText().toString().length());
    }

}
