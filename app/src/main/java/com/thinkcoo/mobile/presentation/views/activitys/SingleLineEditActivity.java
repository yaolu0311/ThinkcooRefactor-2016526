package com.thinkcoo.mobile.presentation.views.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.strategy.SingleLineEditContent;
import com.thinkcoo.mobile.model.strategy.SingleLineEditStrategy;
import com.thinkcoo.mobile.presentation.mvp.presenters.BlankPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/15.
 */
public class SingleLineEditActivity extends BaseActivity implements TextWatcher{

    public static Intent getSingleLineEditIntent(Context context, SingleLineEditStrategy singleLineEditStrategy) {

        Intent intent = new Intent(context,SingleLineEditActivity.class);
        intent.putExtra(KEY_STRATEGY,singleLineEditStrategy);

        return intent;
    }

    public final static String KEY_STRATEGY = "key_Strategy";
    public final static String KEY_CALL_BACK_CONTENT = "key_call_back_content";

    @Bind(R.id.iv_back)
    TextView mTvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.search_layout)
    FrameLayout mSearchLayout;
    @Bind(R.id.list_layout)
    FrameLayout mListLayout;

    private EditText mEditText;

    SingleLineEditStrategy mSingleLineEditStrategy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_list_view);
        ButterKnife.bind(this);
        getSingleLineEditStrategyFromIntent();
        setupEditText();
        setupTitleView();
    }


    private void setupTitleView() {
        mTvOther.setVisibility(View.VISIBLE);
        mTvTitle.setText(mSingleLineEditStrategy.getTitle());
        mTvOther.setText(mSingleLineEditStrategy.getOtherInfo());
    }

    private void setupEditText() {

        View editView = mSingleLineEditStrategy.createEditView(this);
        mEditText = (EditText) editView.findViewById(R.id.edit_content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        int leftAndRightMargin = getResources().getDimensionPixelSize(R.dimen.px_28);
        int TopAndBottomMargin = getResources().getDimensionPixelSize(R.dimen.px_15);
        layoutParams.setMargins(leftAndRightMargin, TopAndBottomMargin, leftAndRightMargin, TopAndBottomMargin);
        mEditText.setHint(mSingleLineEditStrategy.getHint());
        mEditText.setText(mSingleLineEditStrategy.getContent().getDisplayName());
        mEditText.setSelection(mEditText.length());
        mEditText.addTextChangedListener(this);
        mSearchLayout.addView(editView, layoutParams);

    }

    private void getSingleLineEditStrategyFromIntent() {
        Intent intent = getIntent();
        if (!intent.hasExtra(KEY_STRATEGY)){
            throw new IllegalArgumentException("You must set singleLineEditStrategy");
        }
        mSingleLineEditStrategy = intent.getParcelableExtra(KEY_STRATEGY);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return new BlankPresenter();
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().activityModule(getActivityModule()).userModule(new UserModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                resultCancel();
                break;
            case R.id.tv_other:
                resultOkSingleLineEditContent();
                break;
        }
    }

    protected void resultOkSingleLineEditContent() {

        Intent intent = new Intent();
        SingleLineEditContent singleLineEditContent = mSingleLineEditStrategy.getContent();
        singleLineEditContent.setDisplayName(mEditText.getText().toString().trim());
        intent.putExtra(KEY_CALL_BACK_CONTENT,singleLineEditContent);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    private void resultCancel() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    protected EditText getEditText() {
        return mEditText;
    }

    protected FrameLayout getListLayout() {
        return mListLayout;
    }

    public SingleLineEditStrategy getSingleLineEditStrategy() {
        return mSingleLineEditStrategy;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    @Override
    public void afterTextChanged(Editable s) {
        afterEditTextChanged(s);
    }
    protected void afterEditTextChanged(Editable s) {

    }

}
