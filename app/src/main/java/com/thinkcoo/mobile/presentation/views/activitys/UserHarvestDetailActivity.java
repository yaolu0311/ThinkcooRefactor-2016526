package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.UserHarvest;
import com.thinkcoo.mobile.model.entity.UserHarvestDetail;
import com.thinkcoo.mobile.presentation.mvp.presenters.BaseDetailPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserHarvestDetailPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseDetailActivity;
import com.thinkcoo.mobile.presentation.views.adapter.User.HarvestImageAdapter;
import com.thinkcoo.mobile.presentation.views.component.TimePickDialog;
import com.thinkcoo.mobile.utils.DateUtils;
import com.thinkcoo.mobile.utils.TakePhotoUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Leevin
 * CreateTime: 2016/6/1  14:22
 */
public class UserHarvestDetailActivity extends BaseDetailActivity<UserHarvest, UserHarvestDetail> implements TakePhotoUtils.TakePhotoListener{

    public static final String TAG ="UserHarvestDetailActivity" ;
    private EditText mEtHarvestName;
    private EditText mEtGrantedDepartment;
    private TextView mTvHarvestTime;
    private RelativeLayout mRlTimeContainer;
    private EditText mEtHarvestLevel;
    private EditText mEtHarvestRank;
    private RecyclerView mGvImageContainer;
    List<UserHarvestDetail.GrantPicBean> mGrantPicList;

    private HarvestImageAdapter mHarvestImageAdapter;

    @Inject
    TimePickDialog mTimePickDialog;
    @Inject
    TakePhotoUtils mTakePhotoUtils;

    @Inject
    UserHarvestDetailPresenter mUserHarvestDetailPresenter;

    private String mGrantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTakePhotoUtils.init(this,this);
        mTakePhotoUtils.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTakePhotoUtils.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTakePhotoUtils.release();
    }

    @Override
    protected BaseDetailPresenter generateBaseDetailPresenter() {
        return mUserHarvestDetailPresenter;
    }

    @Override
    protected View getDetailLayout() {
        return  createDetailView();
    }

    @Override
    protected void setupTitle(TextView textView) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(R.string.harvest_detail);
    }

    @Override
    protected void setupRightTextView(TextView textView) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(R.string.complete);
    }

    @Override
    protected void bindHostObjectToView(UserHarvest userHarvest) {
        mGrantId = userHarvest.getGrantId();//记录id for 比较时用
        mEtHarvestName.setText(userHarvest.getGrantName());
        String grantTime = userHarvest.getGrantTime();
        mTvHarvestTime.setText(grantTime);
        mEtGrantedDepartment.setText(userHarvest.getGrantDepartment());
    }

    @Override
    protected void bindDetailObjectToView(UserHarvestDetail userHarvestDetail) {
        mEtHarvestRank.setText(userHarvestDetail.getGrantRank());
        mEtHarvestLevel.setText(userHarvestDetail.getGrantLevel());
        // TODO: 2016/6/3 设置图片适配
        showHarvestPics(userHarvestDetail);
    }

    private void showHarvestPics(UserHarvestDetail userHarvestDetail) {
        if (userHarvestDetail != null) {
            mGrantPicList = userHarvestDetail.getGrantPicList();
        }
        mHarvestImageAdapter =  new HarvestImageAdapter(this,mGrantPicList,mTakePhotoUtils);
        mGvImageContainer.setLayoutManager(new GridLayoutManager(this,4));
        mGvImageContainer.setAdapter(mHarvestImageAdapter);
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().userModule(new UserModule()).activityModule(getActivityModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }

    @Override
    public UserHarvest getHostFromUi() {
        return createUserHarvestFromView();
    }

    @Override
    public void setResultCancelAndCloseSelf() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private View createDetailView() {
        View mView = LayoutInflater.from(this).inflate(R.layout.layout_user_harvest_detail, null);

        mEtHarvestName = (EditText) mView.findViewById(R.id.et_harvest_name);
        mEtGrantedDepartment =  (EditText) mView.findViewById(R.id.et_granted_department);
        mTvHarvestTime = (TextView) mView.findViewById(R.id.tv_harvest_time);
        mRlTimeContainer = (RelativeLayout)mView.findViewById(R.id.rl_time_container);
        mEtHarvestLevel= (EditText)mView.findViewById(R.id.et_harvest_level);
        mEtHarvestRank = (EditText) mView.findViewById(R.id.et_harvest_rank);

        mGvImageContainer = (RecyclerView) mView.findViewById(R.id.gv_image_container);
        showHarvestPics(null);
        mRlTimeContainer.setOnClickListener(mClickListener);
        return mView;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_time_container:
                    showSelectDateDialog();
                    break;
            }
        }
    };

    //时间选择器
    private void showSelectDateDialog() {
        mTimePickDialog.setDefaultDate(DateUtils.stringToDate(mTvHarvestTime.getText().toString().trim(),DateUtils.YEAR_MONTH_DAY));
        mTimePickDialog.show();
        mTimePickDialog.setOnTimeSelectedListener(new TimePickDialog.OnTimeSelectedListener() {
            @Override
            public void onTimeSelected(Date date) {
                mTvHarvestTime.setText(DateUtils.dateToString(date,DateUtils.YEAR_MONTH_DAY));
            }
        });
    }

    private UserHarvest createUserHarvestFromView() {
        UserHarvest userHarvest = new UserHarvest();
        userHarvest.setGrantId(mGrantId);
        userHarvest.setGrantName(mEtHarvestName.getText().toString().trim());
        userHarvest.setGrantTime(mTvHarvestTime.getText().toString().trim());
        userHarvest.setGrantDepartment(mEtGrantedDepartment.getText().toString().trim());

        UserHarvestDetail userHarvestDetail = new UserHarvestDetail();
        userHarvestDetail.setGrantRank(mEtHarvestRank.getText().toString().trim());
        userHarvestDetail.setGrantLevel(mEtHarvestLevel.getText().toString().trim());
        userHarvestDetail.setGrantPicList(mGrantPicList);
        userHarvest.setUserHarvestDetail(userHarvestDetail);
        return userHarvest;
    }


    public static Intent getHarvestIntentWithEditMode(Context context, UserHarvest userHarvest) {
        Intent intent = new Intent(context, UserHarvestDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(MODE_KEY, EDIT_MODE);
        bundle.putParcelable(HOST_OBJECT, userHarvest);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent getHarvestIntentWithAddMode(Context context) {
        Intent intent = new Intent(context, UserHarvestDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(MODE_KEY, ADD_MODE);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent getHarvestIntentWithViewMode(Context context, UserHarvest userHarvest) {
        Intent intent = new Intent(context, UserHarvestDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(MODE_KEY, VIEW_MODE);
        bundle.putParcelable(HOST_OBJECT,userHarvest);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onSuccess(List<String> resultList) {
        if (null == resultList || resultList.isEmpty()){
            return;
        }
        for (int i = 0; i < resultList.size(); i++) {
            UserHarvestDetail.GrantPicBean grantPicBean = new UserHarvestDetail.GrantPicBean();
            grantPicBean.setGrantPicPath(resultList.get(i));
            mGrantPicList.add(grantPicBean);
        }
        mHarvestImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String errorMsg) {

    }
}
