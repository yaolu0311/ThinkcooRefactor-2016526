package com.yz.im.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.example.administrator.publicmodule.util.IdOffsetUtil;
import com.hyphenate.easeui.R;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.ui.activity.ContactListActivity;
import com.yz.im.ui.fragment.IMChatFragment;

import java.util.LinkedList;

public class FriendListCardView extends FriendContactListView implements View
        .OnClickListener {

    protected ActionSheetDialog mActionSheetDialog;

    public FriendListCardView(Context context) {
        this(context, null);
    }

    public FriendListCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.layout_group) {
            mNavigator.navigateToGroupListCardActivity(getContext());
            return;
        }
        super.onClick(v);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            return;
        }
        Friend friend = (Friend) contactList.get(position-1);
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
                .setSheetItems(getSheetItemEntity(), false)
                .addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                        Intent intent = new Intent();
                        intent.putExtra(IMChatFragment.KEY_CARD_MESSAGE, createCardContent(friend));
                        ((ContactListActivity)getContext()).setResult(Activity.RESULT_OK, intent);
                        ((ContactListActivity) getContext()).finish();
                    }
                });
        mActionSheetDialog.show();
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntity() {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity
                (context.getString(R.string.send), "0", null);
        itemEntities.add(sheetItemEntity);
        return itemEntities;
    }

    private String createCardContent(Friend friend){
        StringBuffer content = new StringBuffer();
        content.append("card@card").append(";")
                .append(friend.getUserId()).append(";")
                .append(friend.getImage()).append(";")
                .append(friend.getRealName())
                .append(";").append(String.valueOf(IdOffsetUtil.addOffset(friend.getUserId())))
                .append(";")
                .append("friend");
        return content.toString();
    }
}
