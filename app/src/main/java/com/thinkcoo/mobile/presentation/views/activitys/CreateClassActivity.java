package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.ThinkcooApp;
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.mvp.presenters.CreateClassPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.CreateClassView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.adapter.Schedule.StudentListAdapter;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import com.thinkcoo.mobile.presentation.views.fragment.StudentFragment;
import com.yz.im.ui.base.IMNavigator;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateClassActivity extends BaseActivity implements CreateClassView {

    public static final String ACTION_CREATE_CLASS = "com.thinkcoo.mobile.action.create.class";
    private static final String TAG = "CreateClassActivity";

    @Inject
    CreateClassPresenter mCreateClassPresenter;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_other)
    TextView tvOther;
    @Bind(R.id.iv_more)
    ImageView ivMore;
    //    @Bind(R.id.title_layout)
//    RelativeLayout titleLayout;
    @Bind(R.id.txt_shcool)
    TextView txtShcool;
    @Bind(R.id.school_line)
    View schoolLine;
    @Bind(R.id.edit_shcool_name)
    EditText editShcoolName;
    @Bind(R.id.firstLayout)
    RelativeLayout firstLayout;
    @Bind(R.id.txt_class)
    TextView txtClass;
    @Bind(R.id.class_line)
    View classLine;
    @Bind(R.id.edit_clas_name)
    EditText editClasName;
    @Bind(R.id.secondLayout)
    RelativeLayout secondLayout;
    @Bind(R.id.text_add)
    TextView textAdd;
    @Bind(R.id.text_serch)
    TextView textSerch;
    @Bind(R.id.text_all_check)
    TextView textAllCheck;
//    @Bind(R.id.recyclerView)
//    RecyclerView recyclerView;
    @Bind(R.id.serch_layout)
    RelativeLayout serchLayout;
    @Bind(R.id.text_noserch)
    ImageView textNoserch;
    @Bind(R.id.confirm)
    TextView confirm;
    @Bind(R.id.no_serch_layout)
    RelativeLayout noSerchLayout;
    @Bind(R.id.threeLayout)
    RelativeLayout threeLayout;
//    StudentListAdapter mStudentListAdapter;
    public List<Student> mStudentList;
    Event mEvent;
    public StudentFragment studentFragment;
    private CreateClassBroadcast mCreateClassBrocast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        ButterKnife.bind(this);
        setupViews();
        setupSelectStudentFragment();
        registerCreateClassBroadcast();
    }

    private void registerCreateClassBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CREATE_CLASS);
        mCreateClassBrocast = new CreateClassBroadcast();
        registerReceiver(mCreateClassBrocast,intentFilter);
    }

    private class CreateClassBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            mCreateClassPresenter.createClass(mEvent.scheduleId,getSchoolName(),getClassName());
        }
    }

    private void unRegisterCreateClassBroadcast(){
        unregisterReceiver(mCreateClassBrocast);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterCreateClassBroadcast();
    }

    private void setupSelectStudentFragment() {
        studentFragment = new StudentFragment();
        studentFragment.setVisibileCheckBox(true);
        addFragment(R.id.fragement_student,studentFragment);
    }

    private void setupViews() {
        mEvent = getIntent().getParcelableExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY);
        tvTitle.setText("创建班组");
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mCreateClassPresenter;
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

    public void showAddButton(){
        textAdd.setVisibility(View.VISIBLE);
    }
    public void hideAddButton(){
        textAdd.setVisibility(View.GONE);
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
    public String getSchoolName() {
        return editShcoolName.getText().toString().trim();
    }

    @Override
    public String getClassName() {
        return editClasName.getText().toString().trim();
    }

    @Override
    public String getSelectStudentId() {
        return studentFragment.getSelectedStudentString();
    }

    @Override
    public RelativeLayout getSerchLayout() {
        return serchLayout;
    }

    @Override
    public RelativeLayout getNoSerchLayout() {
        return noSerchLayout;
    }

    @Override
    public void setStudents(List<Student> students) {
        if (students.size() > 0) {
            noSerchLayout.setVisibility(View.GONE);
            serchLayout.setVisibility(View.VISIBLE);
        } else {
            noSerchLayout.setVisibility(View.VISIBLE);
            serchLayout.setVisibility(View.GONE);

        }
//        setData(students);
    }

    @Override
    public void setResult() {
        setResult(RESULT_OK);
    }

    @Override
    public Event getevent() {
        return mEvent;
    }

//    void setData(List<Student> studentList) {
//        if (mStudentList != null) {
//            mStudentList.clear();
//            mStudentList.addAll(studentList);
//        } else {
//            mStudentList = studentList;
//        }
//
//
//        if (mStudentListAdapter == null) {
//            mStudentListAdapter = new StudentListAdapter(this, new ItemClickCallBack(), mStudentList);
//            mStudentListAdapter.setVisibileCheckBox(true);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(mStudentListAdapter);
//        } else {
//            refrshData();
//        }
//    }

//    private void refrshData() {
//
//        mStudentListAdapter.notifyDataSetChanged();
//    }

    @OnClick({R.id.text_serch, R.id.iv_back, R.id.text_all_check, R.id.confirm, R.id.text_add})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.text_serch:
                studentFragment.refresh();
                break;
            case R.id.text_all_check:
                allCheck(mStudentList);
                break;
            case R.id.confirm:
                mCreateClassPresenter.createClass(mEvent.scheduleId, getSchoolName(), getClassName());
                break;
            case R.id.text_add:
                mCreateClassPresenter.createClass(mEvent.scheduleId, getSchoolName(), getClassName());
                break;
        }
    }

    private void allCheck(List<Student> mStudentList) {
        for (int i = 0; i < mStudentList.size(); i++) {
            if (mStudentList.get(i).isCheck() == false) {
                mStudentList.get(i).setCheck(true);
            }
        }
    }

    public static Intent getCreateClassIntent(Context context, Event event) {

        Intent intent = new Intent(context, CreateClassActivity.class);
        intent.putExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY, event);
        return intent;
    }

//    private class ItemClickCallBack implements StudentListAdapter.OnRecyclerViewItemClickListener {
//
//        @Override
//        public void onItemClick(Student student) {
//
//            IMNavigator imNavigator = new IMNavigator();
//            imNavigator.navigateToSingleChatActivity(CreateClassActivity.this, student.getAccountId());
//        }
//    }

}
