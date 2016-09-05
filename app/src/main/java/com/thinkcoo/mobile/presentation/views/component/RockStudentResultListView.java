package com.thinkcoo.mobile.presentation.views.component;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.serverresponse.RockSingleResByUuidResponse;
import com.thinkcoo.mobile.presentation.views.activitys.RockCallSingleResultActivity;
import com.thinkcoo.mobile.presentation.views.adapter.Schedule.RockStudentResultAdapter;
import com.thinkcoo.mobile.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author ：ml on 2016/8/23
 */
public class RockStudentResultListView extends LinearLayout implements RockStudentResultAdapter.OnCheckBoxClickListener{

    @Bind(R.id.tv_arrival)
    TextView mTvArrival;
    @Bind(R.id.tv_un_arrival)
    TextView mTvUnArrival;
    @Bind(R.id.tv_late)
    TextView mTvLate;
    @Bind(R.id.tv_leave_early)
    TextView mTvLeaveEarly;
    @Bind(R.id.tv_rockcall_firsttime)
    TextView mRockFirsttime;
    @Bind(R.id.tv_rockcall_secondtime)
    TextView mRockSecondtime;

    private LinearLayout titleLayout;
    private RecyclerView mRecyclerView;
    private RockStudentResultAdapter mAdapter;
    private RockSingleResByUuidResponse mResponse;
    private List<RockSingleResByUuidResponse.ListBean> mList;

    private String eventId;

    public RockStudentResultListView(Context context) {
        this(context, null);
    }

    public RockStudentResultListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addContentView();
        initViewAndEvent();
        ButterKnife.bind(this);
    }

    private void addContentView() {
        titleLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_sc_title_rockcallrecord, null);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(titleLayout, layoutParams);

        //add recyclerView
        mRecyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.single_recyclerview, null);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mRecyclerView, params);
    }


    private void initViewAndEvent() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mAdapter = new RockStudentResultAdapter(getContext());
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void refreshData(RockSingleResByUuidResponse response) {
        if (response == null) {
            return;
        }
        mResponse = response;
        setTopLayoutData();
        setRecyclerViewData();
    }

    private void setTopLayoutData() {
        mTvArrival.setText(DateUtils.setTextForeColor(getContext().getResources().getString(R.string.rock_arrival, mResponse.getNormalCnt())));
        mTvUnArrival.setText(DateUtils.setTextForeColor(getContext().getResources().getString(R.string.rock_un_arrival, mResponse.getAbsenceCnt())));
        mTvLate.setVisibility(GONE);
        mTvLeaveEarly.setVisibility(GONE);

        String time  = getContext().getResources().getString(R.string.rock_first_time, mResponse.getBeginDate());
        if (TextUtils.isEmpty(time) || "null".equals(time)) {
            time = "";
        }
        mRockFirsttime.setText(time);
        mRockSecondtime.setVisibility(GONE);
    }

    private void setRecyclerViewData() {
        if (mResponse == null) {
            return;
        }
        List<RockSingleResByUuidResponse.ListBean> list = mResponse.getList();
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.refreshData(mList);
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public void onClick(RockSingleResByUuidResponse.ListBean bean, boolean isFirst) {
        if (getContext() instanceof RockCallSingleResultActivity) {
            ((RockCallSingleResultActivity)getContext()).getRockCallSingleResultPresenter().modifyRockCallResult(eventId, bean.getEventRosterId());
        }
    }

    public void toggleStatus() {
        mAdapter.toggleStatus();
    }
}
