package com.thinkcoo.mobile.presentation.views.adapter.Schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.serverresponse.RockSingleResByUuidResponse;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author ：ml on 2016/8/23
 */
public class RockStudentResultAdapter extends RecyclerView.Adapter<RockStudentResultAdapter.StudentResultViewHolder> implements View.OnClickListener{

    private Context mContext;
    private List<RockSingleResByUuidResponse.ListBean> mList;
    private OnCheckBoxClickListener mListener;

    private RockSingleResByUuidResponse.ListBean mListBean;

    private CheckBox currentCheckBox;

    public RockStudentResultAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
    }

    public void refreshData(List<RockSingleResByUuidResponse.ListBean> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public StudentResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_sc_rockcall_listview_item, null);
        StudentResultViewHolder holder = new StudentResultViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(StudentResultViewHolder holder, int position) {
        mListBean = mList.get(position);
        holder.bind(mListBean);
        holder.mFirstTimeRock.setOnClickListener(this);
        holder.mSecondTimeRock.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.first_time_rock:
                mListener.onClick(mListBean, true);
                break;
            case R.id.second_time_rock:
                mListener.onClick(mListBean, false);
                break;
        }
        ViewGroup group = (ViewGroup) v;
        currentCheckBox = (CheckBox) group.getChildAt(0);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void toggleStatus() {
        if (currentCheckBox == null) {
            return;
        }
        currentCheckBox.setChecked(!currentCheckBox.isChecked());
    }

    static class StudentResultViewHolder extends RecyclerView.ViewHolder {

        private static final String UN_ARRIVAL = "0";
        private static final String ARRIVAL = "1";

        @Bind(R.id.tv_student_name)
        TextView mStudentName;
        @Bind(R.id.tv_student_number)
        TextView mStudentNumber;
        @Bind(R.id.cb_first)
        CheckBox mCbFirst;
        @Bind(R.id.cb_second)
        CheckBox mCbSecond;
        @Bind(R.id.tv_right_color)
        TextView mTvRightColor;
        @Bind(R.id.second_time_rock)
        FrameLayout mSecondTimeRock;
        @Bind(R.id.first_time_rock)
        FrameLayout mFirstTimeRock;

        public StudentResultViewHolder(View inflateItemStudent) {
            super(inflateItemStudent);
            ButterKnife.bind(this, inflateItemStudent);
        }

        public void bind(RockSingleResByUuidResponse.ListBean bean) {
            if (bean == null) {
                return;
            }
            mStudentName.setText(bean.getStudentName());
            mStudentNumber.setText(bean.getStudentNo());
            String status = bean.getStatus();
            boolean flag = ARRIVAL.equals(status);
            mCbFirst.setChecked(flag);
            mSecondTimeRock.setVisibility(View.GONE);
            setStatusColor(flag);
        }

        private void setStatusColor(boolean flag) {
            if (flag) {
                mTvRightColor.setBackgroundResource(R.color.color_arrival);
            } else {
                mTvRightColor.setBackgroundResource(R.color.color_un_arrival);
            }
        }
    }

    public interface OnCheckBoxClickListener{
        void onClick(RockSingleResByUuidResponse.ListBean bean, boolean isFirst);
    }

    public void setListener(OnCheckBoxClickListener listener) {
        mListener = listener;
    }
}
