package com.thinkcoo.mobile.presentation.views.component.viewBean;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.TrainDetail;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.entity.UserStatusDetail;
import com.thinkcoo.mobile.presentation.views.component.TimePickDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/2.
 */
public class UserTrainStatusContentView extends UserStatusBaseContentView {

    @Bind(R.id.train_time_text)
    TextView mTrainTimeText;
    @Bind(R.id.train_time)
    RelativeLayout mTrainTime;
    @Bind(R.id.train_organization_text)
    EditText mTrainOrganizationText;
    @Bind(R.id.train_organization_id_text)
    EditText mTrainOrganizationIdText;
    @Bind(R.id.train_class_text)
    EditText mTrainClassText;
    @Bind(R.id.ev_train_content_detail)
    EditText mEvTrainContentDetail;
    @Bind(R.id.train_time_flag)
    ImageView mTrainTimeFlag;
    @Bind(R.id.train_detail)
    RelativeLayout mTrainLayout;

    public UserTrainStatusContentView(Context mContext, int type, TimePickDialog timePickDialog) {
        super(mContext, timePickDialog);
        this.mActivityType = type;
    }

    @Override
    protected void initRootView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.user_training_status_detail, null);
    }

    @Override
    protected void changeViewByMode() {
        mTrainLayout.setFocusable(true);
        mTrainLayout.setFocusableInTouchMode(true);
        mTrainTimeFlag.setVisibility(View.GONE);
    }

    @Override
    public UserStatus getUserStatus() {
        UserStatus userStatus = new UserStatus();
        userStatus.setType(mActivityType);
        userStatus.setStartTime(mTrainTimeText.getText().toString().trim());
        userStatus.setTitle(mTrainOrganizationText.getText().toString().trim());
        userStatus.setExtraInfo(mEvTrainContentDetail.getText().toString().trim());

        TrainDetail trainDetail = new TrainDetail();
        trainDetail.setStudentId(mTrainOrganizationIdText.getText().toString().trim());
        trainDetail.setClassNumber(mTrainClassText.getText().toString().trim());

        userStatus.setUserStatusDetail(trainDetail);
        return userStatus;
    }

    @Override
    public void showBasicInfo(UserStatus userStatus) {
        if (null == userStatus) {
            return;
        }
        mTrainTimeText.setText(userStatus.getStartTime());
        mTrainOrganizationText.setText(userStatus.getTitle());
        mEvTrainContentDetail.setText(userStatus.getExtraInfo());
        setEditTextSelection(mEvTrainContentDetail);
        setEditTextSelection(mTrainOrganizationText);
    }

    @Override
    public void showDetailsInfo(UserStatusDetail userStatusDetail) {
        if (null == userStatusDetail) {
            return;
        }
        TrainDetail data = (TrainDetail) userStatusDetail;
        mTrainClassText.setText(data.getClassNumber());
        mTrainOrganizationIdText.setText(data.getStudentId());
        setEditTextSelection(mTrainClassText);
        setEditTextSelection(mTrainOrganizationIdText);
    }

    @Override
    public void handActivityResult(Intent intent, int requestCode, int resultCode) {

    }

    @OnClick({R.id.train_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.train_time:
                showSelectDateDialog(mContext, mTrainTimeText);
                break;
        }
    }
}
