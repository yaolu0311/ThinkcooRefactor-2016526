package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.presenter.BlankPresenter;

import java.util.ArrayList;
import java.util.List;

public class FindUserListActivity extends BaseFindUserListActivity{

    public static String KEY_USER_LIST = "key_user_list";

    public static Intent getUserListIntent(Context context, List<FindUserResponse> list){
        Intent intent = new Intent(context, FindUserListActivity.class);
        intent.putParcelableArrayListExtra(KEY_USER_LIST, (ArrayList<? extends Parcelable>) list);
        return intent;
    }

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        super.continueOnCreate(savedInstanceState);
        tvTitle.setText(R.string.find_user);
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            List<FindUserResponse> list = intent.getParcelableArrayListExtra(KEY_USER_LIST);
            refreshData(list);
        }
    }


    @Override
    public IMMvpPresenter createPresenter() {
        return new BlankPresenter();
    }

}
