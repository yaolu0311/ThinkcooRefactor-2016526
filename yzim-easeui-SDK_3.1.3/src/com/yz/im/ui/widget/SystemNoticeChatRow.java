package com.yz.im.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.yz.im.model.entity.SystemMessageEntity;

import java.util.List;

/**
 * Created by cys on 2016/8/11
 */
public class SystemNoticeChatRow extends EaseChatRow {

    private TextView mTextView;
    private Gson mGson;

    public SystemNoticeChatRow(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
        mGson = new Gson();
    }

    @Override
    protected void onInflatView() {
        inflater.inflate(R.layout.chat_row_system_notice, this);
    }

    @Override
    protected void onFindViewById() {
        mTextView = (TextView) findViewById(R.id.tv_chatcontent);
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSetUpView() {
        EMTextMessageBody body = (EMTextMessageBody) message.getBody();
        String content = getDescription(body);
        if (TextUtils.isEmpty(content)) {
            this.setVisibility(GONE);
        } else {
            mTextView.setText(content);
        }
    }

    private String getDescription(EMTextMessageBody body) {
        String content = body.getMessage().trim().split(";")[1];
        SystemMessageEntity entity = mGson.fromJson(content, SystemMessageEntity.class);
        if (entity == null) {
            return "";
        } else {
            return entity.getDescription();
        }
    }

    @Override
    protected void onBubbleClick() {

    }

    @Override
    protected void onBubbleLongClick() {

    }

    @Override
    protected void onItemClick(String content, int position) {

    }

    @Override
    protected List<String> initListItems() {
        return null;
    }
}
