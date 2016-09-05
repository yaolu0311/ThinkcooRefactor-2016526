package com.yz.im.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hyphenate.easeui.R;
import com.yz.im.model.strategy.FriendListStrategy;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.presenter.BlankPresenter;
import com.yz.im.ui.base.HXBaseActivity;
import com.yz.im.ui.fragment.IMChatFragment;
import com.yz.im.ui.widget.BaseContactListView;
import com.yz.im.ui.widget.FriendContactListView;

public class ContactListActivity extends HXBaseActivity {

    public static final int REQUEST_CODE_GROUP_TRANSFER_MSG = 0x0001;

    public static final String KEY_STRATEGY = "key_strategy";

    public static Intent getContactListActivityIntent(Context context, FriendListStrategy strategy) {
        Intent intent = new Intent(context, ContactListActivity.class);
        intent.putExtra(KEY_STRATEGY, (Parcelable) strategy);
        return intent;
    }

    private FrameLayout mFrameLayout;
    private BaseContactListView mContactListView;
    private FriendListStrategy mStrategy;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_contact_list);
        getDataFromIntent();
        initView();
        addFragmentView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((FriendContactListView) mContactListView).getData();
    }

    @Override
    public IMMvpPresenter createPresenter() {
        return new BlankPresenter();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mStrategy = intent.getParcelableExtra(KEY_STRATEGY);
        }
    }

    private void initView() {
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_contact);
    }

    private void addFragmentView() {
        if (mStrategy == null) {
            return;
        }
        mContactListView = mStrategy.getView(this);
//        if (mContactListView != null && mContactListView instanceof FriendListTransferMsgView) {
//            EMMessage emMessage = ((FriendListStrategyFactory.FriendListTransferStrategy)mStrategy).getMessage();
//            ((FriendListTransferMsgView)mContactListView).setEMMessage(emMessage);
//        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mFrameLayout.addView(mContactListView, params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case IMChatFragment.REQUEST_CODE_USER_CARD:
                    setResult(RESULT_OK, data);
                    finish();
                    break;
                case REQUEST_CODE_GROUP_TRANSFER_MSG:
                    finish();
                    break;
            }
        }
    }
}
