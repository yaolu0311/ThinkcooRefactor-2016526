package com.thinkcoo.mobile.presentation.views.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.serverresponse.MyShieldCompanyResponse;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePresenter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Robert.yao on 2016/8/23.
 */
public class ShieldCompanySearchResultFragment extends BaseLceFragment<MyShieldCompanyResponse> {



    @Override
    protected LceAdapterViewBind<MyShieldCompanyResponse> provideLceAdapterViewBind() {
        return null;
    }

    @Override
    protected LceNewDataBind<MyShieldCompanyResponse> provideLceNewViewBind() {

        return new LceNewDataBind<MyShieldCompanyResponse>() {

            @Bind(R.id.shield_company)
            TextView mShieldCompany;

            @Override
            public View inflateItemView(ViewGroup viewGroup, int viewType) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.item_shield_company, viewGroup, false);
                ButterKnife.bind(this, view);
                return view;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public void bind(MyShieldCompanyResponse data, int position, int viewType) {

            }
        };
    }

    @Override
    protected BaseLcePresenter providePresenter() {
        return null;
    }

    @Override
    protected void initInject() {

    }
}
