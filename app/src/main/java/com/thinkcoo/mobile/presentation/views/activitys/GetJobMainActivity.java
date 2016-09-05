package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerGetJobComponent;
import com.thinkcoo.mobile.injector.modules.ActivityModule;
import com.thinkcoo.mobile.injector.modules.GetJobModule;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.presenters.GetJobMainPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.GetJobMainView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.dialog.SimpleDataSelectDialog;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/19.
 */
public class GetJobMainActivity extends BaseActivity implements GetJobMainView {


    public static Intent getJumpIntent(Context context) {
        Intent intent = new Intent(context,GetJobMainActivity.class);
        return intent;
    }

    @Inject
    GetJobMainPresenter mGetJobMainPresenter;

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.findjob_home_tj_im)
    ImageView mFindjobHomeTjIm;
    @Bind(R.id.indjob_home_tj_edtext)
    EditText mIndjobHomeTjEdtext;
    @Bind(R.id.findjob_home_bj_relayout)
    RelativeLayout mFindjobHomeBjRelayout;
    @Bind(R.id.findjob_home_dq_titl)
    TextView mFindjobHomeDqTitl;
    @Bind(R.id.findjob_home_dq_nr)
    TextView mFindjobHomeDqNr;
    @Bind(R.id.findjob_home_dq_jt)
    ImageView mFindjobHomeDqJt;
    @Bind(R.id.findjob_home_dq_relayout)
    RelativeLayout mFindjobHomeDqRelayout;
    @Bind(R.id.findjob_home_lx_textview)
    TextView mFindjobHomeLxTextview;
    @Bind(R.id.findjob_home_lxnr_textview)
    TextView mFindjobHomeLxnrTextview;
    @Bind(R.id.findjob_home_lx_jt)
    ImageView mFindjobHomeLxJt;
    @Bind(R.id.findjob_home_lb_ralyout)
    RelativeLayout mFindjobHomeLbRalyout;
    @Bind(R.id.ac_line_four)
    View mAcLineFour;
    @Bind(R.id.findjob_home_hy_title)
    TextView mFindjobHomeHyTitle;
    @Bind(R.id.findjob_home_hy_nr)
    TextView mFindjobHomeHyNr;
    @Bind(R.id.findjob_home_hy_jt)
    ImageView mFindjobHomeHyJt;
    @Bind(R.id.findjob_home_hy_relayout)
    RelativeLayout mFindjobHomeHyRelayout;
    @Bind(R.id.ac_line_five)
    View mAcLineFive;
    @Bind(R.id.findjob_home_rq_titl)
    TextView mFindjobHomeRqTitl;
    @Bind(R.id.findjob_home_rq_nr)
    TextView mFindjobHomeRqNr;
    @Bind(R.id.ac_sell_goods_details_address_flag)
    ImageView mAcSellGoodsDetailsAddressFlag;
    @Bind(R.id.findjob_home_rq_ralout)
    RelativeLayout mFindjobHomeRqRalout;
    @Bind(R.id.ac_line_threee)
    View mAcLineThreee;
    @Bind(R.id.findjob_butt_soso)
    Button mFindjobButtSoso;
    @Bind(R.id.ac_image_findjob_call)
    ImageView mAcImageFindjobCall;
    @Bind(R.id.ac_text_findjob_call)
    TextView mAcTextFindjobCall;
    @Bind(R.id.ac_layout_findjob_call)
    LinearLayout mAcLayoutFindjobCall;
    @Bind(R.id.ac_image_findjob_cal)
    ImageView mAcImageFindjobCal;
    @Bind(R.id.ac_text_findjob_cal)
    TextView mAcTextFindjobCal;
    @Bind(R.id.ac_layout_findjob_cal)
    LinearLayout mAcLayoutFindjobCal;
    @Bind(R.id.ac_image_findjob_ca)
    ImageView mAcImageFindjobCa;
    @Bind(R.id.ac_text_findjob_ca)
    TextView mAcTextFindjobCa;
    @Bind(R.id.ac_layout_findjob_ca)
    LinearLayout mAcLayoutFindjobCa;
    @Bind(R.id.ac_image_findjob_c)
    ImageView mAcImageFindjobC;
    @Bind(R.id.ac_text_findjob_c)
    TextView mAcTextFindjobC;
    @Bind(R.id.ac_layout_findjob_c)
    LinearLayout mAcLayoutFindjobC;
    @Bind(R.id.ac_sell_findjob_details)
    LinearLayout mAcSellFindjobDetails;

    Location mLocationFromBaidu;
    Location mLocationFromSelect;

    @Inject
    SimpleDataSelectDialog mSimpleDataSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_job_main);
        ButterKnife.bind(this);
        setupTitle();
        mGetJobMainPresenter.startRequestLocation();
    }

    private void setupTitle() {
        mTvTitle.setText("找工作");
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mGetJobMainPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerGetJobComponent.builder().applicationComponent(getApplicationComponent()).activityModule(new ActivityModule(this)).getJobModule(new GetJobModule()).build().inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLocationFromSelect = getSelectLocation(requestCode, resultCode, data);
    }

    private Location getSelectLocation(int requestCode, int resultCode, Intent data) {
        if (SelectLocationActivity.REQUEST_CODE != requestCode || RESULT_OK != resultCode || null == data || !data.hasExtra(SelectLocationActivity.RESULT_LOCATION_EXTRA_KEY)) {
            return null;
        }
        return data.getParcelableExtra(SelectLocationActivity.RESULT_LOCATION_EXTRA_KEY);
    }

    @Override
    public void setLocation(Location location) {
        mLocationFromBaidu = location;
        mLocationFromSelect = location;
        mFindjobHomeDqNr.setText(location.getCity());
    }

    @OnClick({R.id.iv_back, R.id.findjob_home_bj_relayout, R.id.findjob_home_dq_relayout, R.id.findjob_home_lb_ralyout, R.id.findjob_home_hy_relayout, R.id.findjob_home_rq_ralout, R.id.findjob_butt_soso, R.id.ac_layout_findjob_call, R.id.ac_layout_findjob_cal, R.id.ac_layout_findjob_ca, R.id.ac_layout_findjob_c, R.id.ac_sell_findjob_details})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.findjob_home_bj_relayout:
                break;
            case R.id.findjob_home_dq_relayout:
                if (null != mLocationFromSelect)
                    mNavigator.navigateToSelectLocationActivity(this,mLocationFromSelect);
                break;
            case R.id.findjob_home_lb_ralyout:
                mSimpleDataSelectDialog.showJobType(this, new SimpleDataSelectDialog.OnSimpleDataSelectedListener() {
                    @Override
                    public void onSimpleDataSelected(String jobType) {
                        mFindjobHomeLxnrTextview.setText(jobType);
                    }
                });
                break;
            case R.id.findjob_home_hy_relayout:
                //TODO 去行业选取
                break;
            case R.id.findjob_home_rq_ralout:
                mSimpleDataSelectDialog.showJobReleaseTime(this, new SimpleDataSelectDialog.OnSimpleDataSelectedListener() {
                    @Override
                    public void onSimpleDataSelected(String jobType) {
                        mFindjobHomeRqNr.setText(jobType);
                    }
                });
                break;
            case R.id.findjob_butt_soso:
                //TODO 发起瘦身
                mIndjobHomeTjEdtext.getText().toString();
                break;
            case R.id.ac_layout_findjob_call:
                //TODO 到我的简历
                break;
            case R.id.ac_layout_findjob_cal:
                //TODO 到申请记录
                break;
            case R.id.ac_layout_findjob_ca:
                //TODO 到我的收藏
                break;
            case R.id.ac_layout_findjob_c:
                //TODO 到我的评比列表
                break;
            case R.id.ac_sell_findjob_details:
                break;
        }
    }


}
