package com.thinkcoo.mobile.presentation.views.activitys.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseDetailPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.BaseDetailView;
import com.thinkcoo.mobile.presentation.views.dialog.GlobalTransparentPopupWindow;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by robert on 2016/5/31.
 */
public abstract class BaseDetailActivity<H extends Object , D extends Object> extends BaseActivity implements BaseDetailView<H,D> {

    public static final String NO_MODE_ERROR_MSG = "You must set activity mode in BaseDetailActivity";
    public static final String NO_DETAIL_HOST_ID_ERROR_MSG = "You must set detail host id  BaseDetailActivity ( edit or view mode)";

    public static final int EDIT_MODE = 0x0001;
    public static final int ADD_MODE = 0x0002;
    public static final int VIEW_MODE = 0x0003;

    public static final String MODE_KEY = "mode";
    public static final String HOST_OBJECT = "detail_host";

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.iv_more)
    ImageView mIvMore;
    @Bind(R.id.title_main_view)
    RelativeLayout mTitleMainView;
    @Bind(R.id.content_view)
    FrameLayout mContentView;

    protected int mActivityMode;
    protected H mHostObject;
    BaseDetailPresenter mBaseDetailPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_detial);
        ButterKnife.bind(this);
        getModeFromIntent();
        getDetailHostFromIntent();
        setupDetailViewByMode();
        processIntentBundle(getIntent().getExtras());
        setupViews();
        addDetailLayoutInContentView();
        mBaseDetailPresenter = generateBaseDetailPresenter();
        loadDetail();
    }

    private void getDetailHostFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (null == bundle || (modeInEditAndView() && !bundle.containsKey(HOST_OBJECT))) {
            throw new IllegalArgumentException(NO_DETAIL_HOST_ID_ERROR_MSG);
        }
        mHostObject = bundle.getParcelable(HOST_OBJECT);
    }

    protected boolean modeInEditAndView() {
        return mActivityMode == VIEW_MODE || mActivityMode == EDIT_MODE;
    }

    protected void setupViews() {
        setupTitle(mTvTitle);
        setupRightTextView(mTvOther);
    }

    private void setupDetailViewByMode() {
        if (mActivityMode == VIEW_MODE) {
            mTvOther.setVisibility(View.GONE);
            mTvOther.setOnClickListener(null);
        }
    }

    protected void loadDetail() {
        if (mActivityMode == ADD_MODE) {
            return;
        }
        mBaseDetailPresenter.loadDetail(mHostObject);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mActivityMode == VIEW_MODE) {
            alertFullScreenTransparentDialog();
        }
    }

    protected void alertFullScreenTransparentDialog() {
        new GlobalTransparentPopupWindow(this).showPopupWindow(mTitleMainView);
    }

    protected void addDetailLayoutInContentView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mContentView.addView(getDetailLayout(), layoutParams);
        if (mActivityMode != ADD_MODE){
            bindHostObjectToView(mHostObject);
        }
    }


    public void getModeFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (null == bundle || !bundle.containsKey(MODE_KEY)) {
            throw new IllegalArgumentException(NO_MODE_ERROR_MSG);
        }
        mActivityMode = bundle.getInt(MODE_KEY);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return generateBaseDetailPresenter();
    }

    @Override
    public void setResultOkAndCloseSelf() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void closeSelf() {
        finish();
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
    public void showToast(String stringResMsg) {
        mGlobalToast.showShortToast(stringResMsg);
    }

    @Override
    public void setDetail(D detail) {
        bindDetailObjectToView(detail);
    }

    protected abstract BaseDetailPresenter generateBaseDetailPresenter();
    protected abstract View getDetailLayout();
    protected abstract void setupTitle(TextView textView);
    protected abstract void setupRightTextView(TextView textView);
    protected abstract void bindHostObjectToView(H hostObject);
    protected abstract void bindDetailObjectToView(D detailObject);
    protected void processIntentBundle(Bundle bundle){}

    @OnClick({R.id.iv_back, R.id.tv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.tv_other:
                if (mActivityMode == EDIT_MODE){
                    mBaseDetailPresenter.editHost();
                }else {
                    mBaseDetailPresenter.addHost();
                }
                break;
        }
    }

    protected boolean isEditMode(){
        return mActivityMode == EDIT_MODE;
    }
    protected boolean isAddMode(){
        return mActivityMode == ADD_MODE;
    }
    protected boolean isViewMode(){
        return mActivityMode == VIEW_MODE;
    }

    @Override
    public void setResultCancelAndCloseSelf() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
