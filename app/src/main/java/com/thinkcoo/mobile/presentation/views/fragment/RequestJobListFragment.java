package com.thinkcoo.mobile.presentation.views.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.serverresponse.QueryRequestJobResponse;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.RequestJobListPresenter;
import com.thinkcoo.mobile.utils.GetJobUiUtils;
import com.thinkcoo.mobile.utils.PublicUIUtil;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class RequestJobListFragment extends BaseLcePagedFragment<QueryRequestJobResponse> {


    @Inject
    RequestJobListPresenter mRequestJobListPresenter;
    @Inject
    GetJobUiUtils mGetJobUiUtils;
    @Inject
    PublicUIUtil mPublicUIUtil;



    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mRequestJobListPresenter;
    }

    @Override
    protected LceAdapterViewBind<QueryRequestJobResponse> provideLceAdapterViewBind() {
        return new LceAdapterViewBind<QueryRequestJobResponse>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new InnerViewHolder(inflateView(parent));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, QueryRequestJobResponse queryRequestJobResponse) {
                ((InnerViewHolder) holder).bind(queryRequestJobResponse);
            }
        };
    }

    private View inflateView(ViewGroup parent) {
        return getActivity().getLayoutInflater().inflate(R.layout.item_request_job, parent, false);
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


        private QueryRequestJobResponse mQueryRequestJobResponse;

        public InnerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoRequestJobDetail(mQueryRequestJobResponse);
                }
            });
        }

        public void bind(QueryRequestJobResponse queryRequestJobResponse) {

            mQueryRequestJobResponse = queryRequestJobResponse;
            mFindjobSqZw.setText(mQueryRequestJobResponse.getJobTitle());
            mFindjobSqXz.setText(mGetJobUiUtils.getSalaryRange(mQueryRequestJobResponse.getSalaryRange()));
            mFindjobSqGs.setText(mQueryRequestJobResponse.getCompanyName());
            mFindjobSqDz.setText(mGetJobUiUtils.getFormateAddr(mQueryRequestJobResponse.getSite()));
            mFindjobSqSj.setText(mPublicUIUtil.getGoodsFormatTime(mQueryRequestJobResponse.getCreateTime()));
        }
    }

    private void gotoRequestJobDetail(QueryRequestJobResponse queryRequestJobResponse) {

    }

    @Override
    protected void initInject() {

    }
}
