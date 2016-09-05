package com.thinkcoo.mobile.presentation.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkcoo.mobile.R;

/**
 * author ：ml on 2016/7/26
 */
public class ApplyProjectDialog {
    private Context mContext;
    private final Display display;
    private Dialog dialog;
    private View view;
    private TextView tv_cancel;
    private TextView tv_sure;
    private TextView tv_protocol;
    private RelativeLayout checkLayout;

    public ApplyProjectDialog(Context context) {
        this.mContext = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();

    }

    public void show() {
        builder().show();
    }

    private Dialog builder() {

        // 获取Dialog布局
        view = LayoutInflater.from(mContext).inflate(
                R.layout.apply_project_dialog, null);
        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth((int) (display.getWidth() * 0.95));
        // 定义Dialog布局和参数
        dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        initDialogContent();
        initDialogCheckedEvent();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 20;
        dialogWindow.setAttributes(lp);
        return dialog;
    }


    private void initDialogContent() {
        tv_cancel = (TextView) view.findViewById(R.id.setresource_cancel);
        tv_sure = (TextView) view.findViewById(R.id.setresource_sure);
        tv_protocol = (TextView) view.findViewById(R.id.item_protocol);
        checkLayout = (RelativeLayout) view.findViewById(R.id.setForResource);
    }

    private void initDialogCheckedEvent() {
        tv_protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/7/26  数据    跳转我的简历的界面
                dialog.hide();
            }
        });

    }

    public ApplyProjectDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ApplyProjectDialog setPositiveButton(String text,
                                                final View.OnClickListener listener) {
        if (!TextUtils.isEmpty(text)) {
            tv_sure.setText(text);
        }
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
        return this;
    }

    public ApplyProjectDialog setNegativeButton(String text,
                                                final View.OnClickListener listener) {
        if (!TextUtils.isEmpty(text)) {
            tv_cancel.setText(text);
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
        return this;
    }

    public void hide() {
        dialog.dismiss();
    }


}
