package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.DaggerScheduleComponent;
import com.thinkcoo.mobile.injector.modules.ScheduleModule;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.model.entity.serverresponse.RockSingleResByUuidResponse;
import com.thinkcoo.mobile.presentation.mvp.presenters.RockCallSingleResultPresenter;
import com.thinkcoo.mobile.presentation.mvp.views.RockCallSingleResultView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.component.RockStudentResultListView;
import com.thinkcoo.mobile.presentation.views.component.SpaceItemDecoration;
import com.thinkcoo.mobile.utils.DateUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RockCallSingleResultActivity extends BaseActivity implements RockCallSingleResultView {

    private static long TOTAL_TIME = 301 * 1000;

    public static Intent getRockCallSingle(Context context, String uuid, String eventId) {
        Intent intent = new Intent(context, RockCallSingleResultActivity.class);
        intent.putExtra(EXTRA_KEY_UUID, uuid);
        intent.putExtra(EXTRA_KEY_EVENT_ID, eventId);
        return intent;
    }


    public static int REFRESH_HANDLER_WHAT = 0x12;
    public static int REFRESH_TIME_INTERVAL = 5000;
    public static final String EXTRA_KEY_UUID = "key_uuid";
    public static final String EXTRA_KEY_EVENT_ID = "event_id";
    private static final String DEFAULT_ALL_CLASS_ID = "";
    private String[] colorBg = new String[]{
            "#eb6100", "#63dec2",
            "#fd8bbc", "#7ecdf4", "#f1c973", "#fea92d", "#f29ec2", "#12b5b0",
            "#8fc320", "#89abd9", "#920784", "#c490c0", "#b28750", "#f9b552",
            "#a40000", "#546fb4", "#5f6eb3", "#88aae3", "#007079", "#248dc1",
            "#799a05", "#7474c1", "#e6437b", "#b7ce97", "#00b3e3", "#cea888",
            "#00ad69", "#ff9e6d", "#ffc37b", "#ff4814", "#20cbd4"
    };

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.rv_classlist)
    RecyclerView mRvClasslist;
    @Bind(R.id.layout_rock_result)
    RockStudentResultListView mStudentList;
    @Bind(R.id.tv_count_down)
    TextView mTvCountDown;

    private String mUUIDFromIntent;
    private String mEventIdFromIntent;
    private String mGroupIdFromIntent = "";
    private String mSelectClassId = DEFAULT_ALL_CLASS_ID;
    private ClassListAdapter mClassListAdapter;
    private List<Boolean> isClick;
    private RefreshHandler mRefreshHandler;
    private OnItemClickListener mOnItemClickListener = null;

    @Inject
    RockCallSingleResultPresenter mRockCallSingleResultPresenter;

    private CountDownTimer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rockcall_record);
        ButterKnife.bind(this);
        mTvTitle.setText(R.string.rockcall_result);
        initRefreshHandler();
        initExtraData();
        initClassRecyclerView();

        mRockCallSingleResultPresenter.loadClassListData(mEventIdFromIntent, mUUIDFromIntent, mGroupIdFromIntent);
        mStudentList.setEventId(mEventIdFromIntent);
        mOnItemClickListener = new OnItemClickListener() {
            @Override
            public void OnItemClick(View v, RecyclerView.ViewHolder holder, int position) {
                // TODO: 2016/8/20
            }
        };
        initCountDownTimer();
        mTimer.start();
    }

    private void initCountDownTimer() {
        mTimer = new CountDownTimer(TOTAL_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String content = getResources().getString(R.string.count_down, millisUntilFinished / 1000 -1+ "");
                mTvCountDown.setText(DateUtils.setTextForeColor(content));
            }

            @Override
            public void onFinish() {
//                mTvCountDown.setVisibility(View.GONE);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRefreshHandler.release();
        mTimer.cancel();
    }

    private void initRefreshHandler() {
        mRefreshHandler = new RefreshHandler(this);
    }


    private void initExtraData() {
        mUUIDFromIntent = getIntent().getStringExtra(EXTRA_KEY_UUID);
        mEventIdFromIntent = getIntent().getStringExtra(EXTRA_KEY_EVENT_ID);
    }


    private void initClassRecyclerView() {
        mClassListAdapter = new ClassListAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvClasslist.setAdapter(mClassListAdapter);
        mRvClasslist.setLayoutManager(linearLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.px_25);
        SpaceItemDecoration spaceItemDecoration = new SpaceItemDecoration(spacingInPixels);
        mRvClasslist.addItemDecoration(spaceItemDecoration);
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return mRockCallSingleResultPresenter;
    }

    @Override
    protected void initDaggerInject() {
        DaggerScheduleComponent.builder().scheduleModule(new ScheduleModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.rv_classlist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rv_classlist:
                break;
        }
    }

    private class ClassListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public static final int VIEW_TYPE_ALL = 0x0001;
        public static final int VIEW_TYPE_CLASS = 0x0002;

        List<ClassGroup> mClassResponses;

        public ClassListAdapter() {
            mClassResponses = new ArrayList<>();
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ALL) {
                return new AllClassListItemViewHolder(inflateItemAll(parent));
            } else if (viewType == VIEW_TYPE_CLASS) {
                return new ClassListItemViewHolder(inflateItemClass(parent));
            }
            throw new IllegalArgumentException("error viewType (RockCallResultActivity.ClassListAdapter)");
        }

        private View inflateItemClass(ViewGroup parent) {
            return getLayoutInflater().inflate(R.layout.item_class_2, parent, false);
        }

        private TextView inflateItemAll(ViewGroup parent) {
            return (TextView) getLayoutInflater().inflate(R.layout.item_class_all, parent, false);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            final ClassGroup classGroup = mClassResponses.get(position);
            if (getItemViewType(position) == VIEW_TYPE_CLASS) {
                ((ClassListItemViewHolder) holder).bind(mClassResponses.get(position));
            } else {
                ((AllClassListItemViewHolder) holder).bind();
            }
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String classId = classGroup.getGroupId();
                        classId = TextUtils.isEmpty(classId) ? DEFAULT_ALL_CLASS_ID : classId;
                        mGroupIdFromIntent = classId;
                        mOnItemClickListener.OnItemClick(holder.itemView, holder, position);
                        if (!classId.equals(mSelectClassId)) {
                            mSelectClassId = classId;
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        }


        public int getItemCount() {
            return mClassResponses.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return VIEW_TYPE_ALL;
            } else {
                return VIEW_TYPE_CLASS;
            }
        }

        public void refresh(List<ClassGroup> classResponseList) {
            if (classResponseList == null) {
                classResponseList = new ArrayList<>();
            }
            classResponseList.add(0, ClassGroup.NULL_OBJECT);
            if (TextUtils.isEmpty(mSelectClassId)) {
                mSelectClassId = DEFAULT_ALL_CLASS_ID;
            }
            mClassResponses = classResponseList;
            //初始化状态
            isClick = new ArrayList<>(mClassResponses.size());
            //判断上一次选中的位置
            int count = Math.min(isClick.size(), mClassResponses.size());
            for (int i = 0; i < count; i++) {
                ClassGroup group = mClassResponses.get(i);
                if (group.getGroupId().equals(mSelectClassId)) {
                    isClick.set(i, true);
                } else {
                    isClick.set(i, false);
                }
            }
            notifyDataSetChanged();
        }

    }

    private class ClassListItemViewHolder extends RecyclerView.ViewHolder {
        TextView mSchool;
        TextView mClass;
        RelativeLayout mItemView;
        ClassGroup mClassResponse;

        public ClassListItemViewHolder(final View itemView) {
            super(itemView);
            mSchool = (TextView) itemView.findViewById(R.id.text_school);
            mClass = (TextView) itemView.findViewById(R.id.text_class);
            mItemView = (RelativeLayout) itemView.findViewById(R.id.layout_content);
            //// TODO: 2016/8/17 给 mContentLayout 设置背景颜色
            int color = new Random().nextInt(30);
//            mItemView.setBackgroundColor(Color.parseColor(colorBg[color]));

            float[] outerR = new float[]{10, 10, 10, 10, 10, 10, 10, 10};
            ShapeDrawable myShapeDrawable = new ShapeDrawable(new RoundRectShape(outerR, null, null));
            //得到画笔Paint对象并设置其颜色
            myShapeDrawable.getPaint().setColor(Color.parseColor(colorBg[color]));
            myShapeDrawable.getPaint().setStyle(Paint.Style.FILL);
            mItemView.setBackgroundDrawable(myShapeDrawable);

        }

        public void bind(ClassGroup classResponse) {
            mClassResponse = classResponse;

            if (mSelectClassId.equals(mClassResponse.getGroupId())) {
                itemView.setBackgroundResource(R.drawable.class_group_focus_board_style);
            } else {
                itemView.setBackgroundResource(R.drawable.class_group_item_unfocus_board_style);
            }

            mSchool.setText(classResponse.getSchoolName());
            mClass.setText(classResponse.getGroupName());
        }
    }

    private class AllClassListItemViewHolder extends RecyclerView.ViewHolder {

        public AllClassListItemViewHolder(final TextView itemView) {
            super(itemView);
        }

        public void bind() {
            if (mSelectClassId.equals(DEFAULT_ALL_CLASS_ID)) {
                itemView.setBackgroundResource(R.drawable.class_group_focus_board_style);
            } else {
                itemView.setBackgroundResource(R.drawable.class_group_unfocus_board_style);
            }
        }
    }

    @Override
    public void setClassList(List<ClassGroup> classResponseList) {
        mClassListAdapter.refresh(classResponseList);
        sendTimerMessage();
    }

    @Override
    public void setStudentList(RockSingleResByUuidResponse studentResponse) {
        if (studentResponse == null) {
            return;
        }
        mStudentList.refreshData(studentResponse);
    }

    @Override
    public void toggleCheckBoxStatus() {
        mStudentList.toggleStatus();
    }

    private void startTimerLoadStudentList() {
        mRockCallSingleResultPresenter.loadStudentListData(mUUIDFromIntent, mGroupIdFromIntent);
        sendTimerMessage();
    }

    private void sendTimerMessage() {
        Message msg = Message.obtain();
        msg.what = REFRESH_HANDLER_WHAT;
        mRefreshHandler.sendMessageDelayed(msg, REFRESH_TIME_INTERVAL);
    }

    public static class RefreshHandler extends android.os.Handler {

        private static final String TAG = "RockCallSingleResultActivity.RefreshHandler";
        WeakReference<RockCallSingleResultActivity> mRockCallSingleResultActivityWeakReference;

        public RefreshHandler(RockCallSingleResultActivity rockCallSingleResultActivity) {
            mRockCallSingleResultActivityWeakReference = new WeakReference<>(rockCallSingleResultActivity);

        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RockCallSingleResultActivity rockCallSingleResultActivity = getActivityFromWeakReference();
            if (null == rockCallSingleResultActivity) {
                ThinkcooLog.d(TAG, " ==== Activity Released ==== ");
                return;
            }
            if (msg.what == REFRESH_HANDLER_WHAT) {
                rockCallSingleResultActivity.startTimerLoadStudentList();
            }
        }

        private RockCallSingleResultActivity getActivityFromWeakReference() {
            if (null != mRockCallSingleResultActivityWeakReference)
                return mRockCallSingleResultActivityWeakReference.get();
            else
                return null;
        }

        public void release() {
            if (null != mRockCallSingleResultActivityWeakReference) {
                mRockCallSingleResultActivityWeakReference.clear();
            }
            this.removeMessages(REFRESH_HANDLER_WHAT);
        }
    }

    //  回调
    public interface OnItemClickListener {
        void OnItemClick(View v, RecyclerView.ViewHolder holder, int position);
    }

    public RockCallSingleResultPresenter getRockCallSingleResultPresenter() {
        return mRockCallSingleResultPresenter;
    }
}
