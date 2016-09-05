package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.FindGroupResponse;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.presenter.BlankPresenter;
import com.yz.im.ui.base.HXBaseActivity;
import com.yz.im.utils.GlideRoundTransform;

public class UnJoinGroupInfoActivity extends HXBaseActivity implements View.OnClickListener {

    private static String KEY_GROUP_INFO = "key_group_info";

    public static Intent getUnJoinGroupInfoIntent(Context context, FindGroupResponse response) {
        Intent intent = new Intent(context, UnJoinGroupInfoActivity.class);
        intent.putExtra(KEY_GROUP_INFO, response);
        return intent;
    }

    private ImageView imgBack;
    private TextView mTitle;
    private TextView mGroupName;
    private TextView mGroupId;
    private TextView mItemName;
    private TextView mGroupIntroduction;
    private TextView mGroupMember;
    private ImageView mGroupHead;
    private ImageView mNameArrow;
    private ImageView mIntroductionArrow;
    private ImageView mMemberArrow;
    private Button mButton;

    private FindGroupResponse mResponse;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_un_join_group_info);
        getDataFromIntent();
        initViewAndEvent();
        showData();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mResponse = intent.getParcelableExtra(KEY_GROUP_INFO);
        }
    }

    private void initViewAndEvent() {
        imgBack = (ImageView) findViewById(R.id.image_left);
        mGroupHead = (ImageView) findViewById(R.id.img_group);
        mNameArrow = (ImageView) findViewById(R.id.img_item_name_arrow);
        mIntroductionArrow = (ImageView) findViewById(R.id.item_group_introduction_arrow);
        mMemberArrow = (ImageView) findViewById(R.id.img_group_member_arrow);
        mTitle = (TextView) findViewById(R.id.text_title);
        mGroupName = (TextView) findViewById(R.id.tv_group_name);
        mGroupId = (TextView) findViewById(R.id.tv_group_id);
        mItemName = (TextView) findViewById(R.id.tv_item_name);
        mGroupIntroduction = (TextView) findViewById(R.id.tv_group_introduction);
        mGroupMember = (TextView) findViewById(R.id.tv_group_number);
        mButton = (Button) findViewById(R.id.btn_apply_join);

        imgBack.setImageResource(R.drawable.back);
        imgBack.setVisibility(View.VISIBLE);
        mTitle.setText(R.string.detail_info);

        mNameArrow.setVisibility(View.INVISIBLE);
        mIntroductionArrow.setVisibility(View.INVISIBLE);
        mMemberArrow.setVisibility(View.INVISIBLE);

        imgBack.setOnClickListener(this);
        mButton.setOnClickListener(this);
        mGroupHead.setOnClickListener(this);
    }

    private void showData() {
        if (mResponse == null) {
            return;
        }
        Glide.with(this).load(mResponse.getImage()).error(R.drawable.default_group).transform(new GlideRoundTransform(this, 10)).into(mGroupHead);
        mGroupName.setText(mResponse.getGroupName());
        mItemName.setText(mResponse.getGroupName());
        mGroupId.setText(mResponse.getGroupId());
        mGroupIntroduction.setText(mResponse.getCircleIntroduce());
        mGroupMember.setText(mResponse.getNum());
    }

    @Override
    public IMMvpPresenter createPresenter() {
        return new BlankPresenter();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(R.id.image_left == id){
            finish();
        }else if(R.id.img_group == id){
            showToast("查看大图");
        }else if(R.id.btn_apply_join == id){
            mNavigator.navigateToSendInviteJoinGroupReasonPage(this, mResponse.getGroupId());
        }
    }
}
