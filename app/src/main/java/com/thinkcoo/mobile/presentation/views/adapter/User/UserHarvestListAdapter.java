package com.thinkcoo.mobile.presentation.views.adapter.User;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.UserHarvest;

import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/6/1  9:24
 */
public class UserHarvestListAdapter extends RecyclerView.Adapter<UserHarvestListAdapter.MyViewHolder> {

    private Context mContext;
    private List<UserHarvest> mData;
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    public UserHarvestListAdapter(Context context, List<UserHarvest> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_harvest, null);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserHarvest userHarvest = mData.get(position);
        holder.mTvHarvestName.setText(userHarvest.getGrantName());
        holder.mTvHarvestDepartment.setText(userHarvest.getGrantDepartment());
        holder.itemView.setTag(userHarvest);
        holder.itemView.setOnClickListener(mClickListener);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        private final TextView mTvHarvestName;
        private final TextView mTvHarvestDepartment;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvHarvestName = (TextView) itemView.findViewById(R.id.tv_harvest_name);
            mTvHarvestDepartment = (TextView) itemView.findViewById(R.id.tv_harvest_department);
        }
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: 2016/6/1
            UserHarvest userHarvest = (UserHarvest) v.getTag();
           if (mOnItemClickListener != null){
               mOnItemClickListener.onItemClick(v,userHarvest);
           }
        }
    };

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , UserHarvest data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
