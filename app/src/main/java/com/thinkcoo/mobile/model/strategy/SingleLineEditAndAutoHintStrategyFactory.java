package com.thinkcoo.mobile.model.strategy;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.view.View;

import com.thinkcoo.mobile.R;

/**
 * Created by Robert.yao on 2016/6/17.
 */
public class SingleLineEditAndAutoHintStrategyFactory {

    public static final int SINGLE_LINE_EDIT_TYPE_SCHOOL_SEARCH = 0X0002;
    public static final int SINGLE_LINE_EDIT_TYPE_SCHOOL_DEPARTMENT_SEARCH = 0X0003;
    public static final int SINGLE_LINE_EDIT_TYPE_SCHOOL_MAJOR_SEARCH = 0X0004;

    public static SingleLineEditAndAutoHintStrategy create(int type, SingleLineEditContent singleLineEditContent) {

        switch (type) {
            case SINGLE_LINE_EDIT_TYPE_SCHOOL_SEARCH:
                return new SchoolSingleLineEditStrategy(singleLineEditContent);
            case SINGLE_LINE_EDIT_TYPE_SCHOOL_DEPARTMENT_SEARCH:
                return new SchoolDepartmentSingleLineEditStrategy(singleLineEditContent);
            case SINGLE_LINE_EDIT_TYPE_SCHOOL_MAJOR_SEARCH:
                return new SchoolMajorSingleLineEditStrategy(singleLineEditContent);
            default:
                throw new IllegalArgumentException("unknown SingleLineEdit type");
        }
    }

    private static class SchoolSingleLineEditStrategy implements SingleLineEditAndAutoHintStrategy {

        SingleLineEditContent mSingleLineEditContent;

        public SchoolSingleLineEditStrategy(SingleLineEditContent singleLineEditContent) {
            mSingleLineEditContent = singleLineEditContent;
        }


        @Override
        public int getTitle() {
            return R.string.school_name;
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
            return R.string.input_school_name;
        }

        @Override
        public View createEditView(Context context) {
            return ((Activity) context).getLayoutInflater().inflate(R.layout.single_edit_text, null);
        }

        @Override
        public int getDataDictionaryType() {
            return DataDictionaryStrategyFactory.DD_TYPE_SCHOOL;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.mSingleLineEditContent, flags);
        }

        protected SchoolSingleLineEditStrategy(Parcel in) {
            this.mSingleLineEditContent = in.readParcelable(SingleLineEditContent.class.getClassLoader());
        }

        public static final Creator<SchoolSingleLineEditStrategy> CREATOR = new Creator<SingleLineEditAndAutoHintStrategyFactory.SchoolSingleLineEditStrategy>() {
            @Override
            public SingleLineEditAndAutoHintStrategyFactory.SchoolSingleLineEditStrategy createFromParcel(Parcel source) {
                return new SingleLineEditAndAutoHintStrategyFactory.SchoolSingleLineEditStrategy(source);
            }

            @Override
            public SingleLineEditAndAutoHintStrategyFactory.SchoolSingleLineEditStrategy[] newArray(int size) {
                return new SingleLineEditAndAutoHintStrategyFactory.SchoolSingleLineEditStrategy[size];
            }
        };
    }

    private static class SchoolDepartmentSingleLineEditStrategy implements SingleLineEditAndAutoHintStrategy {

        SingleLineEditContent mSingleLineEditContent;

        public SchoolDepartmentSingleLineEditStrategy(SingleLineEditContent singleLineEditContent) {
            mSingleLineEditContent = singleLineEditContent;
        }

        @Override
        public int getTitle() {
            return R.string.school_department_name;
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
            return R.string.input_school_department;
        }

        @Override
        public View createEditView(Context context) {
            return ((Activity) context).getLayoutInflater().inflate(R.layout.single_edit_text, null);
        }

        @Override
        public int getDataDictionaryType() {
            return  DataDictionaryStrategyFactory.DD_TYPE_SCHOOL_DEPARTMENT;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.mSingleLineEditContent, flags);
        }

        protected SchoolDepartmentSingleLineEditStrategy(Parcel in) {
            this.mSingleLineEditContent = in.readParcelable(SingleLineEditContent.class.getClassLoader());
        }

        public static final Creator<SchoolDepartmentSingleLineEditStrategy> CREATOR = new Creator<SingleLineEditAndAutoHintStrategyFactory.SchoolDepartmentSingleLineEditStrategy>() {
            @Override
            public SingleLineEditAndAutoHintStrategyFactory.SchoolDepartmentSingleLineEditStrategy createFromParcel(Parcel source) {
                return new SingleLineEditAndAutoHintStrategyFactory.SchoolDepartmentSingleLineEditStrategy(source);
            }

            @Override
            public SingleLineEditAndAutoHintStrategyFactory.SchoolDepartmentSingleLineEditStrategy[] newArray(int size) {
                return new SingleLineEditAndAutoHintStrategyFactory.SchoolDepartmentSingleLineEditStrategy[size];
            }
        };
    }

    private static class SchoolMajorSingleLineEditStrategy implements SingleLineEditAndAutoHintStrategy {

        SingleLineEditContent mSingleLineEditContent;

        public SchoolMajorSingleLineEditStrategy(SingleLineEditContent singleLineEditContent) {
            mSingleLineEditContent = singleLineEditContent;
        }

        @Override
        public int getTitle() {
            return R.string.school_major_name;
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
            return R.string.input_school_major;
        }

        @Override
        public View createEditView(Context context) {
            return ((Activity) context).getLayoutInflater().inflate(R.layout.single_edit_text, null);
        }

        @Override
        public int getDataDictionaryType() {
            return DataDictionaryStrategyFactory.DD_TYPE_SCHOOL_MAJOR;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.mSingleLineEditContent, flags);
        }

        protected SchoolMajorSingleLineEditStrategy(Parcel in) {
            this.mSingleLineEditContent = in.readParcelable(SingleLineEditContent.class.getClassLoader());
        }

        public static final Creator<SchoolMajorSingleLineEditStrategy> CREATOR = new Creator<SingleLineEditAndAutoHintStrategyFactory.SchoolMajorSingleLineEditStrategy>() {
            @Override
            public SingleLineEditAndAutoHintStrategyFactory.SchoolMajorSingleLineEditStrategy createFromParcel(Parcel source) {
                return new SingleLineEditAndAutoHintStrategyFactory.SchoolMajorSingleLineEditStrategy(source);
            }

            @Override
            public SingleLineEditAndAutoHintStrategyFactory.SchoolMajorSingleLineEditStrategy[] newArray(int size) {
                return new SingleLineEditAndAutoHintStrategyFactory.SchoolMajorSingleLineEditStrategy[size];
            }
        };
    }


}
