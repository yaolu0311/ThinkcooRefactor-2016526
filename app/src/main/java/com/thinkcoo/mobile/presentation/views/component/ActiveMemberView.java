package com.thinkcoo.mobile.presentation.views.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.mvp.presenters.ActiveMemberPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.ActiveMemberActivity;
import com.yz.im.model.db.entity.Friend;
import com.yz.im.ui.adapter.BaseContactListAdapter;
import com.yz.im.ui.adapter.FriendListAdapter;
import com.yz.im.ui.widget.FriendContactListView;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ActiveMemberView extends FriendContactListView {
    private FrameLayout mHeadView;
    private RelativeLayout mBottomView;
    public ActiveMemberView(Context context) {
        this(context, null);
    }

    public ActiveMemberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBottomView();
    }

    @Override
    public void setAdapter() {
        adapter = new FriendListAdapter(getContext(), contactList, BaseContactListAdapter
                .RIGHT_CHECK_BOX, false);
    }

    @Override
    protected void setTitleLayout() {
        super.setTitleLayout();
        title.setText("成员管理");
    }

    @Override
    public void initHeadView() {
        //// TODO: 2016/8/3
        addMemberHeadView();
        addMemberHeadViewListener();

//        //全选
//        adapter.setSelection(BaseContactListAdapter.Selection.SELECT_ALL);
//        adapter.notifyDataSetChanged();
//
//        //取消全选
//        adapter.setSelection(BaseContactListAdapter.Selection.CLEAR_ALL);
//        adapter.notifyDataSetChanged();
    }

    private String selectIds() {
        //添加至组
        List<Friend> selectors = adapter.getCheckList();
        StringBuilder stringBuilder = new StringBuilder();
        int count = selectors.size();
        for (int i = 0; i < count; i++) {
            Friend friend = selectors.get(i);
            stringBuilder.append(friend.getUserId());
            if (i != count - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString().trim();
    }

    private void addMemberHeadViewListener() {
        mHeadView.setOnClickListener(this);
    }

    private void addMemberHeadView() {
        mHeadView = (FrameLayout) mInflater.inflate(com.hyphenate.easeui.R.layout.contact_head_view, null);
        listView.addHeaderView(mHeadView);
    }

    private void initBottomView() {
        //// TODO: 2016/8/3
        addBottomView();
        addBottomListener();

    }

    private void addBottomListener() {
        mBottomView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.parentLayout:
                ((ActiveMemberActivity) getContext()).mActiveMemberPresenter.addMerberToGroup(((ActiveMemberActivity) getContext()).mEvent.scheduleId, ((ActiveMemberActivity) getContext()).mGroupId, selectIds());
                break;
        }

    }

    private void addBottomView() {
        mBottomView = (RelativeLayout) mInflater.inflate(R.layout.merber_bottom_view, null);
        mBottomLayout.addView(mBottomView);
    }

}
