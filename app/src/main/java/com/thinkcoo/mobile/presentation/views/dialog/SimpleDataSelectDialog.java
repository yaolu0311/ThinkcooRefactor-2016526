package com.thinkcoo.mobile.presentation.views.dialog;

import android.content.Context;
import com.bigkoo.pickerview.OptionsPickerView;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class SimpleDataSelectDialog implements OptionsPickerView.OnOptionsSelectListener{

    public static final int DATA_TYPE_JOB_TYPE = 0x0001;
    private static final int DATA_TYPE_JOB_RELEASE_TIME = 0x0002;

    private OptionsPickerView mOptionsPickerView;
    private OnSimpleDataSelectedListener mOnJobTypeSelectedListener;
    private ArrayList<String> mSimpleData;

    private void callBack(String data){
        if (null != mOnJobTypeSelectedListener){
            mOnJobTypeSelectedListener.onSimpleDataSelected(data);
        }
    }

    public interface OnSimpleDataSelectedListener{
        void onSimpleDataSelected(String jobType);
    }

    @Inject
    public SimpleDataSelectDialog() {

    }

    public void showJobType(Context context ,OnSimpleDataSelectedListener onJobTypeSelectedListener){
       show(context,"工作类型",getJobTypeDataList(),onJobTypeSelectedListener);
    }

    public void showJobReleaseTime(Context context ,OnSimpleDataSelectedListener onJobTypeSelectedListener){
       show(context,"发布日期",getJobReleaseTimeDataList(),onJobTypeSelectedListener);
    }

    private void show(Context context ,String title ,  ArrayList<String> data , OnSimpleDataSelectedListener onSimpleDataSelectedListener){
        mOnJobTypeSelectedListener = onSimpleDataSelectedListener;
        mSimpleData = data;
        initOptionsPickerViewIfNeed(context);
        mOptionsPickerView.setPicker(data);
        mOptionsPickerView.setTitle(title);
        if (mOptionsPickerView.isShowing()){
            mOptionsPickerView.dismiss();
        }
        mOptionsPickerView.setCyclic(false);
        mOptionsPickerView.show();
    }

    private ArrayList getDataByType(int dataType) {
        if (DATA_TYPE_JOB_TYPE == dataType){
            return getJobTypeDataList();
        }else if (DATA_TYPE_JOB_RELEASE_TIME == dataType){
            return getJobReleaseTimeDataList();
        }
        return new ArrayList(0);
    }

    private void initOptionsPickerViewIfNeed(Context context) {
        if (null == mOptionsPickerView){
            mOptionsPickerView = new OptionsPickerView(context);
            mOptionsPickerView.setOnoptionsSelectListener(this);
        }
    }

    private ArrayList<String> getJobTypeDataList() {

        ArrayList<String> dataList = new ArrayList<>();

        dataList.add("全职");
        dataList.add("兼职");
        dataList.add("实习");

        return dataList;
    }

    private ArrayList<String> getJobReleaseTimeDataList() {

        ArrayList<String> dataList = new ArrayList<>();

        dataList.add("不限");
        dataList.add("最近三天");
        dataList.add("最近一周");
        dataList.add("最近两周");
        dataList.add("最近一月");
        dataList.add("最近两月");

        return dataList;
    }

    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {
        callBack(mSimpleData.get(options1));
    }
}
