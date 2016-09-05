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
import com.thinkcoo.mobile.presentation.mvp.presenters.FeedBackPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.FeedBackView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity implements FeedBackView {


    @Bind(R.id.iv_back)
    TextView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_other)
    TextView tvOther;
    @Bind(R.id.edit_opinion_feedback)
    EditText editOpinionFeedback;
    @Bind(R.id.edit_opinion_callNumoremail)
    EditText editOpinionCallNumoremail;
    @Inject
    FeedBackPresenter mFeedBackPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_opinion_feedback);
        ButterKnife.bind(this);
        setupViews();
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mFeedBackPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).userModule(new UserModule()).build().inject(this);
    }


    protected void setupViews() {
        tvOther.setVisibility(View.VISIBLE);
//        tvTitle.setText(R.string.commit);
        tvOther.setText(R.string.commit);

    }


    public static Intent getFeedBackIntent(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        return intent;
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
    public String getSuggestion() {
        return editOpinionFeedback.getText().toString();
    }

    @Override
    public String getContact() {
        return editOpinionCallNumoremail.getText().toString();
    }

    @OnClick({R.id.iv_back, R.id.tv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_other:
                mFeedBackPresenter.feedBack(getSuggestion(),getContact());
                break;
        }
    }

}
