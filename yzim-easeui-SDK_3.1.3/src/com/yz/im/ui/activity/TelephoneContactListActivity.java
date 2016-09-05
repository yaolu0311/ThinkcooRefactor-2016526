package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.TelephoneContact;
import com.yz.im.mvp.presenter.TelephoneContactPresenter;

import java.util.List;

public class TelephoneContactListActivity extends BaseFindUserListActivity implements TelephoneContact.TelephoneView{

    public static Intent getTelephoneContactListIntent(Context context){
        Intent intent = new Intent(context, TelephoneContactListActivity.class);
        return intent;
    }

    private TelephoneContactPresenter mPresenter;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        super.continueOnCreate(savedInstanceState);
        tvTitle.setText(R.string.telephone_contact);
        mPresenter.loadTelephoneContact();
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new TelephoneContactPresenter(this);
        return mPresenter;
    }

    @Override
    public void setData(List<FindUserResponse> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}
