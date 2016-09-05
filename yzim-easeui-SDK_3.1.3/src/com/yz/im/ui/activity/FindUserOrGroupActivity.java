package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.easeui.R;
import com.yz.im.Constant;
import com.yz.im.model.entity.serverresponse.FindGroupResponse;
import com.yz.im.model.entity.serverresponse.FindUserResponse;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.FindUserOrGroupContact;
import com.yz.im.mvp.presenter.FindUserOrGroupPresenter;
import com.yz.im.ui.fragment.FindGroupFragment;
import com.yz.im.ui.fragment.FindUserFragment;

import java.util.List;

public class FindUserOrGroupActivity extends FragmentContainerActivity implements View.OnClickListener, FindUserOrGroupContact.FindUserGroupView {


    private static final String IS_FRIEND = "1";

    private static final String SEARCH_BY_ID = "0";
    private static final String PAGE_NO = "1";

    public static Intent getFindUserIntent(Context context) {
        Intent intent = new Intent(context, FindUserOrGroupActivity.class);
        return intent;
    }

    private EditText mSearch;
    private Button btnSearch;

    private FindUserOrGroupPresenter mPresenter;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        super.continueOnCreate(savedInstanceState);
        initSelfViewAndEvent();
        setEditHint();
        mPresenter.loadInterestGroup("", "1", "1", "20");  //todo my god
    }

    private void initSelfViewAndEvent() {
        mTitle.setText(R.string.find_user_and_group);

        View searchView = LayoutInflater.from(this).inflate(R.layout.layout_search_with_button, null);
        mLinearLayout.addView(searchView);
        mSearch = (EditText) searchView.findViewById(R.id.ed_data);
        btnSearch = (Button) searchView.findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(this);
    }

    @Override
    protected void initFragment() {
        leftFragment = new FindUserFragment();
        rightFragment = new FindGroupFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_find, leftFragment, TAG_LEFT)
                .add(R.id.frame_find, rightFragment, TAG_RIGHT)
                .hide(rightFragment)
                .commit();
        currentTag = TAG_LEFT;
    }


    @Override
    void setTitleText() {
        mTitle.setText(R.string.find_user_and_group);
        tvFindGroup.setText(R.string.find_group);
        tvFindUser.setText(R.string.find_user);
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new FindUserOrGroupPresenter(this);
        return mPresenter;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_left) {
            finish();
        } else if (id == R.id.tag_find_user) {
            currentTag = TAG_LEFT;
            setEditHint();
            changeFragmentByTag();
        } else if (id == R.id.tag_find_group) {
            currentTag = TAG_RIGHT;
            setEditHint();
            changeFragmentByTag();
        } else if (id == R.id.btn_search) {
            String searchKey = mSearch.getText().toString().trim();
            if (currentTag == TAG_LEFT) {
                mPresenter.findUserByNumber(searchKey, SEARCH_BY_ID, PAGE_NO, Constant.PAGE_SIZE);
            } else if (currentTag == TAG_RIGHT) {
                mPresenter.findGroup(searchKey, SEARCH_BY_ID, PAGE_NO, Constant.PAGE_SIZE);
            }

        }
    }

    private void setEditHint() {
        switch (currentTag) {
            case TAG_LEFT:
                mSearch.setHint(R.string.hint_find_user);
                break;
            case TAG_RIGHT:
                mSearch.setHint(R.string.hint_find_group);
                break;
        }
    }

    @Override
    public void gotoUserInfoPage(FindUserResponse response) {
        String isFriend = response.getIsFriends();
        mNavigator.navigateToUserInfoPage(this, response.getUserId());
    }

    @Override
    public void gotoGroupListPage(List<FindGroupResponse> list) {
        mNavigator.navigateToFindGroupActivity(this, list);
    }

    @Override
    public void setGroupListData(List<FindGroupResponse> list) {
        if (rightFragment == null) {
            return;
        }
        ((FindGroupFragment)rightFragment).setData(list);
    }
}
