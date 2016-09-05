package com.yz.im.ui.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by cys on 2016/7/27
 */
public class FriendListAdapter extends BaseContactListAdapter<Friend>{

    /**
     * @param context
     * @param list
     * @param checkBoxType  values={LEFT_CHECK_BOX = 0x0001, RIGHT_CHECK_BOX= 0x0002}
     * @param isGroupMember
     */
    public FriendListAdapter(Context context, List<Friend> list, int checkBoxType, boolean isGroupMember) {
        super(context, list, checkBoxType, isGroupMember, true);
    }

    @Override
    void bindDataToView(ViewHolder holder, Friend bean, int position) {
        holder.mName.setText(bean.getShowName());
        holder.mMemberId.setText(bean.getUserId());
        Glide.with(mContext).load(bean.getImage()).transform(new GlideRoundTransform(mContext, 10)).error(R.drawable.default_avatar)
                .placeholder(R.drawable.default_avatar).into(holder.mImageView);
    }

}
