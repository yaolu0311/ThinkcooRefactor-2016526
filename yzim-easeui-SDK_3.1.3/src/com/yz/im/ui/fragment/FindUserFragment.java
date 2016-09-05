package com.yz.im.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hyphenate.easeui.R;
import com.yz.im.IMHelper;
import com.yz.im.ui.base.IMNavigator;

public class FindUserFragment extends Fragment implements OnClickListener {


    private View rootView;
    private RelativeLayout userName;
    private RelativeLayout phone_number;
    private LinearLayout recommendUser;
    private IMNavigator mNavigator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_find_user, container, false);
        initView();
        mNavigator = IMHelper.getInstance().getNavigator();
        return rootView;
    }

    private void initView() {
        userName = (RelativeLayout) rootView.findViewById(R.id.layout_find_by_name);
        phone_number = (RelativeLayout) rootView.findViewById(R.id.layout_phone_number);
        recommendUser = (LinearLayout) rootView.findViewById(R.id.layout_recommend_user);

        recommendUser.setOnClickListener(this);
        phone_number.setOnClickListener(this);
        userName.setOnClickListener(this);
    }

    /**
     * Description: 自己填写
     * @see OnClickListener#onClick(View)
     */
    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.layout_find_by_name) {
            mNavigator.navigateToExactFindUserPage(getContext());
        } else if (id == R.id.layout_phone_number) {
            mNavigator.navigateToTelephoneContactActivity(getContext());
        } else if (id == R.id.layout_recommend_user) {
            mNavigator.navigateToRecommendUserListActivity(getContext());
        }
    }

}
