package com.thinkcoo.mobile.presentation.views.adapter.Schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.EventNoticeEntity;
import com.thinkcoo.mobile.model.entity.Student;

import java.util.List;

/**
 * Created bywyy
 * CreateTime: 2016/6/1  9:24
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {

    private Context mContext;
    private List<EventNoticeEntity> mData;
    public NoticeAdapter(Context context, List<EventNoticeEntity> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_notice, null);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final EventNoticeEntity eventNoticeEntity = mData.get(position);
        holder.content.setText(eventNoticeEntity.getNoticeContent());
        holder.time.setText(eventNoticeEntity.getCreateTime());
        holder.itemView.setTag(eventNoticeEntity);

    }
        @Override
        public int getItemCount () {
            return mData == null ? 0 : mData.size();
        }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView time;
        private final TextView content;
        public MyViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            content = (TextView) itemView.findViewById(R.id.content);

        }
    }



}
