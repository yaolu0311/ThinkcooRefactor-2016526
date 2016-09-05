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
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.presentation.mvp.presenters.ManualAddPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.ManualAddView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseScheduleDetailActivity;
import com.thinkcoo.mobile.presentation.views.adapter.Schedule.StudentListAdapter;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;
import com.thinkcoo.mobile.presentation.views.fragment.MaualAddFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManualAddActivity extends BaseActivity implements ManualAddView {
    @Inject
    public ManualAddPresenter mManualAddPresenter;
    @Bind(R.id.editText_content)
    EditText editTextContent;
    @Bind(R.id.text_serch)
    TextView textSerch;
    //    @Bind(R.id.recyclerView)
//    RecyclerView recyclerView;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    StudentListAdapter mStudentListAdapter;
    List<Student> mStudentList = new ArrayList<Student>();
    Event mEvent;
    public String mGroupId;
    public MaualAddFragment mMaualAddFragment;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        ButterKnife.bind(this);
        setupViews();
    }

    private void setupViews() {
        mEvent = getIntent().getParcelableExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY);
        mGroupId = getIntent().getStringExtra(ActiveMemberActivity.GROUPID_KEY);
        tvTitle.setText("手动添加");
        mMaualAddFragment = new MaualAddFragment();
        mMaualAddFragment.setVisibileCheckBox(false);
        addFragment(R.id.fragement_manual, mMaualAddFragment);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mManualAddPresenter;
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
    public void setStudentList(List<Student> studentList) {
//        setData(studentList);
    }

//    void setData(List<Student> studentList) {
//        mStudentList.clear();
//        mStudentList.addAll(studentList);
//        if (mStudentListAdapter == null) {
//            mStudentListAdapter = new StudentListAdapter(this, new ItemClickCallBack(), mStudentList);
//            mStudentListAdapter.setVisibileCheckBox(false);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//            recyclerView.setAdapter(mStudentListAdapter);
//
//        } else {
//            mStudentListAdapter.notifyDataSetChanged();
//        }
//    }

    @Override
    public void setResult() {
        setResult(RESULT_OK);
    }

    @Override
    public Event getEvent() {
        return mEvent;
    }

    @Override
    public String getContent() {
        return editTextContent.getText().toString().trim();
    }

    @OnClick({R.id.text_serch, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.text_serch:
                mMaualAddFragment.refresh();
                break;
        }
    }


    public static Intent getManualAddIntent(Context context, Event event, String groupId) {
        Intent intent = new Intent(context, ManualAddActivity.class);
        intent.putExtra(BaseScheduleDetailActivity.EXTRA_EVENT_KEY, event);
        intent.putExtra(ActiveMemberActivity.GROUPID_KEY, groupId);
        return intent;
    }


    private class ItemClickCallBack implements StudentListAdapter.OnRecyclerViewItemClickListener {

        @Override
        public void onItemClick(Student student) {
            mManualAddPresenter.addMerberToGroup(mEvent.scheduleId, mGroupId, student.getAccountId());
        }
    }
}
