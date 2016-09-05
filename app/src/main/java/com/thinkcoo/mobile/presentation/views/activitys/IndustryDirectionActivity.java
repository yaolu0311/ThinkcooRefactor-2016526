package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.entity.IndustryItemEntity;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategyFactory;
import com.thinkcoo.mobile.presentation.mvp.presenters.IndustryDirectionPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.IndustryDirectionView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.adapter.User.IndustryDirectionAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/16.
 */
public class IndustryDirectionActivity extends BaseActivity implements IndustryDirectionView {

    public final static int MAX_SELECTED_COUNT = 3;
    public static final String KEY_DATA_DICTIONARY_LIST = "key_data_list";

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.recycler_industry)
    RecyclerView mRecyclerView;

    private String selectedIndustry;
    private List<IndustryItemEntity> mItemList;
    private List<IndustryItemEntity> mGroupList;
    private IndustryDirectionAdapter mIndustryDirectionAdapter;

    @Inject
    DataDictionaryStrategyFactory mDataDictionaryStrategyFactory;

    public static Intent getIndustryDirectionIntent(Context context, String industry) {
        Intent intent = new Intent(context, IndustryDirectionActivity.class);
        intent.putExtra(KEY_DATA_DICTIONARY_LIST, industry);
        return intent;
    }

    @Inject
    IndustryDirectionPresenter mIndustryDirectionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_direction);
        ButterKnife.bind(this);
        getDataFromIntent();
        initVariables();
        initRecycleView();
        mIndustryDirectionPresenter.queryIndustryFromDb(mDataDictionaryStrategyFactory.create(DataDictionaryStrategyFactory.DD_TYPE_INDUSTRY_DIRECTION));
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mIndustryDirectionPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).userModule(new UserModule()).build().inject(this);
    }

    @Override
    public void setListData(List<DataDictionary> listData) {
        if (null == listData || listData.size() == 0) {
            return;
        }
        for (DataDictionary dictionary : listData) {
            transformToIndustryItemEntity(dictionary);
        }
        mIndustryDirectionAdapter.setIndustryItemEntities(mItemList);
        mIndustryDirectionAdapter.setIndustryTeamEntities(mGroupList);
        mIndustryDirectionAdapter.notifyDataSetChanged();
    }

    private void transformToIndustryItemEntity(DataDictionary dictionary) {
        String code = dictionary.getCode();
        String name = dictionary.getDisplayName();
        IndustryItemEntity itemEntity;
        if (code.length() < MAX_SELECTED_COUNT) {
            itemEntity = getIndustryEntityFromDataDictionary(dictionary, false);
            mGroupList.add(itemEntity);
        } else {
            itemEntity = getItemEntity(dictionary, name);
            mItemList.add(itemEntity);
        }
    }

    @NonNull
    private IndustryItemEntity getIndustryEntityFromDataDictionary(DataDictionary dictionary, boolean isChecked) {
        IndustryItemEntity itemEntity = new IndustryItemEntity(isChecked);
        itemEntity.setDisplayName(dictionary.getDisplayName());
        itemEntity.setCode(dictionary.getCode());
        return itemEntity;
    }

    @NonNull
    private IndustryItemEntity getItemEntity(DataDictionary dictionary, String name) {
        IndustryItemEntity itemEntity;
        if (selectedIndustry.contains(name)) {
            itemEntity = getIndustryEntityFromDataDictionary(dictionary, true);
        } else {
            itemEntity = getIndustryEntityFromDataDictionary(dictionary, false);
        }
        return itemEntity;
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void closeSelf() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(KEY_DATA_DICTIONARY_LIST, (ArrayList<? extends Parcelable>) mIndustryDirectionAdapter.getSelectedList());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initVariables() {
        mItemList = new ArrayList<>();
        mGroupList = new ArrayList<>();
    }

    public void getDataFromIntent() {
        selectedIndustry = getIntent().getStringExtra(KEY_DATA_DICTIONARY_LIST);
        if (TextUtils.isEmpty(selectedIndustry)) {
            selectedIndustry = "";
        }
        mTvOther.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.industry_direction);
        mTvOther.setText(R.string.complete);
    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        mIndustryDirectionAdapter = new IndustryDirectionAdapter(this, mItemList, mGroupList);
        mRecyclerView.setAdapter(mIndustryDirectionAdapter);

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mIndustryDirectionAdapter);
        mRecyclerView.addItemDecoration(headersDecor);
    }

    @Override
    public void showProgressDialog(int stringResId) {
        mBaseActivityDelegate.showProgressDialog(stringResId);
    }

    @Override
    public void hideProgressDialogIfShowing() {
        mBaseActivityDelegate.hideProgressDialogIfShowing();
    }

    @Override
    public void showToast(String errorMsg) {
        mGlobalToast.showShortToast(errorMsg);
    }

    @OnClick({R.id.iv_back, R.id.tv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_other:
                closeSelf();
                break;
        }
    }
}
