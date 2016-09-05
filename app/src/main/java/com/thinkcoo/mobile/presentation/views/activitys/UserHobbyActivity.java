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
import com.thinkcoo.mobile.model.entity.UserHobby;
import com.thinkcoo.mobile.presentation.mvp.presenters.UserHobbyPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.adapter.User.MyHobbyAdapter;
import com.thinkcoo.mobile.presentation.views.component.TagFlowLayout;
import com.thinkcoo.mobile.presentation.views.dialog.UserHobbyDialog;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserHobbyActivity extends BaseActivity implements MvpLceView<List<UserHobby>>, MyHobbyAdapter.CallBackClickListener {


    @Inject
    UserHobbyPresenter userHobbyPresenter;
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
    private List<UserHobby> mlist;
    private MyHobbyAdapter myHobbyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_skill);
        ButterKnife.bind(this);
        setupViews();
    }

    public void setupViews() {
        tvTitleName.setText(R.string.user_hobby);
        tvTitleName.setVisibility(View.VISIBLE);
        tvTitleComplete.setText(R.string.edit);
        tvTitleCompletetwo.setText(R.string.save);
        mlist = new ArrayList<>();
        mlist.clear();
        // 添加行号
        UserHobby hobby = new UserHobby();
        hobby.setHobby("");
        mlist.add(hobby);
        // 初始化适配器
        myHobbyAdapter = new MyHobbyAdapter(mlist, this);
        myHobbyAdapter.setCallBackClickListener(this);
        mFlowLayout.setAdapter(this.myHobbyAdapter);

        LoadUserHobby();
    }

    private void LoadUserHobby() {
        userHobbyPresenter.LoadUserHobby();
    }


    @Override
    protected MvpPresenter generatePresenter() {
        return userHobbyPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUserComponent.builder().applicationComponent(
                getApplicationComponent()).userModule(new UserModule()).activityModule(getActivityModule()).build().inject(this);

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
    public void setData(List<UserHobby> userHobbys) {

        if (null != userHobbys && userHobbys.size() > 0) {
            if (!myHobbyAdapter.isEditMode()) {
                tvTitleComplete.setVisibility(View.VISIBLE);
            }
        }
        mlist.clear();
        mlist.addAll(userHobbys);
        if (!myHobbyAdapter.isEditMode()) {
            UserHobby hobby = new UserHobby();
            hobby.setHobby("");
            mlist.add(hobby);
        }

        myHobbyAdapter.notifyDataChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {

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
                addUserHobby();
                break;
        }
    }
    // 保存
    private void doSave() {
        tvTitleCompletetwo.setVisibility(View.GONE);
        myHobbyAdapter.setEditMode(false);
        LoadUserHobby();
        if (mlist.size()>0) {
            int lastPosition = mlist.size()-1;
            UserHobby userSkill = mlist.get(lastPosition);
            if (TextUtils.isEmpty(userSkill.getHobby())) {
                mlist.remove(lastPosition);
            }
        }
        setData(mlist);
    }

    // 编辑
    private void doEdit() {
        if (mlist.size() > 1) {
            tvTitleComplete.setVisibility(View.GONE);
            tvTitleCompletetwo.setVisibility(View.VISIBLE);
            myHobbyAdapter.setEditMode(true);
            myHobbyAdapter.notifyDataChanged();
        }
    }

    private void addUserHobby() {
        UserHobbyDialog userHobbyDialog = new UserHobbyDialog(this,mGlobalToast);
        userHobbyDialog.show();
        userHobbyDialog.setOnSkillInputCompleteListener(new UserHobbyDialog.OnSkillInputCompleteListener() {
            @Override
            public void onSkillInputComplete(String hobby) {
                userHobbyPresenter.editUserHobby(hobby);
            }
        });

    }


    public static Intent getUserHobbyIntent(Context context) {
        Intent intent = new Intent(context, UserHobbyActivity.class);
        return intent;
    }
        // 删除的回调
    @Override
    public void onClick(String hobbyId) {
        userHobbyPresenter.deteleUserHobby(hobbyId);
    }
            // 编辑增加
    @Override
    public void onAddListener() {
        addUserHobby();
    }
}
