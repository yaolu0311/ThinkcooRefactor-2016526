package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserStatusPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.UserStatusView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.adapter.User.UserStatusListAdapter;
import com.thinkcoo.mobile.utils.PublicUIUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserStatusActivity extends BaseActivity implements UserStatusView {

    private static final String TAG = "UserStatusActivity";

    public static final int USER_STATUS_REQUEST_CODE = 0x0001;

    public static final String ALLOW_PUBLIC_USER_STATUS = "1";
    public static final String FORBID_PUBLIC_USER_STATUS = "0";

    public static Intent getUserStatusListIntent(Context context) {
        Intent intent = new Intent(context, UserStatusActivity.class);
        return intent;
    }

    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.tv_add_status)
    ImageView mTvAddStatus;
    @Bind(R.id.lv_status)
    ListView mLvStatus;

    @Inject
    UserStatusPresenter mUserStatusPresenter;
    @Inject
    PublicUIUtil mPublicUIUtil;

    private ArrayList<UserStatus> mUserStatusesList;
    private UserStatusListAdapter mUserStatusListAdapter;

    private ActionSheetDialog mActionSheetDialog;
    private String[] mSheetItemNames;

    private boolean isEditMode=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);
        ButterKnife.bind(this);
        initView();
        initData();
        mUserStatusPresenter.loadUserStatus(false);
    }

    private void initView() {
        mUserStatusesList = new ArrayList<>();
        mUserStatusListAdapter = new UserStatusListAdapter(this, mUserStatusesList, new ItemClickCallBack(), mPublicUIUtil);
        mLvStatus.setAdapter(mUserStatusListAdapter);
        mLvStatus.setOnItemClickListener(new OnListViewItemClickListener());
        mUserStatusListAdapter.setEditMode(isEditMode);

        mTvOther.setText(R.string.edit);
        mTvOther.setVisibility(View.VISIBLE);
    }

    private void initData() {
        mTvTitle.setText(R.string.my_status);
        mSheetItemNames = getResources().getStringArray(R.array.user_status_list_sheet_dialog);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mUserStatusPresenter;
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
    public void showLoading(boolean pullToRefresh) {
    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {

    }

    @Override
    public void setData(Object data) {
        List<UserStatus> userStatuses = (List<UserStatus>) data;
        updateStatusList(userStatuses);
    }

    private void updateStatusList(List<UserStatus> userStatuses) {
        if (null == userStatuses) {
            return;
        }
        mUserStatusesList.clear();
        mUserStatusesList.addAll(userStatuses);
        mUserStatusListAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @OnClick({R.id.iv_back, R.id.tv_other, R.id.tv_add_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                closeSelf();
                break;
            case R.id.tv_other:
                changeStatusListDeleteIconVisible();
                break;
            case R.id.tv_add_status:
                if (!isEditMode) {
                    showPopupDialog();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == USER_STATUS_REQUEST_CODE && resultCode == RESULT_OK) {
            mUserStatusPresenter.loadUserStatus(true);
        }
    }

    private void changeStatusListDeleteIconVisible() {
        if (isEditMode) {
            mTvOther.setText(R.string.edit);
        }else {
            mTvOther.setText(R.string.save);
        }
        mUserStatusListAdapter.setEditMode(!isEditMode);
        mUserStatusListAdapter.notifyDataSetChanged();
        isEditMode = !isEditMode;
    }

    private void showPopupDialog() {
        if(null != mActionSheetDialog){
            mActionSheetDialog.show();
            return;
        }

        mActionSheetDialog =new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(false)
                .setSheetItems(getSheetItemEntitys(mSheetItemNames), false)
                .addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                        navigateToAddNewStatusByType(sheetItemEntity);
                    }
                });
        mActionSheetDialog.show();
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntitys(String[] sheetItemNames) {
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

    private void navigateToAddNewStatusByType(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
        int type=UserStatusDetailActivity.EDUCATION_STATUS_TYPE;
        String name = sheetItemEntity.getItemName();
        if (getString(R.string.user_academic_education_status).equals(name)) {
            type=UserStatusDetailActivity.EDUCATION_STATUS_TYPE;
        }else if (getString(R.string.user_train_status).equals(name)){
            type=UserStatusDetailActivity.TRAIN_STATUS_TYPE;
        }else if (getString(R.string.user_fulltime_work_status).equals(name)){
            type=UserStatusDetailActivity.FULL_TIME_WORK_STATUS_TYPE;
        }else if (getString(R.string.user_parttime_work_status).equals(name)){
            type=UserStatusDetailActivity.PART_TIME_WORK_STATUS_TYPE;
        }
        UserStatus userStatus = new UserStatus();
        userStatus.setType(type);
        mNavigator.navigateToUserStatusDetailByAddMode(getActivityContext(), userStatus);
    }

    private class ItemClickCallBack implements UserStatusListAdapter.UserStatusListCallBack {

        @Override
        public void deleteUserStatus(UserStatus userStatus, int position) {
            mUserStatusPresenter.deleteUserStatus(userStatus);
        }

        @Override
        public void changeUserStatusOpenStatus(UserStatus userStatus, int position, boolean isOpen) {
            String status = FORBID_PUBLIC_USER_STATUS;
            if (isOpen) {
                status = ALLOW_PUBLIC_USER_STATUS;
            }
            mUserStatusPresenter.toggleUserStatusOpenStatus(userStatus, status);
        }
    }

    private class OnListViewItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (isEditMode) {
                return;
            }
            UserStatus userStatus = mUserStatusesList.get(position);
            ThinkcooLog.e(TAG, userStatus.toString());
            mNavigator.navigateToUserStatusDetailByEditMode(getActivityContext(), userStatus);
        }
    }

}
