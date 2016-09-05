package com.yz.im.ui.widget;

import android.content.Context;
import android.text.TextUtils;
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

/**
 * 给好友发送二维码
 */
public class FriendListSendZxingView extends FriendContactListView implements View
        .OnClickListener {

    protected ActionSheetDialog mActionSheetDialog;
    private String uri;

    private MessageExtraAttribute mExtraAttribute;

    private EMMessage mEMMessage;

    public FriendListSendZxingView(Context context) {
        this(context, null);
    }

    public FriendListSendZxingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mExtraAttribute = new MessageExtraAttribute();
        UserInfoResponse infoResponse = IMHelper.getInstance().getInfoResponse();
        mExtraAttribute.putSenderId(infoResponse.getUserId());
        mExtraAttribute.putSenderImage(infoResponse.getHeadPortrait());
        mExtraAttribute.putSenderName(infoResponse.getFullName());

    }

    @Override
    public void initHeadView() {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Friend friend = (Friend) contactList.get(position);
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
                        createImageMessage(friend);
                    }
                });
        mActionSheetDialog.show();
    }

    private void createImageMessage(Friend friend) {
        String userId = friend.getUserId();
        uri = getDataFromStrategy();
        if (TextUtils.isEmpty(uri)) {
            ToastUtil.getInstance(getContext()).showToastById(R.string.image_unexist);
            return;
        }
        mEMMessage = EMMessage.createImageSendMessage(uri, false, IdOffsetUtil.addOffset(userId));
        mExtraAttribute.putReceiverId(userId);
        sendMessage(mEMMessage);
    }

    private String getDataFromStrategy() {
        String result = "";
        if (mStrategy == null) {
            return result;
        }
        if (mStrategy instanceof FriendListStrategyFactory.FriendListZxingStrategy) {
            result = ((FriendListStrategyFactory.FriendListZxingStrategy)mStrategy).getUri();
        }
        return result;
    }

    private void sendMessage(EMMessage message) {
        if (message == null) {
            return;
        }
        MessageWrapUtil.wrapMessage(message, mExtraAttribute);

        //发送消息
        EMClient.getInstance().chatManager().sendMessage(mEMMessage);
        ((ContactListActivity) getContext()).finish();
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntity(Friend friend) {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        ActionSheetDialog.SheetItemEntity sheetItem = new ActionSheetDialog.SheetItemEntity
                (getContext().getString(R.string.sure_to_send_zxing, friend.getShowName()), "0", ActionSheetDialog.SheetItemColor.GREY);
        sheetItem.setTextSize(ActionSheetDialog.Min_SIZE);
        sheetItem.setCanClick(false);
        itemEntities.add(sheetItem);

        ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity
                (context.getString(R.string.sure), "1", null);
        itemEntities.add(sheetItemEntity);
        return itemEntities;
    }
}
