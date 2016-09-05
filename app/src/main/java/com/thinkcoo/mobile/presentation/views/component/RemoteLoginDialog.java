package com.thinkcoo.mobile.presentation.views.component;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivityDelegateImpl;
import com.thinkcoo.mobile.presentation.views.activitys.base.Navigator;
import com.yz.im.services.BackgroundInitService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cys on 2016/7/5
 * in com.thinkcoo.mobile.presentation.views.component
 */
public class RemoteLoginDialog extends RelativeLayout {

    @Bind(R.id.tv_main_info)
    TextView mTvMainInfo;
    @Bind(R.id.tv_detail_info)
    TextView mTvDetailInfo;
    @Bind(R.id.btn_cancel)
    Button mBtnCancel;
    @Bind(R.id.btn_sure)
    Button mBtnSure;
    @Bind(R.id.lLayout_bg)
    LinearLayout mLLayoutBg;

    private Navigator mNavigator;
    private BaseDialog mDialog;

    public RemoteLoginDialog(Context context) {
        this(context, null);
    }

    public RemoteLoginDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_remote_login, null);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        v.setLayoutParams(new LayoutParams((int) (display.getWidth() * 0.9), LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this, v);
        initView();
        addView(v, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    private void initView() {
        mTvMainInfo.setText(R.string.warn_notice);
        mTvDetailInfo.setText(R.string.remote_login);
        mBtnCancel.setText(R.string.cancel);
        mBtnSure.setText(R.string.login_again);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                Intent intent = new Intent(BaseActivityDelegateImpl.ACTION_FINISH_ALL_ACTIVITY);
                getContext().sendBroadcast(intent);
                mNavigator.navigateToLogin(getContext());
                break;
            case R.id.btn_sure:
//                String userId = String.valueOf(mEasemobConstantsUtils.getEasemobUserName(Long.parseLong(mUser.getUserId())));
                BackgroundInitService.startInit(getContext(), "110005");  //// TODO: 2016/7/14
                break;
        }
    }

    public void setNavigator(Navigator navigator) {
        mNavigator = navigator;
    }

    public void setDialog(BaseDialog dialog) {
        mDialog = dialog;
    }
}
