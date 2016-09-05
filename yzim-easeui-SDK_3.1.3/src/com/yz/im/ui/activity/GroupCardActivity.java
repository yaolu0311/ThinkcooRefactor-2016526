package com.yz.im.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.hyphenate.easeui.R;
import com.yz.im.model.db.entity.Group;
import com.yz.im.ui.fragment.IMChatFragment;

import java.util.LinkedList;

public class GroupCardActivity extends GroupListActivity {

    public static Intent getGroupCardActivityIntent(Context context) {
        Intent intent = new Intent(context, GroupCardActivity.class);
        return intent;
    }

    private ActionSheetDialog mActionSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(Group group, int position) {
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
                .setSheetItems(getSheetItemEntity(), false)
                .addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                        Intent intent = new Intent();
                        intent.putExtra(IMChatFragment.KEY_CARD_MESSAGE, createCardContent(group));
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                });
        mActionSheetDialog.show();
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntity() {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity
                (this.getString(R.string.send), "0", null);
        itemEntities.add(sheetItemEntity);
        return itemEntities;
    }

    private String createCardContent(Group group){
        StringBuffer content = new StringBuffer();
        content.append("card@card").append(";")
                .append(group.getGroupId()).append(";")
                .append(group.getGroupImage()).append(";")
                .append(group.getGroupName())
                .append(";").append(group.getEasemobGroupId())
                .append(";")
                .append("group");
        return content.toString();
    }
}
