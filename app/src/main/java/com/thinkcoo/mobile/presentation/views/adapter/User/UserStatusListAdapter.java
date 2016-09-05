package com.thinkcoo.mobile.presentation.views.adapter.User;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDateStringSpliter;
import com.thinkcoo.mobile.presentation.views.component.SlipButton;
import com.thinkcoo.mobile.utils.PublicUIUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserStatusListAdapter extends BaseAdapter {

    private static final String IS_ALLOW_DELETE = "1";
    private Context context;
    /**
     * 1-显示, 2-隐藏
     */
    private boolean isEditMode = false;
    private List<UserStatus> mStatusList = null;

    private UserStatusListCallBack mUserStatusListCallBack;

    PublicUIUtil mPublicUIUtil;

    public UserStatusListAdapter(Context context, List<UserStatus> mStatusList, UserStatusListCallBack mUserStatusListCallBack,PublicUIUtil publicUiUtil) {
        super();
        this.context = context;
        this.mStatusList = mStatusList;
        this.mUserStatusListCallBack = mUserStatusListCallBack;
        this.mPublicUIUtil = publicUiUtil;
    }

    public void setData(List<UserStatus> mStatusList) {
        this.mStatusList = mStatusList;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    @Override
    public int getCount() {
        return null == mStatusList ? 0 : mStatusList.size();
    }

    @Override
    public Object getItem(int position) {
        return null == mStatusList ? 0 : mStatusList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holderView;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_status_layout, null);
            holderView = new ViewHolder(convertView);
            convertView.setTag(holderView);
        } else {
            holderView = (ViewHolder) convertView.getTag();
        }

        UserStatus userStatus = mStatusList.get(position);
        initView(holderView, userStatus);

        setDeleteListener(holderView, position);
        setToggleListener(holderView, position);

        return convertView;
    }

    private void initView(ViewHolder holderView, UserStatus userStatus) {
        holderView.mSlipButton.setLayoutParams(mPublicUIUtil.setLayout(context));
        holderView.mTvTitle.setText(userStatus.getTitle());
        holderView.mTvExtraInfo.setText(userStatus.getExtraInfo());

        setStartTime(holderView, userStatus);

        String deleteFlag = userStatus.getDeleteFlag();// 7天内是否可以删除（1可以/0不）
        if (isCanDelete(deleteFlag)) {
            holderView.mDeleteStatus.setVisibility(View.VISIBLE);
        } else {
            holderView.mDeleteStatus.setVisibility(View.GONE);
        }
        setOpenStatus(holderView, userStatus.isOpen());
    }

    private boolean isCanDelete(String deleteFlag) {
        if (TextUtils.isEmpty(deleteFlag)) {
            return false;
        }
        return IS_ALLOW_DELETE.equals(deleteFlag) && isEditMode;
    }

    private void setToggleListener(ViewHolder holderView, final int position) {
        if (isEditMode) {
            return;
        }
        holderView.mSlipButton.SetOnChangedListener(new SlipButton.OnChangedListener() {

            @Override
            public void OnChanged(boolean CheckState) {
                mUserStatusListCallBack.changeUserStatusOpenStatus(mStatusList.get(position), position, CheckState);
            }
        });
    }

    private void setDeleteListener(ViewHolder holderView, final int position) {
        if (isEditMode) {
            return;
        }
        holderView.mDeleteStatus
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mUserStatusListCallBack.deleteUserStatus(mStatusList.get(position), position);
                    }
                });
    }

    private void setStartTime(ViewHolder holderView, UserStatus userStatus) {
        ServerDateStringSpliter serverDateStringSpliter = ServerDateStringSpliter.createByServerDateString(userStatus.getStartTime());
        holderView.mTvDay.setText(serverDateStringSpliter.getDay());

        StringBuilder yearAndMonthBuild = new StringBuilder();
        yearAndMonthBuild.append(serverDateStringSpliter.getMonth()).append(context.getString(R.string.month)).append("\n");
        if (!isEqualCurrentYear(serverDateStringSpliter.getYear())) {
            yearAndMonthBuild.append(serverDateStringSpliter.getYear()).append(context.getString(R.string.year));
        }
        holderView.mTvYearMonth.setText(yearAndMonthBuild.toString());
    }

    private boolean isEqualCurrentYear(String year) {
        if (TextUtils.isEmpty(year) || year.length() < 4) {
            return false;
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int startYear = Integer.valueOf(year);
        return currentYear == startYear ? true : false;
    }

    private void setOpenStatus(ViewHolder holderView, boolean isOpen) {
        if (!isOpen) {
            holderView.mSlipButton.setChecked(false);
        } else {
            holderView.mSlipButton.setChecked(true);
        }
    }

    static class ViewHolder {
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_extra_info)
        TextView mTvExtraInfo;
        @Bind(R.id.delete_status)
        ImageView mDeleteStatus;
        @Bind(R.id.slipButton)
        SlipButton mSlipButton;
        @Bind(R.id.tv_year_month)
        TextView mTvYearMonth;
        @Bind(R.id.tv_day)
        TextView mTvDay;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface UserStatusListCallBack {
        void deleteUserStatus(UserStatus userStatus, int position);

        void changeUserStatusOpenStatus(UserStatus userStatus, int position, boolean isOpen);
    }
}
