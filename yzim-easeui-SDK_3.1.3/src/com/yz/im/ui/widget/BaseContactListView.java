package com.yz.im.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.widget.EaseSidebar;
import com.hyphenate.util.EMLog;
import com.yz.im.model.entity.BaseSortEntity;
import com.yz.im.ui.adapter.BaseContactListAdapter;
import com.yz.im.ui.base.IMNavigator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cys on 2016/7/6
 * in com.yz.im.ui.widget
 */
public abstract class BaseContactListView<T extends BaseSortEntity> extends RelativeLayout implements View.OnClickListener, TextWatcher,
        AdapterView.OnItemClickListener {

    protected static final String TAG = BaseContactListView.class.getSimpleName();

    protected Context context;
    protected ImageView leftImage;
    protected TextView title;
    protected ListView listView;
    public BaseContactListAdapter adapter;
    protected List<T> contactList;
    protected EaseSidebar sidebar;
    protected ImageButton clearSearch;
    protected EditText mEditTextQuery;
    protected FrameLayout mBottomLayout;
    protected RelativeLayout searchLayout;

    protected LayoutInflater mInflater;
    protected IMNavigator mNavigator;
    protected InputMethodManager inputMethodManager;

    public BaseContactListView(Context context) {
        this(context, null);
    }

    public BaseContactListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setTitleLayout();
        setAdapter();

        if (adapter == null) {
            throw new IllegalArgumentException("=== contactList adapter can't be null ===");
        }
        listView.setAdapter(adapter);
        sidebar.setListView(listView);

        getData();
    }

    private void init(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.ease_widget_contact_list, this);
        leftImage = (ImageView) findViewById(R.id.image_left);
        title = (TextView) findViewById(R.id.text_title);
        listView = (ListView) findViewById(R.id.list);
        sidebar = (EaseSidebar) findViewById(R.id.sidebar);
        mEditTextQuery = (EditText) findViewById(R.id.query);
        clearSearch = (ImageButton) findViewById(R.id.search_clear);
        mBottomLayout = (FrameLayout) findViewById(R.id.bottom_layout);
        searchLayout = (RelativeLayout) findViewById(R.id.search_bar_view);
        contactList = new ArrayList<>();
        mNavigator = new IMNavigator();

        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        clearSearch.setOnClickListener(this);
        mEditTextQuery.addTextChangedListener(this);
        leftImage.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.search_clear) {
            mEditTextQuery.getText().clear();
            hideSoftKeyboard();
        } else if (id == R.id.image_left) {
            try {
                ((Activity) context).finish();
            } catch (Exception e) {
                ThinkcooLog.e(TAG, e.getMessage(), e);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.filter(s);
        if (s.length() > 0) {
            clearSearch.setVisibility(View.VISIBLE);
        } else {
            clearSearch.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setShowSideBar(boolean showSideBar) {
        if (showSideBar) {
            sidebar.setVisibility(View.VISIBLE);
        } else {
            sidebar.setVisibility(View.GONE);
        }
    }

    public void hideSearchLayout(){
        searchLayout.setVisibility(GONE);
    }

    public void refreshData(List<T> list) {
        if (contactList == null) {
            return;
        }
        contactList.clear();
        contactList.addAll(sortData(list));
        adapter.notifyDataSetChanged();
    }

    private List<T> sortData(List<T> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        Collections.sort(list);
        return list;
    }

    protected void hideSoftKeyboard() {
        try {
            if (((Activity) getContext()).getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                if (((Activity) getContext()).getCurrentFocus() != null)
                    inputMethodManager.hideSoftInputFromWindow(((Activity) getContext()).getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            EMLog.e(TAG, e.getLocalizedMessage());
        }
    }

    protected abstract void setTitleLayout();

    protected abstract void setAdapter();

    protected abstract void getData();

}
