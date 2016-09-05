package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.Address;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.model.entity.TrainCourseFilter;
import com.thinkcoo.mobile.model.entity.TrainPopupItemEntity;
import com.thinkcoo.mobile.presentation.mvp.presenters.TrainSearchPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.TrainSearchView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.adapter.train.TrainSearchListMenuAdapter;
import com.thinkcoo.mobile.presentation.views.fragment.TrainSearchResultFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Leevin
 * CreateTime: 2016/8/19 13:30
 */
public class TrainSearchActivity extends BaseActivity implements TrainSearchView {


    private static final String KEY_WORD = "key_word";
    private static final String CURRENT_LOCATION = "current_location";
    private Location mSelectedLocation;

    public static Intent getJumpIntent(Context context, String searchKeyword, Location location) {
        Intent intent = new Intent(context, TrainSearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_WORD, searchKeyword);
        bundle.putParcelable(CURRENT_LOCATION, location);
        intent.putExtras(bundle);
        return intent;
    }

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_more)
    ImageView mIvMore;
    @Bind(R.id.et_search_content)
    EditText mEtSearchContent;
    @Bind(R.id.btn_train_search)
    Button mBtnTrainSearch;
    @Bind(R.id.tv_index_area)
    TextView mTvArea;
    @Bind(R.id.iv_index_isArrow0)
    ImageView mIvArrow0;
    @Bind(R.id.rl_train_selectType_place)
    RelativeLayout mSelectedPlace;
    @Bind(R.id.tv_index_price)
    TextView mTvPrice;
    @Bind(R.id.iv_index_isArrow1)
    ImageView mIvArrow1;
    @Bind(R.id.rl_train_selectType_price)
    RelativeLayout mSelectedPrice;
    @Bind(R.id.tv_index_team)
    TextView mTvClassType;
    @Bind(R.id.iv_index_isArrow2)
    ImageView mIvArrow2;
    @Bind(R.id.rl_train_selectType_class)
    RelativeLayout mSelectedClassType;
    @Bind(R.id.tv_index_start_time)
    TextView mTvStartTime;
    @Bind(R.id.iv_index_isArrow3)
    ImageView mIvArrow3;
    @Bind(R.id.rl_train_selectType_time)
    RelativeLayout mSelectedStartTime;
    @Bind(R.id.search_result_container)
    FrameLayout mSearchResultContainer;

    @Inject
    TrainSearchPresenter mTrainSearchPresenter;


    private HashMap<Integer, List<TrainPopupItemEntity>> map;
    private ImageView[] mArrows;
    private TextView[] mSelectTexts;
    private RelativeLayout[] mSelectTypes;
    private TrainSearchListMenuAdapter mMenuAdapter;
    private PopupWindow mSearchMenuWindow;
    private List<Address> mAreaList;
    private int whereButton = 0;// 点击的button 0,1,2,3
    private TrainSearchResultFragment mResultFragment;
    private TrainCourseFilter mTrainCourseFilter = new TrainCourseFilter();
    private int mScreenWidth;
    private PopupWindow mMorePopup;
    private String mKeyword;
    private String mCityCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_search);
        ButterKnife.bind(this);
        initData();
        setupViews();
        initSearchFragment();
        initScreenWidth();

        mResultFragment.loadData(false);
    }

    private void setupViews() {
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.findtrain);
        mEtSearchContent.setText(mKeyword);

        // 分类容器
        mSelectTypes = new RelativeLayout[4];
        mSelectTypes[0] = mSelectedPlace;
        mSelectTypes[1] = mSelectedPrice;
        mSelectTypes[2] = mSelectedClassType;
        mSelectTypes[3] = mSelectedStartTime;
        for (RelativeLayout selectType : mSelectTypes) {
            selectType.setTag(false);
        }

        // 内容
        mSelectTexts = new TextView[4];
        mSelectTexts[0] = mTvArea;
        mSelectTexts[1] = mTvPrice;
        mSelectTexts[2] = mTvClassType;
        mSelectTexts[3] = mTvStartTime;

        // 箭头
        mArrows = new ImageView[4];
        mArrows[0] = mIvArrow0;
        mArrows[1] = mIvArrow1;
        mArrows[2] = mIvArrow2;
        mArrows[3] = mIvArrow3;
    }

    private void initSearchFragment() {
        mResultFragment = new TrainSearchResultFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.search_result_container, mResultFragment).commit();
    }

    public TrainCourseFilter getFilter() {
        mTrainCourseFilter.setKeyword(mEtSearchContent.getText().toString().trim());
        return mTrainCourseFilter;
    }

    private void getDataFromIntent() {
        if (!getIntent().hasExtra(KEY_WORD) || !getIntent().hasExtra(CURRENT_LOCATION)) {
            throw new IllegalArgumentException(" no keyword or current location");
        }
        Bundle bundle = getIntent().getExtras();
        mKeyword = bundle.getString(KEY_WORD);
        mSelectedLocation = bundle.getParcelable(CURRENT_LOCATION);
        mCityCode = mSelectedLocation.getCityCode();

        // 赋值给filter
        mTrainCourseFilter.setKeyword(mKeyword);
        mTrainCourseFilter.setAreaCode(mCityCode);
    }

    private void initData() {
        getDataFromIntent();
        // 本地查地区
        mTrainSearchPresenter.loadArea(mCityCode);

        // 获取价格,课程类型,开课时间的数组
//        String[] place = getResources().getStringArray(R.array.train_price);
        String[] price = getResources().getStringArray(R.array.train_price);
        String[] course = getResources().getStringArray(R.array.train_course_type);
        String[] time = getResources().getStringArray(R.array.train_time);

        // 换转到固定格式的list集合中
         List<TrainPopupItemEntity> placeList = getPopupPlaceList(mAreaList);
        List<TrainPopupItemEntity> priceList = arrayToList(price);
        List<TrainPopupItemEntity> courseList = arrayToList(course);
        List<TrainPopupItemEntity> timeList = arrayToList(time);

        // 存于map中
        map = new HashMap<Integer, List<TrainPopupItemEntity>>();
        map.put(0, placeList);
        map.put(1, priceList);
        map.put(2, courseList);
        map.put(3, timeList);
    }

    private List<TrainPopupItemEntity> getPopupPlaceList(List<Address> areaList) {
        if (areaList == null || areaList.size() == 0) {
            throw new RuntimeException("place list cannot be empty");
        }
        List<TrainPopupItemEntity> list = new ArrayList<>();
        for (int i = 0; i < areaList.size(); i++) {
            if (i == 0) {
                list.add(new TrainPopupItemEntity(mSelectedLocation.getCity(), mSelectedLocation.getCityCode(), true));
            } else {
                list.add(new TrainPopupItemEntity(areaList.get(i).getName(), areaList.get(i).getStringCode(), true));
            }
        }
        return list;
    }


    public ArrayList<TrainPopupItemEntity> arrayToList(String[] array) {
        ArrayList<TrainPopupItemEntity> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            // 默认0项选中
            list.add(new TrainPopupItemEntity(array[i],i==0));
        }
        return list;
    }


    @Override
    protected MvpPresenter generatePresenter() {
        return mTrainSearchPresenter;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    public void setArea(List<Address> addresses) {
        mAreaList = addresses;
    }

    @OnClick({R.id.iv_back, R.id.iv_more, R.id.btn_train_search, R.id.rl_train_selectType_place, R.id.rl_train_selectType_price, R.id.rl_train_selectType_class, R.id.rl_train_selectType_time})
    public void onClick(View view) {
            closeOpenedPopup(whereButton);
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:
                showPopupWindow();
                break;
            case R.id.btn_train_search:
                mResultFragment.loadData(false);
                break;
            case R.id.rl_train_selectType_place:
                whereButton = 0;
                showRightState();
                break;
            case R.id.rl_train_selectType_price:
                whereButton = 1;
                showRightState();
                break;
            case R.id.rl_train_selectType_class:
                whereButton = 2;
                showRightState();
                break;
            case R.id.rl_train_selectType_time:
                whereButton = 3;
                showRightState();
                break;
        }
    }

    /**
     * 根据点击button的不同展示正确的状态
     */
    private void showRightState() {
        // 根据选中的button设置背景和颜色
        for (int i = 0; i < mArrows.length; i++) {
            if (i == whereButton) {
                mArrows[i].setImageDrawable(this.getResources().getDrawable(R.drawable.trade_filter_orange_down));
                mSelectTexts[i].setTextColor(getResources().getColor(R.color.color_orange));
            } else {
                mArrows[i].setImageDrawable(this.getResources().getDrawable(R.drawable.trade_filter_gray_down));
                mSelectTexts[i].setTextColor(getResources().getColor(R.color.color_smoke));
            }
        }

        // tag为true则开启popup,false关闭
        if (!(Boolean) mSelectTypes[whereButton].getTag()) {
            mSelectTypes[whereButton].setTag(true);
            mArrows[whereButton].startAnimation(setAnimationSet().getAnimations().get(0));
            // 找开popup
            popWindow(mSelectTypes[whereButton], map.get(whereButton));
        } else {
            mSelectTypes[whereButton].setTag(false);
            mArrows[whereButton].startAnimation(setAnimationSet().getAnimations().get(1));
            // 关闭popup
            mSearchMenuWindow.dismiss();
        }
    }

    /**
     *
     * Description: 点击按钮后弹出来的popuwindow
     *
     * @param view
     *            //显示在哪个控件下
     */
    public void popWindow(RelativeLayout view, final List<TrainPopupItemEntity> arraylist) {

        View contentView = LayoutInflater.from(TrainSearchActivity.this).inflate(R.layout.train_search_menu, null);
        ListView search_menu_list = (ListView) contentView.findViewById(R.id.ac_listview_search_menu_list);
        mMenuAdapter = new TrainSearchListMenuAdapter(arraylist, this);
        search_menu_list.setAdapter(mMenuAdapter);
        mSearchMenuWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mSearchMenuWindow.setOutsideTouchable(false);
        mSearchMenuWindow.setFocusable(false);
        mSearchMenuWindow.setBackgroundDrawable(new BitmapDrawable());
        mSearchMenuWindow.update();
        mSearchMenuWindow.showAsDropDown(view, 0, 1);

        // popup菜单列表的点击事件
        search_menu_list.setOnItemClickListener(new ListView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        menuClick(map.get(whereButton), position);
                    }
                });
    }

    private void menuClick(List<TrainPopupItemEntity> arraylist, int position) {
        // 设置选中
        for (int i = 0; i < arraylist.size(); i++) {
            arraylist.get(i).setChecked(i == position);
        }
        // 刷新数据  赋值
        mMenuAdapter.notifyDataSetChanged();
        mSelectTexts[whereButton].setText(arraylist.get(position).getName());
        mSelectTypes[whereButton].setTag(false);
        mArrows[whereButton].startAnimation(setAnimationSet().getAnimations().get(1));
        mSearchMenuWindow.dismiss();
        // 搜索的关键字keyWord,place ,price,course,time,
        switch (whereButton) {
            case 0:
                mTrainCourseFilter.setAreaCode( map.get(whereButton).get(position).getCode());
                break;
            case 1:
                mTrainCourseFilter.setPrice(Integer.toString(position));
                break;
            case 2:
                mTrainCourseFilter.setCourseType(Integer.toString(position));
                break;
            case 3:
                mTrainCourseFilter.setStartTime(Integer.toString(position));
                break;
            default:
                throw new IllegalArgumentException("unKnow button click");
        }
        // 访问网络load result
        mResultFragment.loadData(false);
    }

    /**
     * 箭头旋转动画设置
     */
    private AnimationSet setAnimationSet() {
        AnimationSet animationSet = new AnimationSet(false);

        Animation rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);

        RotateAnimation animation = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);

        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(animation);
        return animationSet;
    }

    private void showPopupWindow() {
        View pop_view = LayoutInflater.from(this).inflate(R.layout.train_pop_item, null);
        RelativeLayout appointment = (RelativeLayout) pop_view.findViewById(R.id.rl_appointment);
        RelativeLayout collections = (RelativeLayout) pop_view.findViewById(R.id.rl_collections);
        mMorePopup = new PopupWindow(pop_view, (int) (0.37037 * mScreenWidth), RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        mMorePopup.showAsDropDown(mIvMore);
        //整个层获得焦点方便获得页面back事件
        pop_view.setFocusable(true);
        pop_view.setFocusableInTouchMode(true);
        //获得back事件
        pop_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mMorePopup != null && mMorePopup.isShowing()) {
                        mMorePopup.dismiss();
                        mMorePopup = null;
                    }
                    return true;
                }
                return false;
            }
        });
        // 设置点击其他地方关闭popupwindow
        pop_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (mMorePopup != null && mMorePopup.isShowing()) {
                    mMorePopup.dismiss();
                    mMorePopup = null;
                }
                return false;
            }
        });
        appointment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mNavigator.navigateToTrainAppointmentActivity(TrainSearchActivity.this);
                mMorePopup.dismiss();
            }
        });

        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.navigateToTrainCollectionsActivity(TrainSearchActivity.this);
                mMorePopup.dismiss();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeOpenedPopup(whereButton);// 若的popup打开先关闭
        return super.onTouchEvent(event);
    }


    private void closeOpenedPopup(int whereButton) {
        if (!(Boolean) mSelectTypes[whereButton].getTag()) {
            return;
        }
        mSelectTypes[whereButton].setTag(false);
        mArrows[whereButton].startAnimation(setAnimationSet().getAnimations().get(1));
        mSearchMenuWindow.dismiss();
    }

    private void initScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }
}
