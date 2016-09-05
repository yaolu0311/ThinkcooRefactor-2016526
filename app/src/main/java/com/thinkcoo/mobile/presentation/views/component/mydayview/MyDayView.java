package com.thinkcoo.mobile.presentation.views.component.mydayview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.mvp.MvpRealView;
import com.thinkcoo.mobile.presentation.mvp.presenters.LoadEventListPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.EventView;
import com.thinkcoo.mobile.utils.LunarCalendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import static android.widget.Toast.makeText;
import static com.thinkcoo.mobile.presentation.views.component.mydayview.CalendarData.s24Hours;

/**
 * Created by  Leevin
 * on 2016/7/10 ,19:00.
 */
public class MyDayView extends MvpRealView implements ScaleGestureDetector.OnScaleGestureListener, View.OnLongClickListener,EventView {

    private static String TAG = "MyDayView";
    private static boolean DEBUG = false;
    private static boolean DEBUG_SCALING = false;
    private static boolean DEBUG_EVENT_DATA = false;
    private static final String PERIOD_SPACE = ". ";

    private static float mScale = 0; // Used for supporting different screen densities
    private static final long INVALID_EVENT_ID = -1; //This is used for remembering a null event
    // Duration of the allday expansion
    private static final long ANIMATION_DURATION = 400;
    // duration of the more allday event text fade
    private static final long ANIMATION_SECONDARY_DURATION = 200;
    // duration of the scroll to go to a specified time
    private static final int GOTO_SCROLL_DURATION = 200;

    private static int DEFAULT_CELL_HEIGHT = 120;
    private static int MAX_CELL_HEIGHT = 150;
    private static int MIN_Y_SPAN = 100;

    private boolean mOnFlingCalled;
    private boolean mStartingScroll = false;
    protected boolean mPaused = true;
    private Handler mHandler;
    // ID of the last event which was displayed with the toast popup.
    private long mLastPopupEventID;
    protected Context mContext;

    private static final int FROM_NONE = 0;
    private static final int FROM_ABOVE = 1;
    private static final int FROM_BELOW = 2;
    private static final int FROM_LEFT = 4;
    private static final int FROM_RIGHT = 8;

    private static int mHorizontalSnapBackThreshold = 128;

    private ContinueScroll mContinueScroll = new ContinueScroll();

    Time mBaseDate;
    private Time mCurrentTime;
    //Update the current time line every five minutes if the window is left open that long
    private static final int UPDATE_CURRENT_TIME_DELAY = 300000;
    private UpdateCurrentTime mUpdateCurrentTime = new UpdateCurrentTime();
    private int mTodayJulianDay;

    private Typeface mBold = Typeface.DEFAULT_BOLD;
    private int mFirstJulianDay;
    private int mLastJulianDay;

    private int mMonthLength;
    private int mFirstVisibleDate;
    private int mFirstVisibleDayOfWeek;
    private int[] mEarliestStartHour;    // indexed by the week day offset
    private boolean[] mHasAllDayEvent;   // indexed by the week day offset
    private String mEventCountTemplate;
    private CharSequence[] mLongPressItems;
    private String mLongPressTitle;

    protected static StringBuilder mStringBuilder = new StringBuilder(50);
    // TODO recreate formatter when locale changes
    protected static Formatter mFormatter = new Formatter(mStringBuilder, Locale.getDefault());

    private TodayAnimatorListener mTodayAnimatorListener = new TodayAnimatorListener();


    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        float spanY = Math.max(MIN_Y_SPAN, Math.abs(detector.getCurrentSpanY()));

        mCellHeight = (int) (mCellHeightBeforeScaleGesture * spanY / mStartingSpanY);

        if (mCellHeight < mMinCellHeight) {
            // If mStartingSpanY is too small, even a small increase in the
            // gesture can bump the mCellHeight beyond MAX_CELL_HEIGHT
            mStartingSpanY = spanY;
            mCellHeight = mMinCellHeight;
            mCellHeightBeforeScaleGesture = mMinCellHeight;
        } else if (mCellHeight > MAX_CELL_HEIGHT) {
            mStartingSpanY = spanY;
            mCellHeight = MAX_CELL_HEIGHT;
            mCellHeightBeforeScaleGesture = MAX_CELL_HEIGHT;
        }

        int gestureCenterInPixels = (int) detector.getFocusY() - DAY_HEADER_HEIGHT - mAlldayHeight;
        mViewStartY = (int) (mGestureCenterHour * (mCellHeight + DAY_GAP)) - gestureCenterInPixels;
        mMaxViewStartY = HOUR_GAP + 24 * (mCellHeight + HOUR_GAP) - mGridAreaHeight;

        if (DEBUG_SCALING) {
            float ViewStartHour = mViewStartY / (float) (mCellHeight + DAY_GAP);
            Log.d(TAG, "onScale: mGestureCenterHour:" + mGestureCenterHour + "\tViewStartHour: "
                    + ViewStartHour + "\tmViewStartY:" + mViewStartY + "\tmCellHeight:"
                    + mCellHeight + " SpanY:" + detector.getCurrentSpanY());
        }

        if (mViewStartY < 0) {
            mViewStartY = 0;
            mGestureCenterHour = (mViewStartY + gestureCenterInPixels)
                    / (float) (mCellHeight + DAY_GAP);
        } else if (mViewStartY > mMaxViewStartY) {
            mViewStartY = mMaxViewStartY;
            mGestureCenterHour = (mViewStartY + gestureCenterInPixels)
                    / (float) (mCellHeight + DAY_GAP);
        }
        computeFirstHour();

        mRemeasure = true;
        invalidate();
        return true;

    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        mHandleActionUp = false;
        float gestureCenterInPixels = detector.getFocusY() - DAY_HEADER_HEIGHT - mAlldayHeight;
        mGestureCenterHour = (mViewStartY + gestureCenterInPixels) / (mCellHeight + DAY_GAP);

        mStartingSpanY = Math.max(MIN_Y_SPAN, Math.abs(detector.getCurrentSpanY()));
        mCellHeightBeforeScaleGesture = mCellHeight;

