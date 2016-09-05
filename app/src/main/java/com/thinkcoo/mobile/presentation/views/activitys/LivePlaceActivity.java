package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.Address;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;
import com.thinkcoo.mobile.presentation.views.component.AddressDialog;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LivePlaceActivity extends BaseSimpleActivity {

    @Inject
    AddressDialog mAddressDialog;
    @Bind(R.id.iv_back)
    TextView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_other)
    TextView tvOther;
    @Bind(R.id.tv_select_city)
    TextView tvSelectCity;
    @Bind(R.id.iv_icon2)
    ImageView ivIcon2;
    @Bind(R.id.rl_select_city)
    RelativeLayout rlSelectCity;
    @Bind(R.id.et_select_city_details_addr)
    EditText etSelectCityDetailsAddr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_place);
        ButterKnife.bind(this);
        setupViews();
    }

    protected void setupViews() {
        tvOther.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.juzhudi);
        Address address =(Address) getIntent().getParcelableExtra(UserBasicInfoActivity.EXTRA_USER_LIVE_AREA_KEY);
        mAddressDialog.setSelectAddress(address);
        tvSelectCity.setText(address.getName());
        etSelectCityDetailsAddr.setText(getIntent().getStringExtra(UserBasicInfoActivity.EXTRA_USER_LIVE_STREET_KEY));
        tvSelectCity.setTag(address);
        mAddressDialog.setOnAddressSelectedListener(new AddressDialog.OnAddressSelectedListener() {
            @Override
            public void onAddressSelected(Address address) {
                mAddressDialog.setSelectAddress(address);
                tvSelectCity.setTag(address);
                tvSelectCity.setText(address.getName());
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_other, R.id.rl_select_city})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
               finish();
                break;
            case R.id.tv_other:
                Intent intent = new Intent();
                intent.putExtra(UserBasicInfoActivity.EXTRA_USER_LIVE_AREA_KEY, (Address)tvSelectCity.getTag());
                intent.putExtra(UserBasicInfoActivity.EXTRA_USER_LIVE_STREET_KEY, etSelectCityDetailsAddr.getText().toString().trim());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.rl_select_city:
                mAddressDialog.show();
                break;
        }
    }

    public static Intent getLivePlaceIntent(Context context, Address address, String liveStreet) {
        Intent intent = new Intent(context, LivePlaceActivity.class);
        intent.putExtra(UserBasicInfoActivity.EXTRA_USER_LIVE_STREET_KEY, liveStreet);
        intent.putExtra(UserBasicInfoActivity.EXTRA_USER_LIVE_AREA_KEY, address);
        return intent;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().activityModule(getActivityModule()).userModule(new UserModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }
}
