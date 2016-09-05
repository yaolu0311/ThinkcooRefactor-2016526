package com.thinkcoo.mobile.presentation.views.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.serverresponse.MyShieldCompanyResponse;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePagedPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.MyShieldCompanyPresenter;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class MyShieldCompanyFragment extends BaseLcePagedFragment<MyShieldCompanyResponse> {

    @Inject
    MyShieldCompanyPresenter mMyShieldCompanyPresenter;


    @Override
    protected BaseLcePagedPresenter provideBaseLcePagedPresenter() {
        return mMyShieldCompanyPresenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addHeadToReView();
    }

    private void addHeadToReView() {
        //TODO add head
    }

    @Override
    protected LceAdapterViewBind<MyShieldCompanyResponse> provideLceAdapterViewBind() {

        return new LceAdapterViewBind<MyShieldCompanyResponse>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new InnerViewHolder(inflateView(parent));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, MyShieldCompanyResponse myShieldCompanyResponse) {
                ((InnerViewHolder) holder).bind(myShieldCompanyResponse);
            }
        };
    }

    private View inflateView(ViewGroup parent) {
        return getActivity().getLayoutInflater().inflate(R.layout.item_my_shield_company, parent, false);
    }


    private class InnerViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.pingbi_company_text)
        TextView mPingbiCompanyText;

        MyShieldCompanyResponse mMyShieldCompanyResponse;

        public InnerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(MyShieldCompanyResponse myShieldCompanyResponse) {
            mMyShieldCompanyResponse = myShieldCompanyResponse;
            mPingbiCompanyText.setText(myShieldCompanyResponse.getCompanyName());
        }
    }

    @Override
    protected void initInject() {

    }
}
