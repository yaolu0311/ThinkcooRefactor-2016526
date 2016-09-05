package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.MutableShort;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.SchoolLocation;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;
import com.thinkcoo.mobile.presentation.views.fragment.LoadSchoolAddressFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/11.
 */
public class LoadSchoolBaiduAddressActivity extends BaseSimpleActivity {

    private static final String TAG = "LoadSchoolBaiduAddressActivity";
    private static final String KEY_SCHOOL = "school";
    public static final String KEY_RESULT = "result";

    public static final int REQUEST_CODE = 0x0001;

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.now_school)
    TextView mNowSchool;

    private String mSchool;

    public static Intent getJumpIntent(Context context, String school) {
        if (TextUtils.isEmpty(school)) {
            throw new IllegalArgumentException("school is null");
        }
        Intent intent = new Intent(context, LoadSchoolBaiduAddressActivity.class);
        intent.putExtra(KEY_SCHOOL, school);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_baidu_location);
        ButterKnife.bind(this);
        getSchoolFromIntentAndSetToUi();
        setupFragment();
    }

    private void getSchoolFromIntentAndSetToUi() {
        mSchool = getIntent().getStringExtra(KEY_SCHOOL);
        mTvTitle.setText("选择学校地址");
        mNowSchool.setText(mNowSchool.getText() + mSchool);
    }

    private void setupFragment() {
        LoadSchoolAddressFragment loadSchoolAddressFragment = LoadSchoolAddressFragment.newInstance(mSchool);
        loadSchoolAddressFragment.setOnFragmentInteractionListener(new LoadSchoolAddressFragment.OnFragmentInteractionListener() {
            @Override
            public void onSchoolLocationSelected(SchoolLocation schoolLocation) {
                Intent intent = new Intent();
                intent.putExtra(KEY_RESULT, schoolLocation);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        addFragment(R.id.fragment_container, loadSchoolAddressFragment);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

    public String getSchool() {
        return mSchool;
    }
}
