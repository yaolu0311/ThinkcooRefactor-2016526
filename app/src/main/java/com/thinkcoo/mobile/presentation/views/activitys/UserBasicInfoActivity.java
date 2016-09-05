package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.Address;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserBasicInfoPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.UserBasicInfoView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.Navigator;
import com.thinkcoo.mobile.presentation.views.component.DataDictionaryDialog;
import com.thinkcoo.mobile.presentation.views.component.TimePickDialog;
import com.thinkcoo.mobile.utils.DateUtils;

import java.util.Date;
import java.util.LinkedList;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserBasicInfoActivity extends BaseActivity implements UserBasicInfoView {

    public static final int REQUEST_USERINFO_CERTIFICATENUMBER_CODE = 1;
    public static final int REQUEST_USERINFO_LIVEPLACE_CODE = 2;
    public static final int REQUEST_USERINFO__BIRTHPLACE_CODE = 3;
    public static final String EXTRA_USER_CERTIFICATE_NUMBER_KEY = "extra_user_certificate_number_key";
    public static final String EXTRA_USER_LIVE_STREET_KEY = "extra_user_live_street_key";
    public static final String EXTRA_USER_BIRTH_AREA_KEY = "extra_user_live_area_key";
    public static final String EXTRA_USER_BIRTH_STREET_KEY = "extra_user_live_street__key";
    public static final String EXTRA_USER_LIVE_AREA_KEY = "extra_user_live_area_key";
    public static final String EXTRA_USER_BASIC_INFO_KEY = "extra_user_basic_info_key";

    @Inject
    public Navigator mNavigator;
    @Bind(R.id.iv_back)
    TextView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_other)
    TextView tvOther;
    @Bind(R.id.tv_certificate_type)
    TextView tvCertificateType;
    @Bind(R.id.rl_certificate_type_but)
    RelativeLayout rlCertificateTypeBut;
    @Bind(R.id.tv_certificate_number)
    TextView tvCertificateNumber;
    @Bind(R.id.iv_icon1)
    ImageView ivIcon1;
    @Bind(R.id.rl_certificate_number_but)
    RelativeLayout rlCertificateNumberBut;
    @Bind(R.id.personal_view_hm)
    View personalViewHm;
    @Bind(R.id.tv_birthday)
    TextView tvBirthday;
    @Bind(R.id.rl_birthday)
    RelativeLayout rlBirthday;
    @Bind(R.id.text_live_plac)
    TextView textLivePlac;
    @Bind(R.id.tv_phone_contex)
    EditText tvPhoneContex;
    @Bind(R.id.rl_domicilee)
    RelativeLayout rlDomicilee;
    @Bind(R.id.personal_view_lx)
    View personalViewLx;
    @Bind(R.id.text_birth_plac)
    TextView textBirthPlac;
    @Bind(R.id.tv_meiledit)
    EditText tvMeiledit;
    @Bind(R.id.rl_birthAddresse)
    RelativeLayout rlBirthAddresse;
    @Bind(R.id.vew_line_three)
    View vewLineThree;
    @Bind(R.id.vew_line_fore)
    View vewLineFore;
    @Bind(R.id.tv_politicalLandscape)
    TextView tvPoliticalLandscape;
    @Bind(R.id.iv_icon4)
    ImageView ivIcon4;
    @Bind(R.id.rl_politicalLandscape)
    RelativeLayout rlPoliticalLandscape;
    @Bind(R.id.personal_view_zz)
    View personalViewZz;
    @Bind(R.id.tv_ismarryed)
    TextView tvIsmarryed;
    @Bind(R.id.iv_icon10)
    ImageView ivIcon10;
    @Bind(R.id.rl_marriage_condition)
    RelativeLayout rlMarriageCondition;
    @Bind(R.id.personal_view_hy)
    View personalViewHy;
    @Bind(R.id.tv_national)
    TextView tvNational;
    @Bind(R.id.iv_icon5)
    ImageView ivIcon5;
    @Bind(R.id.rl_national)
    RelativeLayout rlNational;
    @Bind(R.id.personal_view_mz)
    View personalViewMz;
    @Bind(R.id.tv_educational)
    TextView tvEducational;
    @Bind(R.id.iv_icon6)
    ImageView ivIcon6;
    @Bind(R.id.rl_educational)
    RelativeLayout rlEducational;
    @Bind(R.id.vew_line_five)
    View vewLineFive;
    @Bind(R.id.vew_line_six)
    View vewLineSix;
    @Bind(R.id.text_live_place)
    TextView textLivePlace;
    @Bind(R.id.tv_liveStreet)
    TextView tvLiveStreet;
    @Bind(R.id.tv_birthStreet)
    TextView tvBirthStreet;

    @Bind(R.id.tv_domicile_context)
    TextView tvDomicileContext;
    @Bind(R.id.iv_icon7)
    ImageView ivIcon7;
    @Bind(R.id.rl_domicile)

    RelativeLayout rlDomicile;
    @Bind(R.id.personal_view_jzd)
    View personalViewJzd;
    @Bind(R.id.text_birth_place)
    TextView textBirthPlace;
    @Bind(R.id.tv_birthAddress)
    TextView tvBirthAddress;
    @Bind(R.id.iv_icon8)
    ImageView ivIcon8;
    @Bind(R.id.rl_birthAddress)
    RelativeLayout rlBirthAddress;
    @Inject
    UserBasicInfoPresenter mUserBasicInfoPresenter;
    ActionSheetDialog mIsMarriedDialog;
    UserBasicInfo mUserBasicInfo;
    @Inject
    @Named("Nation")
    DataDictionaryDialog mNationDialog;
    @Inject
    @Named("Politics")
    DataDictionaryDialog mPoliticsDialog;
    @Inject
    @Named("Education")
    DataDictionaryDialog mEducationDialog;
    @Inject
    @Named("Certificate")
    DataDictionaryDialog mCertificateDialog;
    @Inject
    TimePickDialog mTimePickDialog;
    Address mBirthAddress, mLiveAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_baic_info);
        ButterKnife.bind(this);
        setupViews();
    }

    protected void setupViews() {

        tvTitle.setText(R.string.personal_basic_info);
        mUserBasicInfo = getIntent().getParcelableExtra(EXTRA_USER_BASIC_INFO_KEY);
        mLiveAddress = mUserBasicInfo.getLiveAddress();
        mBirthAddress = mUserBasicInfo.getBirthAddress();
        setUserBasicInfo(mUserBasicInfo);
        tvOther.setVisibility(View.VISIBLE);
        mIsMarriedDialog = createIsMarriedDialog(getResources().getStringArray(R.array.sheetItem_isMarried));
        mIsMarriedDialog.addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                tvIsmarryed.setText(sheetItemEntity.getItemName());

            }
        });
        DataDictionary politicsDictionary = new DataDictionary();
        politicsDictionary.setDisplayName(mUserBasicInfo.getPoliticalStatusName());
        politicsDictionary.setCode(mUserBasicInfo.getPoliticalStatus());
        tvPoliticalLandscape.setTag(politicsDictionary);
        mPoliticsDialog.setDefaultDataDictionary(politicsDictionary);
        mPoliticsDialog.setDataDictionarySelectedListener(new DataDictionaryDialog.DataDictionarySelectedListener() {


            @Override
            public void onDataDictionarySelected(DataDictionary dataDictionary) {
                tvPoliticalLandscape.setText(dataDictionary.getDisplayName());
                tvPoliticalLandscape.setTag(dataDictionary);
                mPoliticsDialog.setDefaultDataDictionary(dataDictionary);
            }
        });

        DataDictionary nationDictionary = new DataDictionary();
        nationDictionary.setDisplayName(mUserBasicInfo.getNationName());
        nationDictionary.setCode(mUserBasicInfo.getNation());
        mNationDialog.setDefaultDataDictionary(nationDictionary);
        tvNational.setTag(nationDictionary);
        mNationDialog.setDataDictionarySelectedListener(new DataDictionaryDialog.DataDictionarySelectedListener() {


            @Override
            public void onDataDictionarySelected(DataDictionary dataDictionary) {
                tvNational.setText(dataDictionary.getDisplayName());
                tvNational.setTag(dataDictionary);
                mNationDialog.setDefaultDataDictionary(dataDictionary);
            }
        });
        DataDictionary educationDictionary = new DataDictionary();
        educationDictionary.setDisplayName(mUserBasicInfo.getHighestEducationName());
        educationDictionary.setCode(mUserBasicInfo.getHighestEducation());
        tvEducational.setTag(educationDictionary);
        mEducationDialog.setDefaultDataDictionary(educationDictionary);
        mEducationDialog.setDataDictionarySelectedListener(new DataDictionaryDialog.DataDictionarySelectedListener() {


            @Override
            public void onDataDictionarySelected(DataDictionary dataDictionary) {
                tvEducational.setText(dataDictionary.getDisplayName());
                tvEducational.setTag(dataDictionary);
                mEducationDialog.setDefaultDataDictionary(dataDictionary);
            }
        });
        DataDictionary certificateDictionary = new DataDictionary();
        certificateDictionary.setDisplayName(mUserBasicInfo.getCertificateTypeName());
        certificateDictionary.setCode(mUserBasicInfo.getCertificateType());
        tvCertificateType.setTag(certificateDictionary);
        mCertificateDialog.setDefaultDataDictionary(certificateDictionary);
        mCertificateDialog.setDataDictionarySelectedListener(new DataDictionaryDialog.DataDictionarySelectedListener() {


            @Override
            public void onDataDictionarySelected(DataDictionary dataDictionary) {
                tvCertificateType.setText(dataDictionary.getDisplayName());
                tvCertificateType.setTag(dataDictionary);
                mCertificateDialog.setDefaultDataDictionary(dataDictionary);
            }
        });
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mUserBasicInfoPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).userModule(new UserModule()).activityModule(getActivityModule()).build().inject(this);
    }

    @Override
    public void setUserBasicInfo(UserBasicInfo userBasicInfo) {
        tvBirthday.setText(userBasicInfo.getBirthDate());
        tvBirthAddress.setText(userBasicInfo.getBirthPlaceName());
        tvIsmarryed.setText(userBasicInfo.getMaritalStatusName());
        tvPoliticalLandscape.setText(userBasicInfo.getPoliticalStatusName());
        tvMeiledit.setText(userBasicInfo.getMail());
        tvDomicileContext.setText(userBasicInfo.getLiveAreaName());
        tvPhoneContex.setText(userBasicInfo.getPersonalPhone());
        tvCertificateType.setText(userBasicInfo.getCertificateTypeName());
        tvCertificateNumber.setText(userBasicInfo.getCertificateNumber());
        tvEducational.setText(userBasicInfo.getHighestEducationName());
        tvNational.setText(userBasicInfo.getNationName());
        tvBirthStreet.setText(userBasicInfo.getBirthStreet());
        tvLiveStreet.setText(userBasicInfo.getLiveStreet());
    }

    @Override
    public void resultToUserMainInfoActivity() {
        setResult(RESULT_OK);
        closeSelf();
    }

    //时间选择器
    private void showSelectDateDialog() {
        mTimePickDialog.setDefaultDate(DateUtils.stringToDate(tvBirthday.getText().toString(),DateUtils.YEAR_MONTH_DAY));
        mTimePickDialog.show();
        mTimePickDialog.setOnTimeSelectedListener(new TimePickDialog.OnTimeSelectedListener() {
            @Override
            public void onTimeSelected(Date date) {
                tvBirthday.setText(DateUtils.dateToString(date, DateUtils.YEAR_MONTH_DAY));
            }
        });
    }

    @Override
    public UserBasicInfo getUserBasicInfo() {

        UserBasicInfo userBasicInfo = new UserBasicInfo();
        userBasicInfo.setBirthDate(tvBirthday.getText().toString());
        userBasicInfo.setBirthPlace(mBirthAddress.getStringCode());
        userBasicInfo.setMaritalStatus(mIsMarriedDialog.getPreviousClickItemEntity().getCode());
        userBasicInfo.setPoliticalStatus(((DataDictionary) tvPoliticalLandscape.getTag()).getCode());
        userBasicInfo.setMail(tvMeiledit.getText().toString());
        userBasicInfo.setLiveStreet(textLivePlac.getText().toString());
        userBasicInfo.setLiveAreaCode(mLiveAddress.getStringCode());
        userBasicInfo.setPersonalPhone(tvPhoneContex.getText().toString());
        userBasicInfo.setCertificateType(((DataDictionary) tvCertificateType.getTag()).getCode());
        userBasicInfo.setCertificateNumber(tvCertificateNumber.getText().toString());
        userBasicInfo.setHighestEducation(((DataDictionary) tvEducational.getTag()).getCode());
        userBasicInfo.setNation(((DataDictionary) tvNational.getTag()).getCode());
        userBasicInfo.setBirthStreet(tvBirthStreet.getText().toString());
        userBasicInfo.setLiveStreet(tvLiveStreet.getText().toString());
        return userBasicInfo;
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
    protected void onResume() {
        super.onResume();
    }


    private ActionSheetDialog createIsMarriedDialog(String[] sheetItemNames) {
        ActionSheetDialog mActionSheetDialog = new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(false)
                .setSheetItems(getGenderMarriedItemEntitys(sheetItemNames), false);
        return mActionSheetDialog;
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getGenderMarriedItemEntitys(String[] sheetItemNames) {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        if (null == sheetItemNames) {
            return itemEntities;
        }
        for(int i=0; i<sheetItemNames.length; i++){
            ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity(sheetItemNames[i], (i+1)+"", null);
            itemEntities.add(sheetItemEntity);
        }
        return itemEntities;
    }

    public static Intent getUserBasicInfoIntent(Context context, UserBasicInfo userBasicInfo) {
        Intent intent = new Intent(context, UserBasicInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_USER_BASIC_INFO_KEY, userBasicInfo);
        intent.putExtras(bundle);
        return intent;
    }

    @OnClick({R.id.iv_back, R.id.tv_other, R.id.rl_certificate_type_but, R.id.rl_certificate_number_but,
            R.id.rl_birthday, R.id.rl_domicile, R.id.rl_birthAddresse, R.id.rl_politicalLandscape, R.id.rl_marriage_condition,
            R.id.rl_national, R.id.rl_educational, R.id.rl_birthAddress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                closeSelf();
                break;
            case R.id.tv_other:
                mUserBasicInfoPresenter.setUserBasicInfo();
                break;
            case R.id.rl_certificate_type_but:
                mCertificateDialog.show();
                break;
            case R.id.rl_certificate_number_but:
                mNavigator.navigateToCertificateNumber(this, tvCertificateNumber.getText().toString().trim());
                break;
            case R.id.rl_birthday:
                showSelectDateDialog();
                break;
            case R.id.rl_domicile:
                mNavigator.navigateToLivePlace(this, mLiveAddress, tvLiveStreet.getText().toString());
                break;
            case R.id.rl_politicalLandscape:
                mPoliticsDialog.show();
                break;
            case R.id.rl_marriage_condition:
                mIsMarriedDialog.show();
                break;
            case R.id.rl_national:
                mNationDialog.show();
                break;
            case R.id.rl_educational:
                mEducationDialog.show();
                break;

            case R.id.rl_birthAddress:
                mNavigator.navigateToBirthPlace(this, mBirthAddress, tvBirthStreet.getText().toString());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode != RESULT_OK) {
            return;

        }
        switch (requestCode) {
            case REQUEST_USERINFO_CERTIFICATENUMBER_CODE:
                tvCertificateNumber.setText(data.getStringExtra(EXTRA_USER_CERTIFICATE_NUMBER_KEY));
                break;
            case REQUEST_USERINFO_LIVEPLACE_CODE:
                mLiveAddress = data.getParcelableExtra(EXTRA_USER_LIVE_AREA_KEY);
                tvLiveStreet.setText(data.getStringExtra(EXTRA_USER_LIVE_STREET_KEY));
                tvDomicileContext.setText(mLiveAddress.getName());
                break;
            case REQUEST_USERINFO__BIRTHPLACE_CODE:
                mBirthAddress = data.getParcelableExtra(EXTRA_USER_BIRTH_AREA_KEY);
                tvBirthStreet.setText(data.getStringExtra(EXTRA_USER_BIRTH_STREET_KEY));
                tvBirthAddress.setText(mBirthAddress.getName());
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
