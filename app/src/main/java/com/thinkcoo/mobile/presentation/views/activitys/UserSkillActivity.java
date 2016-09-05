package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerUserComponent;
import com.thinkcoo.mobile.injector.modules.UserModule;
import com.thinkcoo.mobile.model.entity.UserSkill;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserSkillPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.adapter.User.MyTagAdapter;
import com.thinkcoo.mobile.presentation.views.component.TagFlowLayout;
import com.thinkcoo.mobile.presentation.views.dialog.UserSkillDialog;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserSkillActivity extends BaseActivity implements MvpLceView<List<UserSkill>>,MyTagAdapter.CallBackClickListener {

    @Inject
    UserSkillPresenter mUserSkillPresenter;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_titleName)
    TextView tvTitleName;
    @Bind(R.id.tv_titleComplete)
    TextView tvTitleComplete;
    @Bind(R.id.tv_titleCompletetwo)
    TextView tvTitleCompletetwo;
    @Bind(R.id.custom_user_skill_hobby)
    TagFlowLayout mFlowLayout;
    private List<UserSkill> mlist;
    private MyTagAdapter myTagAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_skill);
        ButterKnife.bind(this);
        setupViews();
    }

    protected void setupViews() {

        tvTitleName.setText(R.string.user_skill);
        tvTitleName.setVisibility(View.VISIBLE);
        tvTitleComplete.setText(R.string.edit);
        tvTitleCompletetwo.setText(R.string.save);
        //显示加号
        mlist = new ArrayList<>();
        UserSkill skill = new UserSkill();
        skill.setSkill("");
        mlist.add(skill);
        //初始化适配器
        myTagAdapter = new MyTagAdapter(mlist, this);
        myTagAdapter.setCallBackClickListener(this);
        mFlowLayout.setAdapter(this.myTagAdapter);
        loadUserSkill();
    }


    private void loadUserSkill() {
        mUserSkillPresenter.loadUserSkill();
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mUserSkillPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).userModule(new UserModule()).build().inject(this);
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
    public void setData(List<UserSkill> userSkills) {

        if (null != userSkills && userSkills.size()>0) {
            if (!myTagAdapter.isEditMode()) {
                tvTitleComplete.setVisibility(View.VISIBLE);
            }
        }
        mlist.clear();
        mlist.addAll(userSkills);
        if (!myTagAdapter.isEditMode()) {
            UserSkill skill = new UserSkill();
            skill.setSkill("");
            mlist.add(skill);
        }
        myTagAdapter.notifyDataChanged();
    }


    @Override
    public void loadData(boolean pullToRefresh) {
        mUserSkillPresenter.loadUserSkill();
    }


    @OnClick({R.id.img_back, R.id.tv_titleComplete, R.id.tv_titleCompletetwo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_titleComplete:
                doEdit();
                break;
            case R.id.tv_titleCompletetwo:
                doSave();
                break;
            case R.id.custom_user_skill_hobby:
                addUserSkill();
                break;
        }
    }

    private void doEdit() {
        if (mlist.size() > 1) {  // list 有一条数据的时候，点击编辑的时候的设置成ture 将添加的按钮隐藏
            tvTitleComplete.setVisibility(View.GONE);
            tvTitleCompletetwo.setVisibility(View.VISIBLE);
            myTagAdapter.setEditMode(true);
            myTagAdapter.notifyDataChanged();
        }
    }

    private void doSave() {
        tvTitleCompletetwo.setVisibility(View.GONE);
        myTagAdapter.setEditMode(false);
        loadUserSkill();
        // 空的加号
        if (mlist.size()>0) {
            int lastPosition = mlist.size()-1;
            UserSkill userSkill = mlist.get(lastPosition);
            if (TextUtils.isEmpty(userSkill.getSkill())) {
                mlist.remove(lastPosition);
            }
        }
        setData(mlist);
    }

    private void addUserSkill() {
        UserSkillDialog skillHobbyDialog = new UserSkillDialog(this, mGlobalToast);
        skillHobbyDialog.show();
        skillHobbyDialog.setOnSkillInputCompleteListener(new UserSkillDialog.OnSkillInputCompleteListener() {
            @Override
            public void onSkillInputComplete(String skill) {
                mUserSkillPresenter.addUserSkill(skill);
            }
        });

    }

    public static Intent getUserSkillIntent(Context context) {
        Intent intent = new Intent(context,UserSkillActivity.class);
        return intent;
    }


    @Override    // 删除的技能
    public void onClick(String skillId) {
        mUserSkillPresenter.deleteUserSkill(skillId);
    }

    @Override   // 编辑增加技能
    public void onAddListener() {
        addUserSkill();
    }
}
