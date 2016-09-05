package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.FindUserExactContact;
import com.yz.im.mvp.presenter.FindExactUserPresenter;

import java.util.List;

public class FindUserExactActivity extends BaseSingleEditActivity implements FindUserExactContact.FindUserExactView {

    public static Intent getFindUserExactIntent(Context context) {
        Intent intent = new Intent(context, FindUserExactActivity.class);
        return intent;
    }

    private View rootView;
    private TextView tvTag;
    private EditText etContent;

    private FindExactUserPresenter mPresenter;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        super.continueOnCreate(savedInstanceState);
        addView();
    }

    private void addView() {
        rootView = LayoutInflater.from(this).inflate(R.layout.part_find_user_exact, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.addView(rootView, params);

        initView();
    }

    private void initView(){
        tvTitle.setText(R.string.search_by_name);
        tv_commit.setText(R.string.find);
        tvTitle.setVisibility(View.VISIBLE);
        tvTag = (TextView) findViewById(R.id.tv_left_tag);
        tvTag.setText(R.string.family_name);
        etContent = (EditText) findViewById(R.id.et_right_content);

        tvTag.setOnClickListener(this);
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new FindExactUserPresenter(this);
        return mPresenter;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.text_right == v.getId()) {
            mPresenter.loadUserList("", "", "", "", etContent.getText().toString().trim(), "1", "1000");  //// TODO  my god
        }
    }

    @Override
    public void goToUserListPage(List<FindUserResponse> list) {
        if (list == null) {
            return;
        }
        mNavigator.navigateToUserListActivity(this, list);
    }
}