        if (DEBUG_SCALING) {
            float ViewStartHour = mViewStartY / (float) (mCellHeight + DAY_GAP);
            Log.d(TAG, "onScaleBegin: mGestureCenterHour:" + mGestureCenterHour
                    + "\tViewStartHour: " + ViewStartHour + "\tmViewStartY:" + mViewStartY
                    + "\tmCellHeight:" + mCellHeight + " SpanY:" + detector.getCurrentSpanY());
        }
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        mScrollStartY = mViewStartY;
        mInitialScrollY = 0;
        mInitialScrollX = 0;
        mStartingSpanY = 0;
    }

    @Override
    public boolean onLongClick(View view) {
        int flags = DateUtils.FORMAT_SHOW_WEEKDAY;
        long time = getSelectedTimeInMillis();
        if (!mSelectionAllday) {
            flags |= DateUtils.FORMAT_SHOW_TIME;
        }
        if (DateFormat.is24HourFormat(mContext)) {
            flags |= DateUtils.FORMAT_24HOUR;
        }
        // TODO: 2016/7/22 onLongClickListener
        if (DEBUG_EVENT_DATA) {
            Toast.makeText(mContext, "onlongclick", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void updateTitle() {
        Time start = new Time(mBaseDate);
        start.normalize(true);
        Time end = new Time(start);
        end.monthDay += mNumDays - 1;
        // Move it forward one minute so the formatter doesn't lose a day
        end.minute += 1;
        end.normalize(true);

        int formatFlags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR;
        if (mNumDays != 1) {
            // Don't show day of the month if for multi-day view
            formatFlags |= DateUtils.FORMAT_NO_MONTH_DAY;

            // Abbreviate the month if showing multiple months
           /* if (start.month != end.month) {
                formatFlags |= DateUtils.FORMAT_ABBREV_MONTH;
            }*/
            formatFlags |= DateUtils.FORMAT_ABBREV_MONTH;
            // 若本周含今天 以今天的月份显示
            if (start.month < mCurrentTime.month && end.month == mCurrentTime.month) {
                start = mCurrentTime;
            }
        }

        mController.updateTitle(mContext,start, end, formatFlags);
    }


    class TodayAnimatorListener extends AnimatorListenerAdapter {
        private volatile Animator mAnimator = null;
        private volatile boolean mFadingIn = false;

        @Override
        public void onAnimationEnd(Animator animation) {
            synchronized (this) {
                if (mAnimator != animation) {
                    animation.removeAllListeners();
                    animation.cancel();
                    return;
                }
                if (mFadingIn) {
                    if (mTodayAnimator != null) {
                        mTodayAnimator.removeAllListeners();
                        mTodayAnimator.cancel();
                    }
                    mTodayAnimator = ObjectAnimator
                            .ofInt(MyDayView.this, "animateTodayAlpha", 255, 0);
                    mAnimator = mTodayAnimator;
                    mFadingIn = false;
                    mTodayAnimator.addListener(this);
                    mTodayAnimator.setDuration(600);
                    mTodayAnimator.start();
                } else {
                    mAnimateToday = false;
                    mAnimateTodayAlpha = 0;
                    mAnimator.removeAllListeners();
                    mAnimator = null;
                    mTodayAnimator = null;
                    invalidate();
                }
            }
        }

        public void setAnimator(Animator animation) {
            mAnimator = animation;
        }

        public void setFadingIn(boolean fadingIn) {
            mFadingIn = fadingIn;
        }

    }

    AnimatorListenerAdapter mAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            mScrolling = true;
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            mScrolling = false;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mScrolling = false;
            resetSelectedHour();
            invalidate();
        }
    };

    private long mLastReloadMillis;
    private ArrayList<Event> mEvents = new ArrayList<>();
    private ArrayList<Event> mAllDayEvents = new ArrayList<>();
    private StaticLayout[] mLayouts = null;
    private StaticLayout[] mAllDayLayouts = null;
    private int mSelectionDay;        // Julian day
    private int mSelectionHour;

    boolean mSelectionAllday;
    private int mLastSelectionDay;
    private int mLastSelectionHour;
    private Event mLastSelectedEvent;

    /**
     * Width of a day or non-conflicting event
     */
    private int mCellWidth;
    // Pre-allocate these objects and re-use them
    private Rect mRect = new Rect();
    private Rect mDestRect = new Rect();
    private Rect mSelectionRect = new Rect();
    // This encloses the more allDay events icon
    private Rect mExpandAllDayRect = new Rect();
    // TODO Clean up paint usage
    private Paint mPaint = new Paint();
    private Paint mEventTextPaint = new Paint();
    private Paint mSelectionPaint = new Paint();
    private float[] mLines;
    private int mFirstDayOfWeek; // First day of the week

    private boolean mRemeasure = true;
    protected EventGeometry mEventGeometry;

    private static float GRID_LINE_LEFT_MARGIN = 0;
    private static final float GRID_LINE_INNER_WIDTH = 1;

    private static final int DAY_GAP = 1;
    private static final int HOUR_GAP = 1;
    // This is the standard height of an allday event with no restrictions
    private static int SINGLE_ALLDAY_HEIGHT = 34;

    private static float MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT = 28.0F; // in pixels
    /**
     * This is how big the unexpanded allday height is allowed to be.
     * It will get adjusted based on screen size
     */
    private static int MAX_UNEXPANDED_ALLDAY_HEIGHT =
            (int) (MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT * 1);

    /**
     * This is the minimum size reserved for displaying regular events.
     * The expanded allDay region can't expand into this.
     */
    private static int MIN_HOURS_HEIGHT = 180;
    private static int ALLDAY_TOP_MARGIN = 1;
    // The largest a single allDay event will become.
    private static int MAX_HEIGHT_OF_ONE_ALLDAY_EVENT = 34;

    private static int HOURS_TOP_MARGIN = 2;
    private static int HOURS_LEFT_MARGIN = 2;
    private static int HOURS_RIGHT_MARGIN = 4;
    private static int HOURS_MARGIN = HOURS_LEFT_MARGIN + HOURS_RIGHT_MARGIN;
    private static int NEW_EVENT_MARGIN = 4;
    private static int NEW_EVENT_WIDTH = 2;
    private static int NEW_EVENT_MAX_LENGTH = 16;

    private static int CURRENT_TIME_LINE_HEIGHT = 2;
    private static int CURRENT_TIME_LINE_BORDER_WIDTH = 1;
    private static int CURRENT_TIME_LINE_SIDE_BUFFER = 4;
    private static int CURRENT_TIME_LINE_TOP_OFFSET = 2;

    /* package */ static final int MINUTES_PER_HOUR = 60;
    /* package */ static final int MINUTES_PER_DAY = MINUTES_PER_HOUR * 24;
    /* package */ static final int MILLIS_PER_MINUTE = 60 * 1000;
    /* package */ static final int MILLIS_PER_HOUR = (3600 * 1000);
    /* package */ static final int MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

    // More events text will transition between invisible and this alpha
    private static final int MORE_EVENTS_MAX_ALPHA = 0x4C;
    private static int DAY_HEADER_ONE_DAY_LEFT_MARGIN = 0;
    private static int DAY_HEADER_ONE_DAY_RIGHT_MARGIN = 5;
    private static int DAY_HEADER_ONE_DAY_BOTTOM_MARGIN = 6;
    private static int DAY_HEADER_LEFT_MARGIN = 5;
    private static int DAY_HEADER_RIGHT_MARGIN = 4;
    private static int DAY_HEADER_BOTTOM_MARGIN = 3;
    private static float DAY_HEADER_FONT_SIZE = 14;
    private static float DATE_HEADER_FONT_SIZE = 32;
    private static float NORMAL_FONT_SIZE = 12;
    private static float EVENT_TEXT_FONT_SIZE = 12;
    private static float HOURS_TEXT_SIZE = 12;
    private static float AMPM_TEXT_SIZE = 9;
    private static int MIN_HOURS_WIDTH = 96;
    private static int MIN_CELL_WIDTH_FOR_TEXT = 20;
    private static final int MAX_EVENT_TEXT_LEN = 500;
    // smallest height to draw an event with
    private static float MIN_EVENT_HEIGHT = 24.0F; // in pixels
    private static int CALENDAR_COLOR_SQUARE_SIZE = 10;
    private static int EVENT_RECT_TOP_MARGIN = 1;
    private static int EVENT_RECT_BOTTOM_MARGIN = 0;
    private static int EVENT_RECT_LEFT_MARGIN = 1;
    private static int EVENT_RECT_RIGHT_MARGIN = 0;
    private static int EVENT_RECT_STROKE_WIDTH = 2;
    private static int EVENT_TEXT_TOP_MARGIN = 2;
    private static int EVENT_TEXT_BOTTOM_MARGIN = 2;
    private static int EVENT_TEXT_LEFT_MARGIN = 6;
    private static int EVENT_TEXT_RIGHT_MARGIN = 6;
    private static int ALL_DAY_EVENT_RECT_BOTTOM_MARGIN = 1;
    private static int EVENT_ALL_DAY_TEXT_TOP_MARGIN = EVENT_TEXT_TOP_MARGIN;
    private static int EVENT_ALL_DAY_TEXT_BOTTOM_MARGIN = EVENT_TEXT_BOTTOM_MARGIN;
    private static int EVENT_ALL_DAY_TEXT_LEFT_MARGIN = EVENT_TEXT_LEFT_MARGIN;
    private static int EVENT_ALL_DAY_TEXT_RIGHT_MARGIN = EVENT_TEXT_RIGHT_MARGIN;
    // margins and sizing for the expand allday icon
    private static int EXPAND_ALL_DAY_BOTTOM_MARGIN = 10;
    // sizing for "box +n" in allDay events
    private static int EVENT_SQUARE_WIDTH = 10;
    private static int EVENT_LINE_PADDING = 4;
    private static int NEW_EVENT_HINT_FONT_SIZE = 12;

    private static int mPressedColor;
    private static int mEventTextColor;
    private static int mMoreEventsTextColor;

    private static int mWeek_saturdayColor;
    private static int mWeek_sundayColor;
    private static int mCalendarDateBannerTextColor;
    private static int mCalendarAmPmLabel;
    private static int mCalendarGridAreaSelected;
    private static int mCalendarGridLineInnerHorizontalColor;
    private static int mCalendarGridLineInnerVerticalColor;
    private static int mFutureBgColor;
    private static int mFutureBgColorRes;
    private static int mBgColor;
    private static int mNewEventHintColor;
    private static int mCalendarHourLabelColor;
    private static int mMoreAlldayEventsTextAlpha = MORE_EVENTS_MAX_ALPHA;

    private  int mHeaderDateColor;
    private  int mTodayDateColor;
    private int mTodayBackgroudColor;
    private  int mHeaderLunarDatecolor;

    private float mAnimationDistance = 0;
    private int mViewStartX;
    private int mViewStartY;
    private int mMaxViewStartY;
    private int mViewHeight;
    private int mViewWidth;
    private int mGridAreaHeight = -1;
    private static int mCellHeight = DEFAULT_CELL_HEIGHT; // shared among all DayViews
    private static int mMinCellHeight = 32;
    private int mScrollStartY;
    private int mPreviousDirection;

    /**
     * Vertical distance or span between the two touch points at the start of a
     * scaling gesture
     */
    private float mStartingSpanY = 0;
    /**
     * Height of 1 hour in pixels at the start of a scaling gesture
     */
    private int mCellHeightBeforeScaleGesture;
    /**
     * The hour at the center two touch points
     */
    private float mGestureCenterHour = 0;
    /**
     * Flag to decide whether to handle the up event. Cases where up events
     * should be ignored are 1) right after a scale gesture and 2) finger was
     * down before app launch
     */
    private boolean mHandleActionUp = true;
    private int mHoursTextHeight;
    /**
     * The height of the area used for allday events
     */
    private int mAlldayHeight;
    /**
     * The height of the allday event area used during animation
     */
    private int mAnimateDayHeight = 0;
    /**
     * The height of an individual allday event during animation
     */
    private int mAnimateDayEventHeight = (int) MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT;
    /**
     * Whether to use the expand or collapse icon.
     */
    private static boolean mUseExpandIcon = true;
    /**
     * The height of the day names/numbers
     */
    private static int DAY_HEADER_HEIGHT = 45;//TODO
    /**
     * The height of the day names/numbers for multi-day views
     */
    private static int MULTI_DAY_HEADER_HEIGHT = DAY_HEADER_HEIGHT;//TODO
    /**
     * The height of the day names/numbers when viewing a single day
     */
    private  static int ONE_DAY_HEADER_HEIGHT = DAY_HEADER_HEIGHT;//TODO
    /**
     * Max of all day events in a given day in this view.
     */
    private int mMaxAlldayEvents;
    /**
     * A count of the number of allday events that were not drawn for each day
     */
    private int[] mSkippedAlldayEvents;
    /**
     * The number of allDay events at which point we start hiding allDay events.
     */
    private int mMaxUnexpandedAlldayEventCount = 1;
    /**
     * Whether or not to expand the allDay area to fill the screen
     */
    private static boolean mShowAllAllDayEvents = false;
    protected int mNumDays = 7;
    private int mNumHours = 10;


    /**
     * Width of the time line (list of hours) to the left.
     */
    private int mHoursWidth;
    private int mDateStrWidth;
    /**
     * Top of the scrollable region i.e. below date labels and all day events
     */
    private int mFirstCell;
    /**
     * First fully visibile hour
     */
    private int mFirstHour = -1;
    /**
     * Distance between the mFirstCell and the top of first fully visible hour.
     */
    private int mFirstHourOffset;
    private String[] mHourStrs;
    private String[] mDayStrs;
    private String[] mDayStrs2Letter;
    private boolean mIs24HourFormat;
    private ArrayList<Event> mSelectedEvents = new ArrayList<>();
    private boolean mComputeSelectedEvents;
    private boolean mUpdateToast;
    private Event mSelectedEvent;
    private Event mPrevSelectedEvent;
    private Rect mPrevBox = new Rect();
    protected Resources mResources = null;
    protected Drawable mCurrentTimeLine = null;
    protected Drawable mCurrentTimeAnimateLine = null;
    protected Drawable mTodayHeaderDrawable = null;
    protected Drawable mExpandAlldayDrawable = null;
    protected Drawable mCollapseAlldayDrawable = null;
    protected Drawable mAcceptedOrTentativeEventBoxDrawable;
    private String mAmString;
    private String mPmString;
    private static int sCounter = 0;

    ScaleGestureDetector mScaleGestureDetector;
    // The initial state of the touch mode when we enter this view.
    private static final int TOUCH_MODE_INITIAL_STATE = 0;
    // Indicates we just received the touch event and we are waiting to see if
    // it is a tap or a scroll gesture.
    private static final int TOUCH_MODE_DOWN = 1;
    // Indicates the touch gesture is a vertical scroll
    private static final int TOUCH_MODE_VSCROLL = 0x20;
    // Indicates the touch gesture is a horizontal scroll
    private static final int TOUCH_MODE_HSCROLL = 0x40;

    private int mTouchMode = TOUCH_MODE_INITIAL_STATE;
    /**
     * The selection modes are HIDDEN, PRESSED, SELECTED, and LONGPRESS.
     */
    private static final int SELECTION_HIDDEN = 0;
    private static final int SELECTION_PRESSED = 1; // D-pad down but not up yet
    private static final int SELECTION_SELECTED = 2;
    private static final int SELECTION_LONGPRESS = 3;
    private int mSelectionMode = SELECTION_HIDDEN;

    private boolean mScrolling = false;
    private float mInitialScrollX;
    private float mInitialScrollY;
    private boolean mAnimateToday = false;
    private int mAnimateTodayAlpha = 0;
    // Animates the height of the allday region
    ObjectAnimator mAlldayAnimator;
    // Animates the height of events in the allday region
    ObjectAnimator mAlldayEventAnimator;
    // Animates the transparency of the more events text
    ObjectAnimator mMoreAlldayEventsAnimator;
    // Animates the current time marker when Today is pressed
    ObjectAnimator mTodayAnimator;
    // whether or not an event is stopping because it was cancelled
    private boolean mCancellingAnimations = false;
    // tracks whether a touch originated in the allday area
    private boolean mTouchStartedInAlldayArea = false;

    private ViewSwitcher mViewSwitcher;
    private GestureDetector mGestureDetector;
    private OverScroller mScroller;
    private EdgeEffect mEdgeEffectTop;
    private EdgeEffect mEdgeEffectBottom;
    private boolean mCallEdgeEffectOnAbsorb;
    private int OVERFLING_DISTANCE;
    private float mLastVelocity;

    private ScrollInterpolator mHScrollInterpolator;
    private AccessibilityManager mAccessibilityMgr = null;
    private boolean mIsAccessibilityEnabled = false;
    private boolean mTouchExplorationEnabled = false;
    private String mCreateNewEventString;
    private String mNewEventHintString;
    private CalendarController mController;

    private LoadEventListPresenter  mLoadEventListPresenter;

    public interface FloatingActionButtonListener {
        void onScroll(float cosumerY);
    }

    private FloatingActionButtonListener mFloatingActionButtonLinstener;
    public void setFloatingActionButtonLinstener(FloatingActionButtonListener floatingActionButtonLinstener) {
        this.mFloatingActionButtonLinstener = floatingActionButtonLinstener;
    }

    @Inject
    public MyDayView(Context context, CalendarController calendarController, ViewSwitcher viewSwitcher,LoadEventListPresenter loadEventListPresenter,int numDays) {
        super(context);
        mContext = context;
        mLoadEventListPresenter = loadEventListPresenter;
        mNumDays = numDays;
        mController = calendarController;
        mViewSwitcher = viewSwitcher;
        initAccessibilityVariables();
        mResources = context.getResources();
        initField(context);
        init(context);
    }

    public void initField(Context context) {
        mCreateNewEventString = mResources.getString(R.string.event_create);
        mNewEventHintString = mResources.getString(R.string.day_view_new_event_hint);

        MULTI_DAY_HEADER_HEIGHT = (int) (getTextStrHeight(DATE_HEADER_FONT_SIZE) + getTextStrHeight(DAY_HEADER_FONT_SIZE)+0.5 +4 );
        DATE_HEADER_FONT_SIZE = (int) mResources.getDimension(R.dimen.date_header_text_size);
        DAY_HEADER_FONT_SIZE = (int) mResources.getDimension(R.dimen.day_label_text_size);
        ONE_DAY_HEADER_HEIGHT = (int) mResources.getDimension(R.dimen.one_day_header_height);
        DAY_HEADER_BOTTOM_MARGIN = (int) mResources.getDimension(R.dimen.day_header_bottom_margin);
        EXPAND_ALL_DAY_BOTTOM_MARGIN = (int) mResources.getDimension(R.dimen.all_day_bottom_margin);
        HOURS_TEXT_SIZE = (int) mResources.getDimension(R.dimen.hours_text_size);
        AMPM_TEXT_SIZE = (int) mResources.getDimension(R.dimen.ampm_text_size);
        MIN_HOURS_WIDTH = (int) mResources.getDimension(R.dimen.min_hours_width);
        HOURS_LEFT_MARGIN = (int) mResources.getDimension(R.dimen.hours_left_margin);
        HOURS_RIGHT_MARGIN = (int) mResources.getDimension(R.dimen.hours_right_margin);
        MULTI_DAY_HEADER_HEIGHT = (int) mResources.getDimension(R.dimen.day_header_height);
        int eventTextSizeId;
        if (mNumDays == 1) {
            eventTextSizeId = R.dimen.day_view_event_text_size;
        } else {
            eventTextSizeId = R.dimen.week_view_event_text_size;
        }
        EVENT_TEXT_FONT_SIZE = (int) mResources.getDimension(eventTextSizeId);
        NEW_EVENT_HINT_FONT_SIZE = (int) mResources.getDimension(R.dimen.new_event_hint_text_size);
        MIN_EVENT_HEIGHT = mResources.getDimension(R.dimen.event_min_height);
        MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT = MIN_EVENT_HEIGHT;
        EVENT_TEXT_TOP_MARGIN = (int) mResources.getDimension(R.dimen.event_text_vertical_margin);
        EVENT_TEXT_BOTTOM_MARGIN = EVENT_TEXT_TOP_MARGIN;
        EVENT_ALL_DAY_TEXT_TOP_MARGIN = EVENT_TEXT_TOP_MARGIN;
        EVENT_ALL_DAY_TEXT_BOTTOM_MARGIN = EVENT_TEXT_TOP_MARGIN;
        EVENT_TEXT_LEFT_MARGIN = (int) mResources.getDimension(R.dimen.event_text_horizontal_margin);
        EVENT_TEXT_RIGHT_MARGIN = EVENT_TEXT_LEFT_MARGIN;
        EVENT_ALL_DAY_TEXT_LEFT_MARGIN = EVENT_TEXT_LEFT_MARGIN;
        EVENT_ALL_DAY_TEXT_RIGHT_MARGIN = EVENT_TEXT_LEFT_MARGIN;
        HOURS_MARGIN = HOURS_LEFT_MARGIN + HOURS_RIGHT_MARGIN;
        DAY_HEADER_HEIGHT = mNumDays == 1 ? ONE_DAY_HEADER_HEIGHT : MULTI_DAY_HEADER_HEIGHT;
        // to support different screen
        if (mScale == 0) {
           mScale = mResources.getDisplayMetrics().density;
           if (mScale != 1) {
               SINGLE_ALLDAY_HEIGHT *= mScale;
               ALLDAY_TOP_MARGIN *= mScale;
               MAX_HEIGHT_OF_ONE_ALLDAY_EVENT *= mScale;

               NORMAL_FONT_SIZE *= mScale;
               GRID_LINE_LEFT_MARGIN *= mScale;
               HOURS_TOP_MARGIN *= mScale;
               MIN_CELL_WIDTH_FOR_TEXT *= mScale;
               MAX_UNEXPANDED_ALLDAY_HEIGHT *= mScale;
               mAnimateDayEventHeight = (int) MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT;

               CURRENT_TIME_LINE_HEIGHT *= mScale;
               CURRENT_TIME_LINE_BORDER_WIDTH *= mScale;
               CURRENT_TIME_LINE_SIDE_BUFFER *= mScale;
               CURRENT_TIME_LINE_TOP_OFFSET *= mScale;

               MIN_Y_SPAN *= mScale;
               MAX_CELL_HEIGHT *= mScale;
               DEFAULT_CELL_HEIGHT *= mScale;
               DAY_HEADER_HEIGHT *= mScale;
               DAY_HEADER_LEFT_MARGIN *= mScale;
               DAY_HEADER_RIGHT_MARGIN *= mScale;
               DAY_HEADER_ONE_DAY_LEFT_MARGIN *= mScale;
               DAY_HEADER_ONE_DAY_RIGHT_MARGIN *= mScale;
               DAY_HEADER_ONE_DAY_BOTTOM_MARGIN *= mScale;
               CALENDAR_COLOR_SQUARE_SIZE *= mScale;
               EVENT_RECT_TOP_MARGIN *= mScale;
               EVENT_RECT_BOTTOM_MARGIN *= mScale;
               ALL_DAY_EVENT_RECT_BOTTOM_MARGIN *= mScale;
               EVENT_RECT_LEFT_MARGIN *= mScale;
               EVENT_RECT_RIGHT_MARGIN *= mScale;
               EVENT_RECT_STROKE_WIDTH *= mScale;
               EVENT_SQUARE_WIDTH *= mScale;
               EVENT_LINE_PADDING *= mScale;
               NEW_EVENT_MARGIN *= mScale;
               NEW_EVENT_WIDTH *= mScale;
               NEW_EVENT_MAX_LENGTH *= mScale;
           }
       }

        mCurrentTimeLine = mResources.getDrawable(R.drawable.timeline_indicator_holo_light);
        mCurrentTimeAnimateLine = mResources.getDrawable(R.drawable.timeline_indicator_activated_holo_light);
        mTodayHeaderDrawable = mResources.getDrawable(R.drawable.today_blue_week_holo_light);
        mExpandAlldayDrawable = mResources.getDrawable(R.drawable.ic_expand_holo_light);
        mCollapseAlldayDrawable = mResources.getDrawable(R.drawable.ic_collapse_holo_light);
        mNewEventHintColor =  mResources.getColor(R.color.new_event_hint_text_color);
        // 添加日期与今天的颜色
        mTodayDateColor =  mResources.getColor(R.color.today_date_color);
        mHeaderDateColor =  mResources.getColor(R.color.header_date_color);
        mHeaderLunarDatecolor = mResources.getColor(R.color.header_lunar_date_color);
        mTodayBackgroudColor = getResources().getColor(R.color.today_circle_background_color);
        mAcceptedOrTentativeEventBoxDrawable = mResources
                .getDrawable(R.drawable.panel_month_event_holo_light);
        mEventGeometry = new EventGeometry();
        mEventGeometry.setMinEventHeight(MIN_EVENT_HEIGHT);
        mEventGeometry.setHourGap(HOUR_GAP);
        mEventGeometry.setCellMargin(DAY_GAP);
        mLongPressItems = new CharSequence[]{mResources.getString(R.string.new_event_dialog_option)};

        mLongPressTitle = mResources.getString(R.string.new_event_dialog_label);
        mLastPopupEventID = INVALID_EVENT_ID;

        mGestureDetector = new GestureDetector(context, new CalendarGestureListener());
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        mScroller = new OverScroller(context);
        mHScrollInterpolator = new ScrollInterpolator();
        mEdgeEffectTop = new EdgeEffect(context);
        mEdgeEffectBottom = new EdgeEffect(context);
        OVERFLING_DISTANCE = ViewConfiguration.get(context).getScaledOverflingDistance();
    }


    private void init(Context context) {
        setFocusable(true);
        // Allow focus in touch mode so that we can do keyboard shortcuts
        // even after we've entered touch mode.
        setFocusableInTouchMode(true);
        setClickable(true);
        mFirstDayOfWeek = Utils.getFirstDayOfWeek(context);

//        mCurrentTime = new Time(Utils.getTimeZone(context, mTZUpdater));
        mCurrentTime = new Time();
        long currentTime = System.currentTimeMillis();
        mCurrentTime.set(currentTime);
        mTodayJulianDay = Time.getJulianDay(currentTime, mCurrentTime.gmtoff);

        mWeek_saturdayColor = mResources.getColor(R.color.week_saturday);
        mWeek_sundayColor = mResources.getColor(R.color.week_sunday);
        mCalendarDateBannerTextColor = mResources.getColor(R.color.calendar_date_banner_text_color);
        mFutureBgColorRes = mResources.getColor(R.color.calendar_future_bg_color);
        mBgColor = mResources.getColor(R.color.calendar_hour_background);
        mCalendarAmPmLabel = mResources.getColor(R.color.calendar_ampm_label);
        mCalendarGridAreaSelected = mResources.getColor(R.color.calendar_grid_area_selected);
        mCalendarGridLineInnerHorizontalColor = mResources.getColor(R.color.calendar_grid_line_inner_horizontal_color);
        mCalendarGridLineInnerVerticalColor = mResources.getColor(R.color.calendar_grid_line_inner_vertical_color);
        mCalendarHourLabelColor = mResources.getColor(R.color.calendar_hour_label);
        mPressedColor = mResources.getColor(R.color.pressed);
        mEventTextColor = mResources.getColor(R.color.calendar_event_text_color);
        mMoreEventsTextColor = mResources.getColor(R.color.month_event_other_color);

        mEventTextPaint.setTextSize(EVENT_TEXT_FONT_SIZE);
        mEventTextPaint.setTextAlign(Paint.Align.LEFT);
        mEventTextPaint.setAntiAlias(true);

        int gridLineColor = mResources.getColor(R.color.calendar_grid_line_highlight_color);
        Paint p = mSelectionPaint;
        p.setColor(gridLineColor);
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(false);

        p = mPaint;
        p.setAntiAlias(true);

        // Allocate space for 2 weeks worth of weekday names so that we can
        // easily start the week display at any week day.
        mDayStrs = new String[14];

        // Also create an array of 2-letter abbreviations.
        mDayStrs2Letter = new String[14];

        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            int index = i - Calendar.SUNDAY;
            // e.g. Tue for Tuesday
            mDayStrs[index] = DateUtils.getDayOfWeekString(i, DateUtils.LENGTH_MEDIUM)
                    .toUpperCase();
            mDayStrs[index + 7] = mDayStrs[index];
            // e.g. Tu for Tuesday
            mDayStrs2Letter[index] = DateUtils.getDayOfWeekString(i, DateUtils.LENGTH_SHORT)
                    .toUpperCase();

            // If we don't have 2-letter day strings, fall back to 1-letter.
            if (mDayStrs2Letter[index].equals(mDayStrs[index])) {
                mDayStrs2Letter[index] = DateUtils.getDayOfWeekString(i, DateUtils.LENGTH_SHORTEST);
            }

            mDayStrs2Letter[index + 7] = mDayStrs2Letter[index];
        }

        // Figure out how much space we need for the 3-letter abbrev names
        // in the worst case.
        p.setTextSize(DATE_HEADER_FONT_SIZE);
        p.setTypeface(mBold);
        String[] dateStrs = {" 28", " 30"};
        mDateStrWidth = computeMaxStringWidth(0, dateStrs, p);
        p.setTextSize(DAY_HEADER_FONT_SIZE);
//        mDateStrWidth += computeMaxStringWidth(0, mDayStrs, p);

        p.setTextSize(HOURS_TEXT_SIZE);
        p.setTypeface(null);
        handleOnResume();

        mAmString = DateUtils.getAMPMString(Calendar.AM).toUpperCase();
        mPmString = DateUtils.getAMPMString(Calendar.PM).toUpperCase();
        String hour24Str = "  24:00 ";
        String[] ampm = {mAmString, mPmString, hour24Str};
        p.setTextSize(AMPM_TEXT_SIZE);
        mHoursWidth = Math.max(HOURS_MARGIN, computeMaxStringWidth(mHoursWidth, ampm, p)
                + HOURS_RIGHT_MARGIN);
        mHoursWidth = Math.max(MIN_HOURS_WIDTH, mHoursWidth);
        // 回调mhourWidth 给fragment 中星期左上角的设置宽度,使其动态对其
//        mHoursWidthListener.onHoursWidthChangeListtener(mHoursWidth);

        // Catch long clicks for creating a new event
        setOnLongClickListener(this);

//        mBaseDate = new Time(Utils.getTimeZone(context, mTZUpdater));
        // TODO: 2016/7/11
        mBaseDate = new Time();
        long millis = System.currentTimeMillis();
        mBaseDate.set(millis);

        mEarliestStartHour = new int[mNumDays];
        mHasAllDayEvent = new boolean[mNumDays];

        // mLines is the array of points used with Canvas.drawLines() in
        // drawGridBackground() and drawAllDayEvents().  Its size depends
        // on the max number of lines that can ever be drawn by any single
        // drawLines() call in either of those methods.
        final int maxGridLines = (24 + 1)  // max horizontal lines we might draw
                + (mNumDays + 1); // max vertical lines we might draw
        mLines = new float[maxGridLines * 4];

    }
    private HoursWidthListener mHoursWidthListener;

    public interface HoursWidthListener{
        void onHoursWidthChangeListtener(int hoursWidth);
    }

    public void setHoursWidthListener(HoursWidthListener listener){
        mHoursWidthListener = mHoursWidthListener;
    }

    public void handleOnResume() {
        initAccessibilityVariables();
        initField(getContext());
        mFutureBgColor = mFutureBgColorRes;
        mIs24HourFormat = DateFormat.is24HourFormat(mContext);
        mHourStrs = mIs24HourFormat ? s24Hours : CalendarData.s12HoursNoAmPm;
        mFirstDayOfWeek = Utils.getFirstDayOfWeek(mContext);
        mLastSelectionDay = 0;
        mLastSelectionHour = 0;
        mLastSelectedEvent = null;
        mSelectionMode = SELECTION_HIDDEN;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mHandler == null) {
            mHandler = getHandler();
            mHandler.post(mUpdateCurrentTime);
        }
    }

    private int computeMaxStringWidth(int currentMax, String[] strings, Paint p) {
        float maxWidthF = 0.0f;

        int len = strings.length;
        for (int i = 0; i < len; i++) {
            float width = p.measureText(strings[i]);
            maxWidthF = Math.max(width, maxWidthF);
        }
        int maxWidth = (int) (maxWidthF + 0.5);
        if (maxWidth < currentMax) {
            maxWidth = currentMax;
        }
        return maxWidth;
    }

    public int cumputeTextWidth(String text, Paint p) {
        float width = p.measureText(text);
        return (int) (width + 0.5);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        mViewWidth = width;
        mViewHeight = height;
        mEdgeEffectTop.setSize(mViewWidth, mViewHeight);
        mEdgeEffectBottom.setSize(mViewWidth, mViewHeight);
        int gridAreaWidth = width - mHoursWidth;
        mCellWidth = (gridAreaWidth - (mNumDays * DAY_GAP)) / mNumDays;

        // This would be about 1 day worth in a 7 day view
        mHorizontalSnapBackThreshold = width / 7;

        Paint p = new Paint();
        p.setTextSize(HOURS_TEXT_SIZE);
        mHoursTextHeight = (int) Math.abs(p.ascent());
        remeasure(width, height);
    }

    private void remeasure(int width, int height) {
        // Shrink to fit available space but make sure we can display at least two events
        MAX_UNEXPANDED_ALLDAY_HEIGHT = (int) (MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT * 2);
        MAX_UNEXPANDED_ALLDAY_HEIGHT = Math.min(MAX_UNEXPANDED_ALLDAY_HEIGHT, height / 6);
        MAX_UNEXPANDED_ALLDAY_HEIGHT = Math.max(MAX_UNEXPANDED_ALLDAY_HEIGHT,
                (int) MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT * 2);
        mMaxUnexpandedAlldayEventCount =
                (int) (MAX_UNEXPANDED_ALLDAY_HEIGHT / MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT);

        // First, clear the array of earliest start times, and the array
        // indicating presence of an all-day event.
        for (int day = 0; day < mNumDays; day++) {
            mEarliestStartHour[day] = 25;  // some big number
            mHasAllDayEvent[day] = false;
        }

        int maxAllDayEvents = mMaxAlldayEvents;

        // The min is where 24 hours cover the entire visible area
        mMinCellHeight = Math.max((height - DAY_HEADER_HEIGHT) / 24, (int) MIN_EVENT_HEIGHT);
        if (mCellHeight < mMinCellHeight) {
            mCellHeight = mMinCellHeight;
        }

        // Calculate mAllDayHeight
        mFirstCell = DAY_HEADER_HEIGHT;
        int allDayHeight = 0;
        if (maxAllDayEvents > 0) {
            int maxAllAllDayHeight = height - DAY_HEADER_HEIGHT - MIN_HOURS_HEIGHT;
            // If there is at most one all-day event per day, then use less
            // space (but more than the space for a single event).
            if (maxAllDayEvents == 1) {
                allDayHeight = SINGLE_ALLDAY_HEIGHT;
            } else if (maxAllDayEvents <= mMaxUnexpandedAlldayEventCount) {
                // Allow the all-day area to grow in height depending on the
                // number of all-day events we need to show, up to a limit.
                allDayHeight = maxAllDayEvents * MAX_HEIGHT_OF_ONE_ALLDAY_EVENT;
                if (allDayHeight > MAX_UNEXPANDED_ALLDAY_HEIGHT) {
                    allDayHeight = MAX_UNEXPANDED_ALLDAY_HEIGHT;
                }
            } else {
                // if we have more than the magic number, check if we're animating
                // and if not adjust the sizes appropriately
                if (mAnimateDayHeight != 0) {
                    // Don't shrink the space past the final allDay space. The animation
                    // continues to hide the last event so the more events text can
                    // fade in.
                    allDayHeight = Math.max(mAnimateDayHeight, MAX_UNEXPANDED_ALLDAY_HEIGHT);
                } else {
                    // Try to fit all the events in
                    allDayHeight = (int) (maxAllDayEvents * MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT);
                    // But clip the area depending on which mode we're in
                    if (!mShowAllAllDayEvents && allDayHeight > MAX_UNEXPANDED_ALLDAY_HEIGHT) {
                        allDayHeight = (int) (mMaxUnexpandedAlldayEventCount *
                                MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT);
                    } else if (allDayHeight > maxAllAllDayHeight) {
                        allDayHeight = maxAllAllDayHeight;
                    }
                }
            }
            mFirstCell = DAY_HEADER_HEIGHT + allDayHeight + ALLDAY_TOP_MARGIN;
        } else {
            mSelectionAllday = false;
        }
        mAlldayHeight = allDayHeight;

        mGridAreaHeight = height - mFirstCell;

        // Set up the expand icon position
        int allDayIconWidth = mExpandAlldayDrawable.getIntrinsicWidth();
        mExpandAllDayRect.left = Math.max((mHoursWidth - allDayIconWidth) / 2,
                EVENT_ALL_DAY_TEXT_LEFT_MARGIN);
        mExpandAllDayRect.right = Math.min(mExpandAllDayRect.left + allDayIconWidth, mHoursWidth
                - EVENT_ALL_DAY_TEXT_RIGHT_MARGIN);
        mExpandAllDayRect.bottom = mFirstCell - EXPAND_ALL_DAY_BOTTOM_MARGIN;
        mExpandAllDayRect.top = mExpandAllDayRect.bottom
                - mExpandAlldayDrawable.getIntrinsicHeight();

        mNumHours = mGridAreaHeight / (mCellHeight + HOUR_GAP);
        mEventGeometry.setHourHeight(mCellHeight);

        final long minimumDurationMillis = (long)
                (MIN_EVENT_HEIGHT * DateUtils.MINUTE_IN_MILLIS / (mCellHeight / 60.0f));
        Event.computePositions(mEvents, minimumDurationMillis);

        // Compute the top of our reachable view
        mMaxViewStartY = HOUR_GAP + 24 * (mCellHeight + HOUR_GAP) - mGridAreaHeight;
        if (DEBUG) {
            Log.e(TAG, "mViewStartY: " + mViewStartY);
            Log.e(TAG, "mMaxViewStartY: " + mMaxViewStartY);
        }
        if (mViewStartY > mMaxViewStartY) {
            mViewStartY = mMaxViewStartY;
            computeFirstHour();
        }

        if (mFirstHour == -1) {
            initFirstHour();
            mFirstHourOffset = 0;
        }

        // When we change the base date, the number of all-day events may
        // change and that changes the cell height.  When we switch dates,
        // we use the mFirstHourOffset from the previous view, but that may
        // be too large for the new view if the cell height is smaller.
        if (mFirstHourOffset >= mCellHeight + HOUR_GAP) {
            mFirstHourOffset = mCellHeight + HOUR_GAP - 1;
        }
        mViewStartY = mFirstHour * (mCellHeight + HOUR_GAP) - mFirstHourOffset;

        final int eventAreaWidth = mNumDays * (mCellWidth + DAY_GAP);
    }

    private void initFirstHour() {
        mFirstHour = mSelectionHour - mNumHours / 5;
        if (mFirstHour < 0) {
            mFirstHour = 0;
        } else if (mFirstHour + mNumHours > 24) {
            mFirstHour = 24 - mNumHours;
        }
    }

    private void initAccessibilityVariables() {
        mAccessibilityMgr = (AccessibilityManager) mContext
                .getSystemService(Service.ACCESSIBILITY_SERVICE);
        mIsAccessibilityEnabled = mAccessibilityMgr != null && mAccessibilityMgr.isEnabled();
        mTouchExplorationEnabled = isTouchExplorationEnabled();
    }

    private boolean isTouchExplorationEnabled() {
        return mIsAccessibilityEnabled && mAccessibilityMgr.isTouchExplorationEnabled();
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        if (DEBUG) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_HOVER_ENTER:
                    Log.e(TAG, "ACTION_HOVER_ENTER");
                    break;
                case MotionEvent.ACTION_HOVER_MOVE:
                    Log.e(TAG, "ACTION_HOVER_MOVE");
                    break;
                case MotionEvent.ACTION_HOVER_EXIT:
                    Log.e(TAG, "ACTION_HOVER_EXIT");
                    break;
                default:
                    Log.e(TAG, "Unknown hover event action. " + event);
            }
        }

        // Mouse also generates hover events
        // Send accessibility events if accessibility and exploration are on.
        if (!mTouchExplorationEnabled) {
            return super.onHoverEvent(event);
        }
        if (event.getAction() != MotionEvent.ACTION_HOVER_EXIT) {
            setSelectionFromPosition((int) event.getX(), (int) event.getY());
            invalidate();
        }
        return true;
    }

    public long getSelectedTimeInMillis() {
        Time time = new Time(mBaseDate);
        time.setJulianDay(mSelectionDay);
        time.hour = mSelectionHour;

        // We ignore the "isDst" field because we want normalize() to figure
        // out the correct DST value and not adjust the selected time based
        // on the current setting of DST.
        return time.normalize(true /* ignore isDst */);
    }

    public void setSelected(Time time, boolean ignoreTime, boolean animateToday) {
        mBaseDate.set(time);
        mSelectionHour = mBaseDate.hour;
        mSelectedEvent = null;
        mPrevSelectedEvent = null;
        long millis = mBaseDate.toMillis(false /* use isDst */);
        mSelectionDay = Time.getJulianDay(millis, mBaseDate.gmtoff);
        mSelectedEvents.clear();
        mComputeSelectedEvents = true;

        int gotoY = Integer.MIN_VALUE;

        if (!ignoreTime && mGridAreaHeight != -1) {
            int lastHour = 0;

            if (mBaseDate.hour < mFirstHour) {
                // Above visible region
                gotoY = mBaseDate.hour * (mCellHeight + HOUR_GAP);
            } else {
                lastHour = (mGridAreaHeight - mFirstHourOffset) / (mCellHeight + HOUR_GAP)
                        + mFirstHour;

                if (mBaseDate.hour >= lastHour) {
                    // Below visible region

                    // target hour + 1 (to give it room to see the event) -
                    // grid height (to get the y of the top of the visible
                    // region)
                    gotoY = (int) ((mBaseDate.hour + 1 + mBaseDate.minute / 60.0f)
                            * (mCellHeight + HOUR_GAP) - mGridAreaHeight);
                }
            }

            if (DEBUG) {
                Log.e(TAG, "Go " + gotoY + " 1st " + mFirstHour + ":" + mFirstHourOffset + "CH "
                        + (mCellHeight + HOUR_GAP) + " lh " + lastHour + " gh " + mGridAreaHeight
                        + " ymax " + mMaxViewStartY);
            }

            if (gotoY > mMaxViewStartY) {
                gotoY = mMaxViewStartY;
            } else if (gotoY < 0 && gotoY != Integer.MIN_VALUE) {
                gotoY = 0;
            }
        }

        recalc();
        mRemeasure = true;
        invalidate();

        boolean delayAnimateToday = false;
        if (gotoY != Integer.MIN_VALUE) {
            ValueAnimator scrollAnim = ObjectAnimator.ofInt(this, "viewStartY", mViewStartY, gotoY);
            scrollAnim.setDuration(GOTO_SCROLL_DURATION);
            scrollAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            scrollAnim.addListener(mAnimatorListener);
            scrollAnim.start();
            delayAnimateToday = true;
        }
        if (animateToday) {
            synchronized (mTodayAnimatorListener) {
                if (mTodayAnimator != null) {
                    mTodayAnimator.removeAllListeners();
                    mTodayAnimator.cancel();
                }
                mTodayAnimator = ObjectAnimator.ofInt(this, "animateTodayAlpha",
                        mAnimateTodayAlpha, 255);
                mAnimateToday = true;
                mTodayAnimatorListener.setFadingIn(true);
                mTodayAnimatorListener.setAnimator(mTodayAnimator);
                mTodayAnimator.addListener(mTodayAnimatorListener);
                mTodayAnimator.setDuration(150);
                if (delayAnimateToday) {
                    mTodayAnimator.setStartDelay(GOTO_SCROLL_DURATION);
                }
                mTodayAnimator.start();
            }
        }
        sendAccessibilityEventAsNeeded(false);
    }

    public void setViewStartY(int viewStartY) {
        if (viewStartY > mMaxViewStartY) {
            viewStartY = mMaxViewStartY;
        }
        mViewStartY = viewStartY;
        computeFirstHour();
        invalidate();
    }

    public void setAnimateTodayAlpha(int todayAlpha) {
        mAnimateTodayAlpha = todayAlpha;
        invalidate();
    }

    public Time getSelectedDay() {
        Time time = new Time(mBaseDate);
        time.setJulianDay(mSelectionDay);
        time.hour = mSelectionHour;

        // We ignore the "isDst" field because we want normalize() to figure
        // out the correct DST value and not adjust the selected time based
        // on the current setting of DST.
        time.normalize(true /* ignore isDst */);
        return time;
    }

    public int compareToVisibleTimeRange(Time time) {
        int savedHour = mBaseDate.hour;
        int savedMinute = mBaseDate.minute;
        int savedSec = mBaseDate.second;

        mBaseDate.hour = 0;
        mBaseDate.minute = 0;
        mBaseDate.second = 0;

        if (DEBUG) {
            Log.d(TAG, "Begin " + mBaseDate.toString());
            Log.d(TAG, "Diff  " + time.toString());
        }

        // Compare beginning of range
        int diff = Time.compare(time, mBaseDate);
        if (diff > 0) {
            // Compare end of range
            mBaseDate.monthDay += mNumDays;
            mBaseDate.normalize(true);
            diff = Time.compare(time, mBaseDate);

            if (DEBUG) Log.d(TAG, "End   " + mBaseDate.toString());

            mBaseDate.monthDay -= mNumDays;
            mBaseDate.normalize(true);
            if (diff < 0) {
                // in visible time
                diff = 0;
            } else if (diff == 0) {
                // Midnight of following day
                diff = 1;
            }
        }

        if (DEBUG) Log.d(TAG, "Diff: " + diff);

        mBaseDate.hour = savedHour;
        mBaseDate.minute = savedMinute;
        mBaseDate.second = savedSec;
        return diff;
    }

    public int getFirstVisibleHour() {
        return mFirstHour;
    }

    public void setFirstVisibleHour(int firstVisibleHour) {
        mFirstHour = firstVisibleHour;
        mFirstHourOffset = 0;
    }

    @Override
    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
        return super.onFilterTouchEventForSecurity(event);
    }

    public void restartCurrentTimeUpdates() {
        mPaused = false;
        if (mHandler != null) {
            mHandler.removeCallbacks(mUpdateCurrentTime);
            mHandler.post(mUpdateCurrentTime);
        }
    }

    // Encapsulates the code to continue the scrolling after the
    // finger is lifted. Instead of stopping the scroll immediately,
    // the scroll continues to "free spin" and gradually slows down.
    private class ContinueScroll implements Runnable {
        public void run() {
            mScrolling = mScrolling && mScroller.computeScrollOffset();
            if (!mScrolling || mPaused) {
                resetSelectedHour();
                invalidate();
                return;
            }

            mViewStartY = mScroller.getCurrY();

            if (mCallEdgeEffectOnAbsorb) {
                if (mViewStartY < 0) {
                    mEdgeEffectTop.onAbsorb((int) mLastVelocity);
                    mCallEdgeEffectOnAbsorb = false;
                } else if (mViewStartY > mMaxViewStartY) {
                    mEdgeEffectBottom.onAbsorb((int) mLastVelocity);
                    mCallEdgeEffectOnAbsorb = false;
                }
                mLastVelocity = mScroller.getCurrVelocity();
            }

            if (mScrollStartY == 0 || mScrollStartY == mMaxViewStartY) {
                // Allow overscroll/springback only on a fling,
                // not a pull/fling from the end
                if (mViewStartY < 0) {
                    mViewStartY = 0;
                } else if (mViewStartY > mMaxViewStartY) {
                    mViewStartY = mMaxViewStartY;
                }
            }

            computeFirstHour();
            mHandler.post(this);
            invalidate();
        }
    }

    // This is called after scrolling stops to move the selected hour
    // to the visible part of the screen.
    private void resetSelectedHour() {
        if (mSelectionHour < mFirstHour + 1) {
            mSelectionHour = mFirstHour + 1;
            mSelectedEvent = null;
            mSelectedEvents.clear();
            mComputeSelectedEvents = true;
        } else if (mSelectionHour > mFirstHour + mNumHours - 3) {
            mSelectionHour = mFirstHour + mNumHours - 3;
            mSelectedEvent = null;
            mSelectedEvents.clear();
            mComputeSelectedEvents = true;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        cleanup();
        super.onDetachedFromWindow();
    }

    /**
     * Recomputes the first full hour that is visible on screen after the
     * screen is scrolled.
     */
    private void computeFirstHour() {
        // Compute the first full hour that is visible on screen
        mFirstHour = (mViewStartY + mCellHeight + HOUR_GAP - 1) / (mCellHeight + HOUR_GAP);
        mFirstHourOffset = mFirstHour * (mCellHeight + HOUR_GAP) - mViewStartY;
    }

    class UpdateCurrentTime implements Runnable {
        public void run() {
            long currentTime = System.currentTimeMillis();
            mCurrentTime.set(currentTime);
            //% causes update to occur on 5 minute marks (11:10, 11:15, 11:20, etc.)
            if (!MyDayView.this.mPaused) {
                mHandler.postDelayed(mUpdateCurrentTime, UPDATE_CURRENT_TIME_DELAY
                        - (currentTime % UPDATE_CURRENT_TIME_DELAY));
            }
            mTodayJulianDay = Time.getJulianDay(currentTime, mCurrentTime.gmtoff);
            invalidate();
        }
    }

    private class ScrollInterpolator implements Interpolator {
        public ScrollInterpolator() {
        }

        public float getInterpolation(float t) {
            t -= 1.0f;
            t = t * t * t * t * t + 1;

            if ((1 - t) * mAnimationDistance < 1) {
                cancelAnimation();
            }

            return t;
        }
    }

    private void cancelAnimation() {
        Animation in = mViewSwitcher.getInAnimation();
        if (in != null) {
            // cancel() doesn't terminate cleanly.
            in.scaleCurrentDuration(0);
        }
        Animation out = mViewSwitcher.getOutAnimation();
        if (out != null) {
            // cancel() doesn't terminate cleanly.
            out.scaleCurrentDuration(0);
        }
    }
    private float lastDistanceY;
    class CalendarGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent ev) {
            if (DEBUG) Log.e(TAG, "GestureDetector.onSingleTapUp");
            MyDayView.this.doSingleTapUp(ev);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent ev) {
            if (DEBUG) Log.e(TAG, "GestureDetector.onLongPress");
            MyDayView.this.doLongPress(ev);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (DEBUG) Log.e(TAG, "GestureDetector.onScroll");
            if (mTouchStartedInAlldayArea) {
                if (Math.abs(distanceX) < Math.abs(distanceY)) {
                    return false;
                }
                // don't scroll vertically if this started in the allday area
                distanceY = 0;
            }

            if (mFloatingActionButtonLinstener != null && lastDistanceY * distanceY <= 0 &&Math.abs(distanceX) < Math.abs(distanceY)) {
                mFloatingActionButtonLinstener.onScroll(distanceY);
            }

            lastDistanceY = distanceY;
            MyDayView.this.doScroll(e1, e2, distanceX, distanceY);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (DEBUG) Log.e(TAG, "GestureDetector.onFling");

            if (mTouchStartedInAlldayArea) {
                if (Math.abs(velocityX) < Math.abs(velocityY)) {
                    return false;
                }
                // don't fling vertically if this started in the allday area
                velocityY = 0;
            }
            MyDayView.this.doFling(e1, e2, velocityX, velocityY);
            return true;
        }

        @Override
        public boolean onDown(MotionEvent ev) {
            if (DEBUG) Log.e(TAG, "GestureDetector.onDown");
            MyDayView.this.doDown(ev);
            return true;
        }
    }

    private void doLongPress(MotionEvent ev) {
        if (mScrolling) {
            return;
        }
        // Scale gesture in progress
        if (mStartingSpanY != 0) {
            return;
        }
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        boolean validPosition = setSelectionFromPosition(x, y);
        if (!validPosition) {
            // return if the touch wasn't on an area of concern
            return;
        }
        mSelectionMode = SELECTION_LONGPRESS;
        invalidate();
        performLongClick();
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        super.setOnLongClickListener(l);
    }

    private void doDown(MotionEvent ev) {
        mTouchMode = TOUCH_MODE_DOWN;
        mViewStartX = 0;
        mOnFlingCalled = false;
        mScrolling = false;// // TODO: 2016/8/9 确认源码时机
        mHandler.removeCallbacks(mContinueScroll);
    }

    private void doFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        cancelAnimation();
        mSelectionMode = SELECTION_HIDDEN;
        mOnFlingCalled = true;

        if ((mTouchMode & TOUCH_MODE_HSCROLL) != 0) {
            // Horizontal fling.
            // initNextView(deltaX);
            mTouchMode = TOUCH_MODE_INITIAL_STATE;
            if (DEBUG) Log.d(TAG, "doFling: velocityX " + velocityX);
            int deltaX = (int) e2.getX() - (int) e1.getX();
            switchViews(deltaX < 0, mViewStartX, mViewWidth, velocityX);
            mViewStartX = 0;
            return;
        }

        if ((mTouchMode & TOUCH_MODE_VSCROLL) == 0) {
            if (DEBUG) Log.d(TAG, "doFling: no fling");
            return;
        }

        // Vertical fling.
        mTouchMode = TOUCH_MODE_INITIAL_STATE;
        mViewStartX = 0;

        if (DEBUG) {
            Log.d(TAG, "doFling: mViewStartY" + mViewStartY + " velocityY " + velocityY);
        }

        // Continue scrolling vertically
        mScrolling = true;
        mScroller.fling(0 /* startX */, mViewStartY /* startY */, 0 /* velocityX */,
                (int) -velocityY, 0 /* minX */, 0 /* maxX */, 0 /* minY */,
                mMaxViewStartY /* maxY */, OVERFLING_DISTANCE, OVERFLING_DISTANCE);

        // When flinging down, show a glow when it hits the end only if it
        // When flinging down, show a glow when it hits the end only if it
        // wasn't started at the top
        if (velocityY > 0 && mViewStartY != 0) {
            mCallEdgeEffectOnAbsorb = true;
        }
        // When flinging up, show a glow when it hits the end only if it wasn't
        // started at the bottom
        else if (velocityY < 0 && mViewStartY != mMaxViewStartY) {
            mCallEdgeEffectOnAbsorb = true;
        }
