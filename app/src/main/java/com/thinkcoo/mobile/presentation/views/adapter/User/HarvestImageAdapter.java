package com.thinkcoo.mobile.presentation.views.adapter.User;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.UserHarvestDetail;
import com.thinkcoo.mobile.utils.TakePhotoUtils;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Leevin
 * CreateTime: 2016/6/6  18:43
 */
public class HarvestImageAdapter extends RecyclerView.Adapter {

    private static final int MAX_IMAGE_COUNT = 5 ;
    private Context mContext;
    private List<UserHarvestDetail.GrantPicBean> mData;

    public static final int ITEM_TYPE_NORMAL = 1;
    public static final int ITEM_TYPE_ADD = 2;

    private ActionSheetDialog mActionSheetDialog;
    private String[] mSheetItemNames;
    private TakePhotoUtils mTakePhotoUtils;

    public HarvestImageAdapter(Context context, List<UserHarvestDetail.GrantPicBean> data, TakePhotoUtils takePhotoUtils) {
        this.mContext = context;
        this.mData = data;
        mSheetItemNames = context.getResources().getStringArray(R.array.sheetItem_photos);
        mTakePhotoUtils = takePhotoUtils;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE_ADD) {
            View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_harvest_image_add, null);
            return new AddViewHolder(addView);
        } else if (viewType == ITEM_TYPE_NORMAL) {
            View normalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_harvest_image, null);
            return new NormalViewHolder(normalView);
        }
        throw new IllegalArgumentException("unknown  itemtype ");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        if (itemType == ITEM_TYPE_ADD) {
            onBindAddViewHolder((AddViewHolder) holder,position);
        } else if (itemType == ITEM_TYPE_NORMAL) {
            onBindNormalViewHolder((NormalViewHolder)holder,position);
        }
    }

    private void onBindNormalViewHolder(NormalViewHolder holder, int position) {
        String grantPicPath = mData.get(position).getGrantPicPath();
        Glide.with(mContext).load(grantPicPath).into(holder.sdvHarvestPic);
    }

    private void onBindAddViewHolder(AddViewHolder holder, int position) {
        if (mData != null && mData.size() > MAX_IMAGE_COUNT) {
            holder.mIvAdd.setVisibility(View.GONE);
        }
        holder.mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomPopupDialog();
            }
        });
    }

    private void showBottomPopupDialog() {
        if(null != mActionSheetDialog){
            mActionSheetDialog.show();
            return;
        }
        mActionSheetDialog = new ActionSheetDialog(mContext)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(false)
                .setSheetItems(getSheetItemEntitys(mSheetItemNames), false)
                .addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                        navigateToImageSourceByType(sheetItemEntity);
                    }
                });
        mActionSheetDialog.show();
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

    private void navigateToImageSourceByType(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
        String name = sheetItemEntity.getItemName();
        if (mSheetItemNames[0].equals(name)) {
            mTakePhotoUtils.takePhotoAndCrop();
        }
        if (mSheetItemNames[1].equals(name)) {
            mTakePhotoUtils.multiSelectPhotoAndCrop(MAX_IMAGE_COUNT);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 1 : mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() - 1 ? ITEM_TYPE_ADD : ITEM_TYPE_NORMAL;
    }

    public class AddViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_add)
        ImageView mIvAdd;

        public AddViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.sdv_harvest_pic)
        ImageView sdvHarvestPic;
        @Bind(R.id.ib_delete)
        ImageButton mIbDelete;
        @Bind(R.id.pb_progressbar)
        ProgressBar mPbProgressbar;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
