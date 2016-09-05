package com.yz.im.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.yz.im.model.entity.BaseContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cys on 2016/7/7
 * in com.yz.im.ui.widget
 */
public class ActivityContactListView extends BaseContactListView implements View
        .OnClickListener {

    private FrameLayout topView;

    public ActivityContactListView(Context context) {
        this(context, null);
    }

    public ActivityContactListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setTitleLayout() {
        title.setText(context.getString(R.string.contact_list));
        leftImage.setVisibility(View.VISIBLE);
        leftImage.setImageResource(R.drawable.back);
    }

//    @Override
//    void setHeadView() {
//        headView = mInflater.inflate(R.layout.contact_head_view, null);
//        topView = (FrameLayout) headView.findViewById(R.id.layout_group);
//    }
//
//    @Override
//    void setFootView() {
//    }

    @Override
    public void setAdapter() {
//        adapter = new BaseContactListAdapter(getContext(), contactList, BaseContactListAdapter
//                .RIGHT_CHECK_BOX);
    }

//    @Override
//    void addHeadViewListener() {
//        headView.setOnClickListener(this);
//    }
//
//    @Override
//    void addFootViewListener() {
//
//    }

    @Override
    public void getData() {
        List<BaseContactBean<EaseUser>> list = new ArrayList<>();
        String[] names = {"dkfjdk", "adfasf", "bdsfdsfd", "解款", "赵电若", "cbhdggh", "dtre", "eretgf", "fhrthrt",
                "gyetwew", "hryghgfhg", "iewtfghrewg", "gdfsgr", "kthqq", "王惮烦", "caa"};

        for (int i = 0; i < names.length; i++) {
            BaseContactBean bean = new BaseContactBean();
            bean.setName(names[i]);
            list.add(bean);
        }
//        refreshData(list);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.layout_group) {
            mNavigator.navigateToGroupListActivity(context);
        }
        super.onClick(v);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }



//    private LinkedList<ActionSheetDialog.SheetItemEntity> getSheetItemEntitys() {
//        LinkedList<ActionSheetDialog.SheetItemEntity> itemEntities = new LinkedList<>();
//        ActionSheetDialog.SheetItemEntity sheetItemEntity = new ActionSheetDialog.SheetItemEntity
//                (context.getString(R.string.send), "0", null);
//        i    temEntities.add(sheetItemEntity);
//        return itemEntities;
//    }
}
