package com.thinkcoo.mobile.presentation.views.component.viewBean;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.FullTimeJobDetail;
import com.thinkcoo.mobile.model.entity.IndustryItemEntity;
import com.thinkcoo.mobile.model.entity.PartTimeJobDetail;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.entity.UserStatusDetail;
import com.thinkcoo.mobile.model.strategy.SingleLineEditStrategyFactory;
import com.thinkcoo.mobile.presentation.views.activitys.IndustryDirectionActivity;
import com.thinkcoo.mobile.presentation.views.activitys.UserStatusDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseDetailActivity;
import com.thinkcoo.mobile.presentation.views.component.TimePickDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/2.
 */
public class UserWorkStatusContentView extends UserStatusBaseContentView {

    public static final int REQUEST_INDUSTRY_DIRECTORY = 0X0013;
    public static final int REQUEST_JOB_RESPONSIBILITY = 0X0014;
    public static final int REQUEST_EXPERIENCE = 0X0015;

    @Bind(R.id.work_time_text)
    TextView mWorkTimeText;
    @Bind(R.id.work_time)
    RelativeLayout mWorkTime;
    @Bind(R.id.work_unit_text)
    EditText mWorkUnitText;
    @Bind(R.id.work_employee_id_text)
    EditText mWorkEmployeeIdText;
    @Bind(R.id.work_business_text)
    TextView mWorkBusinessText;
    @Bind(R.id.work_business)
    RelativeLayout mWorkBusiness;
    @Bind(R.id.work_department_text)
    EditText mWorkDepartmentText;
    @Bind(R.id.work_job_text)
    EditText mWorkJobText;
    @Bind(R.id.gwzz_work_time_textt)
    TextView mGwzzWorkTimeTextt;
    @Bind(R.id.gwzz_work_num)
    RelativeLayout mGwzzWorkNum;
    @Bind(R.id.work_experience)
    RelativeLayout mWorkExperience;
    @Bind(R.id.ac_work_time_flag)
    ImageView mAcWorkTimeFlag;
    @Bind(R.id.ac_work_name_two_flag)
    ImageView mAcWorkNameTwoFlag;
    @Bind(R.id.ac_work_num_flag)
    ImageView mAcWorkNumFlag;
    @Bind(R.id.work_detail)
    RelativeLayout mWorkLayout;

    private List<IndustryItemEntity> mIndustryItemEntities;
    private String workId;

    public UserWorkStatusContentView(Context mContext, int type, TimePickDialog timePickDialog) {
        super(mContext, timePickDialog);
        mActivityType = type;
    }

