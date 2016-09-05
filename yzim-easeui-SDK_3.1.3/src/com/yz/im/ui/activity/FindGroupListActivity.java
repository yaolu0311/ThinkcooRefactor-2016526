package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.FindGroupResponse;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.presenter.BlankPresenter;
import com.yz.im.ui.base.HXBaseActivity;
import com.yz.im.ui.fragment.FindGroupFragment;

import java.util.ArrayList;
import java.util.List;

public class FindGroupListActivity extends HXBaseActivity implements View.OnClickListener{

    public static String KEY_GROUP_LIST = "key_group_list";

    public static Intent getFindGroupIntent(Context context, List<FindGroupResponse> list) {
        if (list == null) {
            throw new NullPointerException("list can't be null");
        }
        Intent intent = new Intent(context, FindGroupListActivity.class);
        intent.putParcelableArrayListExtra(KEY_GROUP_LIST, (ArrayList<? extends Parcelable>) list);
        return intent;
    }

    private ImageView imgBack;
    private TextView mTitle;

    private List<FindGroupResponse> list;
    private FindGroupFragment mGroupFragment;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_find_group_list);
        getDataFromIntent();
        initViewAndEvent();
        initFragment();
    }

    private void getDataFromIntent() {
        list = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            List<FindGroupResponse> mList = intent.getParcelableArrayListExtra(KEY_GROUP_LIST);
            if (mList != null) {
                list.addAll(mList);
            }
        }
    }

    private void initViewAndEvent() {
        imgBack = (ImageView) findViewById(R.id.image_left);
        imgBack.setImageResource(R.drawable.back);
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(this);
        mTitle = (TextView) findViewById(R.id.text_title);
        mTitle.setText(R.string.find_group);
    }

    private void initFragment() {
        mGroupFragment = new FindGroupFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(FindGroupFragment.KEY_LIST, (ArrayList<? extends Parcelable>) list);
        mGroupFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layout_find_group_list, mGroupFragment).show(mGroupFragment).commit();
    }

    @Override
    public IMMvpPresenter createPresenter() {
        return new BlankPresenter();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_left) {
            finish();
        }
    }
}
