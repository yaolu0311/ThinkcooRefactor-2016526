package com.yz.im.ui.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.GroupMemberResponse;
import com.yz.im.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by cys on 2016/7/27
 */
public class GroupMemberAdapter extends BaseContactListAdapter<GroupMemberResponse>{

    private static final String GROUP_OWNER = "1";

    /**
     * @param context
     * @param list
     * @param checkBoxType  values={LEFT_CHECK_BOX = 0x0001, RIGHT_CHECK_BOX= 0x0002}
     */
    public GroupMemberAdapter(Context context, List<GroupMemberResponse> list, int checkBoxType, boolean isShowLetterIndex) {
        super(context, list, checkBoxType, true, isShowLetterIndex);
    }

    @Override
    void bindDataToView(ViewHolder holder, GroupMemberResponse bean, int position) {
        if (GROUP_OWNER.equals(bean.getIdType())) {
            holder.mPromoter.setVisibility(View.VISIBLE);
        }else {
            holder.mPromoter.setVisibility(View.GONE);
        }
        holder.mName.setText(bean.getShowName());
        holder.mMemberId.setText(bean.getUserId());
        Glide.with(mContext).load(bean.getImager()).transform(new GlideRoundTransform(mContext, 10)).error(R.drawable.default_group)
                .placeholder(R.drawable.default_group).into(holder.mImageView);
    }
}
