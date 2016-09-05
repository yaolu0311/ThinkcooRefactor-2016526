package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.publicmodule.ui.listener.OnActivityTouchListener;
import com.example.administrator.publicmodule.ui.listener.RecyclerTouchListener;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.UserHarvest;
import com.thinkcoo.mobile.model.exception.user.EmptyException;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserHarvestPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseLceActivity;
import com.thinkcoo.mobile.presentation.views.adapter.User.HarvestSwipeAdapter;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserHarvestActivity extends BaseLceActivity implements RecyclerTouchListener.RecyclerTouchListenerHelper{


    public static final int USER_HARVEST_REQUEST_CODE = 0x00001;
    private static final String TAG ="UserHarvestActivity" ;

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_more)
    ImageView mIvAdd;
    @Bind(R.id.loadingView)
    FrameLayout mLoadingView;
    @Bind(R.id.contentView)
    RecyclerView mRecyclerView;
    private RecyclerTouchListener onTouchListener;
    private OnActivityTouchListener touchListener;

    @Bind(R.id.errorView)
    TextView mErrorView;

    @Inject
    UserHarvestPresenter mUserHarvestPresenter;
    HarvestSwipeAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_harvest);
        ButterKnife.bind(this);
        loadData(false);
        addItemOnTouchListener();
        setupViews();
    }

    private void addItemOnTouchListener() {
        onTouchListener = new RecyclerTouchListener(this, mRecyclerView);
        onTouchListener
                .setIndependentViews(R.id.iv_harvest_arrow)
                .setViewsToFade(R.id.iv_harvest_arrow)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        mNavigator.navigateToHarvestDetailWithEditMode(UserHarvestActivity.this,mAdapter.mUserHarvestList.get(position));
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                    }
                })
                .setSwipeOptionViews(R.id.btn_delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {

                        if (viewID == R.id.btn_delete) {
                            mUserHarvestPresenter.deleteUserHarvest(mAdapter.mUserHarvestList.get(position).getGrantId());
                        }
                    }
                });

    }

    protected void setupViews() {
        mTvTitle.setText(R.string.my_harvest);
        mIvAdd.setVisibility(View.VISIBLE);
        mIvAdd.setImageResource(R.mipmap.my_harvest_add);
        mAdapter = new HarvestSwipeAdapter(this, Collections.EMPTY_LIST);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mUserHarvestPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().userModule(new UserModule()).activityModule(getActivityModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }


    @OnClick({R.id.iv_back, R.id.iv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:// 加点添加收获
                mNavigator.navigateToHarvestDetailWithAddMode(this);
                break;
        }
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        if (e instanceof EmptyException) {
            return  getResources().getString(R.string.net_no_data);
        }
        return getResources().getString(R.string.load_failure_try_again);
    }

    @Override
    public void setData(Object data) {
        mAdapter.refresh((List<UserHarvest>) data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        mUserHarvestPresenter.loadUserHarvestList(pullToRefresh);
    }

    public static Intent getUserHarvestIntent(Context context) {
        Intent intent = new Intent(context, UserHarvestActivity.class);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USER_HARVEST_REQUEST_CODE && resultCode == RESULT_OK) {
            loadData(true);
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.addOnItemTouchListener(onTouchListener); }

    @Override
    protected void onPause() {
        super.onPause();
        mRecyclerView.removeOnItemTouchListener(onTouchListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (touchListener != null) touchListener.getTouchCoordinates(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setOnActivityTouchListener(OnActivityTouchListener listener) {
        this.touchListener = listener;
    }

    @Override
    protected void setTitle(TextView tvTitle) {

    }

    @Override
    protected int getRealContentLayoutResId() {
        return 0;
    }

    @Override
    protected void getIntentExtras() {

    }
}
