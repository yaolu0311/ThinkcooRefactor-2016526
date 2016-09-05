package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.model.exception.ServerResponseException;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserMainInfoPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.UserMainInfoView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.Navigator;
import com.thinkcoo.mobile.utils.PhotoUriUtils;
import com.thinkcoo.mobile.utils.TakePhotoUtils;
import com.yz.im.utils.GlideRoundTransform;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class UserMainInfoActivity extends BaseActivity implements UserMainInfoView,TakePhotoUtils.TakePhotoListener{

    public static final String EXTRA_USER_SIGNATURE_KEY = "extra_user_signature_key";
    public static final String EXTRA_USER_NAME_KEY = "extra_user_name_key";
    public static final int REQUEST_USER_INFO_NAME_CODE = 1;
    public static final int REQUEST_USER_INFO_SINGATURE_CODE = 2;
    public static final int REQUEST_USER_INFO_CODE = 3;

    @Inject
    public Navigator mNavigator;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
     @Bind(R.id.iv_more)
    ImageView ivMore;
    @Bind(R.id.iv_headPortrait)
    ImageView ivheadPortrait;
    @Bind(R.id.iv_icon1)
    ImageView ivIcon1;
    @Bind(R.id.rl_updata_headPortrait)
    RelativeLayout rlUpdataHeadPortrait;
    @Bind(R.id.personal_view_tm)
    View personalViewTm;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.tv_signature_context)
    TextView tvSignatureContext;
    @Bind(R.id.iv_icon2)
    ImageView ivIcon2;
    @Bind(R.id.rl_myInfo_signature)
    RelativeLayout rlMyInfoSignature;
    @Bind(R.id.personal_view_qm)
    View personalViewQm;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.imageView1)
    ImageView imageView1;
    @Bind(R.id.tv_myinfo_username)
    TextView tvMyinfoUsername;
    @Bind(R.id.rl_myInfo_username)
    RelativeLayout rlMyInfoUsername;
    @Bind(R.id.personal_view_xm)
    View personalViewXm;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.tv_myinfo_sex)
    TextView tvMyinfoSex;
    @Bind(R.id.rl_myInfo_sex)
    RelativeLayout rlMyInfoSex;
    @Bind(R.id.line_10)
    View line10;
    @Bind(R.id.line_11)
    View line11;
    @Bind(R.id.rl_more_info)
    RelativeLayout rlMoreInfo;
    @Bind(R.id.personal_view_tem)
    View personalViewTem;
    @Inject
    UserMainInfoPresenter mUserMainInfoPresenter;
    ActionSheetDialog mGenderDialog,mChoosePhotos;
    UserBasicInfo mUserBasicInfo;

    @Inject
    TakePhotoUtils mTakePhotoUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_info);
        ButterKnife.bind(this);
        setupViews();
        mUserMainInfoPresenter.getUserBasicInfo(false);
        mTakePhotoUtils.init(this,this);
        mTakePhotoUtils.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTakePhotoUtils.release();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mTakePhotoUtils.onSaveInstanceState(outState);
    }

    protected void setupViews() {
        tvTitle.setText(R.string.personal_tittle);
        //mUserMainInfoPresenter.getUserBasicInfo();这段代码不是在干view相关的事，放在这里不合适
        mGenderDialog = createGenderDialog(getResources().getStringArray(R.array.sheetItem_genders));

        mGenderDialog.addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                mUserMainInfoPresenter.changeSex(sheetItemEntity.getItemName());
            }
        });
        mChoosePhotos = createGenderDialog(getResources().getStringArray(R.array.sheetItem_photos));
        mChoosePhotos.addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {

                switch (sheetItemEntity.getCode()){
                    case "0":
                        mTakePhotoUtils.takePhotoAndCrop();
                        break;
                    case "1":
                        mTakePhotoUtils.singleSelectPhotoAndCrop();

                        break;
                    case "2":
                        break;
                    default:
                        throw new IllegalArgumentException("unknown sheetItemEntity code");
                }
            }
        });

    }


    @Override
    public void setUserMainInfo(UserBasicInfo userBasicInfo) {
        mUserBasicInfo = userBasicInfo;
        Glide.with(this).load(userBasicInfo.getHeadPortrait()).transform(new GlideRoundTransform(this)).placeholder(R.mipmap.default_person_imagemin).into(ivheadPortrait);
        tvMyinfoUsername.setText(userBasicInfo.getFullName());
        tvSignatureContext.setText(userBasicInfo.getSignature());
        tvMyinfoSex.setText(userBasicInfo.getSex());
    }

    @Override
    public String getSelectSex() {
        return mGenderDialog.getPreviousClickItemEntity().getItemName();
    }
    @Override
    protected MvpPresenter generatePresenter() {
        return mUserMainInfoPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).userModule(new UserModule()).build().inject(this);
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
    @OnClick({R.id.iv_back, R.id.rl_updata_headPortrait, R.id.rl_myInfo_signature, R.id.rl_myInfo_username, R.id.rl_myInfo_sex, R.id.rl_more_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                closeSelf();
                break;
            case R.id.rl_updata_headPortrait:
                mChoosePhotos.show();
                break;
            case R.id.rl_myInfo_signature:
                mNavigator.navigateToSignature(this,tvSignatureContext.getText().toString());
                break;
            case R.id.rl_myInfo_username:
                mNavigator.navigateToChangeName(this,tvMyinfoUsername.getText().toString().trim());
                break;
            case R.id.rl_myInfo_sex:
                mGenderDialog.show();
                break;
            case R.id.rl_more_info:
                mNavigator.navigateToUserBasicInfo(this,mUserBasicInfo);
                break;
        }
    }
    private ActionSheetDialog createGenderDialog(String[] sheetItemNames) {
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(false)
                .setSheetItems(getGenderSheetItemEntitys(sheetItemNames), false);
       return actionSheetDialog;
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getGenderSheetItemEntitys(String[] sheetItemNames) {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        if (null == sheetItemNames) {
            return itemEntities;
        }
        for(int i=0; i<sheetItemNames.length; i++){
            ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity(sheetItemNames[i], i+"", null);
            itemEntities.add(sheetItemEntity);
        }
        return itemEntities;
    }

    public static Intent getUserMainInfoIntent(Context context) {
        Intent intent = new Intent(context,UserMainInfoActivity.class);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTakePhotoUtils.onActivityResult(requestCode,resultCode,data);
        if(resultCode!=RESULT_OK){
            return;
        }
        mUserMainInfoPresenter.getUserBasicInfo(true);
    }

    @Override
    public void onSuccess(List<String>resultList ) {
        mUserMainInfoPresenter.uploadPhoto(resultList.get(0));
    }

    @Override
    public void onFailure(String errorMsg) {

    }
}
