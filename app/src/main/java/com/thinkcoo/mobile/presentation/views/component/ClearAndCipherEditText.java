package com.thinkcoo.mobile.presentation.views.component;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.thinkcoo.mobile.R;

/**
 * Created by Administrator on 2016/5/25.
 */
public class ClearAndCipherEditText extends RelativeLayout implements View.OnClickListener {

    private EditText etPassword;
    private ImageView ivChangeVisible;

    public ClearAndCipherEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.clear_and_cipher_edittext, this,true);
        init();
        hidePassword();
    }

    private void init() {
        etPassword = (EditText) findViewById(R.id.et_password);
        ivChangeVisible = (ImageView) findViewById(R.id.iv_change_password_visible);
        ivChangeVisible.setOnClickListener(this);

    }

    public ClearAndCipherEditText(Context context) {
        super(context);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_change_password_visible:
                changePasswordVisible();
                break;
        }
    }

    private void changePasswordVisible() {
        if (etPassword.getTransformationMethod() == PasswordTransformationMethod
                .getInstance()) {
            showPassword();
        } else {
            hidePassword();
        }
    }

    private void hidePassword() {
        ivChangeVisible.setImageDrawable(getResources().getDrawable(
                R.mipmap.ic_password_normal));
        etPassword.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
    }

    private void showPassword() {
        ivChangeVisible.setImageDrawable(getResources().getDrawable(
                R.mipmap.ic_password_checked));
        etPassword.setTransformationMethod(HideReturnsTransformationMethod
                .getInstance());
    }

    public String getPassWord() {
        return etPassword.getText().toString().trim();
    }

    public void setHint(int resId) {
        etPassword.setHint(getContext().getString(resId));
    }
}
