package com.thinkcoo.mobile.presentation.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.DataDictionary;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/15.
 */
public class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleRecyclerViewAdapter.SamplerHolder> {

    private final static String TAG = "SampleRecyclerViewAdapter";

    private Context mContext;
    private List<DataDictionary> mDictionaryList;

    private OnRecyclerItemClickListener mOnItemClickListener;

    public SampleRecyclerViewAdapter(Context context, List<DataDictionary> dictionaryList) {
        mContext = context;
        mDictionaryList = dictionaryList;
    }

    @Override
    public SamplerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample_recyclerview, parent, false);
        SamplerHolder samplerHolder = new SamplerHolder(v);
        return samplerHolder;
    }

    @Override
    public void onBindViewHolder(SamplerHolder holder, final int position) {
        final DataDictionary dictionary = mDictionaryList.get(position);
        holder.mItemSampleRecycler.setText(dictionary.getDisplayName());
        holder.mItemSampleRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(dictionary, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDictionaryList.size();
    }


    static class SamplerHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_sample_recycler)
        TextView mItemSampleRecycler;

        public SamplerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnRecyclerItemClickListener {
        void onClick(DataDictionary dataDictionary, int position);
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
