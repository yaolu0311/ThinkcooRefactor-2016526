package com.thinkcoo.mobile.presentation.views.adapter.User;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.SwipeableUltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.UserWorkExperienceEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/20.
 */
public class WorkExperiencesAdapter extends SwipeableUltimateViewAdapter<UserWorkExperienceEntity> {

    private ExperienceCallBackListener mExperienceCallBackListener;

    public WorkExperiencesAdapter(List<UserWorkExperienceEntity> list) {
        super(list);
    }

    @Override
    protected int getNormalLayoutResId() {
        return ExperienceViewHold.layout;
    }

    @Override
    protected UltimateRecyclerviewViewHolder newViewHolder(View view) {
        ExperienceViewHold viewHold = new ExperienceViewHold(view, true);
        return viewHold;
    }

    @Override
    protected void withBindHolder(UltimateRecyclerviewViewHolder holder, final UserWorkExperienceEntity data, final int position) {
        ((ExperienceViewHold)holder).mTvExperienceContent.setText(data.getContent().toString().trim());
        ((ExperienceViewHold)holder).mTvExperienceTime.setText(data.getTime().toString().trim());
        ((ExperienceViewHold)holder).mLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mExperienceCallBackListener) {
                    return;
                }
                mExperienceCallBackListener.onItemClickListener(data, position);
            }
        });

        ((ExperienceViewHold)holder).mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mExperienceCallBackListener) {
                    return;
                }
                mExperienceCallBackListener.onDeleteItemListener(data, position);
            }
        });

        super.withBindHolder(holder, data, position);
    }

    public final static class ExperienceViewHold extends UltimateRecyclerviewViewHolder {
        public static final int layout = R.layout.item_work_experiences;
        @Bind(R.id.btn_delete)
        Button mBtnDelete;
        @Bind(R.id.tv_experience_content)
        TextView mTvExperienceContent;
        @Bind(R.id.tv_experience_time)
        TextView mTvExperienceTime;
        @Bind(R.id.layout_content)
        RelativeLayout mLayoutContent;
        @Bind(R.id.recyclerview_swipe)
        SwipeLayout mRecycleViewSwipe;

        public ExperienceViewHold(View itemView, boolean bind) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (bind) {
                settingSwipeLayout();
            }
        }

        private void settingSwipeLayout() {
            mRecycleViewSwipe.setDragEdge(SwipeLayout.DragEdge.Right);
            mRecycleViewSwipe.setShowMode(SwipeLayout.ShowMode.PullOut);
        }
    }

    public interface ExperienceCallBackListener<T>{
        void onDeleteItemListener(T t, int position);
        void onItemClickListener(T t, int position);
    }

    public void setExperienceCallBackListener(ExperienceCallBackListener experienceCallBackListener) {
        mExperienceCallBackListener = experienceCallBackListener;
    }
}
