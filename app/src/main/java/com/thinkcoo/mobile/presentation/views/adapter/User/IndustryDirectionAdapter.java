package com.thinkcoo.mobile.presentation.views.adapter.User;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.IndustryItemEntity;
import com.thinkcoo.mobile.presentation.views.activitys.IndustryDirectionActivity;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/16.
 */
public class IndustryDirectionAdapter extends RecyclerView.Adapter<IndustryDirectionAdapter.IndustryViewHold>
        implements StickyRecyclerHeadersAdapter<IndustryDirectionAdapter.IndustryTeamViewHold> {

    private Context mContext;
    private List<IndustryItemEntity> mIndustryItemEntities;
    private List<IndustryItemEntity> mIndustryTeamEntities;
    private List<IndustryItemEntity> mSelectedList;

    public IndustryDirectionAdapter(Context context, List<IndustryItemEntity> dictionaryList, List<IndustryItemEntity> mIndustryTeamEntities) {
        mContext = context;
        this.mIndustryItemEntities = dictionaryList;
        this.mIndustryTeamEntities = mIndustryTeamEntities;
        mSelectedList = new ArrayList<>();
    }

    public void setIndustryItemEntities(List<IndustryItemEntity> industryItemEntities) {
        mIndustryItemEntities = industryItemEntities;
    }

    public void setIndustryTeamEntities(List<IndustryItemEntity> industryTeamEntities) {
        mIndustryTeamEntities = industryTeamEntities;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public IndustryViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_industry_recycler_view, parent, false);
        return new IndustryViewHold(v);
    }

    @Override
    public void onBindViewHolder(IndustryViewHold holder, final int position) {
        final IndustryItemEntity itemEntity = mIndustryItemEntities.get(position);
        holder.mTvIndustryName.setText(itemEntity.getDisplayName());
        if (itemEntity.isChecked()) {
            addOrRemoveSelectedEntity(itemEntity, true);
            holder.mIvIndustryChecked.setVisibility(View.VISIBLE);
        } else {
            addOrRemoveSelectedEntity(itemEntity, false);
            holder.mIvIndustryChecked.setVisibility(View.GONE);
        }

        addClickListener(holder, position, itemEntity);
    }

    private void addOrRemoveSelectedEntity(IndustryItemEntity itemEntity, boolean isAdd) {
        if (isAdd && !mSelectedList.contains(itemEntity)) {
            mSelectedList.add(itemEntity);
        }
        if (!isAdd && mSelectedList.contains(itemEntity)) {
            mSelectedList.remove(itemEntity);
        }
    }

    private synchronized void addClickListener(IndustryViewHold holder, final int position, final IndustryItemEntity itemEntity) {
        holder.mItemIndustryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean currentFlag = itemEntity.isChecked();
                if (currentFlag) {
                    addOrRemoveSelectedEntity(itemEntity, false);
                } else {
                    if (checkSelectedSizeOverMax(IndustryDirectionActivity.MAX_SELECTED_COUNT)) {
                        return;
                    }
                    addOrRemoveSelectedEntity(itemEntity, true);
                }
                refreshData(currentFlag, itemEntity, position);
            }
        });
    }


    private void refreshData(boolean currentFlag, IndustryItemEntity itemEntity, int position) {
        itemEntity.setChecked(!currentFlag);
        mIndustryItemEntities.remove(position);
        mIndustryItemEntities.add(position, itemEntity);
        notifyDataSetChanged();
    }

    private boolean checkSelectedSizeOverMax(int max) {
        if (mSelectedList.size() >= max) {
            ((IndustryDirectionActivity) mContext).showToast(mContext.getString(R.string.max_select_count_three));
            return true;
        }
        return false;
    }

    @Override
    public long getHeaderId(int position) {
        return getTeamCode(mIndustryItemEntities.get(position));
    }

    @Override
    public IndustryDirectionAdapter.IndustryTeamViewHold onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_view, parent, false);
        return new IndustryTeamViewHold(view);
    }

    @Override
    public void onBindHeaderViewHolder(IndustryDirectionAdapter.IndustryTeamViewHold holder, int position) {
        String str = getTeamName((int) getHeaderId(position));
        holder.mTvTeamName.setText(str);
    }

    @Override
    public int getItemCount() {
        return mIndustryItemEntities.size();
    }

    private int getTeamCode(IndustryItemEntity itemEntity) {
        if (null == itemEntity) {
            return 0;
        }
        String code = itemEntity.getCode();
        if (TextUtils.isEmpty(code)) {
            return 0;
        }

        if (code.length() < 3) {
            return Integer.valueOf(code);
        }
        return Integer.valueOf(code.substring(0, 2));
    }

    private String getTeamName(int code) {
        for (IndustryItemEntity itemEntity : mIndustryTeamEntities) {
            if (code == Integer.valueOf(itemEntity.getCode())) {
                return itemEntity.getDisplayName();
            }
        }
        return "";
    }

    public List<IndustryItemEntity> getSelectedList() {
        return mSelectedList;
    }

    public class IndustryViewHold extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_industry_name)
        TextView mTvIndustryName;
        @Bind(R.id.iv_industry_checked)
        ImageView mIvIndustryChecked;
        @Bind(R.id.item_industry_layout)
        LinearLayout mItemIndustryLayout;

        public IndustryViewHold(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class IndustryTeamViewHold extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_team_name)
        TextView mTvTeamName;

        public IndustryTeamViewHold(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
