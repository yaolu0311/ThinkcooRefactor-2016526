package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.CreatActivityGroupPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.CreateActivityGroupView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.Navigator;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateActivityGroupActivity extends BaseActivity implements CreateActivityGroupView {
    public static final int REQUEST_CREATE_ACTIVITY_CODE = 0x00001;
    @Inject
    CreatActivityGroupPresenter mCreatActivityGroupPresenter;
    @Bind(R.id.editText_content)
    EditText editTextContent;
    @Bind(R.id.text_confirm)
    TextView textConfirm;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Inject
    Navigator mNavigator;
    Event mEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_activity_group);
        ButterKnife.bind(this);
        setupViews();
    }

    private void setupViews() {
        mEvent = getIntent().getParcelableExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mCreatActivityGroupPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerScheduleComponent.builder().scheduleModule(new ScheduleModule()).applicationComponent(getApplicationComponent()).build().inject(this);
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



    @OnClick({R.id.iv_back, R.id.text_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.text_confirm:
                mCreatActivityGroupPresenter.createClass(mEvent.scheduleId,"",editTextContent.getText().toString());
                break;
        }
    }
    public static Intent getCreateActivityGroupIntent(Context context, Event event) {
        Intent intent = new Intent(context, CreateActivityGroupActivity.class);
        intent.putExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY, event);
        return intent;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }

    }
    @Override
    public void toActiveMemberActivity(String groupid) {
    mNavigator.navigateToActiveMember(this,mEvent,groupid);
    }
}
