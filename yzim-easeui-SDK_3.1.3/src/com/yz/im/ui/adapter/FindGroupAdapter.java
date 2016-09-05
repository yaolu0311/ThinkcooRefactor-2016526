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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.R;
import com.yz.im.model.entity.serverresponse.FindGroupResponse;
import com.yz.im.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by cys on 2016/8/1
 */
public class FindGroupAdapter extends RecyclerView.Adapter<FindGroupAdapter.GroupViewHolder> {

    private Context mContext;
    private List<FindGroupResponse> mList;
    private JoinGroupInterface mInterface;

    public FindGroupAdapter(Context context, List<FindGroupResponse> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_group_search, null);
        GroupViewHolder viewHolder = new GroupViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, final int position) {
        final FindGroupResponse response = mList.get(position);
        holder.mGroupName.setText(response.getGroupName());
        holder.mGroupId.setText(response.getGroupId());
        Glide.with(mContext).load(response.getImage()).transform(new GlideRoundTransform(mContext, 10))
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.default_group).into(holder.mHead);
        holder.mJoinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterface == null) {
                    throw new NullPointerException("JoinGroupInterface is null");
                }
                mInterface.joinGroup(position, response);
            }
        });
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterface == null) {
                    throw new NullPointerException("JoinGroupInterface is null");
                }
                mInterface.onItemClick(position, response);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {

        private ImageView mHead;
        private TextView mGroupName;
        private TextView mGroupId;
        private TextView mJoinView;
        private RelativeLayout mLayout;

        public GroupViewHolder(View itemView) {
            super(itemView);
            mHead = (ImageView) itemView.findViewById(R.id.img_head);
            mGroupName = (TextView) itemView.findViewById(R.id.tv_group_name);
            mGroupId = (TextView) itemView.findViewById(R.id.tv_group_id);
            mJoinView = (TextView) itemView.findViewById(R.id.tv_join);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.item_find_group);
        }
    }

    public interface JoinGroupInterface {
        void joinGroup(int position, FindGroupResponse response);

        void onItemClick(int position, FindGroupResponse response);
    }

    public void setInterface(JoinGroupInterface anInterface) {
        mInterface = anInterface;
    }
}
