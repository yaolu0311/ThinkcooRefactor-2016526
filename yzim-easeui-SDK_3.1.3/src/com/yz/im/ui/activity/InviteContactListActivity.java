package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.presenter.BlankPresenter;
import com.yz.im.ui.base.HXBaseActivity;
import com.yz.im.ui.widget.InviteContactListView;

import java.util.ArrayList;
import java.util.List;

public class InviteContactListActivity extends HXBaseActivity {


    public static final String KEY_GROUP_ID = "key_group_id";
    public static final String KEY_GROUP_MEMBERS = "key_group_member";

    public static Intent getContactListActivityIntent(Context context, String groupId, List<GroupMemberResponse> list) {
        Intent intent = new Intent(context, InviteContactListActivity.class);
        intent.putExtra(KEY_GROUP_ID, groupId);
        intent.putParcelableArrayListExtra(KEY_GROUP_MEMBERS, (ArrayList<? extends Parcelable>) list);
        return intent;
    }

    private FrameLayout mFrameLayout;
    private List<GroupMemberResponse> list;
    private String groupId;
    private InviteContactListView mContactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_contact_list);
        getDataFromIntent();
        initView();
        addFragmentView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            groupId = intent.getStringExtra(KEY_GROUP_ID );
            list = intent.getParcelableArrayListExtra(KEY_GROUP_MEMBERS);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
    }

    private void initView() {
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_contact);
    }

    private void addFragmentView() {
        mContactListView = new InviteContactListView(this, list, groupId);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mFrameLayout.addView(mContactListView, params);
    }

    @Override
    public IMMvpPresenter createPresenter() {
        return new BlankPresenter();
    }

}
