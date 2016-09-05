package com.thinkcoo.mobile.presentation.views.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerScheduleMainComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleMainModule;
import com.thinkcoo.mobile.presentation.mvp.presenters.BlankPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.ScheduleView;
import com.thinkcoo.mobile.presentation.views.activitys.MainActivity;
import com.thinkcoo.mobile.presentation.views.component.mydayview.CalendarController;
import com.thinkcoo.mobile.presentation.views.component.mydayview.MyDayView;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Utils;
import javax.inject.Inject;
import javax.inject.Named;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Lazy;

public class ScheduleFragment extends BaseFragment implements ScheduleView, ViewSwitcher.ViewFactory {

    private static final String NUM_OF_DAYS = "num_of_days";
    private static final String TIME_MILLIS = "time_millis";
    private static final String TAG = "ScheduleFragment";
    private int mNumDays = 7;
    protected Animation mInAnimationForward;
    protected Animation mOutAnimationForward;
    protected Animation mInAnimationBackward;
    protected Animation mOutAnimationBackward;
    Time mSelectedDay = new Time();
    public int currentViewTag = 1;
    public int nextViewTag = 2;

    public int defaultTag = currentViewTag;
    @Bind(R.id.switcher)
    ViewSwitcher mViewSwitcher;
    @Bind(R.id.tv_date)
    TextView mTvDate;
    @Bind(R.id.tv_today)
    TextView mTvToday;
    @Bind(R.id.fab_add)
    FloatingActionButton mAdd;
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.week_container)
    LinearLayout mWeekContainer;
    @Bind(R.id.tv_empty_view)
    TextView mTvEmptyView;

    @Inject
    @Named("current1")
    Lazy<MyDayView> m1CurrentMyDayView;
    @Inject
    @Named("next1")
    Lazy<MyDayView> m1NextMyDayView;

    @Inject
    @Named("current7")
    Lazy<MyDayView> m7CurrentMyDayView;
    @Inject
    @Named("next7")
    Lazy<MyDayView> m7NextMyDayView;

    MyDayView mCurrentMyDayView;
    MyDayView mNextMyDayView;

    @Inject
    CalendarController mCalendarController;
    private long mTimeMillis;


    public ScheduleFragment() {
//        mSelectedDay.setToNow();
    }

    public static ScheduleFragment newInstance(int dayNumber) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NUM_OF_DAYS, dayNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ScheduleFragment newInstance(int dayNumber,long timeMillis) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(TIME_MILLIS, timeMillis);
        bundle.putInt(NUM_OF_DAYS, dayNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getActivity();

        mInAnimationForward = AnimationUtils.loadAnimation(context, R.anim.slide_left_in);
        mOutAnimationForward = AnimationUtils.loadAnimation(context, R.anim.slide_left_out);
        mInAnimationBackward = AnimationUtils.loadAnimation(context, R.anim.slide_right_in);
        mOutAnimationBackward = AnimationUtils.loadAnimation(context, R.anim.slide_right_out);
    }

    @Override
    public void initArguments() {
        mNumDays = getArguments().getInt(NUM_OF_DAYS);
        mTimeMillis = getArguments().getLong(TIME_MILLIS);
        if (mTimeMillis != 0) {
            mSelectedDay.set(mTimeMillis);
        } else {
              mSelectedDay.setToNow();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;
    }

    // 日视图时,更新UI
    private void initViews() {
        if (mNumDays == 1) {
            mIvBack.setVisibility(View.VISIBLE);
            mWeekContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 必须在onViewCreated, inject presenter
        mViewSwitcher.setFactory(this);
        mViewSwitcher.getCurrentView().requestFocus();
        mCalendarController.setNavigator(getFragmentNavigatorHelper().getNavigator());
        mCalendarController.setTime(mSelectedDay.toMillis(false));
        mCalendarController.setTitleChangeListener(new CalendarController.TitleChangeListener() {
            @Override
            public void update(String title) {
                mTvDate.setText(title);
            }
        });
        ((MyDayView) mViewSwitcher.getCurrentView()).updateTitle();
    }

    @Override
    public View makeView() {
        if (mNumDays == 1) {
            mCurrentMyDayView = m1CurrentMyDayView.get();
            mNextMyDayView = m1NextMyDayView.get();

        } else if (mNumDays ==7) {
            mCurrentMyDayView = m7CurrentMyDayView.get();
            mNextMyDayView = m7NextMyDayView.get();
        }

        MyDayView myDayView = mCurrentMyDayView;
        if (defaultTag == currentViewTag) {
            defaultTag = nextViewTag;
        }else {
            myDayView = mNextMyDayView;
        }
        myDayView.setLayoutParams(new ViewSwitcher.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        myDayView.setSelected(mSelectedDay, false, false);
        myDayView.setFloatingActionButtonLinstener(new MyDayView.FloatingActionButtonListener() {
            @Override
            public void onScroll(float cosumerY) {
                if (cosumerY > 0 && mAdd.getVisibility() == View.VISIBLE) {
                    mAdd.hide();
                } else if (cosumerY < 0 && mAdd.getVisibility() != View.VISIBLE) {
                    mAdd.show();
                }
            }
        });
        return myDayView;
    }

    // 动态设置星期左上角的
    private void setEmptyViewWidth(int hoursWidth) {
       ViewGroup.LayoutParams layoutParams = mTvEmptyView.getLayoutParams();
        layoutParams.width = hoursWidth;
        mTvEmptyView.setLayoutParams(layoutParams);
    }

    @Override
    public void onPause() {
        super.onPause();
        allViewDetache();
        allViewCleanup();
    }

    private void allViewCleanup() {
        MyDayView view = (MyDayView) mViewSwitcher.getCurrentView();
        view.cleanup();
        view = (MyDayView) mViewSwitcher.getNextView();
        view.cleanup();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_home_schedule_fragment;
    }

    @Override
    protected void initDaggerInject() {
        DaggerScheduleMainComponent.builder().applicationComponent(getFragmentInjectHelper().getApplicationComponent())
                .scheduleMainModule(new ScheduleMainModule(getActivity(),mViewSwitcher)).build().inject(this);
    }

    @Override
    protected MvpBasePresenter generatePresenter() {
        return new BlankPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();

        allViewAttache();

        eventsChanged();
        MyDayView view = (MyDayView) mViewSwitcher.getCurrentView();
        view.handleOnResume();
        view.restartCurrentTimeUpdates();

        view = (MyDayView) mViewSwitcher.getNextView();
        view.handleOnResume();
        view.restartCurrentTimeUpdates();
    }

    private void allViewAttache() {
        mCurrentMyDayView.attache();
        mNextMyDayView.attache();
    }
    private void allViewDetache(){
        mCurrentMyDayView.detach();
        mCurrentMyDayView.detach();
    }

    // 重新加载
    public void eventsChanged() {
        if (mViewSwitcher == null) {
            return;
        }
        MyDayView view = (MyDayView) mViewSwitcher.getCurrentView();
        view.clearCacheEvents();
        view.reloadEvents();

        view = (MyDayView) mViewSwitcher.getNextView();
        view.clearCacheEvents();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_today,R.id.fab_add,R.id.iv_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_today:
                toGoToday();
                break;
            case R.id.fab_add:
                createNewEvent();
                break;
            case R.id.iv_back:
                getActivity().finish();
                break;
        }
    }

    private void toGoToday() {
        Time today = new Time();
        today.setToNow();
        goTo(today, false, true);
        mCalendarController.setTime(today.toMillis(true));
    }

    private void createNewEvent() {
        Time t = new Time();
        t.set(mCalendarController.getTime());
        if (t.minute > 30) {
            t.hour++;
            t.minute = 0;
        } else if (t.minute > 0 && t.minute < 30) {
            t.minute = 30;
        }
        mCalendarController.createEvent(t.toMillis(true), t.toMillis(true) + Utils.HOUR_IN_MILLIS, -1);
    }

    @Override
    public void showProgressDialog(int stringResId) {
        getHostActivity().showProgressDialog(stringResId);
    }

    @Override
    public void hideProgressDialogIfShowing() {
        getHostActivity().hideProgressDialogIfShowing();
    }

    @Override
    public void showToast(String errorMsg) {
        getHostActivity().showToast(errorMsg);
    }

    public MainActivity getHostActivity() {
        return (MainActivity) getActivity();
    }

    public MyDayView getNextView() {
        return (MyDayView) mViewSwitcher.getNextView();
    }

    private void goTo(Time goToTime, boolean ignoreTime, boolean animateToday) {
        if (mViewSwitcher == null) {
            // The view hasn't been set yet. Just save the time and use it later.
            mSelectedDay.set(goToTime);
            return;
        }
        MyDayView currentView = (MyDayView) mViewSwitcher.getCurrentView();

        // How does goTo time compared to what's already displaying?
        int diff = currentView.compareToVisibleTimeRange(goToTime);

        if (diff == 0) {
            // In visible range. No need to switch view
            currentView.setSelected(goToTime, ignoreTime, animateToday);
        } else {
            // Figure out which way to animate
            if (diff > 0) {
                mViewSwitcher.setInAnimation(mInAnimationForward);
                mViewSwitcher.setOutAnimation(mOutAnimationForward);
            } else {
                mViewSwitcher.setInAnimation(mInAnimationBackward);
                mViewSwitcher.setOutAnimation(mOutAnimationBackward);
            }

            MyDayView next = (MyDayView) mViewSwitcher.getNextView();
            if (ignoreTime) {
                next.setFirstVisibleHour(currentView.getFirstVisibleHour());
            }

            next.setSelected(goToTime, ignoreTime, animateToday);
            next.reloadEvents();
            mViewSwitcher.showNext();
            next.requestFocus();
            next.updateTitle();
            next.restartCurrentTimeUpdates();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CalendarController.removeInstance(getActivity());
    }


}


