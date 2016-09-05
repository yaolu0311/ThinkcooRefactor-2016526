package com.thinkcoo.mobile.presentation.views.dialog;

import android.content.Context;
import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.utils.TakePhotoUtils;
import java.util.LinkedList;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/10.
 */
public class TakePhotoChoiceDialog {

    private ActionSheetDialog mActionSheetDialog;
    private TakePhotoUtils mTakePhotoUtils;

    @Inject
    public TakePhotoChoiceDialog() {

    }

    public void setTakePhotoUtils(TakePhotoUtils takePhotoUtils){
        mTakePhotoUtils = takePhotoUtils;
    }

    public void showDialog(Context context,final int maxPhotoCount){

        if(null != mActionSheetDialog){
            mActionSheetDialog.show();
            return;
        }
        String[] mSheetItemNames = context.getResources().getStringArray(R.array.sheetItem_photos);
        mActionSheetDialog = new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(false)
                .setSheetItems(getSheetItemEntitys(mSheetItemNames), false)
                .addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                        switch (sheetItemEntity.getCode()){
                            case "0":
                                takePhotoAndCrop();
                                break;
                            case "1":
                                multiSelectPhotoAndCrop(maxPhotoCount);
                                break;
                        }
                    }
                });
        mActionSheetDialog.show();
    }

    private void multiSelectPhotoAndCrop(int maxPhotoCount) {
        if (null != mTakePhotoUtils){
            mTakePhotoUtils.multiSelectPhotoAndCrop(maxPhotoCount);
        }
    }
    private void takePhotoAndCrop() {
        if (null != mTakePhotoUtils){
            mTakePhotoUtils.takePhotoAndCrop();
        }
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntitys(String[] sheetItemNames) {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        if (null == sheetItemNames) {
            return itemEntities;
        }
        for(int i=0; i<sheetItemNames.length; i++){
            ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity(sheetItemNames[i], i+"", null);
            itemEntities.add(sheetItemEntity);
        }
        return itemEntities;
    }
}
