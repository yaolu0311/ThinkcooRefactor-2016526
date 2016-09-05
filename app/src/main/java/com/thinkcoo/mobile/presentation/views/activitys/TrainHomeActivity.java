package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.presenters.LoadLocationPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.TrainHomeView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Leevin
 * CreateTime: 2016/8/18  9:18
 */
public class TrainHomeActivity extends BaseActivity implements TrainHomeView{

    public static Intent getJumpIntent(Context context) {
        return new Intent(context, TrainHomeActivity.class);
    }


    //@Inject
    //LoadLocationPresenter mLoadLocationPresenter;

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.iv_more)
    ImageView mIvMore;
    @Bind(R.id.tv_location)
    TextView mTvLocation;
    @Bind(R.id.et_search_key)
    EditText mEtSearchKey;
    private int mScreenWidth;
    private PopupWindow mPopupWindow;
    private Location mCurrentLocation;
    private Location mSelectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_train_home);
        ButterKnife.bind(this);
        //mLoadLocationPresenter.getLocation();
        initScreenWidth();
    }

    private void initScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return null;
    }

    @Override
    protected void initDaggerInject() {

    }


    @OnClick({R.id.iv_back, R.id.iv_more, R.id.rl_location_container, R.id.btn_train_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:
                showPopupWindow();
                break;
            case R.id.rl_location_container:
                mNavigator.navigateToSelectLocationActivity(this,mCurrentLocation);
                break;
            case R.id.btn_train_search:
                mNavigator.navigateToTrainSearchActivity(this,mEtSearchKey.getText().toString().trim(),mSelectedLocation);
                break;
        }
    }

    private void showPopupWindow() {
        View pop_view = LayoutInflater.from(this).inflate(R.layout.train_pop_item, null);
        RelativeLayout appointment = (RelativeLayout) pop_view.findViewById(R.id.rl_appointment);
        RelativeLayout collections = (RelativeLayout) pop_view.findViewById(R.id.rl_collections);
        mPopupWindow = new PopupWindow(pop_view, (int)(0.37037*mScreenWidth), RelativeLayout.LayoutParams.WRAP_CONTENT,true);
        mPopupWindow.showAsDropDown(mIvMore);
        //整个层获得焦点方便获得页面back事件
        pop_view.setFocusable(true);
        pop_view.setFocusableInTouchMode(true);
        //获得back事件
        pop_view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                        mPopupWindow = null;
                    }
                    return true;
                }
                return false;
            }
        });
        // 设置点击其他地方关闭popupwindow
        pop_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                }
                return false;
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mNavigator.navigateToTrainAppointmentActivity(TrainHomeActivity.this);
                mPopupWindow.dismiss();
            }
        });

        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.navigateToTrainCollectionsActivity(TrainHomeActivity.this);
                mPopupWindow.dismiss();
            }
        });
    }

    @Override
    public void setLocation(Location location) {
        mCurrentLocation = location;
        mSelectedLocation = location;
        refreshLocationText();
    }

    @Override
    public void getLocationFailure() {
        mTvLocation.setText(R.string.get_loacation_failure);
    }

    @Override
    public void onBackPressed() {
        if (mPopupWindow!=null&&mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data.hasExtra(SelectLocationActivity.RESULT_LOCATION_EXTRA_KEY)) {
                Location location = data.getParcelableExtra(SelectLocationActivity.RESULT_LOCATION_EXTRA_KEY);
                if (!locationEquals(mSelectedLocation,location)) {
                    mSelectedLocation = location;
                    refreshLocationText();
                }
            }
        }
    }

    private void refreshLocationText() {
        if (mSelectedLocation == null || mSelectedLocation.getCity() == null) {
            mTvLocation.setText(R.string.get_loacation_failure);
            return;
        }
         mTvLocation.setText(mSelectedLocation.getCity());
    }

    private boolean locationEquals(Location location1 , Location location2 ){
        return location1.getCityCode().equals(location2.getCityCode());
    }
}
