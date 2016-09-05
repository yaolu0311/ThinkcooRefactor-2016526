package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.easeui.R;
import com.yz.im.model.strategy.SendInviteReasonStrategy;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.BaseInviteContact;

public class SendInviteReasonActivity extends BaseSingleEditActivity implements BaseInviteContact.BaseInviteView {

    private static String KEY_STRATEGY = "key_strategy";

    public static Intent getInviteReasonIntent(Context context, SendInviteReasonStrategy strategy) {
        Intent intent = new Intent(context, SendInviteReasonActivity.class);
        intent.putExtra(KEY_STRATEGY, strategy);
        return intent;
    }

    private View contentView;
    private ImageView ivClear;
    private EditText etContent;

    private SendInviteReasonStrategy mStrategy;

    private BaseInviteContact.BaseInvitePresenter mPresenter;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        super.continueOnCreate(savedInstanceState);
        addContentView();
        initView();
    }

    @Override
    public IMMvpPresenter createPresenter() {
        getDataFromIntent();
        mPresenter = (BaseInviteContact.BaseInvitePresenter) mStrategy.getPresenter();
        return mPresenter;
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mStrategy = intent.getParcelableExtra(KEY_STRATEGY);
    }



    private void addContentView() {
        contentView = LayoutInflater.from(this).inflate(R.layout.activity_send_invite, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layout.addView(contentView, layoutParams);
    }

    private void initView() {
        tvTitle.setText(R.string.verify_msg);
        tv_commit.setText(R.string.send);
        tv_commit.setVisibility(View.VISIBLE);
        ivClear = (ImageView) contentView.findViewById(R.id.img_clear_single);
        ivClear.setVisibility(View.VISIBLE);
        etContent = (EditText) contentView.findViewById(R.id.et_invite_reason);

        ivClear.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.text_right) {
            String reason = etContent.getText().toString().trim();
            mPresenter.sendInviteReason(mStrategy.getGroupId(), mStrategy.getUserId(), reason);
        } else if (id == R.id.img_clear_single) {
            etContent.setText("");
        }
    }

}
