package com.thinkcoo.mobile.presentation.views.adapter.Schedule;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/8/11  16:25
 */
public class ResourceListAdapter extends RecyclerView.Adapter<ResourceListAdapter.ResourceItemHolder> {

    private List<String> mData;

    public ResourceListAdapter(List<String> data) {
        this.mData = data;
    }

    @Override
    public ResourceItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_resource_list, null);
        return new ResourceItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ResourceItemHolder holder, int position) {
        holder.mTextView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

    public class ResourceItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTextView;

        public ResourceItemHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_resource_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemListener != null) {
                mItemListener.onItemClick(itemView,getLayoutPosition());
            }
        }
    }

    private static OnItemClickListener mItemListener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemListener = listener;
    }
}
