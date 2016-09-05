package com.thinkcoo.mobile.presentation.views.component;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by cys on 2016/7/5
 * in com.thinkcoo.mobile.presentation.views.component
 */
public class BaseDialog extends Dialog {

    private boolean isForbidBackPress = false;

    private BaseDialog(Context context) {
        this(context, 0);
    }

    private BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onBackPressed() {
        if (isForbidBackPress) {
            return;
        }
        super.onBackPressed();
    }

    private void setForbidBackPress(boolean reactBackPress) {
        isForbidBackPress = reactBackPress;
    }

    public static class Builder {

        private Context mContext;
        private View root;
        private RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        private int themeStyle = 0;
        private boolean cancelOnOutside = false;
        private OnDismissListener mListener;
        private boolean isForbidBackPress = false;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setRootView(View v) {
            this.root = v;
            return this;
        }

        public Builder setLayoutParams(RelativeLayout.LayoutParams params) {
            this.mParams = params;
            return this;
        }

        public Builder setThemeStyle(int style) {
            this.themeStyle = style;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean cancleOnOutside) {
            this.cancelOnOutside = cancleOnOutside;
            return this;
        }

        public Builder setForbidBackPress(boolean backPress) {
            this.isForbidBackPress = backPress;
            return this;
        }

        public Builder setOnCancelListener(OnDismissListener listener) {
            this.mListener = mListener;
            return this;
        }

        public BaseDialog creat(){
            BaseDialog dialog= new BaseDialog(mContext, themeStyle);
            if (root == null) {
                throw  new IllegalArgumentException("BaseDialog contentView can't be null");
            }
            dialog.setContentView(root, mParams);
            dialog.setCanceledOnTouchOutside(cancelOnOutside);
            if (mListener != null) {
                dialog.setOnDismissListener(mListener);
            }
            dialog.setForbidBackPress(isForbidBackPress);
            return dialog;
        }

    }

}
