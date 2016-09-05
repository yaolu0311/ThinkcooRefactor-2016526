package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.ChangeNamePresenter;
import com.thinkcoo.mobile.presentation.mvp.views.ChangeNameView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeNameActivity extends BaseActivity implements ChangeNameView {
    @Inject
    ChangeNamePresenter mChangeNamePresenter;
    @Bind(R.id.iv_back)
    TextView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_other)
    TextView tvOther;
    @Bind(R.id.edit_content)
    EditText editContent;
    @Bind(R.id.image_del_edit)
    ImageView imageDelEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        ButterKnife.bind(this);
        setupViews();
    }

    protected void setupViews() {
        tvOther.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.xingming);
        editContent.setText(getIntent().getStringExtra(UserMainInfoActivity.EXTRA_USER_NAME_KEY));
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mChangeNamePresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).userModule(new UserModule()).build().inject(this);
    }


    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void closeSelf() {
        finish();
    }

    @Override
    public void showProgressDialog(int stringResId) {
        mBaseActivityDelegate.showProgressDialog(stringResId);
    }

    @Override
    public void hideProgressDialogIfShowing() {
        mBaseActivityDelegate.hideProgressDialogIfShowing();
    }

    @Override
    public void showToast(String stringResMsg) {
        mGlobalToast.showShortToast(stringResMsg);
    }

    @Override
    public String getName() {
        return editContent.getText().toString().trim();
    }

    @Override
    public void resultToUserMainInfoActivity() {
        Intent intent = new Intent();
        intent.putExtra(UserMainInfoActivity.EXTRA_USER_NAME_KEY, editContent.getText().toString().trim());
        setResult(RESULT_OK, intent);
        finish();

    }

    public static Intent getChangeNameIntent(Context context, String name) {
        Intent intent = new Intent(context, ChangeNameActivity.class);
        intent.putExtra(UserMainInfoActivity.EXTRA_USER_NAME_KEY, name);
        return intent;
    }

    @OnClick({R.id.iv_back, R.id.tv_other, R.id.image_del_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                closeSelf();
                break;
            case R.id.tv_other:
                mChangeNamePresenter.changeName();
                break;
            case R.id.image_del_edit:
                editContent.setText("");
                break;
        }
    }
}