//        mHandler.post(mContinueScroll);
    }

    private View switchViews(boolean forward, float xOffSet, float width, float velocity) {
        mAnimationDistance = width - xOffSet;
        if (DEBUG) {
            Log.d(TAG, "switchViews(" + forward + ") O:" + xOffSet + " Dist:" + mAnimationDistance);
        }

        float progress = Math.abs(xOffSet) / width;
        if (progress > 1.0f) {
            progress = 1.0f;
        }

        float inFromXValue, inToXValue;
        float outFromXValue, outToXValue;
        if (forward) {
            inFromXValue = 1.0f - progress;
            inToXValue = 0.0f;
            outFromXValue = -progress;
            outToXValue = -1.0f;
        } else {
            inFromXValue = progress - 1.0f;
            inToXValue = 0.0f;
            outFromXValue = progress;
            outToXValue = 1.0f;
        }

        final Time start = new Time(mBaseDate.timezone);
        start.set(mController.getTime());
        if (forward) {
            start.monthDay += mNumDays;
        } else {
            start.monthDay -= mNumDays;
        }
        mController.setTime(start.normalize(true));

        Time newSelected = start;

        if (mNumDays == 7) {
            newSelected = new Time(start);
            adjustToBeginningOfWeek(start);
        }

        final Time end = new Time(start);
        end.monthDay += mNumDays - 1;

        // We have to allocate these animation objects each time we switch views
        // because that is the only way to set the animation parameters.
        TranslateAnimation inAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, inFromXValue,
                Animation.RELATIVE_TO_SELF, inToXValue,
                Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, 0.0f);

        TranslateAnimation outAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, outFromXValue,
                Animation.RELATIVE_TO_SELF, outToXValue,
                Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, 0.0f);

        long duration = calculateDuration(width - Math.abs(xOffSet), width, velocity);
        inAnimation.setDuration(duration);
        inAnimation.setInterpolator(mHScrollInterpolator);
        outAnimation.setInterpolator(mHScrollInterpolator);
        outAnimation.setDuration(duration);
        outAnimation.setAnimationListener(new GotoBroadcaster(start, end));
        mViewSwitcher.setInAnimation(inAnimation);
        mViewSwitcher.setOutAnimation(outAnimation);

        MyDayView view = (MyDayView) mViewSwitcher.getCurrentView();
        view.cleanup();
        mViewSwitcher.showNext();
        view = (MyDayView) mViewSwitcher.getCurrentView();
        view.setSelected(newSelected, true, false);
        view.requestFocus();
        view.reloadEvents();
        view.updateTitle();
        view.restartCurrentTimeUpdates();
        return view;
    }

    private class GotoBroadcaster implements Animation.AnimationListener {
        private final int mCounter;
        private final Time mStart;
        private final Time mEnd;

        public GotoBroadcaster(Time start, Time end) {
            mCounter = ++sCounter;
            mStart = start;
            mEnd = end;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            MyDayView view = (MyDayView) mViewSwitcher.getCurrentView();
            view.mViewStartX = 0;
            view = (MyDayView) mViewSwitcher.getNextView();
            view.mViewStartX = 0;
            // TODO: 2016/7/11
            if (mCounter == sCounter) {
                // TODO: 2016/8/9  go to when ??
                // Log.e(TAG, "=======flying==onAnimationEnd======");
                // mController.
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    }

    /**
     * Cleanup the pop-up and timers.
     */
    public void cleanup() {
        mPaused = true;
        mLastPopupEventID = INVALID_EVENT_ID;
        if (mHandler != null) {
            mHandler.removeCallbacks(mUpdateCurrentTime);
        }
        // Turn off redraw
        mRemeasure = false;
    }

    // The rest of this file was borrowed from Launcher2 - PagedView.java
    private static final int MINIMUM_SNAP_VELOCITY = 2200;

    private long calculateDuration(float delta, float width, float velocity) {
        /*
         * Here we compute a "distance" that will be used in the computation of
         * the overall snap duration. This is a function of the actual distance
         * that needs to be traveled; we keep this value close to half screen
         * size in order to reduce the variance in snap duration as a function
         * of the distance the page needs to travel.
         */
        final float halfScreenSize = width / 2;
        float distanceRatio = delta / width;
        float distanceInfluenceForSnapDuration = distanceInfluenceForSnapDuration(distanceRatio);
        float distance = halfScreenSize + halfScreenSize * distanceInfluenceForSnapDuration;

        velocity = Math.abs(velocity);
        velocity = Math.max(MINIMUM_SNAP_VELOCITY, velocity);

        /*
         * we want the page's snap velocity to approximately match the velocity
         * at which the user flings, so we scale the duration by a value near to
         * the derivative of the scroll interpolator at zero, ie. 5. We use 6 to
         * make it a little slower.
         */
        long duration = 6 * Math.round(1000 * Math.abs(distance / velocity));
        if (DEBUG) {
            Log.e(TAG, "halfScreenSize:" + halfScreenSize + " delta:" + delta + " distanceRatio:"
                    + distanceRatio + " distance:" + distance + " velocity:" + velocity
                    + " duration:" + duration + " distanceInfluenceForSnapDuration:"
                    + distanceInfluenceForSnapDuration);
        }
        return duration;
    }

    private void doScroll(MotionEvent e1, MotionEvent e2, float deltaX, float deltaY) {
        cancelAnimation();
        if (mStartingScroll) {
            mInitialScrollX = 0;
            mInitialScrollY = 0;
            mStartingScroll = false;
        }

        mInitialScrollX += deltaX;
        mInitialScrollY += deltaY;
        int distanceX = (int) mInitialScrollX;
        int distanceY = (int) mInitialScrollY;

        // If we haven't figured out the predominant scroll direction yet,
        // then do it now.
        if (mTouchMode == TOUCH_MODE_DOWN) {
            int absDistanceX = Math.abs(distanceX);
            int absDistanceY = Math.abs(distanceY);
            mScrollStartY = mViewStartY;
            mPreviousDirection = 0;

            if (absDistanceX > absDistanceY) {
                mTouchMode = TOUCH_MODE_HSCROLL;
                mViewStartX = distanceX;
                initNextView(-mViewStartX);
            } else {
                mTouchMode = TOUCH_MODE_VSCROLL;
            }
        } else if ((mTouchMode & TOUCH_MODE_HSCROLL) != 0) {
            // We are already scrolling horizontally, so check if we
            // changed the direction of scrolling so that the other week
            // is now visible.
            mViewStartX = distanceX;
            if (distanceX != 0) {
                int direction = (distanceX > 0) ? 1 : -1;
                if (direction != mPreviousDirection) {
                    // The user has switched the direction of scrolling
                    // so re-init the next view
                    initNextView(-mViewStartX);
                    mPreviousDirection = direction;
                }
            }
        }

        if ((mTouchMode & TOUCH_MODE_VSCROLL) != 0) {
            mViewStartY = mScrollStartY + distanceY;

            // If dragging while already at the end, do a glow
            final int pulledToY = (int) (mScrollStartY + deltaY);
            if (pulledToY < 0) {
                mEdgeEffectTop.onPull(deltaY / mViewHeight);
                if (!mEdgeEffectBottom.isFinished()) {
                    mEdgeEffectBottom.onRelease();
                }
            } else if (pulledToY > mMaxViewStartY) {
                mEdgeEffectBottom.onPull(deltaY / mViewHeight);
                if (!mEdgeEffectTop.isFinished()) {
                    mEdgeEffectTop.onRelease();
                }
            }

            if (mViewStartY < 0) {
                mViewStartY = 0;
            } else if (mViewStartY > mMaxViewStartY) {
                mViewStartY = mMaxViewStartY;
            }
            computeFirstHour();
        }

        mScrolling = true;

        mSelectionMode = SELECTION_HIDDEN;
        invalidate();
    }

    private boolean initNextView(int deltaX) {

        // Change the view to the previous day or week
        MyDayView view = (MyDayView) mViewSwitcher.getNextView();
        Time date = view.mBaseDate;
        date.set(mBaseDate);
        boolean switchForward;
        if (deltaX > 0) {
            date.monthDay -= mNumDays;
            view.mSelectionDay = mSelectionDay - mNumDays;
            switchForward = false;
        } else {
            date.monthDay += mNumDays;
            view.mSelectionDay = mSelectionDay + mNumDays;
            switchForward = true;
        }
        date.normalize(true /* ignore isDst */);
        initView(view);
        view.layout(getLeft(), getTop(), getRight(), getBottom());
        view.reloadEvents(); // TODO: 2016/7/11
        return switchForward;

    }

    private void initView(MyDayView view) {
        view.mSelectionHour = mSelectionHour;
        view.mSelectedEvents.clear();
        view.mComputeSelectedEvents = true;
        view.mFirstHour = mFirstHour;
        view.mFirstHourOffset = mFirstHourOffset;
        view.remeasure(getWidth(), getHeight());
        view.initAllDayHeights();
        view.mSelectedEvent = null;
        view.mPrevSelectedEvent = null;
        view.mFirstDayOfWeek = mFirstDayOfWeek;
        if (view.mEvents.size() > 0) {
            view.mSelectionAllday = mSelectionAllday;
        } else {
            view.mSelectionAllday = false;
        }
        // Redraw the screen so that the selection box will be redrawn.  We may
        // have scrolled to a different part of the day in some other view
        // so the selection box in this view may no longer be visible.
        view.recalc();
    }

    private void recalc() {
        // Set the base date to the beginning of the week if we are displaying
        // 7 days at a time.
        if (mNumDays == 7) {
            adjustToBeginningOfWeek(mBaseDate);
        }

        final long start = mBaseDate.toMillis(false /* use isDst */);
        mFirstJulianDay = Time.getJulianDay(start, mBaseDate.gmtoff);
        mLastJulianDay = mFirstJulianDay + mNumDays - 1;

        mMonthLength = mBaseDate.getActualMaximum(Time.MONTH_DAY);
        mFirstVisibleDate = mBaseDate.monthDay;
        mFirstVisibleDayOfWeek = mBaseDate.weekDay;
    }

    private void adjustToBeginningOfWeek(Time time) {
        int dayOfWeek = time.weekDay;
        int diff = dayOfWeek - mFirstDayOfWeek;
        if (diff != 0) {
            if (diff < 0) {
                diff += 7;
            }
            time.monthDay -= diff;
            time.normalize(true /* ignore isDst */);
        }
    }

    private void doSingleTapUp(MotionEvent ev) {
        if (!mHandleActionUp || mScrolling) {
            return;
        }

        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int selectedDay = mSelectionDay;
        int selectedHour = mSelectionHour;

        if (mMaxAlldayEvents > mMaxUnexpandedAlldayEventCount) {
            // check if the tap was in the allday expansion area
            int bottom = mFirstCell;
            if ((x < mHoursWidth && y > DAY_HEADER_HEIGHT && y < DAY_HEADER_HEIGHT + mAlldayHeight)
                    || (!mShowAllAllDayEvents && mAnimateDayHeight == 0 && y < bottom &&
                    y >= bottom - MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT)) {
                doExpandAllDayClick(); // 展开全天事件的onclick
                return;
            }
        }

        boolean validPosition = setSelectionFromPosition(x, y);
        if (!validPosition) {
            if (y < DAY_HEADER_HEIGHT) {
                Time selectedTime = new Time(mBaseDate);
                selectedTime.setJulianDay(mSelectionDay);
                selectedTime.hour = mSelectionHour;
                selectedTime.normalize(true /* ignore isDst */);
                // TODO: 2016/7/11   点击 day header  controller分发处理
                if (DEBUG_EVENT_DATA) {
                    makeText(mContext, "===DAY_HEADER====", Toast.LENGTH_SHORT).show();
                }

                mController.gotoDayView(selectedTime);
            }
            return;
        }

        boolean hasSelection = mSelectionMode != SELECTION_HIDDEN;
        boolean pressedSelected = (hasSelection || mTouchExplorationEnabled)
                && selectedDay == mSelectionDay && selectedHour == mSelectionHour;

        if (pressedSelected) {
            // If the tap is on an already selected hour slot, then create a new
            // event
            long extraLong = 0;
            if (mSelectionAllday) {
                // 全天事件类型params标记
                extraLong = CalendarController.EXTRA_CREATE_ALL_DAY;
            }
            mSelectionMode = SELECTION_SELECTED;
            long startTimeInMillis = getSelectedTimeInMillis();
            // 新增事件
            mController.createEvent(startTimeInMillis, startTimeInMillis + Utils.HOUR_IN_MILLIS, extraLong);
        } else if (mSelectedEvent != null) {
            // If the tap is on an event, launch the "View event" view
            if (mIsAccessibilityEnabled) {
                mAccessibilityMgr.interrupt();
            }

            mSelectionMode = SELECTION_HIDDEN;

            int yLocation = (int) ((mSelectedEvent.top + mSelectedEvent.bottom) / 2);
            // Y location is affected by the position of the event in the scrolling
            // view (mViewStartY) and the presence of all day events (mFirstCell)
            if (!mSelectedEvent.allDay) {
                yLocation += (mFirstCell - mViewStartY);
            }
            if (DEBUG_EVENT_DATA) {
                mSelectedEvent.dump();//// TODO: 2016/8/10 logo
            }
            mController.viewEvent(mSelectedEvent);
        } else {
            // Select time
            Time startTime = new Time(mBaseDate);
            startTime.setJulianDay(mSelectionDay);
            startTime.hour = mSelectionHour;
            startTime.normalize(true /* ignore isDst */);

            Time endTime = new Time(startTime);
            endTime.hour++;

            mSelectionMode = SELECTION_SELECTED;
        }
        invalidate();
    }

    private boolean setSelectionFromPosition(int x, int y) {
        if (x < mHoursWidth) {
            x = mHoursWidth;
        }
        int day = (x - mHoursWidth) / (mCellWidth + DAY_GAP);
        if (day >= mNumDays) {
            day = mNumDays - 1;
        }
        day += mFirstJulianDay;
        mSelectionDay = day;

        if (y < DAY_HEADER_HEIGHT) {
            sendAccessibilityEventAsNeeded(false);
            return false;
        }

        mSelectionHour = mFirstHour; /* First fully visible hour */

        if (y < mFirstCell) {
            mSelectionAllday = true;
        } else {
            // y is now offset from top of the scrollable region
            int adjustedY = y - mFirstCell;

            if (adjustedY < mFirstHourOffset) {
                --mSelectionHour; /* In the partially visible hour */
            } else {
                mSelectionHour += (adjustedY - mFirstHourOffset) / (mCellHeight + HOUR_GAP);
            }

            mSelectionAllday = false;
        }

        findSelectedEvent(x, y);
        sendAccessibilityEventAsNeeded(true);
        return true;
    }

    private void findSelectedEvent(int x, int y) {
        int date = mSelectionDay;
        int cellWidth = mCellWidth;
        ArrayList<Event> events = mEvents;
        int numEvents = events.size();
        int left = computeDayLeftPosition(mSelectionDay - mFirstJulianDay);
        int top = 0;
        mSelectedEvent = null;

        mSelectedEvents.clear();
        if (mSelectionAllday) {
            float yDistance;
            float minYdistance = 10000.0f; // any large number
            Event closestEvent = null;
            float drawHeight = mAlldayHeight;
            int yOffset = DAY_HEADER_HEIGHT + ALLDAY_TOP_MARGIN;
            int maxUnexpandedColumn = mMaxUnexpandedAlldayEventCount;
            if (mMaxAlldayEvents > mMaxUnexpandedAlldayEventCount) {
                // Leave a gap for the 'box +n' text
                maxUnexpandedColumn--;
            }
            events = mAllDayEvents;
            numEvents = events.size();
            for (int i = 0; i < numEvents; i++) {
                Event event = events.get(i);
                if (!event.drawAsAllday() ||
                        (!mShowAllAllDayEvents && event.getColumn() >= maxUnexpandedColumn)) {
                    // Don't check non-allday events or events that aren't shown
                    continue;
                }

                if (event.startDay <= mSelectionDay && event.endDay >= mSelectionDay) {
                    float numRectangles = mShowAllAllDayEvents ? mMaxAlldayEvents
                            : mMaxUnexpandedAlldayEventCount;
                    float height = drawHeight / numRectangles;
                    if (height > MAX_HEIGHT_OF_ONE_ALLDAY_EVENT) {
                        height = MAX_HEIGHT_OF_ONE_ALLDAY_EVENT;
                    }
                    float eventTop = yOffset + height * event.getColumn();
                    float eventBottom = eventTop + height;
                    if (eventTop < y && eventBottom > y) {
                        // If the touch is inside the event rectangle, then
                        // add the event.
                        mSelectedEvents.add(event);
                        closestEvent = event;
                        break;
                    } else {
                        // Find the closest event
                        if (eventTop >= y) {
                            yDistance = eventTop - y;
                        } else {
                            yDistance = y - eventBottom;
                        }
                        if (yDistance < minYdistance) {
                            minYdistance = yDistance;
                            closestEvent = event;
                        }
                    }
                }
            }
            mSelectedEvent = closestEvent;
            return;
        }

        // Adjust y for the scrollable bitmap
        y += mViewStartY - mFirstCell;

        // Use a region around (x,y) for the selection region
        Rect region = mRect;
        region.left = x - 10;
        region.right = x + 10;
        region.top = y - 10;
        region.bottom = y + 10;

        EventGeometry geometry = mEventGeometry;

        for (int i = 0; i < numEvents; i++) {
            Event event = events.get(i);
            // Compute the event rectangle.
            if (!geometry.computeEventRect(date, left, top, cellWidth, event)) {
                continue;
            }

            // If the event intersects the selection region, then add it to
            // mSelectedEvents.
            if (geometry.eventIntersectsSelection(event, region)) {
                mSelectedEvents.add(event);
            }
        }

        // If there are any events in the selected region, then assign the
        // closest one to mSelectedEvent.
        if (mSelectedEvents.size() > 0) {
            int len = mSelectedEvents.size();
            Event closestEvent = null;
            float minDist = mViewWidth + mViewHeight; // some large distance
            for (int index = 0; index < len; index++) {
                Event ev = mSelectedEvents.get(index);
                float dist = geometry.pointToEvent(x, y, ev);
                if (dist < minDist) {
                    minDist = dist;
                    closestEvent = ev;
                }
            }
            mSelectedEvent = closestEvent;

            // Keep the selected hour and day consistent with the selected
            // event. They could be different if we touched on an empty hour
            // slot very close to an event in the previous hour slot. In
            // that case we will select the nearby event.
            int startDay = mSelectedEvent.startDay;
            int endDay = mSelectedEvent.endDay;
            if (mSelectionDay < startDay) {
                mSelectionDay = startDay;
            } else if (mSelectionDay > endDay) {
                mSelectionDay = endDay;
            }

            int startHour = mSelectedEvent.startTime / 60;
            int endHour;
            if (mSelectedEvent.startTime < mSelectedEvent.endTime) {
                endHour = (mSelectedEvent.endTime - 1) / 60;
            } else {
                endHour = mSelectedEvent.endTime / 60;
            }

            if (mSelectionHour < startHour && mSelectionDay == startDay) {
                mSelectionHour = startHour;
            } else if (mSelectionHour > endHour && mSelectionDay == endDay) {
                mSelectionHour = endHour;
            }
        }
    }

    private void sendAccessibilityEventAsNeeded(boolean speakEvents) {

        if (!mIsAccessibilityEnabled) {
            return;
        }
        boolean dayChanged = mLastSelectionDay != mSelectionDay;
        boolean hourChanged = mLastSelectionHour != mSelectionHour;
        if (dayChanged || hourChanged || mLastSelectedEvent != mSelectedEvent) {
            mLastSelectionDay = mSelectionDay;
            mLastSelectionHour = mSelectionHour;
            mLastSelectedEvent = mSelectedEvent;

            StringBuilder b = new StringBuilder();

            // Announce only the changes i.e. day or hour or both
            if (dayChanged) {
                b.append(getSelectedTime().format("%A "));
            }
            if (hourChanged) {
                b.append(getSelectedTime().format(mIs24HourFormat ? "%k" : "%l%p"));
            }
            if (dayChanged || hourChanged) {
                b.append(PERIOD_SPACE);
            }

            if (speakEvents) {
                if (mEventCountTemplate == null) {
                    // TODO: 2016/7/11 about speak
                    // mEventCountTemplate = mContext.getString(R.string.template_announce_item_index);
                }

                // Read out the relevant event(s)
                int numEvents = mSelectedEvents.size();
                if (numEvents > 0) {
                    if (mSelectedEvent == null) {
                        // Read out all the events
                        int i = 1;
                        for (Event calEvent : mSelectedEvents) {
                            if (numEvents > 1) {
                                // Read out x of numEvents if there are more than one event
                                mStringBuilder.setLength(0);
                                b.append(mFormatter.format(mEventCountTemplate, i++, numEvents));
                                b.append(" ");
                            }
                            //  appendEventAccessibilityString(b, calEvent);
                        }
                    } else {
                        if (numEvents > 1) {
                            // Read out x of numEvents if there are more than one event
                            mStringBuilder.setLength(0);
                            b.append(mFormatter.format(mEventCountTemplate, mSelectedEvents
                                    .indexOf(mSelectedEvent) + 1, numEvents));
                            b.append(" ");
                        }
                        // appendEventAccessibilityString(b, mSelectedEvent);
                    }
                } else {
                    b.append(mCreateNewEventString);
                }
            }

            if (dayChanged || hourChanged || speakEvents) {
                AccessibilityEvent event = AccessibilityEvent
                        .obtain(AccessibilityEvent.TYPE_VIEW_FOCUSED);
                CharSequence msg = b.toString();
                event.getText().add(msg);
                event.setAddedCount(msg.length());
                sendAccessibilityEventUnchecked(event);
            }
        }
    }

    public Time getSelectedTime() {
        Time time = new Time(mBaseDate);
        time.setJulianDay(mSelectionDay);
        time.hour = mSelectionHour;
        // We ignore the "isDst" field because we want normalize() to figure
        // out the correct DST value and not adjust the selected time based
        // on the current setting of DST.
        time.normalize(true /* ignore isDst */);
        return time;
    }

    /*
     * We want the duration of the page snap animation to be influenced by the
     * distance that the screen has to travel, however, we don't want this
     * duration to be effected in a purely linear fashion. Instead, we use this
     * method to moderate the effect that the distance of travel has on the
     * overall snap duration.
     */
    private float distanceInfluenceForSnapDuration(float f) {
        f -= 0.5f; // center the values about 0.
        f *= 0.3f * Math.PI / 2.0f;
        return (float) Math.sin(f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRemeasure) {
            remeasure(getWidth(), getHeight());
            mRemeasure = false;
        }
        canvas.save();
        float yTranslate = -mViewStartY + DAY_HEADER_HEIGHT + mAlldayHeight;
        // offset canvas by the current drag and header position
        canvas.translate(-mViewStartX, yTranslate);
        // clip to everything below the allDay area
        Rect dest = mDestRect;
        dest.top = (int) (mFirstCell - yTranslate);
        dest.bottom = (int) (mViewHeight - yTranslate);
        dest.left = 0;
        dest.right = mViewWidth;
        canvas.save();
        canvas.clipRect(dest);
        // Draw the movable part of the view
        doDraw(canvas);
        // restore to having no clip
        canvas.restore();

        if ((mTouchMode & TOUCH_MODE_HSCROLL) != 0) {
            float xTranslate;
            if (mViewStartX > 0) {
                xTranslate = mViewWidth;
            } else {
                xTranslate = -mViewWidth;
            }
            // Move the canvas around to prep it for the next view
            // specifically, shift it by a screen and undo the
            // yTranslation which will be redone in the nextView's onDraw().
            canvas.translate(xTranslate, -yTranslate);
            MyDayView nextView = (MyDayView) mViewSwitcher.getNextView();

            // Prevent infinite recursive calls to onDraw().
            nextView.mTouchMode = TOUCH_MODE_INITIAL_STATE;
            // TODO: 2016/7/11 待确认是ondraw or draw
            nextView.draw(canvas); //nextView.onDraw(canvas)
            // Move it back for this view
            canvas.translate(-xTranslate, 0);
        } else {
            // If we drew another view we already translated it back
            // If we didn't draw another view we should be at the edge of the
            // screen
            canvas.translate(mViewStartX, -yTranslate);
        }

        // Draw the fixed areas (that don't scroll) directly to the canvas.
        drawAfterScroll(canvas);
        if (mComputeSelectedEvents && mUpdateToast) {
//            updateEventDetails();// TODO: 2016/7/11 about Event
            mUpdateToast = false;
        }
        mComputeSelectedEvents = false;

        // Draw overscroll glow
        if (!mEdgeEffectTop.isFinished()) {
            if (DAY_HEADER_HEIGHT != 0) {
                canvas.translate(0, DAY_HEADER_HEIGHT);
            }
            if (mEdgeEffectTop.draw(canvas)) {
                invalidate();
            }
            if (DAY_HEADER_HEIGHT != 0) {
                canvas.translate(0, -DAY_HEADER_HEIGHT);
            }
        }
        if (!mEdgeEffectBottom.isFinished()) {
            canvas.rotate(180, mViewWidth / 2, mViewHeight / 2);
            if (mEdgeEffectBottom.draw(canvas)) {
                invalidate();
            }
        }
        canvas.restore();
    }

    private void doDraw(Canvas canvas) {
        Paint p = mPaint;
        Rect r = mRect;
        if (mFutureBgColor != 0) {
            drawBgColors(r, canvas, p);
        }
        drawGridBackground(r, canvas, p);
        drawHours(r, canvas, p);

        // Draw each day
        int cell = mFirstJulianDay;
        p.setAntiAlias(false);
        for (int day = 0; day < mNumDays; day++, cell++) {
            // TODO Wow, this needs cleanup. drawEvents loop through all the
            // events on every call.
            drawEvents(cell, day, HOUR_GAP, canvas, p);
            // If this is today
            if (cell == mTodayJulianDay) {
                int lineY = mCurrentTime.hour * (mCellHeight + HOUR_GAP)
                        + ((mCurrentTime.minute * mCellHeight) / 60) + 1;

                // And the current time shows up somewhere on the screen
                if (lineY >= mViewStartY && lineY < mViewStartY + mViewHeight - 2) {
                    drawCurrentTimeLine(r, day, lineY, canvas, p);
                }
            }
        }
        p.setAntiAlias(true);
        drawSelectedRect(r, canvas, p);
    }

    private void drawSelectedRect(Rect r, Canvas canvas, Paint p) {
        // Draw a highlight on the selected hour (if needed)
        if (mSelectionMode != SELECTION_HIDDEN && !mSelectionAllday) {
            int daynum = mSelectionDay - mFirstJulianDay;
            r.top = mSelectionHour * (mCellHeight + HOUR_GAP);
            r.bottom = r.top + mCellHeight + HOUR_GAP;
            r.left = computeDayLeftPosition(daynum) + 1;
            r.right = computeDayLeftPosition(daynum + 1) + 1;

            saveSelectionPosition(r.left, r.top, r.right, r.bottom);

            // Draw the highlight on the grid
            p.setColor(mCalendarGridAreaSelected);
            r.top += HOUR_GAP;
            r.right -= DAY_GAP;
            p.setAntiAlias(false);
            canvas.drawRect(r, p);

            // Draw a "new event hint" on top of the highlight
            // For the week view, show a "+", for day view, show "+ New event"
            p.setColor(mNewEventHintColor);
            if (mNumDays > 1) {
                p.setStrokeWidth(NEW_EVENT_WIDTH);
                int width = r.right - r.left;
                int midX = r.left + width / 2;
                int midY = r.top + mCellHeight / 2;
                int length = Math.min(mCellHeight, width) - NEW_EVENT_MARGIN * 2;
                length = Math.min(length, NEW_EVENT_MAX_LENGTH);
                int verticalPadding = (mCellHeight - length) / 2;
                int horizontalPadding = (width - length) / 2;
                canvas.drawLine(r.left + horizontalPadding, midY, r.right - horizontalPadding,
                        midY, p);
                canvas.drawLine(midX, r.top + verticalPadding, midX, r.bottom - verticalPadding, p);
            } else {
                p.setStyle(Paint.Style.FILL);
                p.setTextSize(NEW_EVENT_HINT_FONT_SIZE);
                p.setTextAlign(Paint.Align.LEFT);
                p.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                canvas.drawText(mNewEventHintString, r.left + EVENT_TEXT_LEFT_MARGIN,
                        r.top + Math.abs(p.getFontMetrics().ascent) + EVENT_TEXT_TOP_MARGIN, p);
            }
        }

    }

    private void saveSelectionPosition(int left, int top, int right, int bottom) {
        mPrevBox.left = (int) left;
        mPrevBox.right = (int) right;
        mPrevBox.top = (int) top;
        mPrevBox.bottom = (int) bottom;
    }

    private void drawCurrentTimeLine(Rect r, int day, int top, Canvas canvas, Paint p) {
        r.left = computeDayLeftPosition(day) - CURRENT_TIME_LINE_SIDE_BUFFER + 1;
        r.right = computeDayLeftPosition(day + 1) + CURRENT_TIME_LINE_SIDE_BUFFER + 1;

        r.top = top - CURRENT_TIME_LINE_TOP_OFFSET;
        r.bottom = r.top + mCurrentTimeLine.getIntrinsicHeight();

        mCurrentTimeLine.setBounds(r);
        mCurrentTimeLine.draw(canvas);
        if (mAnimateToday) {
            mCurrentTimeAnimateLine.setBounds(r);
            mCurrentTimeAnimateLine.setAlpha(mAnimateTodayAlpha);
            mCurrentTimeAnimateLine.draw(canvas);
        }

    }

    private void drawEvents(int date, int dayIndex, int top, Canvas canvas, Paint p) {
        Paint eventTextPaint = mEventTextPaint;
        int left = computeDayLeftPosition(dayIndex) + 1;
        int cellWidth = computeDayLeftPosition(dayIndex + 1) - left + 1;
        int cellHeight = mCellHeight;

        // Use the selected hour as the selection region
        Rect selectionArea = mSelectionRect;
        selectionArea.top = top + mSelectionHour * (cellHeight + HOUR_GAP);
        selectionArea.bottom = selectionArea.top + cellHeight;
        selectionArea.left = left;
        selectionArea.right = selectionArea.left + cellWidth;

        final ArrayList<Event> events = mEvents;
        int numEvents = events.size();
        EventGeometry geometry = mEventGeometry;

        final int viewEndY = mViewStartY + mViewHeight - DAY_HEADER_HEIGHT - mAlldayHeight;
        for (int i = 0; i < numEvents; i++) {
            Event event = events.get(i);
            if (!geometry.computeEventRect(date, left, top, cellWidth, event)) {
                continue;
            }

            // Don't draw it if it is not visible
            if (event.bottom < mViewStartY || event.top > viewEndY) {
                continue;
            }

            if (date == mSelectionDay && !mSelectionAllday && mComputeSelectedEvents
                    && geometry.eventIntersectsSelection(event, selectionArea)) {
                mSelectedEvents.add(event);
            }

            Rect r = drawEventRect(event, canvas, p, eventTextPaint, mViewStartY, viewEndY);
            setupTextRect(r);

            // Don't draw text if it is not visible
            if (r.top > viewEndY || r.bottom < mViewStartY) {
                continue;
            }
            StaticLayout layout = getEventLayout(mLayouts, i, event, eventTextPaint, r);
            // TODO: not sure why we are 4 pixels off
            drawEventText(layout, r, canvas, mViewStartY + 4, mViewStartY + mViewHeight
                    - DAY_HEADER_HEIGHT - mAlldayHeight);
        }

        if (date == mSelectionDay && !mSelectionAllday && isFocused()
                && mSelectionMode != SELECTION_HIDDEN) {
            computeNeighbors();
        }

    }

    // Computes the "nearest" neighbor event in four directions (left, right,
    // up, down) for each of the events in the mSelectedEvents array.
    private void computeNeighbors() {
        int len = mSelectedEvents.size();
        if (len == 0 || mSelectedEvent != null) {
            return;
        }

        // First, clear all the links
        for (int ii = 0; ii < len; ii++) {
            Event ev = mSelectedEvents.get(ii);
            ev.nextUp = null;
            ev.nextDown = null;
            ev.nextLeft = null;
            ev.nextRight = null;
        }

        Event startEvent = mSelectedEvents.get(0);
        int startEventDistance1 = 100000; // any large number
        int startEventDistance2 = 100000; // any large number
        int prevLocation = FROM_NONE;
        int prevTop;
        int prevBottom;
        int prevLeft;
        int prevRight;
        int prevCenter = 0;
        Rect box = getCurrentSelectionPosition();
        if (mPrevSelectedEvent != null) {
            prevTop = (int) mPrevSelectedEvent.top;
            prevBottom = (int) mPrevSelectedEvent.bottom;
            prevLeft = (int) mPrevSelectedEvent.left;
            prevRight = (int) mPrevSelectedEvent.right;
            // Check if the previously selected event intersects the previous
            // selection box. (The previously selected event may be from a
            // much older selection box.)
            if (prevTop >= mPrevBox.bottom || prevBottom <= mPrevBox.top
                    || prevRight <= mPrevBox.left || prevLeft >= mPrevBox.right) {
                mPrevSelectedEvent = null;
                prevTop = mPrevBox.top;
                prevBottom = mPrevBox.bottom;
                prevLeft = mPrevBox.left;
                prevRight = mPrevBox.right;
            } else {
                // Clip the top and bottom to the previous selection box.
                if (prevTop < mPrevBox.top) {
                    prevTop = mPrevBox.top;
                }
                if (prevBottom > mPrevBox.bottom) {
                    prevBottom = mPrevBox.bottom;
                }
            }
        } else {
            // Just use the previously drawn selection box
            prevTop = mPrevBox.top;
            prevBottom = mPrevBox.bottom;
            prevLeft = mPrevBox.left;
            prevRight = mPrevBox.right;
        }

        // Figure out where we came from and compute the center of that area.
        if (prevLeft >= box.right) {
            // The previously selected event was to the right of us.
            prevLocation = FROM_RIGHT;
            prevCenter = (prevTop + prevBottom) / 2;
        } else if (prevRight <= box.left) {
            // The previously selected event was to the left of us.
            prevLocation = FROM_LEFT;
            prevCenter = (prevTop + prevBottom) / 2;
        } else if (prevBottom <= box.top) {
            // The previously selected event was above us.
            prevLocation = FROM_ABOVE;
            prevCenter = (prevLeft + prevRight) / 2;
        } else if (prevTop >= box.bottom) {
            // The previously selected event was below us.
            prevLocation = FROM_BELOW;
            prevCenter = (prevLeft + prevRight) / 2;
        }

        // For each event in the selected event list "mSelectedEvents", search
        // all the other events in that list for the nearest neighbor in 4
        // directions.
        for (int ii = 0; ii < len; ii++) {
            Event ev = mSelectedEvents.get(ii);

            int startTime = ev.startTime;
            int endTime = ev.endTime;
            int left = (int) ev.left;
            int right = (int) ev.right;
            int top = (int) ev.top;
            if (top < box.top) {
                top = box.top;
            }
            int bottom = (int) ev.bottom;
            if (bottom > box.bottom) {
                bottom = box.bottom;
            }
//            if (false) {
//                int flags = DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_ABBREV_ALL
//                        | DateUtils.FORMAT_CAP_NOON_MIDNIGHT;
//                if (DateFormat.is24HourFormat(mContext)) {
//                    flags |= DateUtils.FORMAT_24HOUR;
//                }
//                String timeRange = DateUtils.formatDateRange(mContext, ev.startMillis,
//                        ev.endMillis, flags);
//                Log.i("Cal", "left: " + left + " right: " + right + " top: " + top + " bottom: "
//                        + bottom + " ev: " + timeRange + " " + ev.title);
//            }
            int upDistanceMin = 10000; // any large number
            int downDistanceMin = 10000; // any large number
            int leftDistanceMin = 10000; // any large number
            int rightDistanceMin = 10000; // any large number
            Event upEvent = null;
            Event downEvent = null;
            Event leftEvent = null;
            Event rightEvent = null;

            // Pick the starting event closest to the previously selected event,
            // if any. distance1 takes precedence over distance2.
            int distance1 = 0;
            int distance2 = 0;
            if (prevLocation == FROM_ABOVE) {
                if (left >= prevCenter) {
                    distance1 = left - prevCenter;
                } else if (right <= prevCenter) {
                    distance1 = prevCenter - right;
                }
                distance2 = top - prevBottom;
            } else if (prevLocation == FROM_BELOW) {
                if (left >= prevCenter) {
                    distance1 = left - prevCenter;
                } else if (right <= prevCenter) {
                    distance1 = prevCenter - right;
                }
                distance2 = prevTop - bottom;
            } else if (prevLocation == FROM_LEFT) {
                if (bottom <= prevCenter) {
                    distance1 = prevCenter - bottom;
                } else if (top >= prevCenter) {
                    distance1 = top - prevCenter;
                }
                distance2 = left - prevRight;
            } else if (prevLocation == FROM_RIGHT) {
                if (bottom <= prevCenter) {
                    distance1 = prevCenter - bottom;
                } else if (top >= prevCenter) {
                    distance1 = top - prevCenter;
                }
                distance2 = prevLeft - right;
            }
            if (distance1 < startEventDistance1
                    || (distance1 == startEventDistance1 && distance2 < startEventDistance2)) {
                startEvent = ev;
                startEventDistance1 = distance1;
                startEventDistance2 = distance2;
            }

            // For each neighbor, figure out if it is above or below or left
            // or right of me and compute the distance.
            for (int jj = 0; jj < len; jj++) {
                if (jj == ii) {
                    continue;
                }
                Event neighbor = mSelectedEvents.get(jj);
                int neighborLeft = (int) neighbor.left;
                int neighborRight = (int) neighbor.right;
                if (neighbor.endTime <= startTime) {
                    // This neighbor is entirely above me.
                    // If we overlap the same column, then compute the distance.
                    if (neighborLeft < right && neighborRight > left) {
                        int distance = startTime - neighbor.endTime;
                        if (distance < upDistanceMin) {
                            upDistanceMin = distance;
                            upEvent = neighbor;
                        } else if (distance == upDistanceMin) {
                            int center = (left + right) / 2;
                            int currentDistance = 0;
                            int currentLeft = (int) upEvent.left;
                            int currentRight = (int) upEvent.right;
                            if (currentRight <= center) {
                                currentDistance = center - currentRight;
                            } else if (currentLeft >= center) {
                                currentDistance = currentLeft - center;
                            }

                            int neighborDistance = 0;
                            if (neighborRight <= center) {
                                neighborDistance = center - neighborRight;
                            } else if (neighborLeft >= center) {
                                neighborDistance = neighborLeft - center;
                            }
                            if (neighborDistance < currentDistance) {
                                upDistanceMin = distance;
                                upEvent = neighbor;
                            }
                        }
                    }
                } else if (neighbor.startTime >= endTime) {
                    // This neighbor is entirely below me.
                    // If we overlap the same column, then compute the distance.
                    if (neighborLeft < right && neighborRight > left) {
                        int distance = neighbor.startTime - endTime;
                        if (distance < downDistanceMin) {
                            downDistanceMin = distance;
                            downEvent = neighbor;
                        } else if (distance == downDistanceMin) {
                            int center = (left + right) / 2;
                            int currentDistance = 0;
                            int currentLeft = (int) downEvent.left;
                            int currentRight = (int) downEvent.right;
                            if (currentRight <= center) {
                                currentDistance = center - currentRight;
                            } else if (currentLeft >= center) {
                                currentDistance = currentLeft - center;
                            }

                            int neighborDistance = 0;
                            if (neighborRight <= center) {
                                neighborDistance = center - neighborRight;
                            } else if (neighborLeft >= center) {
                                neighborDistance = neighborLeft - center;
                            }
                            if (neighborDistance < currentDistance) {
                                downDistanceMin = distance;
                                downEvent = neighbor;
                            }
                        }
                    }
                }

                if (neighborLeft >= right) {
                    // This neighbor is entirely to the right of me.
                    // Take the closest neighbor in the y direction.
                    int center = (top + bottom) / 2;
                    int distance = 0;
                    int neighborBottom = (int) neighbor.bottom;
                    int neighborTop = (int) neighbor.top;
                    if (neighborBottom <= center) {
                        distance = center - neighborBottom;
                    } else if (neighborTop >= center) {
                        distance = neighborTop - center;
                    }
                    if (distance < rightDistanceMin) {
                        rightDistanceMin = distance;
                        rightEvent = neighbor;
                    } else if (distance == rightDistanceMin) {
                        // Pick the closest in the x direction
                        int neighborDistance = neighborLeft - right;
                        int currentDistance = (int) rightEvent.left - right;
                        if (neighborDistance < currentDistance) {
                            rightDistanceMin = distance;
                            rightEvent = neighbor;
                        }
                    }
                } else if (neighborRight <= left) {
                    // This neighbor is entirely to the left of me.
                    // Take the closest neighbor in the y direction.
                    int center = (top + bottom) / 2;
                    int distance = 0;
                    int neighborBottom = (int) neighbor.bottom;
                    int neighborTop = (int) neighbor.top;
                    if (neighborBottom <= center) {
                        distance = center - neighborBottom;
                    } else if (neighborTop >= center) {
                        distance = neighborTop - center;
                    }
                    if (distance < leftDistanceMin) {
                        leftDistanceMin = distance;
                        leftEvent = neighbor;
                    } else if (distance == leftDistanceMin) {
                        // Pick the closest in the x direction
                        int neighborDistance = left - neighborRight;
                        int currentDistance = left - (int) leftEvent.right;
                        if (neighborDistance < currentDistance) {
                            leftDistanceMin = distance;
                            leftEvent = neighbor;
                        }
                    }
                }
            }
            ev.nextUp = upEvent;
            ev.nextDown = downEvent;
            ev.nextLeft = leftEvent;
            ev.nextRight = rightEvent;
        }
        mSelectedEvent = startEvent;
    }

    private void saveSelectionPosition(float left, float top, float right, float bottom) {
        mPrevBox.left = (int) left;
        mPrevBox.right = (int) right;
        mPrevBox.top = (int) top;
        mPrevBox.bottom = (int) bottom;
    }

    private Rect getCurrentSelectionPosition() {
        Rect box = new Rect();
        box.top = mSelectionHour * (mCellHeight + HOUR_GAP);
        box.bottom = box.top + mCellHeight + HOUR_GAP;
        int daynum = mSelectionDay - mFirstJulianDay;
        box.left = computeDayLeftPosition(daynum) + 1;
        box.right = computeDayLeftPosition(daynum + 1);
        return box;
    }

    private void drawEventText(StaticLayout eventLayout, Rect rect, Canvas canvas, int top,
                               int bottom) {
        // drawEmptyRect(canvas, rect, 0xFFFF00FF); // for debugging

        int width = rect.right - rect.left;
        int height = rect.bottom - rect.top;

        // If the rectangle is too small for text, then return
        if (eventLayout == null || width < MIN_CELL_WIDTH_FOR_TEXT) {
            return;
        }

        int totalLineHeight = 0;
        int lineCount = eventLayout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            int lineBottom = eventLayout.getLineBottom(i);
            if (lineBottom <= height) {
                totalLineHeight = lineBottom;
            } else {
                break;
            }
        }

        if (totalLineHeight == 0 || rect.top > bottom || rect.top + totalLineHeight < top) {
            return;
        }
        // Use a StaticLayout to format the string.
        canvas.save();
        canvas.translate(rect.left, rect.top);
        rect.left = 0;
        rect.right = width;
        rect.top = 0;
        rect.bottom = totalLineHeight;
        // There's a bug somewhere. If this rect is outside of a previous
        // cliprect, this becomes a no-op. What happens is that the text draw
        // past the event rect. The current fix is to not draw the staticLayout
        // at all if it is completely out of bound.
        canvas.clipRect(rect);
        eventLayout.draw(canvas);
        canvas.restore();
    }

    /**
     * Return the layout for a numbered event. Create it if not already existing
     */
    private StaticLayout getEventLayout(StaticLayout[] layouts, int i, Event event, Paint paint,
                                        Rect r) {
        if (i < 0 || i >= layouts.length) {
            return null;
        }

        StaticLayout layout = layouts[i];
        // Check if we have already initialized the StaticLayout and that
        // the width hasn't changed (due to vertical resizing which causes
        // re-layout of events at min height)
        if (layout == null || r.width() != layout.getWidth()) {
            SpannableStringBuilder bob = new SpannableStringBuilder();
            if (event.title != null) {
                // MAX - 1 since we add a space
                bob.append(drawTextSanitizer(event.title.toString(), MAX_EVENT_TEXT_LEN - 1));
                bob.setSpan(new StyleSpan(Typeface.BOLD), 0, bob.length(), 0);
                bob.append(' ');
            }
            if (event.location != null) {
                bob.append(drawTextSanitizer(event.location.toString(),
                        MAX_EVENT_TEXT_LEN - bob.length()));
            }
            // TODO: 2016/7/11 设置画笔颜色
           /* switch (event.selfAttendeeStatus) {
                case Attendees.ATTENDEE_STATUS_INVITED:
                    paint.setColor(event.color);
                    break;
                case Attendees.ATTENDEE_STATUS_DECLINED:
                    paint.setColor(mEventTextColor);
                    paint.setAlpha(Utils.DECLINED_EVENT_TEXT_ALPHA);
                    break;
                case Attendees.ATTENDEE_STATUS_NONE: // Your own events
                case Attendees.ATTENDEE_STATUS_ACCEPTED:
                case Attendees.ATTENDEE_STATUS_TENTATIVE:
                default:
                    paint.setColor(mEventTextColor);
                    break;
            }*/
            paint.setColor(mEventTextColor);

            // Leave a one pixel boundary on the left and right of the rectangle for the event
            layout = new StaticLayout(bob, 0, bob.length(), new TextPaint(paint), r.width(),
                    Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true, null, r.width());

            layouts[i] = layout;
        }

        return layout;
    }

    private Pattern drawTextSanitizerFilter = Pattern.compile("[\t\n],");

    // Sanitize a string before passing it to drawText or else we get little
    // squares. For newlines and tabs before a comma, delete the character.
    // Otherwise, just replace them with a space.
    private String drawTextSanitizer(String string, int maxEventTextLen) {
        Matcher m = drawTextSanitizerFilter.matcher(string);
        string = m.replaceAll(",");

        int len = string.length();
        if (maxEventTextLen <= 0) {
            string = "";
            len = 0;
        } else if (len > maxEventTextLen) {
            string = string.substring(0, maxEventTextLen);
            len = maxEventTextLen;
        }

        return string.replace('\n', ' ');
    }


    private void setupTextRect(Rect r) {
        if (r.bottom <= r.top || r.right <= r.left) {
            r.bottom = r.top;
            r.right = r.left;
            return;
        }

        if (r.bottom - r.top > EVENT_TEXT_TOP_MARGIN + EVENT_TEXT_BOTTOM_MARGIN) {
            r.top += EVENT_TEXT_TOP_MARGIN;
            r.bottom -= EVENT_TEXT_BOTTOM_MARGIN;
        }
        if (r.right - r.left > EVENT_TEXT_LEFT_MARGIN + EVENT_TEXT_RIGHT_MARGIN) {
            r.left += EVENT_TEXT_LEFT_MARGIN;
            r.right -= EVENT_TEXT_RIGHT_MARGIN;
        }
    }

    private Rect drawEventRect(Event event, Canvas canvas, Paint p, Paint eventTextPaint,
                               int visibleTop, int visibleBot) {
        // Draw the Event Rect
        Rect r = mRect;
        r.top = Math.max((int) event.top + EVENT_RECT_TOP_MARGIN, visibleTop);
        r.bottom = Math.min((int) event.bottom - EVENT_RECT_BOTTOM_MARGIN, visibleBot);
        r.left = (int) event.left + EVENT_RECT_LEFT_MARGIN;
        r.right = (int) event.right;

        int color = event.color;
        // TODO: 2016/7/11  不同事件类型设置 paint color
        p.setStyle(Paint.Style.FILL_AND_STROKE);
//        switch (event.selfAttendeeStatus) {
//            case Attendees.ATTENDEE_STATUS_INVITED:
//                p.setStyle(Style.STROKE);
//                break;
//            case Attendees.ATTENDEE_STATUS_DECLINED:
//                color = Utils.getDeclinedColorFromColor(color);
//            case Attendees.ATTENDEE_STATUS_NONE: // Your own events
//            case Attendees.ATTENDEE_STATUS_ACCEPTED:
//            case Attendees.ATTENDEE_STATUS_TENTATIVE:
//            default:
//                p.setStyle(Style.FILL_AND_STROKE);
//                break;
//        }
        p.setAntiAlias(false);

        int floorHalfStroke = (int) Math.floor(EVENT_RECT_STROKE_WIDTH / 2.0f);
        int ceilHalfStroke = (int) Math.ceil(EVENT_RECT_STROKE_WIDTH / 2.0f);
        r.top = Math.max((int) event.top + EVENT_RECT_TOP_MARGIN + floorHalfStroke, visibleTop);
        r.bottom = Math.min((int) event.bottom - EVENT_RECT_BOTTOM_MARGIN - ceilHalfStroke,
                visibleBot);
        r.left += floorHalfStroke;
        r.right -= ceilHalfStroke;
        p.setStrokeWidth(EVENT_RECT_STROKE_WIDTH);
        p.setColor(color);
        canvas.drawRect(r, p);

        p.setStyle(Paint.Style.FILL);

        // If this event is selected, then use the selection color
        if (mSelectedEvent == event) {
            boolean paintIt = false;
            color = 0;
            if (mSelectionMode == SELECTION_PRESSED) {
                // Also, remember the last selected event that we drew
                mPrevSelectedEvent = event;
                color = mPressedColor;
                paintIt = true;
            } else if (mSelectionMode == SELECTION_SELECTED) {
                // Also, remember the last selected event that we drew
                mPrevSelectedEvent = event;
                color = mPressedColor;
                paintIt = true;
            }

            if (paintIt) {
                p.setColor(color);
                canvas.drawRect(r, p);
            }

            p.setAntiAlias(true);
        }
        // Setup rect for drawEventText which follows
        r.top = (int) event.top + EVENT_RECT_TOP_MARGIN;
        r.bottom = (int) event.bottom - EVENT_RECT_BOTTOM_MARGIN;
        r.left = (int) event.left + EVENT_RECT_LEFT_MARGIN;
        r.right = (int) event.right - EVENT_RECT_RIGHT_MARGIN;
        return r;
    }


    private void drawHours(Rect r, Canvas canvas, Paint p) {
        setupHourTextPaint(p);
        int y = HOUR_GAP + mHoursTextHeight + HOURS_TOP_MARGIN;

        for (int i = 0; i < 24; i++) {
            String time = mHourStrs[i];
            //canvas.drawText(time, HOURS_LEFT_MARGIN, y, p);
            canvas.drawText(time, HOURS_LEFT_MARGIN, y, p);
            y += mCellHeight + HOUR_GAP;
        }
    }

    private void setupHourTextPaint(Paint p) {
        p.setColor(mCalendarHourLabelColor);
        p.setTextSize(HOURS_TEXT_SIZE);
        p.setTypeface(Typeface.DEFAULT);
        p.setTextAlign(Paint.Align.CENTER);
        p.setAntiAlias(true);
    }

    private void drawGridBackground(Rect r, Canvas canvas, Paint p) {
        Paint.Style savedStyle = p.getStyle();

        final float stopX = computeDayLeftPosition(mNumDays + 1);
        float y = 0;
        final float deltaY = mCellHeight + HOUR_GAP;
        int linesIndex = 0;
        final float startY = 0;
        final float stopY = HOUR_GAP + 24 * (mCellHeight + HOUR_GAP);
        float x = mHoursWidth;

        // Draw the inner horizontal grid lines
        p.setColor(mCalendarGridLineInnerHorizontalColor);
        p.setStrokeWidth(GRID_LINE_INNER_WIDTH);
        p.setAntiAlias(false);
        y = 0;
        linesIndex = 0;
        for (int hour = 0; hour <= 24; hour++) {
            mLines[linesIndex++] = GRID_LINE_LEFT_MARGIN;
            mLines[linesIndex++] = y;
            mLines[linesIndex++] = stopX;
            mLines[linesIndex++] = y;
            y += deltaY;
        }
        if (mCalendarGridLineInnerVerticalColor != mCalendarGridLineInnerHorizontalColor) {
            canvas.drawLines(mLines, 0, linesIndex, p);
            linesIndex = 0;
            p.setColor(mCalendarGridLineInnerVerticalColor);
        }

        // Draw the inner vertical grid lines
        for (int day = 0; day <= mNumDays; day++) {
            x = computeDayLeftPosition(day);
            mLines[linesIndex++] = x;
            mLines[linesIndex++] = startY;
            mLines[linesIndex++] = x;
            mLines[linesIndex++] = stopY;
        }
        canvas.drawLines(mLines, 0, linesIndex, p);

        // Restore the saved style.
        p.setStyle(savedStyle);
        p.setAntiAlias(true);
    }

    private void drawBgColors(Rect r, Canvas canvas, Paint p) {
        int todayIndex = mTodayJulianDay - mFirstJulianDay;
        // Draw the hours background color
        r.top = mDestRect.top;
        r.bottom = mDestRect.bottom;
        r.left = 0;
        r.right = mHoursWidth;
        p.setColor(mBgColor);
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(false);
        canvas.drawRect(r, p);

        // Draw background for grid area
        if (mNumDays == 1 && todayIndex == 0) {
            // Draw a white background for the time later than current time
            int lineY = mCurrentTime.hour * (mCellHeight + HOUR_GAP)
                    + ((mCurrentTime.minute * mCellHeight) / 60) + 1;
            if (lineY < mViewStartY + mViewHeight) {
                lineY = Math.max(lineY, mViewStartY);
                r.left = mHoursWidth;
                r.right = mViewWidth;
                r.top = lineY;
                r.bottom = mViewStartY + mViewHeight;
                p.setColor(mFutureBgColor);
                canvas.drawRect(r, p);
            }
        } else if (todayIndex >= 0 && todayIndex < mNumDays) {
            // Draw today with a white background for the time later than current time
            int lineY = mCurrentTime.hour * (mCellHeight + HOUR_GAP)
                    + ((mCurrentTime.minute * mCellHeight) / 60) + 1;
            if (lineY < mViewStartY + mViewHeight) {
                lineY = Math.max(lineY, mViewStartY);
                r.left = computeDayLeftPosition(todayIndex) + 1;
                r.right = computeDayLeftPosition(todayIndex + 1);
                r.top = lineY;
                r.bottom = mViewStartY + mViewHeight;
                p.setColor(mFutureBgColor);
                canvas.drawRect(r, p);
            }

            // Paint Tomorrow and later days with future color
            if (todayIndex + 1 < mNumDays) {
                r.left = computeDayLeftPosition(todayIndex + 1) + 1;
                r.right = computeDayLeftPosition(mNumDays + 1);
                r.top = mDestRect.top;
                r.bottom = mDestRect.bottom;
                p.setColor(mFutureBgColor);
                canvas.drawRect(r, p);
            }
        } else if (todayIndex < 0) {
            // Future
            r.left = computeDayLeftPosition(0) + 1;
            r.right = computeDayLeftPosition(mNumDays + 1);
            r.top = mDestRect.top;
            r.bottom = mDestRect.bottom;
            p.setColor(mFutureBgColor);
            canvas.drawRect(r, p);
        }
        p.setAntiAlias(true);
    }

    private void drawAfterScroll(Canvas canvas) {
        Paint p = mPaint;
        Rect r = mRect;
        drawAllDayHighlights(r, canvas, p);
        if (mMaxAlldayEvents != 0) {
            drawAllDayEvents(mFirstJulianDay, mNumDays, canvas, p);
            drawUpperLeftCorner(r, canvas, p);
        }
        drawScrollLine(r, canvas, p);
        drawDayHeaderLoop(r, canvas, p);
        // Draw the AM and PM indicators if we're in 12 hour mode
        if (!mIs24HourFormat) {
            drawAmPm(canvas, p);
        }
    }

    // This isn't really the upper-left corner. It's the square area just
    // below the upper-left corner, above the hours and to the left of the
    // all-day area.
    // 绘出全天事件左上角的区域
    private void drawUpperLeftCorner(Rect r, Canvas canvas, Paint p) {
        setupHourTextPaint(p);

        if (mMaxAlldayEvents > mMaxUnexpandedAlldayEventCount) {
            // Draw the allDay expand/collapse icon
            if (mUseExpandIcon) {
                mExpandAlldayDrawable.setBounds(mExpandAllDayRect);
                mExpandAlldayDrawable.draw(canvas);
            } else {
                mCollapseAlldayDrawable.setBounds(mExpandAllDayRect);
                mCollapseAlldayDrawable.draw(canvas);
            }
        }
        // 绘制全天
        String allDayString = CalendarData.allDayString;
        float width = p.measureText(allDayString);
        p.setTextSize(AMPM_TEXT_SIZE);
        p.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(allDayString, mExpandAllDayRect.centerX() - width/2,mExpandAllDayRect.top - 5,p);
    }

    private void drawAllDayEvents(int firstDay, int numDays, Canvas canvas, Paint p) {

        p.setTextSize(NORMAL_FONT_SIZE);
        p.setTextAlign(Paint.Align.LEFT);
        Paint eventTextPaint = mEventTextPaint;

        final float startY = DAY_HEADER_HEIGHT;
        final float stopY = startY + mAlldayHeight + ALLDAY_TOP_MARGIN;
        float x = 0;
        int linesIndex = 0;

        // Draw the inner vertical grid lines
        p.setColor(mCalendarGridLineInnerVerticalColor);
        x = mHoursWidth;
        p.setStrokeWidth(GRID_LINE_INNER_WIDTH);
        // Line bounding the top of the all day area
        mLines[linesIndex++] = GRID_LINE_LEFT_MARGIN;
        mLines[linesIndex++] = startY;
        mLines[linesIndex++] = computeDayLeftPosition(mNumDays + 1);
        mLines[linesIndex++] = startY;

        for (int day = 0; day <= mNumDays; day++) {
            x = computeDayLeftPosition(day);
            mLines[linesIndex++] = x;
            mLines[linesIndex++] = startY;
            mLines[linesIndex++] = x;
            mLines[linesIndex++] = stopY;
        }
        p.setAntiAlias(false);
        canvas.drawLines(mLines, 0, linesIndex, p);
        p.setStyle(Paint.Style.FILL);

        int y = DAY_HEADER_HEIGHT + ALLDAY_TOP_MARGIN;
        int lastDay = firstDay + numDays - 1;
        final ArrayList<Event> events = mAllDayEvents;
        int numEvents = events.size();
        // Whether or not we should draw the more events text
        boolean hasMoreEvents = false;
        // size of the allDay area
        float drawHeight = mAlldayHeight;
        // max number of events being drawn in one day of the allday area
        float numRectangles = mMaxAlldayEvents;
        // Where to cut off drawn allday events
        int allDayEventClip = DAY_HEADER_HEIGHT + mAlldayHeight + ALLDAY_TOP_MARGIN;
        // The number of events that weren't drawn in each day
        mSkippedAlldayEvents = new int[numDays];
        if (mMaxAlldayEvents > mMaxUnexpandedAlldayEventCount && !mShowAllAllDayEvents &&
                mAnimateDayHeight == 0) {
            // We draw one fewer event than will fit so that more events text
            // can be drawn
            numRectangles = mMaxUnexpandedAlldayEventCount - 1;
            // We also clip the events above the more events text
            allDayEventClip -= MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT;
            hasMoreEvents = true;
        } else if (mAnimateDayHeight != 0) {
            // clip at the end of the animating space
            allDayEventClip = DAY_HEADER_HEIGHT + mAnimateDayHeight + ALLDAY_TOP_MARGIN;
        }
        for (int i = 0; i < numEvents; i++) {
            Event event = events.get(i);
            int startDay = event.startDay;
            int endDay = event.endDay;
            if (startDay > lastDay || endDay < firstDay) {
                continue;
            }
            if (startDay < firstDay) {
                startDay = firstDay;
            }
            if (endDay > lastDay) {
                endDay = lastDay;
            }
            int startIndex = startDay - firstDay;
            int endIndex = endDay - firstDay;
            float height = mMaxAlldayEvents > mMaxUnexpandedAlldayEventCount ? mAnimateDayEventHeight :
                    drawHeight / numRectangles;

            // Prevent a single event from getting too big
            if (height > MAX_HEIGHT_OF_ONE_ALLDAY_EVENT) {
                height = MAX_HEIGHT_OF_ONE_ALLDAY_EVENT;
            }

            // Leave a one-pixel space between the vertical day lines and the
            // event rectangle.
            event.left = computeDayLeftPosition(startIndex);
            event.right = computeDayLeftPosition(endIndex + 1) - DAY_GAP;
            event.top = y + height * event.getColumn();
            event.bottom = event.top + height - ALL_DAY_EVENT_RECT_BOTTOM_MARGIN;
            if (mMaxAlldayEvents > mMaxUnexpandedAlldayEventCount) {
                // check if we should skip this event. We skip if it starts
                // after the clip bound or ends after the skip bound and we're
                // not animating.
                if (event.top >= allDayEventClip) {
                    incrementSkipCount(mSkippedAlldayEvents, startIndex, endIndex);
                    continue;
                } else if (event.bottom > allDayEventClip) {
                    if (hasMoreEvents) {
                        incrementSkipCount(mSkippedAlldayEvents, startIndex, endIndex);
                        continue;
                    }
                    event.bottom = allDayEventClip;
                }
            }
            Rect r = drawEventRect(event, canvas, p, eventTextPaint, (int) event.top,
                    (int) event.bottom);
            setupAllDayTextRect(r);
            StaticLayout layout = getEventLayout(mAllDayLayouts, i, event, eventTextPaint, r);
            drawEventText(layout, r, canvas, r.top, r.bottom);

            // Check if this all-day event intersects the selected day
            if (mSelectionAllday && mComputeSelectedEvents) {
                if (startDay <= mSelectionDay && endDay >= mSelectionDay) {
                    mSelectedEvents.add(event);
                }
            }
        }
        if (mMoreAlldayEventsTextAlpha != 0 && mSkippedAlldayEvents != null) {
            // If the more allday text should be visible, draw it.
            p.setColor(mMoreAlldayEventsTextAlpha << 24 & mMoreEventsTextColor);
            for (int i = 0; i < mSkippedAlldayEvents.length; i++) {
                if (mSkippedAlldayEvents[i] > 0) {
                    drawMoreAlldayEvents(canvas, mSkippedAlldayEvents[i], i, p);
                }
            }
        }

        if (mSelectionAllday) {
            // Compute the neighbors for the list of all-day events that
            // intersect the selected day.
            computeAllDayNeighbors();
            // Set the selection position to zero so that when we move down
            // to the normal event area, we will highlight the topmost event.
            saveSelectionPosition(0f, 0f, 0f, 0f);
        }
    }

    // 计算全天事件周围
    private void computeAllDayNeighbors() {
        int len = mSelectedEvents.size();
        if (len == 0 || mSelectedEvent != null) {
            return;
        }
        // First, clear all the links
        for (int ii = 0; ii < len; ii++) {
            Event ev = mSelectedEvents.get(ii);
            ev.nextUp = null;
            ev.nextDown = null;
            ev.nextLeft = null;
            ev.nextRight = null;
        }
        // For each event in the selected event list "mSelectedEvents", find
        // its neighbors in the up and down directions. This could be done
        // more efficiently by sorting on the Event.getColumn() field, but
        // the list is expected to be very small.

        // Find the event in the same row as the previously selected all-day
        // event, if any.
        int startPosition = -1;
        if (mPrevSelectedEvent != null && mPrevSelectedEvent.drawAsAllday()) {
            startPosition = mPrevSelectedEvent.getColumn();
        }
        int maxPosition = -1;
        Event startEvent = null;
        Event maxPositionEvent = null;
        for (int ii = 0; ii < len; ii++) {
            Event ev = mSelectedEvents.get(ii);
            int position = ev.getColumn();
            if (position == startPosition) {
                startEvent = ev;
            } else if (position > maxPosition) {
                maxPositionEvent = ev;
                maxPosition = position;
            }
            for (int jj = 0; jj < len; jj++) {
                if (jj == ii) {
                    continue;
                }
                Event neighbor = mSelectedEvents.get(jj);
                int neighborPosition = neighbor.getColumn();
                if (neighborPosition == position - 1) {
                    ev.nextUp = neighbor;
                } else if (neighborPosition == position + 1) {
                    ev.nextDown = neighbor;
                }
            }
        }
        if (startEvent != null) {
            mSelectedEvent = startEvent;
        } else {
            mSelectedEvent = maxPositionEvent;
        }
    }

    // Draws the "box +n" text for hidden allday events // 画出更多全天事件
    protected void drawMoreAlldayEvents(Canvas canvas, int remainingEvents, int day, Paint p) {
        int x = computeDayLeftPosition(day) + EVENT_ALL_DAY_TEXT_LEFT_MARGIN;
        int y = (int) (mAlldayHeight - .5f * MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT - .5f
                * EVENT_SQUARE_WIDTH + DAY_HEADER_HEIGHT + ALLDAY_TOP_MARGIN);
        Rect r = mRect;
        r.top = y;
        r.left = x;
        r.bottom = y + EVENT_SQUARE_WIDTH;
        r.right = x + EVENT_SQUARE_WIDTH;
        p.setColor(mMoreEventsTextColor);
        p.setStrokeWidth(EVENT_RECT_STROKE_WIDTH);
        p.setStyle(Paint.Style.STROKE);
        p.setAntiAlias(false);
        canvas.drawRect(r, p);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        p.setTextSize(EVENT_TEXT_FONT_SIZE);
        // 获取全天事件,用+1,+2显示
        String text = mResources.getQuantityString(R.plurals.month_more_events, remainingEvents);
        y += EVENT_SQUARE_WIDTH;
        x += EVENT_SQUARE_WIDTH + EVENT_LINE_PADDING;
        canvas.drawText(String.format(text, remainingEvents), x, y, p);
    }

    private void setupAllDayTextRect(Rect r) {
        if (r.bottom <= r.top || r.right <= r.left) {
            r.bottom = r.top;
            r.right = r.left;
            return;
        }

        if (r.bottom - r.top > EVENT_ALL_DAY_TEXT_TOP_MARGIN + EVENT_ALL_DAY_TEXT_BOTTOM_MARGIN) {
            r.top += EVENT_ALL_DAY_TEXT_TOP_MARGIN;
            r.bottom -= EVENT_ALL_DAY_TEXT_BOTTOM_MARGIN;
        }
        if (r.right - r.left > EVENT_ALL_DAY_TEXT_LEFT_MARGIN + EVENT_ALL_DAY_TEXT_RIGHT_MARGIN) {
            r.left += EVENT_ALL_DAY_TEXT_LEFT_MARGIN;
            r.right -= EVENT_ALL_DAY_TEXT_RIGHT_MARGIN;
        }
    }

    // Helper method for counting the number of allday events skipped on each day
    private void incrementSkipCount(int[] counts, int startIndex, int endIndex) {
        if (counts == null || startIndex < 0 || endIndex > counts.length) {
            return;
        }
        for (int i = startIndex; i <= endIndex; i++) {
            counts[i]++;
        }
    }

    private void drawAllDayHighlights(Rect r, Canvas canvas, Paint p) {
        if (mFutureBgColor != 0) {
            // First, color the labels area light gray
            r.top = 0;
            r.bottom = DAY_HEADER_HEIGHT;
            r.left = 0;
            r.right = mViewWidth;
            p.setColor(mBgColor);
            p.setStyle(Paint.Style.FILL);
            canvas.drawRect(r, p);
            // and the area that says All day
            r.top = DAY_HEADER_HEIGHT;
            r.bottom = mFirstCell - 1;
            r.left = 0;
            r.right = mHoursWidth;
            canvas.drawRect(r, p);

            int startIndex = -1;

            int todayIndex = mTodayJulianDay - mFirstJulianDay;
            if (todayIndex < 0) {
                // Future
                startIndex = 0;
            } else if (todayIndex >= 1 && todayIndex + 1 < mNumDays) {
                // Multiday - tomorrow is visible.
                startIndex = todayIndex + 1;
            }

            if (startIndex >= 0) {
                // Draw the future highlight
                r.top = 0;
                r.bottom = mFirstCell - 1;
                r.left = computeDayLeftPosition(startIndex) + 1;
                r.right = computeDayLeftPosition(mNumDays + 1);
                p.setColor(mFutureBgColor);
                p.setStyle(Paint.Style.FILL);
                canvas.drawRect(r, p);
            }
        }

        if (mSelectionAllday && mSelectionMode != SELECTION_HIDDEN) {
            // Draw the selection highlight on the selected all-day area
            mRect.top = DAY_HEADER_HEIGHT + 1;
            mRect.bottom = mRect.top + mAlldayHeight + ALLDAY_TOP_MARGIN - 2;
            int daynum = mSelectionDay - mFirstJulianDay;
            mRect.left = computeDayLeftPosition(daynum) + 1;
            mRect.right = computeDayLeftPosition(daynum + 1);
            p.setColor(mCalendarGridAreaSelected);
            canvas.drawRect(mRect, p);
        }
    }

    private void drawDayHeaderLoop(Rect r, Canvas canvas, Paint p) {
        if (mNumDays == 1 && ONE_DAY_HEADER_HEIGHT == 0) {
            return;
        }
        p.setTypeface(mBold);
        p.setTextAlign(Paint.Align.RIGHT);
        int cell = mFirstJulianDay;

        // 星期的String
       /* String[] dayNames;
        if (mDateStrWidth < mCellWidth) {
            dayNames = mDayStrs;
        } else {
            dayNames = mDayStrs2Letter;
        }*/

        p.setAntiAlias(true);
        for (int day = 0; day < mNumDays; day++, cell++) {
            int dayOfWeek = day + mFirstVisibleDayOfWeek;
            if (dayOfWeek >= 14) {
                dayOfWeek -= 14;
            }

            int color = mCalendarDateBannerTextColor;
            if (mNumDays == 1) {
                if (dayOfWeek == Time.SATURDAY) {
                    color = mWeek_saturdayColor;
                } else if (dayOfWeek == Time.SUNDAY) {
                    color = mWeek_sundayColor;
                }
            } else {
                final int column = day % 7;
                if (Utils.isSaturday(column, mFirstDayOfWeek)) {
                    color = mWeek_saturdayColor;
                } else if (Utils.isSunday(column, mFirstDayOfWeek)) {
                    color = mWeek_sundayColor;
                }
            }

            p.setColor(color);
            String lunarDate = getLunarDate(cell);//根据julianDay获取农历日期
           // drawDayHeader(dayNames[dayOfWeek], day, cell, canvas, p);
            drawDayHeader(lunarDate, day, cell, canvas, p);

        }
        p.setTypeface(null);
    }

    private String getLunarDate(int firstJulianDay) {
        Time time = new Time();
        time.setJulianDay(firstJulianDay);
        LunarCalendar mLunarCalendar = new LunarCalendar();
        return mLunarCalendar.getLunarDate(time.year, time.month + 1, time.monthDay,true);
    }

    private void drawDayHeader(String dayStr, int day, int cell, Canvas canvas, Paint p) {
        int dateNum = mFirstVisibleDate + day;
        int x;
        if (dateNum > mMonthLength) {
            dateNum -= mMonthLength;
        }
        p.setAntiAlias(true);

        int todayIndex = mTodayJulianDay - mFirstJulianDay;
        // Draw day of the month
        String dateNumStr = String.valueOf(dateNum);
        if (mNumDays > 1) {
            p.setTextAlign(Paint.Align.LEFT);
            p.setTextSize(DATE_HEADER_FONT_SIZE);
            x = computeDayLeftPosition(day) +(mCellWidth + DAY_GAP - cumputeTextWidth(dateNumStr,p))/2;

            float y = (float) (getTextStrHeight(DATE_HEADER_FONT_SIZE)) - 4 ;

            if (todayIndex == day) {
                // draw today circle backgroud
                int circleY = DAY_HEADER_HEIGHT/2;
                int circleX = computeDayLeftPosition(day) +(mCellWidth + DAY_GAP +DAY_HEADER_RIGHT_MARGIN )/2;
                p.setColor(mTodayBackgroudColor);
                canvas.drawCircle(circleX,circleY,(DAY_HEADER_HEIGHT +DAY_GAP)/2,p);
            }
            p.setTypeface(todayIndex == day ? mBold : Typeface.DEFAULT);
            p.setColor(todayIndex == day ? mTodayDateColor : mHeaderDateColor);
            canvas.drawText(dateNumStr, x, y, p);

            // Draw lunar date
            p.setTextSize(DAY_HEADER_FONT_SIZE);
            p.setTypeface(Typeface.DEFAULT);
            p.setColor(todayIndex == day  ? mTodayDateColor :mHeaderLunarDatecolor);
            p.setTypeface(todayIndex == day ? mBold : Typeface.DEFAULT);
            x = computeDayLeftPosition(day) +(mCellWidth + DAY_GAP -  cumputeTextWidth(dayStr,p))/2;
            // dayStr 的y坐标
            y = DAY_HEADER_HEIGHT - DAY_HEADER_BOTTOM_MARGIN -2 ;
            canvas.drawText(dayStr, x, y, p);
        } else {
            float y = ONE_DAY_HEADER_HEIGHT - DAY_HEADER_ONE_DAY_BOTTOM_MARGIN;
            p.setTextAlign(Paint.Align.LEFT);
            // Draw day of the week
            x = computeDayLeftPosition(day) + DAY_HEADER_ONE_DAY_LEFT_MARGIN;
            p.setTextSize(DAY_HEADER_FONT_SIZE);
            p.setTypeface(Typeface.DEFAULT);
            canvas.drawText(dayStr, x, y, p);

            // Draw day of the month
            x += p.measureText(dayStr) + DAY_HEADER_ONE_DAY_RIGHT_MARGIN;
            p.setTextSize(DATE_HEADER_FONT_SIZE);
            p.setTypeface(todayIndex == day ? mBold : Typeface.DEFAULT);
            canvas.drawText(dateNumStr, x, y, p);
        }
    }

    private float getTextStrHeight(float textSize) {
        Paint p = new Paint();
        p.setTextSize(textSize);
        p.setAntiAlias(true);
        Paint.FontMetrics fm = p.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent);
    }

    private void drawScrollLine(Rect r, Canvas canvas, Paint p) {
        final int right = computeDayLeftPosition(mNumDays + 1);
        final int y = mFirstCell - 1;

        p.setAntiAlias(false);
        p.setStyle(Paint.Style.FILL);

        p.setColor(mCalendarGridLineInnerHorizontalColor);
        p.setStrokeWidth(GRID_LINE_INNER_WIDTH);
        canvas.drawLine(GRID_LINE_LEFT_MARGIN, y, right, y, p);
        p.setAntiAlias(true);

    }

    // Computes the x position for the left side of the given day
    private int computeDayLeftPosition(int day) {
        int effectiveWidth = mViewWidth - mHoursWidth;
        return day * effectiveWidth / mNumDays + mHoursWidth;
    }

    private void drawAmPm(Canvas canvas, Paint p) {
        p.setColor(mCalendarAmPmLabel);
        p.setTextSize(AMPM_TEXT_SIZE);
        p.setTypeface(mBold);
        p.setAntiAlias(true);
        p.setTextAlign(Paint.Align.CENTER);
        String text = mAmString;
        if (mFirstHour >= 12) {
            text = mPmString;
        }
        int y = mFirstCell + mFirstHourOffset + 2 * mHoursTextHeight + HOUR_GAP;
        canvas.drawText(text, HOURS_LEFT_MARGIN, y, p);

        if (mFirstHour < 12 && mFirstHour + mNumHours > 12) {
            // Also draw the "PM"
            text = mPmString;
            y = mFirstCell + mFirstHourOffset + (12 - mFirstHour) * (mCellHeight + HOUR_GAP)
                    + 2 * mHoursTextHeight + HOUR_GAP;
            canvas.drawText(text, HOURS_LEFT_MARGIN, y, p);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (DEBUG) Log.e(TAG, "" + action + " ev.getPointerCount() = " + ev.getPointerCount());

        if ((mTouchMode & TOUCH_MODE_HSCROLL) == 0) {
            mScaleGestureDetector.onTouchEvent(ev);
            if (mScaleGestureDetector.isInProgress()) {
                return true;
            }
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartingScroll = true;
                if (DEBUG) {
                    Log.e(TAG, "ACTION_DOWN ev.getDownTime = " + ev.getDownTime() + " Cnt="
                            + ev.getPointerCount());
                }

                int bottom = mAlldayHeight + DAY_HEADER_HEIGHT + ALLDAY_TOP_MARGIN;
                if (ev.getY() < bottom) {
                    mTouchStartedInAlldayArea = true;
                } else {
                    mTouchStartedInAlldayArea = false;
                }
                mHandleActionUp = true;
                mGestureDetector.onTouchEvent(ev);
                return true;

            case MotionEvent.ACTION_MOVE:
                if (DEBUG) Log.e(TAG, "ACTION_MOVE Cnt=" + ev.getPointerCount() + MyDayView.this);
                mGestureDetector.onTouchEvent(ev);
                return true;

            case MotionEvent.ACTION_UP:
                if (DEBUG) Log.e(TAG, "ACTION_UP Cnt=" + ev.getPointerCount() + mHandleActionUp);
                mEdgeEffectTop.onRelease();
                mEdgeEffectBottom.onRelease();
                mStartingScroll = false;
                mGestureDetector.onTouchEvent(ev);
                if (!mHandleActionUp) {
                    mHandleActionUp = true;
                    mViewStartX = 0;
                    invalidate();
                    return true;
                }
                if (mOnFlingCalled) {
                    return true;
                }
                if ((mTouchMode & TOUCH_MODE_HSCROLL) != 0) {
                    mTouchMode = TOUCH_MODE_INITIAL_STATE;
                    if (Math.abs(mViewStartX) > mHorizontalSnapBackThreshold) {
                        // The user has gone beyond the threshold so switch views
                        if (DEBUG) Log.d(TAG, "- horizontal scroll: switch views");
                        switchViews(mViewStartX > 0, mViewStartX, mViewWidth, 0);
                        mViewStartX = 0;
                        return true;
                    } else {
                        // Not beyond the threshold so invalidate which will cause
                        // the view to snap back. Also call recalc() to ensure
                        // that we have the correct starting date and title.
                        if (DEBUG) Log.d(TAG, "- horizontal scroll: snap back");
                        recalc();
                        invalidate();
                        mViewStartX = 0;
                    }
                }

                // If we were scrolling, then reset the selected hour so that it
                // is visible.
                if (mScrolling) {
                    mScrolling = false;
                    resetSelectedHour();
                    invalidate();
                }
                return true;

            // This case isn't expected to happen.
            case MotionEvent.ACTION_CANCEL:
                if (DEBUG) Log.e(TAG, "ACTION_CANCEL");
                mGestureDetector.onTouchEvent(ev);
                mScrolling = false;
                resetSelectedHour();
                return true;

            default:
                if (DEBUG) Log.e(TAG, "Not MotionEvent " + ev.toString());
                if (mGestureDetector.onTouchEvent(ev)) {
                    return true;
                }
                return super.onTouchEvent(ev);
        }
    }

    public void clearCacheEvents() {
        mLastReloadMillis = 0;
    }


    public void reloadEvents() {
        // Make sure our time zones are up to date
        if (DEBUG_EVENT_DATA) {
            Log.e(TAG, "=start===reloadEvents==============" + mBaseDate.toString());
            Log.e(TAG, " =start===reloadEvents==firstJulianDay===" +mFirstJulianDay +"===mLastJulianDay==="+mLastJulianDay);
        }
//        mTZUpdater.run();
        mSelectedEvent = null;
        mPrevSelectedEvent = null;
        mSelectedEvents.clear();

        // The start date is the beginning of the week at 12am
        //Time weekStart = new Time(Utils.getTimeZone(mContext, mTZUpdater));
        // TODO: 2016/7/12
        Time weekStart = new Time();
        weekStart.set(mBaseDate);
        weekStart.hour = 0;
        weekStart.minute = 0;
        weekStart.second = 0;
        long millis = weekStart.normalize(true /* ignore isDst */);

        // Avoid reloading events unnecessarily.
        if (millis == mLastReloadMillis) {
            return;
        }
        mLastReloadMillis = millis;

        String startDate = com.thinkcoo.mobile.utils.DateUtils.getDateFromJulianDay(mFirstJulianDay);
        String endDate = com.thinkcoo.mobile.utils.DateUtils.getDateFromJulianDay(mFirstJulianDay + mNumDays - 1);
        ThinkcooLog.e(TAG,startDate +"==real===reloadEvents=========" +endDate);
        mLoadEventListPresenter.loadEvents(startDate, endDate);

    }

    public void refresh(ArrayList<Event> eventList) {
        if (null == eventList) {
            return;
        }
        if (DEBUG_EVENT_DATA) {
            Log.e(TAG, "===refreshEvents==================" + mBaseDate.toString());
            Log.e(TAG, "== refreshEvents===firstJulianDay===" + mFirstJulianDay + "===mLastJulianDay===" + mLastJulianDay);
        }
        // load events
        ArrayList<Event> events = new ArrayList<>();
        events = eventList;
        mEvents = events;
        // 加载成功
        if (DEBUG_EVENT_DATA) {
            for (int i = 0; i < mEvents.size(); i++) {
                mEvents.get(i).dump();
            }
        }

        if (mAllDayEvents == null) {
            mAllDayEvents = new ArrayList<>();
        } else {
            mAllDayEvents.clear();
        }

        // Create a shorter array for all day events
        for (Event e : events) {
            if (e.drawAsAllday()) {
                mAllDayEvents.add(e);
            }
        }

        // New events, new layouts
        if (mLayouts == null || mLayouts.length < events.size()) {
            mLayouts = new StaticLayout[events.size()];
        } else {
            Arrays.fill(mLayouts, null);
        }

        if (mAllDayLayouts == null || mAllDayLayouts.length < mAllDayEvents.size()) {
            mAllDayLayouts = new StaticLayout[events.size()];
        } else {
            Arrays.fill(mAllDayLayouts, null);
        }

        computeEventRelations();

        mRemeasure = true;
        mComputeSelectedEvents = true;
        recalc();
        invalidate();
    }

    // 计算事件关系
    private void computeEventRelations() {
        // Compute the layout relation between each event before measuring cell
        // width, as the cell width should be adjusted along with the relation.
        //
        // Examples: A (1:00pm - 1:01pm), B (1:02pm - 2:00pm)
        // We should mark them as "overwapped". Though they are not overwapped logically, but
        // minimum cell height implicitly expands the cell height of A and it should look like
        // (1:00pm - 1:15pm) after the cell height adjustment.

        // Compute the space needed for the all-day events, if any.
        // Make a pass over all the events, and keep track of the maximum
        // number of all-day events in any one day.  Also, keep track of
        // the earliest event in each day.
        int maxAllDayEvents = 0;
        final ArrayList<Event> events = mEvents;
        final int len = events.size();
        // Num of all-day-events on each day.
        final int eventsCount[] = new int[mLastJulianDay - mFirstJulianDay + 1];
        Arrays.fill(eventsCount, 0);
        for (int ii = 0; ii < len; ii++) {
            Event event = events.get(ii);
            if (event.startDay > mLastJulianDay || event.endDay < mFirstJulianDay) {
                continue;
            }
            if (event.drawAsAllday()) {
                // Count all the events being drawn as allDay events
                final int firstDay = Math.max(event.startDay, mFirstJulianDay);
                final int lastDay = Math.min(event.endDay, mLastJulianDay);
                for (int day = firstDay; day <= lastDay; day++) {
                    final int count = ++eventsCount[day - mFirstJulianDay];
                    if (maxAllDayEvents < count) {
                        maxAllDayEvents = count;
                    }
                }

                int daynum = event.startDay - mFirstJulianDay;
                int durationDays = event.endDay - event.startDay + 1;
                if (daynum < 0) {
                    durationDays += daynum;
                    daynum = 0;
                }
                if (daynum + durationDays > mNumDays) {
                    durationDays = mNumDays - daynum;
                }
                for (int day = daynum; durationDays > 0; day++, durationDays--) {
                    mHasAllDayEvent[day] = true;
                }
            } else {
                int daynum = event.startDay - mFirstJulianDay;
                int hour = event.startTime / 60;
                if (daynum >= 0 && hour < mEarliestStartHour[daynum]) {
                    mEarliestStartHour[daynum] = hour;
                }

                // Also check the end hour in case the event spans more than
                // one day.
                daynum = event.endDay - mFirstJulianDay;
                hour = event.endTime / 60;
                if (daynum < mNumDays && hour < mEarliestStartHour[daynum]) {
                    mEarliestStartHour[daynum] = hour;
                }
            }
        }
        mMaxAlldayEvents = maxAllDayEvents;
        initAllDayHeights();
    }

    /**
     * Figures out the initial heights for allDay events and space when
     * a view is being set up.
     */
    public void initAllDayHeights() {
        if (mMaxAlldayEvents <= mMaxUnexpandedAlldayEventCount) {
            return;
        }
        if (mShowAllAllDayEvents) {
            int maxADHeight = mViewHeight - DAY_HEADER_HEIGHT - MIN_HOURS_HEIGHT;
            maxADHeight = Math.min(maxADHeight,
                    (int) (mMaxAlldayEvents * MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT));
            mAnimateDayEventHeight = maxADHeight / mMaxAlldayEvents;
        } else {
            mAnimateDayEventHeight = (int) MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT;
        }
    }

    // Kicks off all the animations when the expand allday area is tapped
    private void doExpandAllDayClick() {
        mShowAllAllDayEvents = !mShowAllAllDayEvents;

        ObjectAnimator.setFrameDelay(0);

        // Determine the starting height
        if (mAnimateDayHeight == 0) {
            mAnimateDayHeight = mShowAllAllDayEvents ?
                    mAlldayHeight - (int) MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT : mAlldayHeight;
        }
        // Cancel current animations
        mCancellingAnimations = true;
        if (mAlldayAnimator != null) {
            mAlldayAnimator.cancel();
        }
        if (mAlldayEventAnimator != null) {
            mAlldayEventAnimator.cancel();
        }
        if (mMoreAlldayEventsAnimator != null) {
            mMoreAlldayEventsAnimator.cancel();
        }
        mCancellingAnimations = false;
        // get new animators
        mAlldayAnimator = getAllDayAnimator();
        mAlldayEventAnimator = getAllDayEventAnimator();
        mMoreAlldayEventsAnimator = ObjectAnimator.ofInt(this,
                "moreAllDayEventsTextAlpha",
                mShowAllAllDayEvents ? MORE_EVENTS_MAX_ALPHA : 0,
                mShowAllAllDayEvents ? 0 : MORE_EVENTS_MAX_ALPHA);

        // Set up delays and start the animators
        mAlldayAnimator.setStartDelay(mShowAllAllDayEvents ? ANIMATION_SECONDARY_DURATION : 0);
        mAlldayAnimator.start();
        mMoreAlldayEventsAnimator.setStartDelay(mShowAllAllDayEvents ? 0 : ANIMATION_DURATION);
        mMoreAlldayEventsAnimator.setDuration(ANIMATION_SECONDARY_DURATION);
        mMoreAlldayEventsAnimator.start();
        if (mAlldayEventAnimator != null) {
            // This is the only animator that can return null, so check it
            mAlldayEventAnimator
                    .setStartDelay(mShowAllAllDayEvents ? ANIMATION_SECONDARY_DURATION : 0);
            mAlldayEventAnimator.start();
        }
    }

    // Sets up an animator for changing the height of the allday area
    private ObjectAnimator getAllDayAnimator() {
        // Calculate the absolute max height
        int maxADHeight = mViewHeight - DAY_HEADER_HEIGHT - MIN_HOURS_HEIGHT;
        // Find the desired height but don't exceed abs max
        maxADHeight =
                Math.min(maxADHeight, (int) (mMaxAlldayEvents * MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT));
        // calculate the current and desired heights
        int currentHeight = mAnimateDayHeight != 0 ? mAnimateDayHeight : mAlldayHeight;
        int desiredHeight = mShowAllAllDayEvents ? maxADHeight :
                (int) (MAX_UNEXPANDED_ALLDAY_HEIGHT - MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT - 1);

        // Set up the animator with the calculated values
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "animateDayHeight",
                currentHeight, desiredHeight);
        animator.setDuration(ANIMATION_DURATION);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!mCancellingAnimations) {
                    // when finished, set this to 0 to signify not animating
                    mAnimateDayHeight = 0;
                    mUseExpandIcon = !mShowAllAllDayEvents;
                }
                mRemeasure = true;
                invalidate();
            }
        });
        return animator;
    }

    // Sets up an animator for changing the height of allday events
    private ObjectAnimator getAllDayEventAnimator() {
        // First calculate the absolute max height
        int maxADHeight = mViewHeight - DAY_HEADER_HEIGHT - MIN_HOURS_HEIGHT;
        // Now expand to fit but not beyond the absolute max
        maxADHeight =
                Math.min(maxADHeight, (int) (mMaxAlldayEvents * MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT));
        // calculate the height of individual events in order to fit
        int fitHeight = maxADHeight / mMaxAlldayEvents;
        int currentHeight = mAnimateDayEventHeight;
        int desiredHeight =
                mShowAllAllDayEvents ? fitHeight : (int) MIN_UNEXPANDED_ALLDAY_EVENT_HEIGHT;
        // if there's nothing to animate just return
        if (currentHeight == desiredHeight) {
            return null;
        }

        // Set up the animator with the calculated values
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "animateDayEventHeight",
                currentHeight, desiredHeight);
        animator.setDuration(ANIMATION_DURATION);
        return animator;
    }

    // setter for the 'box +n' alpha text used by the animator
    public void setMoreAllDayEventsTextAlpha(int alpha) {
        mMoreAlldayEventsTextAlpha = alpha;
        invalidate();
    }

    // setter for the height of the allday area used by the animator
    public void setAnimateDayHeight(int height) {
        mAnimateDayHeight = height;
        mRemeasure = true;
        invalidate();
    }

    // setter for the height of allday events used by the animator
    public void setAnimateDayEventHeight(int height) {
        mAnimateDayEventHeight = height;
        mRemeasure = true;
        invalidate();
    }

    @Override
    public void setEvents(List<Event> events) {
        refresh((ArrayList<Event>) events);
    }

    @Override
    public MvpPresenter createPresenter() {
        return mLoadEventListPresenter;
    }

    public void setNumDays(int numDays) {
        mNumDays = numDays;
    }

}
