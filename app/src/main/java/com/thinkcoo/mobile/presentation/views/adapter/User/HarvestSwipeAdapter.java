package com.thinkcoo.mobile.presentation.views.adapter.User;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.SwipeableUltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.UserHarvest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Leevin
 * CreateTime: 2016/6/3  13:37
 */
public class HarvestSwipeAdapter extends RecyclerView.Adapter<HarvestSwipeAdapter.SVHolder> {

    LayoutInflater inflater;
    public List<UserHarvest> mUserHarvestList;
    public HarvestSwipeAdapter(Context context, List<UserHarvest> list) {
        inflater = LayoutInflater.from(context);
        mUserHarvestList =list;
    }

    @Override
    public SVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_swipe_harvest, parent, false);
        return new SVHolder(view);
    }

    @Override
    public void onBindViewHolder(SVHolder holder, int position) {
        holder.bindData(mUserHarvestList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserHarvestList.size();
    }

    public static class SVHolder extends RecyclerView.ViewHolder  {
        Button mBtnDelete;
        TextView mTvHarvestName;
        TextView mTvHarvestDepartment;
        public SVHolder(View itemView) {
            super(itemView);
            mBtnDelete = (Button) itemView.findViewById(R.id.btn_delete);
            mTvHarvestName= (TextView) itemView.findViewById(R.id.tv_harvest_name);
            mTvHarvestDepartment  = (TextView) itemView.findViewById(R.id.tv_harvest_department);
        }
        public void bindData(UserHarvest userHarvest) {
            mTvHarvestName.setText(userHarvest.getGrantName());
            mTvHarvestDepartment.setText(userHarvest.getGrantDepartment());
        }
    }

    public void refresh(List<UserHarvest> list){
        mUserHarvestList = list;
        notifyDataSetChanged();
    }



}

