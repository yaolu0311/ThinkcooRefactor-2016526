package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.publicmodule.ui.widget.ActionSheetDialog;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.mvp.presenters.StudentManagePresenter;
import com.thinkcoo.mobile.presentation.mvp.views.StudentManageView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.Navigator;
import com.thinkcoo.mobile.presentation.views.adapter.Schedule.ClassAdapter;
import com.thinkcoo.mobile.presentation.views.adapter.Schedule.StudentListAdapter;
import com.thinkcoo.mobile.presentation.views.component.SpaceItemDecoration;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;
import com.yz.im.ui.base.IMNavigator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentManageActivity extends BaseActivity implements StudentManageView {
    private static final String TAG = "StudentManageActivity";
    public static final int REQUEST_CREATE_CLASS_CODE = 1;
    public static final int REQUEST_MANUAL_ADD_CODE = 2;
    ClassAdapter classAdapter;
    @Inject
    StudentManagePresenter mStudentManagePresenter;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.gridView)
    RecyclerView gridView;
    @Bind(R.id.text_access_yet)
    TextView textAccessYet;
    @Bind(R.id.text_edite)
    TextView textEdite;
    @Bind(R.id.millleLayout)
    RelativeLayout millleLayout;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.text_manual_add)
    TextView textManualAdd;
    StudentListAdapter mStudentListAdapter;
    List<Student> mStudentList = new ArrayList<Student>();
    List<ClassGroup> mClassGroupList = new ArrayList<ClassGroup>();
    private int mSelectClassPosion;
    @Inject
    public Navigator mNavigator;
    public Event mEvent;
    @Bind(R.id.bottom_layout)
    RelativeLayout bottomLayout;
    public ActionSheetDialog mDeleteClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manage);
        ButterKnife.bind(this);
        setupViews();

    }

    protected void setupViews() {
        mEvent = getIntent().getParcelableExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY);
        if (!mEvent.isCreateAuthor) {
            textEdite.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.GONE);
        }
        tvTitle.setText(R.string.merber_manage);
        mStudentManagePresenter.loadClassList(mEvent.scheduleId, false);
        mDeleteClass = createDeleteClassDialog(getResources().getStringArray(R.array.sheetItem_delete_class));

        mDeleteClass.addSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(ActionSheetDialog.SheetItemEntity sheetItemEntity) {
                if (sheetItemEntity.getCode().equals("0")) {
                    mStudentManagePresenter.removeClass(mClassGroupList.get(classAdapter.OnLongPosition).getGroupId());
                }
            }
        });
    }

    private ActionSheetDialog createDeleteClassDialog(String[] sheetItemNames) {
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(false)
                .setSheetItems(getSheetItemEntitys(sheetItemNames), false);
        return actionSheetDialog;
    }

    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntitys(String[] sheetItemNames) {
        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
        if (null == sheetItemNames) {
            return itemEntities;
        }
        for (int i = 0; i < sheetItemNames.length; i++) {
            ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity(sheetItemNames[i], i + "", null);
            itemEntities.add(sheetItemEntity);
        }
        return itemEntities;
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mStudentManagePresenter;
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

    @Override
    public void setStudentList(List<Student> studentList, boolean isVisibileCheck) {
        setData(studentList, isVisibileCheck);
    }

    void setData(List<Student> studentList, boolean isVisibileCheck) {
        mStudentList.clear();
        mStudentList.addAll(studentList);
        if (mStudentListAdapter == null) {
            mStudentListAdapter = new StudentListAdapter(this, new ItemClickCallBack(), mStudentList);
            mStudentListAdapter.setVisibileCheckBox(isVisibileCheck);
            mStudentListAdapter.setVisibileCheckBox(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(mStudentListAdapter);
        } else {

            mStudentListAdapter.setVisibileCheckBox(isVisibileCheck);
            mStudentListAdapter.notifyDataSetChanged();
        }
        textEdite.setText(R.string.edit);
        textAccessYet.setText(getString(R.string.acess_yet) + mStudentList.size() + getString(R.string.people));
    }

    @Override
    public void setClassList(List<ClassGroup> classGroupList) {
        mClassGroupList.clear();
        mClassGroupList.addAll(classGroupList);
        if (classAdapter == null) {
            GridLayoutManager mgr = new GridLayoutManager(this, 3);
            gridView.setLayoutManager(mgr);
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.px_25);
            gridView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
            classAdapter = new ClassAdapter(this, mStudentManagePresenter, mClassGroupList, mEvent.isCreateAuthor);
            gridView.setAdapter(classAdapter);
        } else {
            classAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public Event getEvent() {
        return mEvent;
    }

    @Override
    public int getSelectClassPosion() {
        return mSelectClassPosion;
    }

    @Override
    public int setSelectClassPosion(int seletPosion) {
        mSelectClassPosion = seletPosion;
        return mSelectClassPosion;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CREATE_CLASS_CODE:
                mStudentManagePresenter.loadClassList(mEvent.scheduleId, true);
                break;
            case REQUEST_MANUAL_ADD_CODE:
                mStudentManagePresenter.loadStudentList(mEvent.scheduleId, mClassGroupList.get(mSelectClassPosion).getGroupId());
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.qunliao, R.id.iv_back, R.id.text_edite, R.id.text_manual_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.qunliao:
                if(mEvent!=null&&mEvent.easemobGroupId!=null){
                    IMNavigator imNavigator = new IMNavigator();
                    imNavigator.navigateToGroupChatActivity(StudentManageActivity.this, mEvent.easemobGroupId);
                }else{
                    showToast(getString(R.string.error_no_group));
                }

                break;
            case R.id.text_edite:
                if(mClassGroupList.size()<1||mStudentList.size()<1){
                    return;
                }
                if ("编辑".equals(textEdite.getText().toString().trim())) {
                    mStudentListAdapter.setVisibileCheckBox(true);
                    mStudentListAdapter.notifyDataSetChanged();
                    textEdite.setText(R.string.complete);
                } else {
                    mStudentManagePresenter.confirmStudentList(mEvent.scheduleId, mClassGroupList.get(mSelectClassPosion).getGroupId(), getaccountIds(mStudentList));
                }
                break;
            case R.id.text_manual_add:
                if(mClassGroupList.size()>mSelectClassPosion){
                    mNavigator.navigateToManualAdd(this, mEvent, mClassGroupList.get(mSelectClassPosion).getGroupId());
                }

                break;
        }
    }

    private String getaccountIds(List<Student> listStudent) {
        String accountIds = "";
        for (int i = 0; i < mStudentList.size(); i++) {
            if (mStudentList.get(i).isCheck() == true) {
                accountIds += mStudentList.get(i).getAccountId() + ",";
            }
        }
        ThinkcooLog.d(TAG, accountIds);
        if (accountIds.equals("")) {
            return accountIds;
        }
        return accountIds.substring(0, accountIds.length() - 1);
    }

    private class ItemClickCallBack implements StudentListAdapter.OnRecyclerViewItemClickListener {

        @Override
        public void onItemClick(Student student) {
            IMNavigator imNavigator = new IMNavigator();
            imNavigator.navigateToSingleChatActivity(StudentManageActivity.this, student.getAccountId());
        }
    }

    public static Intent getStudentManageIntent(Context context, Event event) {
        Intent intent = new Intent(context, StudentManageActivity.class);
        intent.putExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY, event);
        return intent;
    }

}
