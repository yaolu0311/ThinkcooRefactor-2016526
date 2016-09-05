package com.thinkcoo.mobile.presentation.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerBaiduComponent;
import com.thinkcoo.mobile.model.entity.SchoolLocation;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.LoadSchoolBaiduAddressPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.BaseLceView;
import com.thinkcoo.mobile.presentation.mvp.views.LoadSchoolBaiduAddressView;
import com.thinkcoo.mobile.presentation.views.activitys.LoadSchoolBaiduAddressActivity;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/12.
 */
public class LoadSchoolAddressFragment extends BaseLceFragment<SchoolLocation> implements LoadSchoolBaiduAddressView {

    private static final String EXTRA_KEY_SCHOOL_NAME = "school_name";

    private String mSchoolName;

    @Inject
    LoadSchoolBaiduAddressPresenter mLoadSchoolBaiduAddressPresenter;

    OnFragmentInteractionListener mOnFragmentInteractionListener;

    public static LoadSchoolAddressFragment newInstance(String schoolName){
        if (TextUtils.isEmpty(schoolName)){
            throw new IllegalArgumentException("schoolName is null(LoadSchoolAddressFragment)");
        }
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_KEY_SCHOOL_NAME,schoolName);
        LoadSchoolAddressFragment loadSchoolAddressFragment = new LoadSchoolAddressFragment();
        loadSchoolAddressFragment.setArguments(bundle);
        return loadSchoolAddressFragment;
    }

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener onFragmentInteractionListener) {
        mOnFragmentInteractionListener = onFragmentInteractionListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSchoolName = getSchoolNameFromArguments();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private String getSchoolNameFromArguments() {
        Bundle bundle = getArguments();
        return bundle.getString(EXTRA_KEY_SCHOOL_NAME);
    }

    @Override
    protected LceAdapterViewBind<SchoolLocation> provideLceAdapterViewBind() {

        return new LceAdapterViewBind<SchoolLocation>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new InnerViewHolder(inflateItemView(parent));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, SchoolLocation schoolLocation) {
                ((InnerViewHolder)holder).bind(schoolLocation);
            }
        };
    }

    private View inflateItemView(ViewGroup parent) {
        return getActivity().getLayoutInflater().inflate(R.layout.item_school_baidu_address,parent,false);
    }

    @Override
    protected BaseLcePresenter providePresenter() {
        return mLoadSchoolBaiduAddressPresenter;
    }

    @Override
    protected void initInject() {
        DaggerBaiduComponent.builder().applicationComponent(getFragmentInjectHelper().getApplicationComponent()).build().inject(this);
    }

    private class InnerViewHolder extends RecyclerView.ViewHolder{

        private TextView mAddressName;
        private TextView mAddressDetail;
        private SchoolLocation mSchoolLocation;

        public InnerViewHolder(View itemView) {
            super(itemView);
            mAddressName = (TextView) itemView.findViewById(R.id.item_seach_activiaddress_name);
            mAddressDetail = (TextView) itemView.findViewById(R.id.item_seach_activiaddress_addre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackOnItemSelected(mSchoolLocation);
                }
            });
        }
        public void bind(SchoolLocation schoolLocation){
            mSchoolLocation = schoolLocation;
            mAddressDetail.setText(schoolLocation.getAddress());
            mAddressName.setText(schoolLocation.getAddressName());
        }

        private void callBackOnItemSelected(SchoolLocation schoolLocation) {
            if (null != mOnFragmentInteractionListener){
                mOnFragmentInteractionListener.onSchoolLocationSelected(schoolLocation);
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onSchoolLocationSelected(SchoolLocation schoolLocation);
    }

    @Override
    public String getSchoolName() {
        return ((LoadSchoolBaiduAddressActivity)getActivity()).getSchool();
    }
}
