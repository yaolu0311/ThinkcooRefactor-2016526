package com.yz.im.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by cys on 2016/8/3
 */
public class BlackListAdapter extends RecyclerView.Adapter<BlackListAdapter.BlackViewHolder>{

    private Context mContext;
    private List<Friend> mList;

    private OnItemCallBack mCallBack;

    public BlackListAdapter(Context context, List<Friend> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public BlackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_black_user_list, null);
        BlackViewHolder holder= new BlackViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(BlackViewHolder holder, final int position) {
        final Friend friend = mList.get(position);
        holder.bindData(mContext, friend);
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.onItemClick(position, friend);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class BlackViewHolder extends RecyclerView.ViewHolder{

        private ImageView mHead;
        private TextView mName;
        private RelativeLayout mLayout;

        public BlackViewHolder(View itemView) {
            super(itemView);
            mHead = (ImageView) itemView.findViewById(R.id.img_head);
            mName = (TextView) itemView.findViewById(R.id.tv_name);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout_friend_info);
        }

        public void bindData(Context context, Friend friend){
            if (friend == null) {
                return;
            }
            Glide.with(context).load(friend.getImage()).error(R.drawable.default_avatar).transform(new GlideRoundTransform(context, 10)).into(mHead);
            mName.setText(friend.getRemarkName());
        }
    }

    public interface OnItemCallBack{
        void onItemClick(int position, Friend friend);
    }

    public void setCallBack(OnItemCallBack callBack) {
        mCallBack = callBack;
    }
}
