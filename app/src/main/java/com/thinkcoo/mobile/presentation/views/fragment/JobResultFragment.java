package com.thinkcoo.mobile.presentation.views.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerGetJobComponent;
import com.thinkcoo.mobile.injector.modules.GetJobModule;
import com.thinkcoo.mobile.model.entity.serverresponse.FindJobResponse;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.JobResultPresenter;
import com.thinkcoo.mobile.utils.GetJobUiUtils;
import com.thinkcoo.mobile.utils.PublicUIUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class JobResultFragment extends BaseLcePagedFragment<FindJobResponse> {

    @Inject
    JobResultPresenter mJobResultPresenter;
    @Inject
    GetJobUiUtils mGetJobUiUtils;
    @Inject
    PublicUIUtil mPublicUIUtil;

    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mJobResultPresenter;
    }

    @Override
    protected LceAdapterViewBind<FindJobResponse> provideLceAdapterViewBind() {

        return new LceAdapterViewBind<FindJobResponse>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new InnerViewHolder(inflateView(parent));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, FindJobResponse findJobResponse) {
                ((InnerViewHolder) holder).bind(findJobResponse);
            }
        };
    }

    private View inflateView(ViewGroup viewGroup) {
        return getActivity().getLayoutInflater().inflate(R.layout.item_job_result, viewGroup, false);
    }


    private class InnerViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.findjob_sq_zw)
        TextView mFindjobSqZw;
        @Bind(R.id.findjob_sq_xz)
        TextView mFindjobSqXz;
        @Bind(R.id.findjob_sq_gs)
        TextView mFindjobSqGs;
        @Bind(R.id.findjob_sq_dz)
        TextView mFindjobSqDz;
        @Bind(R.id.findjob_sq_sj)
        TextView mFindjobSqSj;
        @Bind(R.id.findjob_sq_jt)
        ImageView mFindjobSqJt;

        private FindJobResponse mFindJobListResponse;

        public InnerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoDetail(mFindJobListResponse);
                }
            });
        }

        public void bind(FindJobResponse findJobResponse) {

            mFindJobListResponse = findJobResponse;
            mFindjobSqZw.setText(findJobResponse.getJobTitle());
            mFindjobSqXz.setText(mGetJobUiUtils.getSalaryRange(findJobResponse.getSalaryRange()));
            mFindjobSqGs.setText(findJobResponse.getCompanyName());
            mFindjobSqDz.setText(mGetJobUiUtils.getFormateAddr(findJobResponse.getSite()));
            mFindjobSqSj.setText(mPublicUIUtil.getGoodsFormatTime(findJobResponse.getCreateTime()));

        }
    }

    private void gotoDetail(FindJobResponse findJobListResponse) {

    }



    @Override
    protected void initInject() {
        DaggerGetJobComponent.builder().getJobModule(new GetJobModule()).activityModule(getFragmentInjectHelper().getActivityModule()).applicationComponent(getFragmentInjectHelper().getApplicationComponent()).build().inject(this);
    }
}
