package com.thinkcoo.mobile.presentation.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.thinkcoo.mobile.R;

/**
 * Created by ml on 2016/7/7
 *              参与者签到成功和失败的dagio
 */
public class SignDialog {

    private Context mContext;
    private GlobalToast mGlobalToast;

    public SignDialog(Context context,GlobalToast globalToast){
        this.mContext=context;
        this.mGlobalToast=globalToast;
    }
    public void show() {
        builder().show();
    }

    private Dialog builder(){
        final Dialog dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        View view =  LayoutInflater.from(mContext).inflate(R.layout.item_rockcallsign, null);
        dialog.setContentView(view);
        TextView signyes = (TextView) view.findViewById(R.id.sign_yes);
        TextView signno = (TextView) view.findViewById(R.id.sign_no);
        signyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGlobalToast.showLongToast(R.string.shakesuccess);
            }
        });

        signno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGlobalToast.showLongToast(R.string.Shakeagain);
                dialog.dismiss();
            }
        });

        return dialog;
    }
}
