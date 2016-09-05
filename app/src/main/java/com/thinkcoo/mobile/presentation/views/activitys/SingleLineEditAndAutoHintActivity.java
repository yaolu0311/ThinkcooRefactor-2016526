package com.thinkcoo.mobile.presentation.views.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategy;
import com.thinkcoo.mobile.model.strategy.DataDictionaryStrategyFactory;
import com.thinkcoo.mobile.model.strategy.SingleLineEditAndAutoHintStrategy;
import com.thinkcoo.mobile.model.strategy.SingleLineEditContent;
import com.thinkcoo.mobile.model.strategy.StringSingleLineEditContent;
import com.thinkcoo.mobile.presentation.mvp.presenters.EditAndAutoHinPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.SingleLineEditAndAutoHintView;
import com.thinkcoo.mobile.presentation.views.adapter.SampleRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/6/15.
 */
public class SingleLineEditAndAutoHintActivity extends SingleLineEditActivity implements SingleLineEditAndAutoHintView{

    public static final String TAG = "SingleLineEditAndAutoHintActivity";

    public static Intent getSingleLineEditAndAutoHintIntent(Context context, SingleLineEditAndAutoHintStrategy singleLineEditAndAutoHintStrategy) {

        Intent intent = new Intent(context,SingleLineEditAndAutoHintActivity.class);
        intent.putExtra(KEY_STRATEGY,singleLineEditAndAutoHintStrategy);

        return intent;
    }

    @Inject
    EditAndAutoHinPresenter mEditAndAutoHinPresenter;

    private RecyclerView mRecyclerView;
    private SampleRecyclerViewAdapter mSampleRecyclerViewAdapter;
    private List<DataDictionary> mDictionaryList;

    @Inject
    DataDictionaryStrategyFactory mDataDictionaryStrategyFactory;
    SingleLineEditAndAutoHintStrategy mSingleLineEditAndAutoHintStrategy;
    DataDictionary mSelectDataDictionary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpRecyclerView();
        mSingleLineEditAndAutoHintStrategy = (SingleLineEditAndAutoHintStrategy) getSingleLineEditStrategy();
    }

    private void setUpRecyclerView() {

        mRecyclerView = (RecyclerView) LayoutInflater.from(this).inflate(R.layout.layout_recycler_view, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getListLayout().addView(mRecyclerView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        mDictionaryList = new ArrayList<>();
        mSampleRecyclerViewAdapter = new SampleRecyclerViewAdapter(this, mDictionaryList);
        mSampleRecyclerViewAdapter.setOnItemClickListener(new SampleRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onClick(DataDictionary dataDictionary, int position) {
                getEditText().setText(dataDictionary.getDisplayName());
                mSelectDataDictionary = dataDictionary;
            }
        });
        mRecyclerView.setAdapter(mSampleRecyclerViewAdapter);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mEditAndAutoHinPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).userModule(new UserModule()).activityModule(getActivityModule()).build().inject(this);
    }

    @Override
    public void setSearchResult(List<DataDictionary> searchResult) {
        if (null == searchResult) {
            return;
        }
        mDictionaryList.clear();
        mDictionaryList.addAll(searchResult);
        mSampleRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void resultOkSingleLineEditContent() {
        SingleLineEditContent dataDictionary = getSingleLineEditContent();
        Intent intent = new Intent();
        intent.putExtra(KEY_CALL_BACK_CONTENT,dataDictionary);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    private SingleLineEditContent getSingleLineEditContent() {

        if (null != mSelectDataDictionary){
            return mSelectDataDictionary;
        }else {
            return createSingleLineEditContentByInput();
        }
    }

    private SingleLineEditContent createSingleLineEditContentByInput() {
        String input = getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(input)){
            return mSingleLineEditAndAutoHintStrategy.getContent();
        }else {
            return pickDataDictionaryList(input);
        }
    }

    private SingleLineEditContent pickDataDictionaryList(String input) {

        SingleLineEditContent singleLineEditContent = null;

        if (null != mDictionaryList && mDictionaryList.size() > 0) {
            for (DataDictionary dataDictionary : mDictionaryList) {
                if (dataDictionary.getDisplayName().equals(input)) {
                    singleLineEditContent = dataDictionary;
                }
            }
        }
        if (null == singleLineEditContent) {
            singleLineEditContent = new StringSingleLineEditContent(input);
        }
        return singleLineEditContent;
    }

    @Override
    protected void afterEditTextChanged(Editable s) {
        String input = getEditText().getText().toString().trim();
        getEditText().setSelection(getEditText().length());
        mEditAndAutoHinPresenter.startSearch(getDataDictionaryStrategy(),input);
    }

    protected EditAndAutoHinPresenter getEditAndAutoHinPresenter(){
        return mEditAndAutoHinPresenter;
    }
    protected DataDictionaryStrategy getDataDictionaryStrategy(){
        return mDataDictionaryStrategyFactory.create(mSingleLineEditAndAutoHintStrategy.getDataDictionaryType());
    }
}
