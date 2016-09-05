package com.thinkcoo.mobile.presentation.views.adapter.Schedule;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.thinkcoo.mobile.R;

import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/8/11  16:25
 */
public class ResourceFileListAdapter extends RecyclerView.Adapter<ResourceFileListAdapter.FileItemHolder> {

    private List<String> mData;

    public ResourceFileListAdapter(List<String> data) {
        this.mData = data;
    }

    @Override
    public FileItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_file_list, null);
        return new FileItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final FileItemHolder holder, final int position) {
        holder.mMTextView.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

    public class FileItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mMTextView;
        public CheckBox mCbChecked;
        public FileItemHolder(View itemView) {
            super(itemView);
            mMTextView = (TextView) itemView.findViewById(R.id.tv_file_name);
             mCbChecked = (CheckBox) itemView.findViewById(R.id.cb_checked);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnCheckChangeListener != null) {
                mOnCheckChangeListener.onCheckChange(mCbChecked,getLayoutPosition());
            }
        }
    }

    public interface OnCheckChangeListener {
        void onCheckChange(CheckBox checked, int position);
    }

    private OnCheckChangeListener mOnCheckChangeListener;

    public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener) {
        mOnCheckChangeListener = onCheckChangeListener;
    }
}
