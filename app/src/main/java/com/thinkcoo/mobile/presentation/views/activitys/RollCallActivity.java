package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.RollCallPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.RollCallView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.ShakeListener;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;
import com.thinkcoo.mobile.presentation.views.dialog.SignDialog;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RollCallActivity extends BaseActivity implements RollCallView, View.OnClickListener {


    private static final String TAG = "RollCallActivity";
    private List<String> distancesList;

    public static Intent getRollCallIntent(Context context, Event event) {

        if (null == event) {
            throw new IllegalArgumentException("Event is null");
        }
        Intent intent = new Intent(context, RollCallActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_EVENT_KEY, event);
        intent.putExtras(bundle);

        return intent;
    }

    @Inject
    RollCallPresenter mRollCallPresenter;

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titleName)
    TextView tvTitleName;
    @Bind(R.id.iv_call_more)
    ImageView ivMore;
    @Bind(R.id.shake_title_bar)
    RelativeLayout shakeTitleBar;
    @Bind(R.id.tv_rollcallactivity)
    TextView tvRollcallactivity;
    @Bind(R.id.tv_yaoyiyao)
    TextView tvYaoyiyao;
    @Bind(R.id.iv_uplogo)
    ImageView ivUplogo;
    @Bind(R.id.iv_downlogo)
    ImageView ivDownlogo;

    public static String EXTRA_EVENT_KEY;
    private ShakeListener mShakeListener = null;//摇动监听器
    private Vibrator mVibrator;//震动器
    private String[] distances;
    private Event mEvent;

    private boolean isShakedWorking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rollcall);
        ButterKnife.bind(this);
        initArguements();
        initData();
        initView();
        mShakeListener = new ShakeListener(RollCallActivity.this);
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {

            @Override
            public void onShake() {
                if (isShakedWorking) {
                    return;
                }
                closeShake();
                startAnim();
                setVibratorAndMedia();
                ThinkcooLog.e(TAG, "=============OnShake================");
                if (mEvent.isCreateAuthor) {
                    mRollCallPresenter.ownerShakeAndShake(mEvent, initUuid());
                } else {
                    mRollCallPresenter.signerShakeAndShake(mEvent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShakedWorking = false;
    }

    private void closeShake() {
        isShakedWorking = true;
    }

    private void initArguements() {
        mEvent = getIntent().getExtras().getParcelable(EXTRA_EVENT_KEY);
    }

    private String initUuid() {
        // 初始化生成UUID
        UUID uid = UUID.randomUUID();
        String uuid = uid.toString();
        return uuid;
    }


    // 摇一摇开始的动画
    private void startAnim() {
        // 向上动画
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.22f);
        mytranslateanimup0.setDuration(500);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.22f);
        mytranslateanimup1.setDuration(500);
        mytranslateanimup1.setStartOffset(1000);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        ivUplogo.startAnimation(animup);

        // 向下
        AnimationSet animdown = new AnimationSet(true);
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.22f);
        mytranslateanimdn0.setDuration(500);
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.22f);
        mytranslateanimdn1.setDuration(500);
        mytranslateanimdn1.setStartOffset(1000);
        animdown.addAnimation(mytranslateanimdn0);
        animdown.addAnimation(mytranslateanimdn1);
        ivDownlogo.startAnimation(animdown);
    }

    private void setVibratorAndMedia() {
        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);
        MediaPlayer player = MediaPlayer.create(this, R.raw.rockcall);
        // 循环播放
        player.setLooping(false);
        player.start();
        mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1);//{节奏数组}， -1不重复
    }

    private void initView() {
        if (mEvent.isCreateAuthor) {
            tvTitleName.setText(R.string.sc_call);  //活动创建者
            tvRollcallactivity.setText(mEvent.title); // 获取的事件的信息
            // 创建者点名
            tvYaoyiyao.setText(R.string.rocktocallname);
            ivMore.setVisibility(View.VISIBLE);
            //   mNavigator.navigateToRockCallResult(this);
        } else {
            ivMore.setVisibility(View.GONE);
            // 活动参与者
            tvTitleName.setText(R.string.sc_sign);
            tvRollcallactivity.setText(mEvent.title); // 获取的事件的信息
            tvYaoyiyao.setText(R.string.rocktocallsign);  //摇一摇开始签到


        }

        // 签到失败的弹窗dagio的显示
        SignDialog signDialog = new SignDialog(this, mGlobalToast);

    }

    private void initData() {
        // 设置的点名的半径  30,50,70
        distances = getResources().getStringArray(R.array.rockcall_distance);
        distancesList = Arrays.asList(distances);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mRollCallPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerScheduleComponent.builder().scheduleModule(new ScheduleModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_call_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_call_more:
                showSelectDistanceDialog();
                break;
        }
    }

    private void showSelectDistanceDialog() {
        {
            ActionSheetDialog mActionSheetDialog = null;
            if (null != mActionSheetDialog) {
                mActionSheetDialog.show();
                return;
            }
            mActionSheetDialog = new ActionSheetDialog(this)
                    .builder()
                    .setCancelable(true)
                    .setCanceledOnTouchOutside(false)
                    .setSheetItems(getSheetItemEntitys(distances), false)
                    .addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                            showSelectTypeByType(sheetItemEntity);
                        }
                    });
            mActionSheetDialog.show();
        }
    }

    private void showSelectTypeByType(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
        String selectedDistanc = sheetItemEntity.getItemName();
        // 更新半径 // TODO: 2016/8/13
        mRollCallPresenter.updateRadiu(mEvent.createdUser.getUserId(), mEvent.scheduleId, selectedDistanc);
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntitys(String[] scheduleTypes) {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        if (null == scheduleTypes) {
            return itemEntities;
        }
        for (int i = 0; i < scheduleTypes.length; i++) {
            ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity(scheduleTypes[i], i + "", null);
            itemEntities.add(sheetItemEntity);
        }
        return itemEntities;
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
    public void showToast(String errorMsg) {
        mGlobalToast.showLongToast(errorMsg);

    }

    @Override
    protected void onStop() {
        super.onStop();
        isShakedWorking = true;
//        if (mShakeListener != null) {
//            mShakeListener.stop();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
        mShakeListener = null;
        mVibrator = null;
    }

    @Override
    public void gotoRollCallResultPage(String uuid) {
        mNavigator.navigateToRockCallSingleReslut(this, uuid, mEvent.scheduleId);
    }

    @Override
    public void reOpenShake() {
        isShakedWorking = false;
    }

}
