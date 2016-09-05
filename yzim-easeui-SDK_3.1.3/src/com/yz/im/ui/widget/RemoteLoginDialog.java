package com.yz.im.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.publicmodule.ui.widget.BaseDialog;
import com.hyphenate.easeui.R;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import com.yz.im.services.BackgroundInitService;
import com.yz.im.ui.base.IMNavigator;

/**
 * Created by cys on 2016/7/5
 */
public class RemoteLoginDialog extends RelativeLayout implements View.OnClickListener {

    TextView mTvMainInfo;
    TextView mTvDetailInfo;
    Button mBtnCancel;
    Button mBtnSure;

    private IMNavigator mNavigator;
    private BaseDialog mDialog;


    public RemoteLoginDialog(Context context) {
        this(context, null);
        mDialog = new BaseDialog.Builder(context).setRootView(this).setCanceledOnTouchOutside(false).setForbidBackPress(false).setThemeStyle(R.style.AlertDialogStyle).create();
    }

    private RemoteLoginDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_remote_login, null);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        v.setLayoutParams(new LayoutParams((int) (display.getWidth() * 0.9), LayoutParams.WRAP_CONTENT));
        initView(v);
        addView(v, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    private void initView(View view) {
        mTvMainInfo = (TextView) view.findViewById(R.id.tv_main_info);
        mTvDetailInfo = (TextView) view.findViewById(R.id.tv_detail_info);
        mBtnCancel = (Button) view.findViewById(R.id.btn_cancel);
        mBtnSure = (Button) view.findViewById(R.id.btn_sure);

        mTvMainInfo.setText(R.string.warn_notice);
        mTvDetailInfo.setText(R.string.remote_login);
        mBtnCancel.setText(R.string.cancel);
        mBtnSure.setText(R.string.login_again);

        mBtnCancel.setOnClickListener(this);
        mBtnSure.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        mNavigator = IMHelper.getInstance().getNavigator();
        if (id == R.id.btn_cancel) {
            Intent intent = new Intent(Constant.ACTION_FINISH_ALL_ACTIVITY);
            getContext().sendBroadcast(intent);
            mNavigator.sendIntentService(getContext(), Constant.ACTION_LOGIN);
            dismiss();
        } else if (id == R.id.btn_sure) {
            BackgroundInitService.startInit(getContext(), IMHelper.getInstance().getLoginUserId());
            dismiss();
        }
    }

    public void dismiss(){
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    public void show(){
        if (mDialog != null) {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

}
