package com.thinkcoo.mobile.presentation.views.activitys;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.mvp.presenters.FindCooperationMainPresent;
import com.thinkcoo.mobile.presentation.mvp.views.FindCooperationMainActivityView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindCooperationMainActivity extends BaseActivity implements FindCooperationMainActivityView {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_other)
    TextView tvOther;
    @Bind(R.id.iv_more)
    ImageView ivMore;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.et_search_content)
    EditText etSearchContent;
    @Bind(R.id.iv_serachicon)
    ImageView ivSerachicon;
    @Bind(R.id.btn_train_search)
    Button btnTrainSearch;
    private RelativeLayout rl_collect;
    private RelativeLayout rl_apply;
    private int Width;
    private PopupWindow pop;
    @Inject
    FindCooperationMainPresent mFindCooperationMainPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findcooperationmain);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return null;
    }

    @Override
    protected void initDaggerInject() {

    }

    private void initView() {

    }

    public static Intent getFindCooperationIntent(Context context) {
        Intent intent = new Intent(context, FindCooperationMainActivity.class);
        return intent;
    };

    @OnClick({R.id.iv_back, R.id.iv_more, R.id.btn_train_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:
                doPop();
                break;
            case R.id.btn_train_search:
                hideSoftKeyboard();
                loadTrainData();
                break;
        }
    }
//  隐藏软键盘
    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(FindCooperationMainActivity.this.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private void loadTrainData() {
        mFindCooperationMainPresent.loadCooperationData();
    }

    private void doPop() {
        final View pop_view = LayoutInflater.from(FindCooperationMainActivity.this).inflate(R.layout.item_pop_cooperation, null);
        pop_view.setFocusable(true);
        pop_view.setFocusableInTouchMode(true);
        rl_collect = (RelativeLayout) pop_view.findViewById(R.id.rl_layout_collect);
        rl_apply = (RelativeLayout) pop_view.findViewById(R.id.rl_apply_layout);
        pop = new PopupWindow(pop_view, (int) (0.37037 * Width), ActionBar.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        pop.showAsDropDown(ivMore);
        pop_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (pop != null && pop.isShowing()) {
                        pop.dismiss();
                        pop = null;
                    }
                    return true;
                }
                return false;
            }
        });
        pop_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (pop != null && pop.isShowing()) {
                    pop.dismiss();
                    pop = null;
                }
                return false;
            }
        });
        rl_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/7/22 跳转到--我的收藏
                pop.dismiss();

            }
        });
        rl_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/7/22 跳转到--我的申请
                pop.dismiss();

            }
        });





    }

    @Override
    public String getKeyword() {
        return etSearchContent.getText().toString().trim();
    }


    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void closeSelf() {

    }

    @Override
    public void showProgressDialog(int stringResId) {

    }

    @Override
    public void hideProgressDialogIfShowing() {

    }

    @Override
    public void showToast(String errorMsg) {

    }
}
