package com.yz.im.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.GroupInfoResponse;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;
import com.yz.im.mvp.mvpContract.GroupMemberContact;
import com.yz.im.ui.activity.GroupMemberListActivity;
import com.yz.im.ui.adapter.BaseContactListAdapter;
import com.yz.im.ui.adapter.GroupMemberAdapter;

import java.util.LinkedList;

/**
 * Created by cys on 2016/7/27
 */
public class GroupTransferListView extends BaseContactListView<GroupMemberResponse> {

    private GroupInfoResponse mInfoResponse;
    private ActionSheetDialog mActionSheetDialog;

    public GroupTransferListView(Context context, GroupInfoResponse infoResponse) {
        super(context);
        this.mInfoResponse = infoResponse;
        hideSearchLayout();
        setShowSideBar(false);
    }

    @Override
    protected void setTitleLayout() {
        title.setText(R.string.transfer_onwer);
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
        if ("1".equals(response.getIdType())) {
            ((GroupMemberListActivity) context).showToast(R.string.transfer_group_failer);
            return;
        }
        showPopupDialog(response);
    }

    private void showPopupDialog(final GroupMemberResponse response) {
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
                        ((GroupMemberContact.GroupMemberPresenter) ((GroupMemberListActivity) context).getPresenter()).transferGroup(mInfoResponse.getGroupId(), response.getUserId());
                    }
                });
        mActionSheetDialog.show();
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntity() {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity
                (context.getString(R.string.transfer_sure), "0", null);
        itemEntities.add(sheetItemEntity);
        return itemEntities;
    }
}
