package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.yz.im.IMHelper;
import com.yz.im.model.db.entity.Group;
import com.yz.im.model.entity.MessageExtraAttribute;
import com.yz.im.model.entity.serverresponse.UserInfoResponse;
import com.yz.im.model.im.MessageWrapUtil;

import java.util.LinkedList;

public class GroupTransferMsgActivity extends GroupListActivity {

    private static String KEY_MESSAGE = "key_message";

    public static Intent getGroupTransferMsgActivityIntent(Context context, EMMessage message) {
        Intent intent = new Intent(context, GroupTransferMsgActivity.class);
        intent.putExtra(KEY_MESSAGE, message);
        return intent;
    }

    private EMMessage mEMMessage;
    private ActionSheetDialog mActionSheetDialog;
    private MessageExtraAttribute mExtraAttribute;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        super.continueOnCreate(savedInstanceState);
        mExtraAttribute= new MessageExtraAttribute();
        UserInfoResponse infoResponse = IMHelper.getInstance().getInfoResponse();
        mExtraAttribute.putSenderId(infoResponse.getUserId());
        mExtraAttribute.putSenderImage(infoResponse.getHeadPortrait());
        mExtraAttribute.putSenderName(infoResponse.getFullName());
    }

    @Override
    protected void getDataFromIntent() {
        super.getDataFromIntent();
        Intent intent = getIntent();
        mEMMessage = intent.getParcelableExtra(KEY_MESSAGE);
    }

    @Override
    public void onClick(Group group, int position) {
        mExtraAttribute.putReceiverId(group.getEasemobGroupId());
        mExtraAttribute.putGroupName(group.getGroupName());
        mExtraAttribute.putGroupImage(group.getGroupImage());
        String remarkName = group.getRealName();
        if (TextUtils.isEmpty(remarkName)) {
            remarkName = mExtraAttribute.getSenderName();
        }
        mExtraAttribute.putUserGroupRemark(remarkName);
        mEMMessage.setTo(group.getEasemobGroupId());
        mEMMessage.setChatType(EMMessage.ChatType.GroupChat);
        showPopupDialog(group);
    }

    private void showPopupDialog(final Group group) {
        if (null != mActionSheetDialog) {
            mActionSheetDialog.show();
            return;
        }
        mActionSheetDialog = new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .setSheetItems(getSheetItemEntity(group), false)
                .addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                        int position = Integer.valueOf(sheetItemEntity.getCode());
                        if (0 == position) {
                            return;
                        }
                        sendMessage(mEMMessage);
                    }
                });
        mActionSheetDialog.show();
    }

    protected void sendMessage(EMMessage message) {
        if (message == null) {
            return;
        }
        MessageWrapUtil.wrapMessage(message, mExtraAttribute);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(mEMMessage);
        setResult(RESULT_OK);
        finish();
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntity(Group group) {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        ActionSheetDialog.SheetItemEntity sheetItem = new ActionSheetDialog.SheetItemEntity
                (this.getString(R.string.sure_to_transfer_msg, "到圈子"+group.getGroupName()), "0", ActionSheetDialog.SheetItemColor.GREY);
        sheetItem.setTextSize(ActionSheetDialog.Min_SIZE);
        sheetItem.setCanClick(false);
        itemEntities.add(sheetItem);

        ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity
                (this.getString(R.string.sure), "1", ActionSheetDialog.SheetItemColor.Blue);
        itemEntities.add(sheetItemEntity);
        return itemEntities;
    }

}
