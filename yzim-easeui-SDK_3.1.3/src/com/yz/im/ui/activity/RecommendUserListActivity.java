package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.RecommendUserContact;
import com.yz.im.mvp.presenter.RecommendUserPresenter;

import java.util.List;

public class RecommendUserListActivity extends BaseFindUserListActivity implements RecommendUserContact.RecommendUserView{

    public static Intent getRecommendUserListIntent(Context context){
        Intent intent = new Intent(context, RecommendUserListActivity.class);
        return intent;
    }

    private RecommendUserPresenter mPresenter;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        super.continueOnCreate(savedInstanceState);
        tvTitle.setText(R.string.recommend_user);
        mPresenter.loadRecommendUser("", "1", "1", "100");  //// TODO my god
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new RecommendUserPresenter(this);
        return mPresenter;
    }

    @Override
    public void setList(List<FindUserResponse> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}
