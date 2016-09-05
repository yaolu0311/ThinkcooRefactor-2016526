package com.thinkcoo.mobile.model.strategy;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.view.View;

import com.thinkcoo.mobile.R;

/**
 * Created by Administrator on 2016/6/15.
 */
public class SingleLineEditStrategyFactory {

    public static final int SINGLE_LINE_EDIT_TYPE_JOB_RESPONSIBILITY = 0X0001;

    public static SingleLineEditStrategy create(int type, SingleLineEditContent singleLineEditContent) {

        switch (type) {
            case SINGLE_LINE_EDIT_TYPE_JOB_RESPONSIBILITY:
                return new JobResponsibilitySingleLineEditStrategy(singleLineEditContent);
            default:
                throw new IllegalArgumentException("unknown SingleLineEdit type");
        }
    }

    private static class JobResponsibilitySingleLineEditStrategy implements SingleLineEditStrategy {

        SingleLineEditContent mSingleLineEditContent;

        public JobResponsibilitySingleLineEditStrategy(SingleLineEditContent singleLineEditContent) {
            mSingleLineEditContent = singleLineEditContent;
        }

        @Override
        public int getTitle() {
            return R.string.job_responsibility;
        }

        @Override
        public int getOtherInfo() {
            return R.string.complete;
        }

        @Override
        public SingleLineEditContent getContent() {
            return mSingleLineEditContent;
        }

        @Override
        public int getHint() {
            return R.string.personal_job_description;
        }

        @Override
        public View createEditView(Context context) {
            return ((Activity) context).getLayoutInflater().inflate(R.layout.edittext_full, null);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.mSingleLineEditContent, flags);
        }

        protected JobResponsibilitySingleLineEditStrategy(Parcel in) {
            this.mSingleLineEditContent = in.readParcelable(SingleLineEditContent.class.getClassLoader());
        }

        public static final Creator<JobResponsibilitySingleLineEditStrategy> CREATOR = new Creator<SingleLineEditStrategyFactory.JobResponsibilitySingleLineEditStrategy>() {
            @Override
            public SingleLineEditStrategyFactory.JobResponsibilitySingleLineEditStrategy createFromParcel(Parcel source) {
                return new SingleLineEditStrategyFactory.JobResponsibilitySingleLineEditStrategy(source);
            }

            @Override
            public SingleLineEditStrategyFactory.JobResponsibilitySingleLineEditStrategy[] newArray(int size) {
                return new SingleLineEditStrategyFactory.JobResponsibilitySingleLineEditStrategy[size];
            }
        };
    }

}
