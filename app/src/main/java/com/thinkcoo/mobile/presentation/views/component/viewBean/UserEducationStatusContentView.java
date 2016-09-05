package com.thinkcoo.mobile.presentation.views.component.viewBean;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.DataDictionary;
import com.thinkcoo.mobile.model.entity.EducationDetail;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.entity.UserStatusDetail;
import com.thinkcoo.mobile.model.strategy.SingleLineEditAndAutoHintStrategyFactory;
import com.thinkcoo.mobile.presentation.views.activitys.UserStatusDetailActivity;
import com.thinkcoo.mobile.presentation.views.component.DataDictionaryDialog;
import com.thinkcoo.mobile.presentation.views.component.TimePickDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/2.
 */
public class UserEducationStatusContentView extends UserStatusBaseContentView {


    @Bind(R.id.education_time_text)
    TextView mEducationTimeText;
    @Bind(R.id.education_time)
    RelativeLayout mEducationTime;
    @Bind(R.id.education_school_text)
    TextView mEducationSchoolText;
    @Bind(R.id.education_school)
    RelativeLayout mEducationSchool;
    @Bind(R.id.education_department_text)
    TextView mEducationDepartmentText;
    @Bind(R.id.education_department)
    RelativeLayout mEducationDepartment;
    @Bind(R.id.education_major_text)
    TextView mEducationMajorText;
    @Bind(R.id.education_major)
    RelativeLayout mEducationMajor;
    @Bind(R.id.education_class_text)
    EditText mEducationClassText;
    @Bind(R.id.education_student_id_text)
    EditText mEducationStudentIdText;
    @Bind(R.id.education_job_text)
    EditText mEducationJobText;
    @Bind(R.id.education_level_text)
    TextView mEducationLevelText;
    @Bind(R.id.education_level)
    RelativeLayout mEducationLevel;
    @Bind(R.id.education_time_flag)
    ImageView mEducationTimeFlag;
    @Bind(R.id.education_school_flag)
    ImageView mEducationSchoolFlag;
    @Bind(R.id.education_department_flag)
    ImageView mEducationDepartmentFlag;
    @Bind(R.id.education_major_flag)
    ImageView mEducationMajorFlag;
    @Bind(R.id.education_level_flag)
    ImageView mEducationLevelFlag;
    @Bind(R.id.education_detail)
    RelativeLayout mEducationDetail;

    private DataDictionaryDialog mDataDictionaryDialog;

    public UserEducationStatusContentView(Context context, int type, DataDictionaryDialog dataDictionaryDialog, TimePickDialog timePickDialog) {
        super(context, timePickDialog);
        mActivityType = type;
        this.mDataDictionaryDialog = dataDictionaryDialog;
    }

    @Override
    public void showBasicInfo(UserStatus userStatus) {
        if (null == userStatus) {
            return;
        }

        mEducationSchoolText.setText(userStatus.getTitle());
        mEducationTimeText.setText(userStatus.getStartTime());
        mEducationLevelText.setText(userStatus.getExtraInfo());
    }

    @Override
    public void showDetailsInfo(UserStatusDetail userStatusDetail) {
        if (null == userStatusDetail) {
            return;
        }
        EducationDetail data = (EducationDetail) userStatusDetail;
        mEducationDepartmentText.setText(data.getDepartment());
        mEducationMajorText.setText(data.getMajor());
        mEducationClassText.setText(data.getClassNumber());
        mEducationStudentIdText.setText(data.getStudentId());
        mEducationJobText.setText(data.getPostName());
        setEditTextSelection(mEducationClassText);
        setEditTextSelection(mEducationStudentIdText);
        setEditTextSelection(mEducationJobText);
    }

