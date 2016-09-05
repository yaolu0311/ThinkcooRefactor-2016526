package com.thinkcoo.mobile.presentation.views.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerDataDictionaryComponent;
import com.thinkcoo.mobile.model.entity.SortedCity;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseLcePresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.CityListPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseLceSectionSortedFragment;
import javax.inject.Inject;


/**
 * Created by Robert.yao on 2016/7/25.
 */
public class CityListFragment extends BaseLceSectionSortedFragment<SortedCity> {

    private static final String TAG = "CityListFragment";

    @Inject
    CityListPresenter mCityListPresenter;

    @Override
    protected BaseLceSectionSortedFragment.LceSectionSortedAdapterViewBind<SortedCity> provideLceSectionSortedAdapterViewBind() {

        return new LceSectionSortedAdapterViewBind<SortedCity>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(createItemView(parent));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, SortedCity sortedCity) {
                ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
                itemViewHolder.bind(sortedCity);
            }

            @Override
            public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
                return new SectionViewHolder(createSectionView(parent));
            }

            @Override
            public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, String sectionName) {
                SectionViewHolder sectionViewHolder = (SectionViewHolder) holder;
                sectionViewHolder.setSectionName(sectionName);
            }
        };
    }

    private View createItemView(ViewGroup viewGroup) {
        return getActivity().getLayoutInflater().inflate(R.layout.list_item_select_city,viewGroup,false);
    }
    private View createSectionView(ViewGroup viewGroup){
        return getActivity().getLayoutInflater().inflate(R.layout.list_section_select_city,viewGroup,false);
    }

    @Override
    protected BaseLcePresenter providePresenter() {
        return mCityListPresenter;
    }
    @Override
    protected void initInject() {
        DaggerDataDictionaryComponent.builder().applicationComponent(getFragmentInjectHelper().getApplicationComponent()).build().inject(this);
    }

    public static Fragment newInstance() {
        return new CityListFragment();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView mCityTv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mCityTv = (TextView)itemView.findViewById(R.id.ac_city_name);
        }
        public void bind(SortedCity sortedCity){
            mCityTv.setText(sortedCity.getCityName());
        }
    }


    private class SectionViewHolder extends RecyclerView.ViewHolder{
        TextView mSectionTv;
        public SectionViewHolder(View itemView) {
            super(itemView);
            mSectionTv = (TextView)itemView.findViewById(R.id.ac_city_catalog);
        }
        public void setSectionName(String sectionName){
            mSectionTv.setText(sectionName);
        }
    }
}
