package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.yz.im.IMHelper;
import com.yz.im.model.entity.serverresponse.GroupInfoResponse;
import com.yz.im.model.strategy.GroupInfoStrategy;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.GroupInfoContact;
import com.yz.im.mvp.presenter.GroupInfoPresenter;
import com.yz.im.ui.base.HXBaseActivity;
import com.yz.im.utils.GlideRoundTransform;

public class GroupInfoActivity extends HXBaseActivity implements GroupInfoContact.GroupInfoView,
        View.OnClickListener {

    public static int REQUEST_GROUP_NAME = 0X0001;
    public static int REQUEST_GROUP_INTRODUCE = 0X0002;
    public static int REQUEST_GROUP_REMARK_NAME = 0X0003;

    private static final String STATUS_CHECKED = "1";
    private static final String STATUS_UNCHECKED = "0";

    public static String KEY_GROUP_ID = "key_group_id";
    public static String KEY_GROUP_STRATEGY = "key_group_strategy";

    public static Intent getGroupInfoActivityIntent(Context context, String groupId, GroupInfoStrategy strategy) {
        if (TextUtils.isEmpty(groupId) || strategy == null) {
            throw new NullPointerException("unknown argument is null");
        }
        Intent intent = new Intent(context, GroupInfoActivity.class);
        intent.putExtra(KEY_GROUP_ID, groupId);
        intent.putExtra(KEY_GROUP_STRATEGY, (Parcelable) strategy);
        return intent;
    }

    private ImageView mLeftImage;
    private TextView mTitle;
    private ImageView mHead;
    private TextView mName;
    private TextView mGroupId;
    private LinearLayout mItemName;
    private TextView mTvItemName;
    private LinearLayout mGroupImage;
    private LinearLayout mItemIntroduction;
    private TextView mTvIntroduction;
    private LinearLayout mGroupMember;
    private TextView mGroupMemberNum;
    private LinearLayout mGroupCard;
    private TextView mTvGroupCard;
    private LinearLayout mGroupTransfer;
    private LinearLayout mItemMsgUp;
    private LinearLayout mItemMsgDisturb;
    private RelativeLayout mMsgUpLayout;
    private RelativeLayout mMsgDisturbLayout;
    private ToggleButton mToggleUp;
    private ToggleButton mToggleDisturb;
    private LinearLayout mGroupReport;
    private Button mBtnChat;
    private Button mBtnQuitGroup;

    private GroupInfoPresenter mPresenter;
    private String groupId;
    private GroupInfoResponse mResponse;

    private GroupInfoStrategy mStrategy;

    private ToggleButton currentToggle;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_group_info);
        getDataFromIntent();
        initViewAndEvent();
        mPresenter.loadGroupInfo(groupId);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            groupId = intent.getStringExtra(KEY_GROUP_ID);
            mStrategy = intent.getParcelableExtra(KEY_GROUP_STRATEGY);
        }
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new GroupInfoPresenter(this);
        return mPresenter;
    }

    private void initViewAndEvent() {
        mLeftImage = (ImageView) findViewById(R.id.image_left);
        mLeftImage.setImageResource(R.drawable.back);
        mLeftImage.setVisibility(View.VISIBLE);
        mTitle = (TextView) findViewById(R.id.text_title);
        mTitle.setText(R.string.group_setting);
        mHead = (ImageView) findViewById(R.id.img_group);
        mName = (TextView) findViewById(R.id.tv_group_name);
        mGroupId = (TextView) findViewById(R.id.tv_group_id);
        mItemName = (LinearLayout) findViewById(R.id.item_group_name);
        mTvItemName = (TextView) findViewById(R.id.tv_item_name);
        mGroupImage = (LinearLayout) findViewById(R.id.item_group_image);
        mItemIntroduction = (LinearLayout) findViewById(R.id.item_group_introduction);
        mTvIntroduction = (TextView) findViewById(R.id.tv_group_introduction);
        mGroupMember = (LinearLayout) findViewById(R.id.item_group_member);
        mGroupMemberNum = (TextView) findViewById(R.id.tv_group_number);
        mGroupCard = (LinearLayout) findViewById(R.id.item_group_card_name);
        mItemMsgUp = (LinearLayout) findViewById(R.id.item_group_msg_up);
        mItemMsgDisturb = (LinearLayout) findViewById(R.id.item_group_disturb);
        mTvGroupCard = (TextView) findViewById(R.id.tv_group_card_name);
        mGroupTransfer = (LinearLayout) findViewById(R.id.item_group_transfer);
        mGroupReport = (LinearLayout) findViewById(R.id.item_group_report);
        mMsgUpLayout = (RelativeLayout) findViewById(R.id.rl_push_top);
        mMsgDisturbLayout = (RelativeLayout) findViewById(R.id.rl_msg_disturb);
        mToggleUp = (ToggleButton) findViewById(R.id.toggle_group_up);
        mToggleDisturb = (ToggleButton) findViewById(R.id.toggle_group_disturb);
        mBtnChat = (Button) findViewById(R.id.btn_group_chat);
        mBtnQuitGroup = (Button) findViewById(R.id.btn_group_quit);

        setViewVisibility();

        mLeftImage.setOnClickListener(this);
        mHead.setOnClickListener(this);
        mItemName.setOnClickListener(this);
        mGroupImage.setOnClickListener(this);
        mItemIntroduction.setOnClickListener(this);
        mGroupMember.setOnClickListener(this);
        mGroupCard.setOnClickListener(this);
        mGroupTransfer.setOnClickListener(this);
        mGroupReport.setOnClickListener(this);
        mMsgUpLayout.setOnClickListener(this);
        mMsgDisturbLayout.setOnClickListener(this);
        mBtnQuitGroup.setOnClickListener(this);
        mBtnChat.setOnClickListener(this);
    }

    private void setViewVisibility() {
        if (mStrategy == null) {
            return;
        }
        mItemName.setVisibility(mStrategy.isShowName());
        mGroupImage.setVisibility(mStrategy.isShowHead());
        mItemIntroduction.setVisibility(mStrategy.isShowIntroduction());
        mGroupMember.setVisibility(mStrategy.isShowGroupMember());
        mGroupCard.setVisibility(mStrategy.isShowRemarkName());
        mGroupTransfer.setVisibility(mStrategy.isShowTransfer());
        mGroupReport.setVisibility(mStrategy.isReportGroup());
        mItemMsgUp.setVisibility(mStrategy.isMsgTop());
        mItemMsgDisturb.setVisibility(mStrategy.isMsgDisturb());
        mBtnChat.setVisibility(mStrategy.isSupportChat());
        mBtnQuitGroup.setVisibility(mStrategy.isSupportQuit());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_left) {
            finish();
        }
        if (null == mResponse) {
            return;
        }
        if (id == R.id.img_group) {
            mNavigator.navigateToShowBIgImageActivity(this, mResponse == null ? "" : mResponse.getGroupImage());
        } else if (id == R.id.item_group_name) {
            mNavigator.navigateToUpdateGroupNameActivity(this, mResponse.getGroupId(), mResponse.getGroupName());
        } else if (id == R.id.item_group_image) {
            showToast("修改群头像");
        } else if (id == R.id.item_group_introduction) {
            mNavigator.navigateToUpdateGroupIntroductionActivity(this, mResponse.getGroupId(), mResponse.getCircleIntroduce());
        } else if (id == R.id.item_group_member) {
            mNavigator.navigateToGroupMemberActivity(this, mResponse);
        } else if (id == R.id.item_group_card_name) {
            mNavigator.navigateToUpdateGroupRemarkActivity(this, mResponse.getGroupId(), mResponse.getRealName());
        } else if (id == R.id.item_group_transfer) {
            mNavigator.navigateToGroupTransferActivity(this, mResponse);
        } else if (id == R.id.item_group_report) {
            mNavigator.navigateToReportGroupActivity(this, mResponse.getGroupId());
        } else if (id == R.id.btn_group_chat) {
            IMHelper.getInstance().getNavigator().navigateToGroupChatActivity(this, mResponse.getEasemobGroupId());
        } else if (id == R.id.btn_group_quit) {
            mPresenter.quitGroup(groupId, "", "0");
        } else if (id == R.id.rl_push_top) {
            currentToggle = mToggleUp;
            mPresenter.updateToggleStatus(groupId, getToggleReverseStatus(mToggleUp), getToggleStatus(mToggleDisturb));
        } else if (id == R.id.rl_msg_disturb) {
            currentToggle = mToggleDisturb;
            mPresenter.updateToggleStatus(groupId, getToggleStatus(mToggleUp), getToggleReverseStatus(mToggleDisturb));
        }
    }

    @Override
    public void setData(GroupInfoResponse response) {
        if (response == null) {
            return;
        }
        mResponse = response;
        Glide.with(this).load(response.getGroupImage()).error(R.drawable.default_group)
                .transform(new GlideRoundTransform(this, 10)).placeholder(R.drawable.default_group).into(mHead);
        String name = response.getGroupName();
        mName.setText(name);
        mTvItemName.setText(name);
        mGroupId.setText(response.getEasemobGroupId());
        mTvIntroduction.setText(response.getCircleIntroduce());
        mTvGroupCard.setText(response.getRealName());
        mGroupMemberNum.setText(response.getGroupNum());

        mToggleUp.setChecked(Integer.valueOf(response.getStick()) == 1);
        mToggleDisturb.setChecked(Integer.valueOf(response.getDisturb()) == 1);
    }

    @Override
    public void changeToggleStatus() {
        if (currentToggle != null) {
            currentToggle.setChecked(!currentToggle.isChecked());
        }
        currentToggle = null;
    }

    private String getToggleStatus(ToggleButton toggleButton) {
        return toggleButton.isChecked() ? STATUS_CHECKED : STATUS_UNCHECKED;
    }

    private String getToggleReverseStatus(ToggleButton toggleButton) {
        return toggleButton.isChecked() ? STATUS_UNCHECKED : STATUS_CHECKED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            mPresenter.loadGroupInfo(mResponse.getGroupId());
        }
    }
}
