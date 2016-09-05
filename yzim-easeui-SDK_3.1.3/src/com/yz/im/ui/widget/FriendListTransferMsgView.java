package com.yz.im.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.example.administrator.publicmodule.util.IdOffsetUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.yz.im.IMHelper;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.model.entity.MessageExtraAttribute;
import com.yz.im.model.entity.serverresponse.UserInfoResponse;
import com.yz.im.model.im.MessageWrapUtil;
import com.yz.im.model.strategy.FriendListStrategyFactory;
import com.yz.im.ui.activity.ContactListActivity;
import com.yz.im.utils.ToastUtil;

import java.util.LinkedList;

public class FriendListTransferMsgView extends FriendContactListView implements View
        .OnClickListener {

    protected ActionSheetDialog mActionSheetDialog;
    private EMMessage mEMMessage;

    private MessageExtraAttribute mExtraAttribute;

    public FriendListTransferMsgView(Context context) {
        this(context, null);
    }

    public FriendListTransferMsgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mExtraAttribute= new MessageExtraAttribute();
        UserInfoResponse infoResponse = IMHelper.getInstance().getInfoResponse();
        mExtraAttribute.putSenderId(infoResponse.getUserId());
        mExtraAttribute.putSenderImage(infoResponse.getHeadPortrait());
        mExtraAttribute.putSenderName(infoResponse.getFullName());

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.layout_group) {
            mNavigator.navigateToGroupListTransferMsgActivity(getContext(), mEMMessage);
            return;
        }
        super.onClick(v);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            return;
        }
        Friend friend = (Friend) contactList.get(position - 1);
        showPopupDialog(friend);
    }

    private void showPopupDialog(final Friend friend) {
        if (null != mActionSheetDialog) {
            mActionSheetDialog.show();
            return;
        }
        mActionSheetDialog = new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .setSheetItems(getSheetItemEntity(friend), false)
                .addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                        int position = Integer.valueOf(sheetItemEntity.getCode());
                        if (0 == position) {
                            return;
                        }
                        mEMMessage = getDataFromStrategy();
                        if (mEMMessage == null) {
                            ToastUtil.getInstance(getContext()).showToastById(R.string.send_fail);
                            return;
                        }
                        String userId = friend.getUserId();
                        mExtraAttribute.putReceiverId(userId);
                        mEMMessage.setTo(IdOffsetUtil.addOffset(userId));
                        sendMessage(mEMMessage);
                    }
                });
        mActionSheetDialog.show();
    }

    private EMMessage getDataFromStrategy() {
        EMMessage message = null;
        if (mStrategy == null) {
            return message;
        }
        if (mStrategy instanceof FriendListStrategyFactory.FriendListTransferStrategy) {
            message = ((FriendListStrategyFactory.FriendListTransferStrategy)mStrategy).getMessage();
        }
        return message;
    }

    protected void sendMessage(EMMessage message) {
        if (message == null) {
            return;
        }
        MessageWrapUtil.wrapMessage(message, mExtraAttribute);

        //发送消息
        EMClient.getInstance().chatManager().sendMessage(mEMMessage);
        ((ContactListActivity)getContext()).finish();
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntity(Friend friend) {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        ActionSheetDialog.SheetItemEntity sheetItem = new ActionSheetDialog.SheetItemEntity
                (getContext().getString(R.string.sure_to_transfer_msg, "给" + friend.getRealName()), "0", ActionSheetDialog.SheetItemColor.GREY);
        sheetItem.setTextSize(ActionSheetDialog.Min_SIZE);
        sheetItem.setCanClick(false);
        itemEntities.add(sheetItem);

        ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity
                (context.getString(R.string.send), "1", null);
        itemEntities.add(sheetItemEntity);
        return itemEntities;
    }
}
