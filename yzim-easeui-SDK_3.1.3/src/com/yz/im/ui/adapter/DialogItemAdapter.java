package com.yz.im.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.R;

import java.util.List;

/**
 * Created by cys on 2016/8/12
 */
public class DialogItemAdapter extends RecyclerView.Adapter<DialogItemAdapter.ItemViewHolder> {

    private Context mContext;
    private List<String> mList;
    private OnItemClickListener mListener;

    public DialogItemAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_chat_row_long_click, null);
        ItemViewHolder holder = new ItemViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.bindData(mList.get(position));
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) {
                    return;
                }
                mListener.onClick(mList.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLayout;
        private TextView mContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_chat_row_long_click);
            mContent = (TextView) itemView.findViewById(R.id.tv_chat_row_item_content);
        }

        public void bindData(String tag) {
            mContent.setText(tag);
        }
    }

    public interface OnItemClickListener {

        void onClick(String content, int position);
    }
}
