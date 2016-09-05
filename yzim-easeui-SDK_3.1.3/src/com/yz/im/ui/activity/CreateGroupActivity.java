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
import com.yz.im.Constant;
import com.yz.im.model.db.entity.Group;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.CreateGroupContact;
import com.yz.im.mvp.presenter.CreateGroupPresenter;

public class CreateGroupActivity extends BaseSingleEditActivity implements CreateGroupContact.CreateGroupView{

    public static Intent getCreateGroupIntent(Context context) {
        Intent intent = new Intent(context, CreateGroupActivity.class);
        return intent;
    }

    private View contentView;
    private ImageView ivClear;
    private EditText etContent;

    private CreateGroupPresenter mPresenter;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        super.continueOnCreate(savedInstanceState);
        addContentView();
        initView();
    }

    private void initView() {
        tvTitle.setText(R.string.group_name_title);
        tv_commit.setText(R.string.save);
        tv_commit.setVisibility(View.VISIBLE);
        ivClear = (ImageView) contentView.findViewById(R.id.img_clear_single);
        ivClear.setVisibility(View.VISIBLE);
        etContent = (EditText) contentView.findViewById(R.id.et_invite_reason);

        ivClear.setOnClickListener(this);
    }

    private void addContentView() {
        contentView = LayoutInflater.from(this).inflate(R.layout.layout_search_with_clear, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int)getResources().getDimension(R.dimen.px_44), (int)getResources().getDimension(R.dimen.px_30), (int)getResources().getDimension(R.dimen.px_44), 0);
        layout.addView(contentView, layoutParams);
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new CreateGroupPresenter(this);
        return mPresenter;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.text_right) {
            String reason = etContent.getText().toString().trim();
            mPresenter.createGroup(reason, Constant.GROUP_IS_PUBLIC, Constant.GROUP_APPROVAL, Constant.TYPE_INITIATIVE_GROUP);
        } else if (id == R.id.img_clear_single) {
            etContent.setText("");
        }
    }

    @Override
    public void goToGroupInfoPage(Group group) {
        if (group == null) {
            return;
        }
        mNavigator.navigateToGroupInfoPage(this, group.getEasemobGroupId(), true);
    }
}
