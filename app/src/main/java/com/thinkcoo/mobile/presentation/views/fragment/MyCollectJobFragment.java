package com.thinkcoo.mobile.presentation.views.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectJobResponse;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.MyCollectJobPresenter;
import com.thinkcoo.mobile.utils.GetJobUiUtils;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class MyCollectJobFragment extends BaseLcePagedFragment<MyCollectJobResponse> {


    @Inject
    MyCollectJobPresenter mMyCollectJobPresenter;
    @Inject
    GetJobUiUtils mGetJobUiUtils;


    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mMyCollectJobPresenter;
    }

    @Override
    protected LceAdapterViewBind<MyCollectJobResponse> provideLceAdapterViewBind() {
        return new LceAdapterViewBind<MyCollectJobResponse>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new InnerViewHolder(inflateView(parent));
            }
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, MyCollectJobResponse myCollectJobResponse) {
                ((InnerViewHolder)holder).bind(myCollectJobResponse);
            }
        };
    }

    private View inflateView(ViewGroup viewGroup) {
        return getActivity().getLayoutInflater().inflate(R.layout.item_my_collect_job, viewGroup, false);
    }

    private class InnerViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.findjob_sc_zw)
        TextView mFindjobScZw;
        @Bind(R.id.findjob_sc_xz)
        TextView mFindjobScXz;
        @Bind(R.id.findjob_sc_gs)
        TextView mFindjobScGs;
        @Bind(R.id.findjob_sc_dz)
        TextView mFindjobScDz;

        MyCollectJobResponse mMyCollectJobResponse;

        public InnerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoJobDetail(mMyCollectJobResponse);
                }
            });
        }

        public void bind(MyCollectJobResponse myCollectJobResponse) {

            mMyCollectJobResponse = myCollectJobResponse;
            mFindjobScZw.setText(myCollectJobResponse.getJobTitle());
            mFindjobScXz.setText(mGetJobUiUtils.getSalaryRange(myCollectJobResponse.getSalaryRange()));
            mFindjobScGs.setText(myCollectJobResponse.getCompanyName());
            mFindjobScDz.setText(mGetJobUiUtils.getFormateAddr(myCollectJobResponse.getSite()));
        }
    }

    private void gotoJobDetail(MyCollectJobResponse myCollectJobResponse) {

    }

    @Override
    protected void initInject() {

    }
}
