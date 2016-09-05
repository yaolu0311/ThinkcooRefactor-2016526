package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.injector.components.ApplicationComponent;
import com.thinkcoo.mobile.injector.components.DaggerLoginComponent;
import com.thinkcoo.mobile.injector.modules.ActivityModule;
import com.thinkcoo.mobile.injector.modules.LoginModule;
import com.thinkcoo.mobile.model.entity.Account;
import com.thinkcoo.mobile.presentation.mvp.presenters.BlankPresenter;
import com.thinkcoo.mobile.presentation.mvp.presenters.MainPagePresenter;
import com.thinkcoo.mobile.presentation.mvp.views.MainActivityView;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseActivity;
import com.thinkcoo.mobile.presentation.views.fragment.ScheduleFragment;
import com.thinkcoo.mobile.presentation.views.fragment.TradeFragment;
import com.thinkcoo.mobile.presentation.views.fragment.UserMainFragment;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainActivityView {
    
    
    private static final String TAG = "MainActivity";

    private final static String RUNNING_PART = "running";
    private final static String CHAT_PART = "chat";
    private final static String TRADE_PART = "trade";
    private final static String PERSON_PART = "person";

    private final static String KEY_CURRENT_TAG = "key_current_tag";
    private static final String KEY_AUTO_LOGIN_ACCOUNT = "AUTO_LOGIN";

    public static Intent getMainActivityIntent(Context context) {
        Intent intent = new Intent(context,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_AUTO_LOGIN_ACCOUNT, Account.NULL_ACCOUNT);
        intent.putExtras(bundle);
        return intent;

    }

    public static Intent getMainActivityAutoLoginIntent(Context context,Account account){
        Intent intent = new Intent(context,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_AUTO_LOGIN_ACCOUNT,account);
        intent.putExtras(bundle);
        return intent;
    }

    @Bind(R.id.sort_part)
    RelativeLayout mSortPart;
    @Bind(R.id.tv_chat_unread)
    TextView mTvChatUnread;
    @Bind(R.id.chat_part)
    RelativeLayout mChatPart;
    @Bind(R.id.trade_part)
    RelativeLayout mTradePart;
    @Bind(R.id.person_part)
    RelativeLayout mPersonPart;
    @Bind(R.id.tv_sort_part_notice)
    TextView mTvSortPartNotice;
    @Bind(R.id.tv_chat_part_notice)
    TextView mTvChatPartNotice;
    @Bind(R.id.tv_trade_part_notice)
    TextView mTvTradePartNotice;
    @Bind(R.id.tv_person_part_notice)
    TextView mTvPersonPartNotice;

    private Fragment runningFragment;
    private Fragment chatFragment;
    private Fragment tradeFragment;
    private Fragment personFragment;

    private String currentTag;

    private MessageReceiver mReceiver;

    @Inject
    MainPagePresenter mMainPagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        doAutoLoginIdNeed();
        registerReceiver();
        setupFragmentWithTag(savedInstanceState);
    }

    private void setupFragmentWithTag(Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            getFragmentFromStack();
            currentTag = savedInstanceState.getString(KEY_CURRENT_TAG);
            showFragment(currentTag);
        } else {
            initFragment();
            currentTag = RUNNING_PART;
        }
    }

    private void doAutoLoginIdNeed() {
        Account account = getIntent().getParcelableExtra(KEY_AUTO_LOGIN_ACCOUNT);
        if (account != null && !account.isEmpty()){
            ThinkcooLog.e(TAG,"Do Auto Login in MainActivity");
            //mMainPagePresenter.autoLogin(account);
        }
    }

    public void getFragmentFromStack() {
        runningFragment = getSupportFragmentManager().findFragmentByTag(RUNNING_PART);
        chatFragment = getSupportFragmentManager().findFragmentByTag(CHAT_PART);
        tradeFragment = getSupportFragmentManager().findFragmentByTag(TRADE_PART);
        personFragment = getSupportFragmentManager().findFragmentByTag(PERSON_PART);
    }


    private void showFragment(String currentTag) {
        if (TextUtils.isEmpty(currentTag)) {
            currentTag = RUNNING_PART;
        }
        switch (currentTag) {
            case RUNNING_PART:
                getSupportFragmentManager().beginTransaction().show(runningFragment).hide(chatFragment).hide(tradeFragment).hide(personFragment).commit();
                break;
            case CHAT_PART:
                getSupportFragmentManager().beginTransaction().show(chatFragment).hide(runningFragment).hide(tradeFragment).hide(personFragment).commit();
                break;
            case TRADE_PART:
                getSupportFragmentManager().beginTransaction().show(tradeFragment).hide(runningFragment).hide(chatFragment).hide(personFragment).commit();
                break;
            case PERSON_PART:
                getSupportFragmentManager().beginTransaction().show(personFragment).hide(runningFragment).hide(runningFragment).hide(tradeFragment).commit();
                break;
        }
    }

    private void initFragment() {
        runningFragment = ScheduleFragment.newInstance(7);
        tradeFragment = TradeFragment.newInstance();
        personFragment = UserMainFragment.newInstance();
        chatFragment = IMHelper.getInstance().createConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_main, runningFragment, RUNNING_PART)
                .add(R.id.frame_main, chatFragment, RUNNING_PART)
                .add(R.id.frame_main, tradeFragment, RUNNING_PART)
                .add(R.id.frame_main, personFragment, RUNNING_PART)
                .hide(chatFragment).hide(tradeFragment).hide(personFragment)
                .commit();
        mSortPart.setSelected(true);
        mTvSortPartNotice.setTextColor(getResources().getColor(R.color.blue_title));
        currentTag = RUNNING_PART;
    }

    @Override
    protected MvpPresenter generatePresenter() {
        return new BlankPresenter();
    }

    @Override
    protected void initDaggerInject() {
        DaggerLoginComponent.builder().applicationComponent(getApplicationComponent()).loginModule(new LoginModule()).build().inject(this);
    }

    @OnClick({R.id.sort_part, R.id.chat_part, R.id.trade_part, R.id.person_part})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_part:
                if (currentTag.equals(RUNNING_PART)) {
                    return;
                }
                togglePreviousSelectStatus();
                mSortPart.setSelected(true);
                mTvSortPartNotice.setTextColor(getResources().getColor(R.color.blue_title));
                currentTag = RUNNING_PART;
                break;
            case R.id.chat_part:
                if (currentTag.equals(CHAT_PART)) {
                    return;
                }
                togglePreviousSelectStatus();
                mChatPart.setSelected(true);
                mTvChatPartNotice.setTextColor(getResources().getColor(R.color.blue_title));
                currentTag = CHAT_PART;
                break;
            case R.id.trade_part:
                if (currentTag.equals(TRADE_PART)) {
                    return;
                }
                togglePreviousSelectStatus();
                mTradePart.setSelected(true);
                mTvTradePartNotice.setTextColor(getResources().getColor(R.color.blue_title));
                currentTag = TRADE_PART;
                break;
            case R.id.person_part:
                if (currentTag.equals(PERSON_PART)) {
                    return;
                }
                togglePreviousSelectStatus();
                mPersonPart.setSelected(true);
                mTvPersonPartNotice.setTextColor(getResources().getColor(R.color.blue_title));
                currentTag = PERSON_PART;
                break;
        }
        showFragment(currentTag);
    }

    private void togglePreviousSelectStatus() {
        switch (currentTag) {
            case RUNNING_PART:
                mSortPart.setSelected(false);
                mTvSortPartNotice.setTextColor(getResources().getColor(R.color.font1));
                break;
            case CHAT_PART:
                mChatPart.setSelected(false);
                mTvChatPartNotice.setTextColor(getResources().getColor(R.color.font1));
                break;
            case TRADE_PART:
                mTradePart.setSelected(false);
                mTvTradePartNotice.setTextColor(getResources().getColor(R.color.font1));
                break;
            case PERSON_PART:
                mPersonPart.setSelected(false);
                mTvPersonPartNotice.setTextColor(getResources().getColor(R.color.font1));
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CURRENT_TAG, currentTag);
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void closeSelf() {
        //// TODO: 2016/6/27 操作不统一
    }

    @Override
    public void showProgressDialog(int stringResId) {
        mBaseActivityDelegate.showProgressDialog(stringResId);
    }

    @Override
    public void hideProgressDialogIfShowing() {
        mBaseActivityDelegate.hideProgressDialogIfShowing();
    }

    @Override
    public void showToast(String errorMsg) {
        mGlobalToast.showShortToast(errorMsg);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    /**
     * 扩大访问范围让fragment可以使用
     */
    @Override
    public ApplicationComponent getApplicationComponent() {
        return super.getApplicationComponent();
    }

    /**
     * 扩大访问范围让fragment可以使用
     */
    @Override
    public ActivityModule getActivityModule() {
        return super.getActivityModule();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterReceiver();
    }

    private void registerReceiver() {
        mReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_REFRESH_UNREAD_MESSAGE_COUNT);
        registerReceiver(mReceiver, filter);
    }

    private void unRegisterReceiver() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }

    class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.ACTION_REFRESH_UNREAD_MESSAGE_COUNT:
                    int count = intent.getIntExtra(Constant.KEY_UNREAD_MESSAGE, 0);
                    refreshUnreadMessageUI(count);
                    break;
            }
        }
    }

    private void refreshUnreadMessageUI(final int count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (count > 0 && count < 100) {
                    mTvChatUnread.setText(String.valueOf(count));
                    mTvChatUnread.setVisibility(View.VISIBLE);
                } else if (count >= 100) {
                    mTvChatUnread.setText("99+");
                    mTvChatUnread.setVisibility(View.VISIBLE);
                } else {
                    mTvChatUnread.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
