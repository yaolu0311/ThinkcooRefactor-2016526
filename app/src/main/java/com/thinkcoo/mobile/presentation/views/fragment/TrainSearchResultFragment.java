package com.thinkcoo.mobile.presentation.views.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.TrainCourse;
import com.thinkcoo.mobile.model.entity.TrainCourseFilter;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.TrainSearchResultPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.TrainSearchResultView;
import com.thinkcoo.mobile.presentation.views.activitys.TrainSearchActivity;

import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Leevin
 * CreateTime: 2016/8/19  16:30
 */
public class TrainSearchResultFragment extends BaseLcePagedFragment<TrainCourse> implements TrainSearchResultView<TrainCourse>{

    @Inject
    TrainSearchResultPresenter mTrainSearchResultPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mTrainSearchResultPresenter;
    }

    @Override
    protected LceAdapterViewBind<TrainCourse> provideLceAdapterViewBind() {
        return new LceAdapterViewBind<TrainCourse>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.item_list_train_course, parent, false);
                return new CourseViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, TrainCourse trainCourse) {
                ((CourseViewHolder) holder).bind(trainCourse);
            }
        };
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_train_course)
        TextView mTvTrainCourse;
        @Bind(R.id.tv_train_school)
        TextView mTvTrainSchool;
        @Bind(R.id.tv_train_courseType)
        TextView mTvTrainCourseType;
        @Bind(R.id.tv_train_coursePrice)
        TextView mTvTrainCoursePrice;

        public CourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(TrainCourse trainCourse) {
            mTvTrainCourse.setText(trainCourse.getCourseName());
            mTvTrainSchool.setText(trainCourse.getCourseName());
            mTvTrainCourseType.setText(trainCourse.getCourseType());
            mTvTrainCoursePrice.setText(trainCourse.getCoursePrice());
        }

    }

    @Override
    protected void initInject() {

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        super.loadData(pullToRefresh);
    }

    @Override
    public TrainCourseFilter getFilter() {
        return getHostActivity().getFilter();
    }

    public TrainSearchActivity getHostActivity() {
        return (TrainSearchActivity)getActivity();
    }
}
