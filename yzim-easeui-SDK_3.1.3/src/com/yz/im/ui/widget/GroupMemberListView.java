package com.yz.im.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.hyphenate.easeui.R;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import com.yz.im.model.entity.serverresponse.GroupInfoResponse;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;
import com.yz.im.ui.activity.GroupMemberListActivity;
import com.yz.im.ui.adapter.BaseContactListAdapter;
import com.yz.im.ui.adapter.GroupMemberAdapter;
import com.yz.im.utils.ToastUtil;

import java.util.LinkedList;

/**
 * Created by cys on 2016/7/27
 */
public class GroupMemberListView extends BaseContactListView<GroupMemberResponse> {

    private static final String IS_STRANGER = "0";
    private static final String IS_FRIEND = "1";
    private static final String IS_BLACK_USER = "1";

    private ImageView rightImage;

    private boolean isOwner = false; //是否是群主
    private GroupInfoResponse mInfoResponse;
    private ActionSheetDialog mActionSheetDialog;
    private String loginUserId;

    public GroupMemberListView(Context context, GroupInfoResponse infoResponse, boolean isOwner) {
        super(context);
        this.isOwner = isOwner;
        if (infoResponse == null) {
            throw new NullPointerException("group is null");
        }
        this.mInfoResponse = infoResponse;
        title.setText(mInfoResponse.getGroupName());
        initView();
        hideSearchLayout();
        setShowSideBar(false);
        loginUserId = IMHelper.getInstance().getInfoResponse().getUserId();
    }

    private void initView() {
        if (isOwner && Constant.TYPE_INITIATIVE_GROUP.equals(mInfoResponse.getGroupType())) {
            rightImage = (ImageView) findViewById(R.id.image_right);
            rightImage.setVisibility(VISIBLE);
            rightImage.setImageResource(R.drawable.conversation_list_menu);
            rightImage.setOnClickListener(this);
        }
    }

    @Override
    protected void setTitleLayout() {
        leftImage.setVisibility(View.VISIBLE);
        leftImage.setImageResource(R.drawable.back);
    }

    @Override
    protected void setAdapter() {
        adapter = new GroupMemberAdapter(getContext(), contactList, BaseContactListAdapter
                .NONE_CHECK_BOX, false);
    }

    @Override
    protected void getData() {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GroupMemberResponse response = contactList.get(position);
        String userId = response.getUserId();
        if (loginUserId.equals(userId)) {
            ((GroupMemberListActivity)context).showToast(R.string.forbid_show_self_info);
            return;
        }
        ((GroupMemberListActivity) context).mNavigator.navigateToUserInfoPage(context, userId);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.image_right) {
            showPopupDialog();
        }
    }

    private void showPopupDialog() {
        if (null != mActionSheetDialog) {
            mActionSheetDialog.show();
            return;
        }
        mActionSheetDialog = new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .setSheetItems(getSheetItemEntity(), false)
                .addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                        int id = Integer.valueOf(sheetItemEntity.getCode());
                        if(id == 0){
                            mNavigator.navigateToInviteContactActivity(context, mInfoResponse.getGroupId(), contactList);
                        }else {
                            ToastUtil.getInstance(context).showToast("邀请其他人加入圈子");
                        }
                    }
                });
        mActionSheetDialog.show();
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntity() {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        ActionSheetDialog.SheetItemEntity userSheetEntity = new ActionSheetDialog.SheetItemEntity
                (context.getString(R.string.invite_user_from_contact), "0", null);
        ActionSheetDialog.SheetItemEntity otherSheetEntity = new ActionSheetDialog.SheetItemEntity
                (context.getString(R.string.invite_user_from_other), "1", null);
        itemEntities.add(userSheetEntity);
        itemEntities.add(otherSheetEntity);
        return itemEntities;
    }
}