    @Override
    protected void initRootView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.user_work_status_layout, null);
    }

    @Override
    protected void changeViewByMode() {
        mWorkLayout.setFocusable(true);
        mWorkLayout.setFocusableInTouchMode(true);
        mAcWorkTimeFlag.setVisibility(View.GONE);
        mAcWorkNameTwoFlag.setVisibility(View.GONE);
        mAcWorkNumFlag.setVisibility(View.GONE);
    }

    @Override
    public UserStatus getUserStatus() {
        UserStatus userStatus = new UserStatus();
        userStatus.setType(mActivityType);
        userStatus.setStartTime(mWorkTimeText.getText().toString().trim());
        userStatus.setTitle(mWorkUnitText.getText().toString().trim());
        userStatus.setExtraInfo(mWorkJobText.getText().toString().trim());

        switch (mActivityType) {
            case UserStatusDetailActivity.FULL_TIME_WORK_STATUS_TYPE:
                getFullTimeJobEntity(userStatus);
                break;
            case UserStatusDetailActivity.PART_TIME_WORK_STATUS_TYPE:
                getPartTimeJobEntity(userStatus);
                break;
        }

        return userStatus;
    }

    private void getFullTimeJobEntity(UserStatus userStatus) {
        FullTimeJobDetail fullTimeJobDetail = new FullTimeJobDetail();
        fullTimeJobDetail.setEmployerId(mWorkEmployeeIdText.getText().toString().trim());
        fullTimeJobDetail.setIndustry_direction(mWorkBusinessText.getText().toString().trim());
        fullTimeJobDetail.setBranchName(mWorkDepartmentText.getText().toString().trim());
        fullTimeJobDetail.setResponsibility(mGwzzWorkTimeTextt.getText().toString().trim());
        userStatus.setUserStatusDetail(fullTimeJobDetail);
    }

    private void getPartTimeJobEntity(UserStatus userStatus) {
        PartTimeJobDetail partTimeJobDetail = new PartTimeJobDetail();
        partTimeJobDetail.setEmployerId(mWorkEmployeeIdText.getText().toString().trim());
        partTimeJobDetail.setIndustry_direction(mWorkBusinessText.getText().toString().trim());
        partTimeJobDetail.setBranchName(mWorkDepartmentText.getText().toString().trim());
        partTimeJobDetail.setResponsibility(mGwzzWorkTimeTextt.getText().toString().trim());
        userStatus.setUserStatusDetail(partTimeJobDetail);
    }

    @Override
    public void showBasicInfo(UserStatus userStatus) {
        workId = userStatus.getId();
        mWorkTimeText.setText(userStatus.getStartTime());
        mWorkUnitText.setText(userStatus.getTitle());
        mWorkJobText.setText(userStatus.getExtraInfo());
        setEditTextSelection(mWorkUnitText);
        setEditTextSelection(mWorkJobText);
    }

    @Override
    public void showDetailsInfo(UserStatusDetail userStatusDetail) {
        FullTimeJobDetail data = (FullTimeJobDetail) userStatusDetail;
        mWorkEmployeeIdText.setText(data.getEmployerId());
        mWorkBusinessText.setText(data.getIndustry_direction());
        mWorkDepartmentText.setText(data.getBranchName());
        mGwzzWorkTimeTextt.setText(data.getResponsibility());
        setEditTextSelection(mWorkEmployeeIdText);
        setEditTextSelection(mWorkDepartmentText);
    }

    @Override
    public void handActivityResult(Intent intent, int requestCode, int resultCode) {
        if (resultCode != ((UserStatusDetailActivity) mContext).RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_INDUSTRY_DIRECTORY:
                getIndustryList(intent);
                mWorkBusinessText.setText(getIndustryNames());
                break;
            case SingleLineEditStrategyFactory.SINGLE_LINE_EDIT_TYPE_JOB_RESPONSIBILITY:
                mGwzzWorkTimeTextt.setText(getItemContentFromIntent(intent).getDisplayName());
                break;
            case REQUEST_EXPERIENCE:
                break;
        }
    }

    private void getIndustryList(Intent intent) {
        if (null == intent) {
            return;
        }
        mIndustryItemEntities = intent.getParcelableArrayListExtra(IndustryDirectionActivity.KEY_DATA_DICTIONARY_LIST);
    }

    @NonNull
    private String getIndustryNames() {
        if (null == mIndustryItemEntities) {
            return mWorkBusinessText.getText().toString().trim();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (IndustryItemEntity itemEntity : mIndustryItemEntities) {
            stringBuilder.append(itemEntity.getDisplayName()).append(";");
        }
        return splitTheLaseSign(stringBuilder, ";");
    }

    private String getIndustryCodes() {
        if (null == mIndustryItemEntities || mIndustryItemEntities.size() == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (IndustryItemEntity itemEntity : mIndustryItemEntities) {
            stringBuilder.append(itemEntity.getCode()).append(",");
        }
        return splitTheLaseSign(stringBuilder, ",");
    }

    @NonNull
    private String splitTheLaseSign(StringBuilder stringBuilder, String sign) {
        if (null == stringBuilder) {
            return "";
        }
        if (TextUtils.isEmpty(sign)) {
            return stringBuilder.toString();
        }
        String result = stringBuilder.toString();
        if (result.endsWith(sign)) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    @OnClick({R.id.work_time, R.id.work_business, R.id.gwzz_work_num, R.id.work_experience})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.work_time:
                showSelectDateDialog(mContext, mWorkTimeText);
                break;
            case R.id.work_business:  //行业方向
                ((UserStatusDetailActivity) mContext).mNavigator.navigateToIndustryDirectionPage(mContext, mWorkBusinessText.getText().toString().trim());
                break;
            case R.id.gwzz_work_num:
                String responsibility = mGwzzWorkTimeTextt.getText().toString().trim();
                ((UserStatusDetailActivity) mContext).mNavigator.navigateToSingleLineEditActivity(mContext, SingleLineEditStrategyFactory.SINGLE_LINE_EDIT_TYPE_JOB_RESPONSIBILITY, responsibility);
                break;
            case R.id.work_experience:
                if (mActivityMode == BaseDetailActivity.ADD_MODE) {
                    ((UserStatusDetailActivity) mContext).showToast(mContext.getString(R.string.work_experience_notice));
                    return;
                }
                ((UserStatusDetailActivity) mContext).mNavigator.navigateToUserWorkExperiencePage(mContext, workId);
                break;
        }
    }
}
