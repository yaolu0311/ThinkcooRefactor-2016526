package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.mvp.presenters.BlankPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.SelectLocationView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.fragment.CityListFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/7/25.
 */
public class SelectLocationActivity extends BaseActivity implements SelectLocationView {

    private static final String CURRENT_LOCATION_EXTRA_KEY = "current_location";
    public static final String RESULT_LOCATION_EXTRA_KEY = "result_location";
    public static final int REQUEST_CODE = 0x0001;

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.iv_more)
    ImageView mIvMore;
    @Bind(R.id.ac_select_city_text)
    TextView mAcSelectCityText;
    @Bind(R.id.ac_city_info)
    TextView mAcCityInfo;
    @Bind(R.id.ac_city_lay)
    RelativeLayout mAcCityLay;

    public static Intent getJumpIntent(Context context, Location currentLocation) {
        Intent intent = new Intent(context, SelectLocationActivity.class);
        intent.putExtra(CURRENT_LOCATION_EXTRA_KEY, currentLocation);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        ButterKnife.bind(this);
        setupCurrentLocationUseIntentExtra();
        addFragment(R.id.city_list_content, CityListFragment.newInstance());
    }

    private void setupCurrentLocationUseIntentExtra() {
        Location location = getIntent().getParcelableExtra(CURRENT_LOCATION_EXTRA_KEY);
        mAcSelectCityText.setText(getString(R.string.current_location,location.getCity()));
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return new BlankPresenter();
    }

    @Override
    protected void initDaggerInject() {

    }

    @OnClick({R.id.iv_back, R.id.iv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.iv_more:
                break;
        }
    }
}
