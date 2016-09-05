package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.example.administrator.publicmodule.util.IdOffsetUtil;
import com.hyphenate.easeui.R;
import com.yz.im.Constant;
import com.yz.im.IMHelper;
import com.yz.im.model.strategy.ChatFragmentStrategy;
import com.yz.im.model.strategy.ChatFragmentStrategyFactory;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.presenter.BlankPresenter;
import com.yz.im.ui.base.HXBaseActivity;
import com.yz.im.ui.fragment.IMChatFragment;

public class ChatActivity extends HXBaseActivity {

    public static final String KEY_STRATEGY = "key_strategy";

    public static final String KEY_FROM_NOTIFICATION = "key_from_notification";
    private static final String KEY_TYPE = "key_type";
    private static final String KEY_CHAT_ID = "key_chat_id";

    public static Intent getChatActivityIntent(Context context, int chatType, String chatId, ChatFragmentStrategy strategy) {
        if (TextUtils.isEmpty(chatId)) {
            throw new NullPointerException("chatId is null");
        }
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Constant.EXTRA_CHAT_TYPE, chatType);
        if (chatType == Constant.CHATTYPE_SINGLE) {
            intent.putExtra(Constant.EXTRA_USER_ID, IdOffsetUtil.addOffset(chatId));
        } else {
            intent.putExtra(Constant.EXTRA_USER_ID, chatId);
        }
        intent.putExtra(KEY_STRATEGY, strategy);
        return intent;
    }

    private FrameLayout mFrameLayout;
    private IMChatFragment mIMChatFragment;

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chat);
        initView();
        initChatFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIMChatFragment != null) {
            IMHelper.getInstance().setCurrentChatId(mIMChatFragment.getChatId());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        IMHelper.getInstance().setCurrentChatId(null);
    }

    @Override
    public IMMvpPresenter createPresenter() {
        return new BlankPresenter();
    }

    private void initView() {
        mFrameLayout = (FrameLayout) findViewById(R.id.layout_chat);
    }


    private void initChatFragment() {
        mIMChatFragment = new IMChatFragment();
        Bundle bundle = getIntent().getExtras();
        isStartFromNotification(bundle);
        mIMChatFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.layout_chat, mIMChatFragment).commit();
    }

    private void isStartFromNotification(Bundle bundle) {
        if (bundle.getBoolean(KEY_FROM_NOTIFICATION, false)) {
            int type = getIntent().getIntExtra(KEY_TYPE, Constant.CHATTYPE_SINGLE);
            bundle.putInt(Constant.EXTRA_CHAT_TYPE, type);
            ChatFragmentStrategy strategy;
            if (Constant.CHATTYPE_SINGLE == type) {
                strategy = ChatFragmentStrategyFactory.create(this, Constant.CHATTYPE_SINGLE);
                bundle.putString(Constant.EXTRA_USER_ID, IdOffsetUtil.addOffset(getIntent().getStringExtra(KEY_CHAT_ID)));
            } else {
                strategy = ChatFragmentStrategyFactory.create(this, Constant.CHATTYPE_GROUP);
                bundle.putString(Constant.EXTRA_USER_ID, getIntent().getStringExtra(KEY_CHAT_ID));
            }
            bundle.putParcelable(KEY_STRATEGY, strategy);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mIMChatFragment.onActivityResult(requestCode, resultCode, data);
    }
}
