package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hyphenate.easeui.R;
import com.yz.im.model.strategy.IMSingleEditTextStrategy;
import com.yz.im.model.strategy.IMSingleEditTextStrategyFactory;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.SingleEditTextContract;
import com.yz.im.mvp.presenter.SingleEditTextPresenter;

public class SingleEditTextActivity extends BaseSingleEditActivity implements SingleEditTextContract.SingleEditTextView {

    private static final int MAX_LENGTH = 20;

    private static String KEY_ID = "key_id";

    public static Intent getSingleEditTextIntent(Context context, String id, IMSingleEditTextStrategy strategy) {
        Intent intent = new Intent(context, SingleEditTextActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_STRATEGY, strategy);
        return intent;
    }

    private SingleEditTextPresenter mPresenter;
    private String friendId = "";
    private String groupId = "";
    private int editType;

    protected EditText mEditText;
    protected View contentView;
    protected IMSingleEditTextStrategy mStrategy;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        super.continueOnCreate(savedInstanceState);
        getDataFromIntent();
        initView();
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new SingleEditTextPresenter(this);
        return mPresenter;
    }

    @Override
    public String getOldContent() {
        if (mStrategy == null) {
            return "";
        }
        String oldText = mStrategy.getOldContent();
        return TextUtils.isEmpty(oldText) ? "" : oldText;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.text_right) {
            mPresenter.updateContent(friendId, groupId, mEditText.getText().toString().trim(), String.valueOf(editType));
        }
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mStrategy = intent.getParcelableExtra(KEY_STRATEGY);
        if (mStrategy != null) {
            setTitle(mStrategy.getTitle());
            setCommitText(getString(mStrategy.getRightText()));
            editType = mStrategy.getEditType();
            if (editType == IMSingleEditTextStrategyFactory.TYPE_MODIFY_FRIEND_REMARK_NAME) {
                friendId = intent.getStringExtra(KEY_ID);
            } else {
                groupId = intent.getStringExtra(KEY_ID);

            }
            initEditView();
        }
    }

    private void initEditView() {
        contentView = mStrategy.createView(this);
        mEditText = (EditText) contentView.findViewById(R.id.edit_content);
        mEditText.setHint(mStrategy.getHint());
        String oldContent = mStrategy.getOldContent();
        if (TextUtils.isEmpty(oldContent)) {
            oldContent = "";
        }
        mEditText.setText(oldContent);
        mEditText.setSelection(oldContent.length());
        mEditText.setMaxEms(MAX_LENGTH);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        int leftAndRightMargin = getResources().getDimensionPixelSize(R.dimen.px_28);
        int TopAndBottomMargin = getResources().getDimensionPixelSize(R.dimen.px_15);
        layoutParams.setMargins(leftAndRightMargin, TopAndBottomMargin, leftAndRightMargin, TopAndBottomMargin);
        layout.addView(contentView, layoutParams);
    }

    private void initView() {
        tvTitle.setText(mStrategy.getTitle());
    }
}
