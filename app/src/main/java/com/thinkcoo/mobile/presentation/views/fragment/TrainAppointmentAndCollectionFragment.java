package com.thinkcoo.mobile.presentation.views.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.TrainCourse;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.TrainMyCollectionPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Leevin
 * CreateTime: 2016/8/22  17:00
 */

public class TrainAppointmentAndCollectionFragment extends BaseLcePagedFragment<TrainCourse> {

    private static final String OPERATE_TYPE = "operate_type";// 删除收藏 type 2, 删除预约 type 为3
    OnItemClickListener mListener;
    @Inject
    TrainMyCollectionPresenter mTrainMyCollectionPresenter;

    public static TrainAppointmentAndCollectionFragment getInstance(int operateType) {
        TrainAppointmentAndCollectionFragment fragment = new TrainAppointmentAndCollectionFragment();
        Bundle args = new Bundle();
        args.putInt(OPERATE_TYPE,operateType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnItemClickListener){
            this.mListener = (OnItemClickListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement TrainAppointmentAndCollectionFragment.OnItemClickListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
           // initArgument();
        }
    }

    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mTrainMyCollectionPresenter;
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

    @Override
    protected void initInject() {

    }

    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            itemView.setOnClickListener(this);
        }

        public void bind(TrainCourse trainCourse) {
            mTvTrainCourse.setText(trainCourse.getCourseName());
            mTvTrainSchool.setText(trainCourse.getCourseName());
            mTvTrainCourseType.setText(trainCourse.getCourseType());
            mTvTrainCoursePrice.setText(trainCourse.getCoursePrice());
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick();
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick();
    }
}