    @Override
    protected void initRootView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.user_education_status_layout, null);
    }

    @Override
    protected void changeViewByMode() {
        mEducationDetail.setFocusable(true);
        mEducationDetail.setFocusableInTouchMode(true);
        mEducationTimeFlag.setVisibility(View.GONE);
        mEducationSchoolFlag.setVisibility(View.GONE);
        mEducationDepartmentFlag.setVisibility(View.GONE);
        mEducationMajorFlag.setVisibility(View.GONE);
        mEducationLevelFlag.setVisibility(View.GONE);
    }

    @Override
    public UserStatus getUserStatus() {
        UserStatus userStatus = new UserStatus();
        userStatus.setType(mActivityType);
        userStatus.setStartTime(mEducationTimeText.getText().toString().trim());
        userStatus.setTitle(mEducationSchoolText.getText().toString().trim());
        userStatus.setExtraInfo(mEducationLevelText.getText().toString().trim());

        EducationDetail educationDetail = getEducationDetail();
        userStatus.setUserStatusDetail(educationDetail);
        return userStatus;
    }

    @NonNull
    private EducationDetail getEducationDetail() {
        EducationDetail educationDetail = new EducationDetail();
        educationDetail.setDepartment(mEducationDepartmentText.getText().toString().trim());
        educationDetail.setMajor(mEducationMajorText.getText().toString().trim());
        educationDetail.setClassNumber(mEducationClassText.getText().toString().trim());
        educationDetail.setStudentId(mEducationStudentIdText.getText().toString().trim());
        educationDetail.setPostName(mEducationJobText.getText().toString().trim());
        return educationDetail;
    }

    @Override
    public void handActivityResult(Intent intent, int requestCode, int resultCode) {
        if (resultCode != ((UserStatusDetailActivity) mContext).RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case SingleLineEditAndAutoHintStrategyFactory.SINGLE_LINE_EDIT_TYPE_SCHOOL_SEARCH:
                mEducationSchoolText.setText(getItemContentFromIntent(intent).getDisplayName());
                mEducationSchoolText.setTag(getItemContentFromIntent(intent));
                break;
            case SingleLineEditAndAutoHintStrategyFactory.SINGLE_LINE_EDIT_TYPE_SCHOOL_DEPARTMENT_SEARCH:
                mEducationDepartmentText.setText(getItemContentFromIntent(intent).getDisplayName());
                mEducationDepartmentText.setTag(getItemContentFromIntent(intent));
                break;
            case SingleLineEditAndAutoHintStrategyFactory.SINGLE_LINE_EDIT_TYPE_SCHOOL_MAJOR_SEARCH:
                mEducationMajorText.setText(getItemContentFromIntent(intent).getDisplayName());
                mEducationMajorText.setTag(getItemContentFromIntent(intent));
                break;
        }
    }

    @OnClick({R.id.education_time, R.id.education_school, R.id.education_major, R.id.education_department, R.id.education_level})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.education_time:
                showSelectDateDialog(mContext, mEducationTimeText);
                break;
            case R.id.education_school:
                String editText = mEducationSchoolText.getText().toString().trim();
                ((UserStatusDetailActivity) mContext).mNavigator.navigateToSingleLineEditAndAutoHintActivity(mContext, SingleLineEditAndAutoHintStrategyFactory.SINGLE_LINE_EDIT_TYPE_SCHOOL_SEARCH, editText);
                break;
            case R.id.education_department:
                String department = mEducationDepartmentText.getText().toString().trim();
                ((UserStatusDetailActivity) mContext).mNavigator.navigateToDepartmentEditActivity(mContext, SingleLineEditAndAutoHintStrategyFactory.SINGLE_LINE_EDIT_TYPE_SCHOOL_DEPARTMENT_SEARCH, department,mEducationSchoolText.getText().toString().trim());
                break;
            case R.id.education_major:
                String major = mEducationMajorText.getText().toString().trim();
                ((UserStatusDetailActivity) mContext).mNavigator.navigateToSingleLineEditAndAutoHintActivity(mContext, SingleLineEditAndAutoHintStrategyFactory.SINGLE_LINE_EDIT_TYPE_SCHOOL_MAJOR_SEARCH, major);
                break;
            case R.id.education_level:
                setAndShowDictionaryDialog();
                break;
        }
    }

    private void setAndShowDictionaryDialog() {
        mDataDictionaryDialog.setDataDictionarySelectedListener(new DataDictionaryDialog.DataDictionarySelectedListener() {
            @Override
            public void onDataDictionarySelected(DataDictionary dataDictionary) {
                mEducationLevelText.setText(dataDictionary.getDisplayName());
            }
        });
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setDisplayName(mEducationLevelText.getText().toString().trim());
        mDataDictionaryDialog.setDefaultDataDictionary(dataDictionary);
        mDataDictionaryDialog.show();
    }

}
