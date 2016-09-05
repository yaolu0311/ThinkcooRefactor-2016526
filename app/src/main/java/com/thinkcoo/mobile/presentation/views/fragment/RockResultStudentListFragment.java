package com.thinkcoo.mobile.presentation.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.publicmodule.ui.listener.RecyclerTouchListener;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.model.entity.serverresponse.RockResultByUuidResponse;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.RockResultStudentListPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.RockResultStudentListView;
import com.thinkcoo.mobile.presentation.views.activitys.RockCallResultActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author ：ml on 2016/8/12
 */
public class RockResultStudentListFragment extends BaseLcePagedFragment<RockResultByUuidResponse.ListBean> implements RockResultStudentListView<RockResultByUuidResponse.ListBean> {

    private static final String TAG = "RockResultStudentListFragment";
    @Inject
    RockResultStudentListPresenter mRockResultStudentListPresenter;

    private RecyclerTouchListener mOnTouchListener;
    private RockResultByUuidResponse.ListBean mListBean;



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       setupLeftSlip();
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.addOnItemTouchListener(mOnTouchListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mRecyclerView.removeOnItemTouchListener(mOnTouchListener);
    }

    private void setupLeftSlip() {
        //mRecyclerView; //TODO 这里可以拿到类容区域的recyclerView，所以在这里配置左滑动
        mOnTouchListener = new RecyclerTouchListener(getActivity(), mRecyclerView);
        mOnTouchListener.setIndependentViews(R.id.tv_studentid)
                .setViewsToFade(R.id.tv_studentid)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {

            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {

            }
        }).setSwipeOptionViews(R.id.btn_delete)
                .setSwipeable(R.id.rl_out, R.id.ll_in, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
            @Override
            public void onSwipeOptionClicked(int viewID, int position) {
                if (viewID==position) {
                    // TODO: 2016/8/15   执行case   更换删除的图片
              //      String eventId = ((RockCallResultActivity) getActivity()).getEventId();
               //     String eventRosterId = mListBean.getEventRosterId();
                    ThinkcooLog.d(TAG,""+position+"viewID");
                    // 修改点名记录
                    mRockResultStudentListPresenter.modifyRockCallResult(((RockCallResultActivity) getActivity()).getEventId(),mListBean.getEventRosterId());

                }

            }
        });
    }

    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mRockResultStudentListPresenter;
    }

    public void refresh(){
        loadData(false);
        ThinkcooLog.d(TAG,"=== RockResultStudentListFragment Refreshed ===");
    }

    @Override
    protected LceAdapterViewBind<RockResultByUuidResponse.ListBean> provideLceAdapterViewBind() {
        return new LceAdapterViewBind<RockResultByUuidResponse.ListBean>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new InnerViewHolder(inflateItemView(parent));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, RockResultByUuidResponse.ListBean listBean) {
                ((InnerViewHolder) holder).bind(listBean);
            }
        };
    }

    private View inflateItemView(ViewGroup parent) {
        return getActivity().getLayoutInflater().inflate(R.layout.item_sc_rockcallresult, parent, false);
    }

    @Override
    protected void initInject() {

        DaggerScheduleComponent.builder()
                .applicationComponent(getFragmentInjectHelper().getApplicationComponent())
                .scheduleModule(new ScheduleModule())
                .build()
                .inject(this);
    }

    @Override
    public String getGroupId() {
        return getHostActivity().getGroupId();
    }

    private RockCallResultActivity getHostActivity() {
        return (RockCallResultActivity) getActivity();
    }

    @Override
    public String getuuid() {
        return getHostActivity().getuuid();
    }

    public class InnerViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.btn_delete)
        public Button btnDelete;
        @Bind(R.id.ll_in)
        public LinearLayout llIn;
        @Bind(R.id.iv_usericon)
        public ImageView ivUsericon;
        @Bind(R.id.tv_useridname)
        public TextView tvUseridname;
        @Bind(R.id.tv_classname)
        public TextView tvClassname;
        @Bind(R.id.tv_studentid)
        public TextView tvStudentid;
        @Bind(R.id.rl_out)
        public RelativeLayout rlOut;

        public InnerViewHolder(View rootView) {
            super(rootView);
            ButterKnife.bind(this, rootView);
        }

        public void bind(RockResultByUuidResponse.ListBean listBean) {

            mListBean = listBean;
            if (listBean.hasheadPortrait()){
                Glide.with(getContext()).load(listBean.getHeadPortrait()).into(ivUsericon);
            }else {
                displayNameImage(ivUsericon,listBean.getStudentName());
            }
            tvUseridname.setText(listBean.getStudentName());
            tvClassname.setText(listBean.getGroupName());
            tvStudentid.setText(listBean.getStudentNo());
        }
    }

    private void displayNameImage(ImageView ivUsericon, String studentName) {
        //TODO
    }

    @Override
    protected boolean isSearchMode() {
        return true;
    }
}
