package com.yz.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.yz.im.model.entity.ReportEntity;
import com.yz.im.mvp.IMMvpPresenter;
import com.yz.im.mvp.mvpContract.ReportContact;
import com.yz.im.mvp.presenter.ReportPresenter;
import com.yz.im.ui.adapter.ReportAdapter;
import com.yz.im.ui.base.HXBaseActivity;

import java.util.LinkedList;
import java.util.List;

public class ReportActivity extends HXBaseActivity implements View.OnClickListener, ReportContact.ReportView {

    private static String KEY_ID = "key_id";
    private static String KEY_type = "key_type";

    public static final int FRIEND = 0X0001;
    public static final int GROUP = 0X0002;

    public static Intent getReportActivityIntent(Context context, String id, int type) {
        if (TextUtils.isEmpty(id)) {
            throw new NullPointerException("id is null");
        }
        Intent intent = new Intent(context, ReportActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_type, type);
        return intent;
    }

    private ReportPresenter mPresenter;
    private String friendId;
    private String groupId;
    private int type;

    private ImageView leftImage;
    private TextView title;
    private TextView rightText;
    private EditText mEditText;
    private RecyclerView mRecyclerView;

    private ReportAdapter mAdapter;
    private List<ReportEntity> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public IMMvpPresenter createPresenter() {
        mPresenter = new ReportPresenter(this);
        return mPresenter;
    }

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_report);
        getDataFromIntent();
        initViewAndEvent();
        initRecyclerView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra(KEY_type, FRIEND);
            if (FRIEND == type) {
                friendId = intent.getStringExtra(KEY_ID);
                groupId = "";
            } else {
                groupId = intent.getStringExtra(KEY_ID);
                friendId = "";
            }
        }
    }


    private void initViewAndEvent() {
        leftImage = (ImageView) findViewById(R.id.image_left);
        leftImage.setImageResource(R.drawable.back);
        leftImage.setVisibility(View.VISIBLE);
        title = (TextView) findViewById(R.id.text_title);
        title.setText(R.string.report_reason);
        rightText = (TextView) findViewById(R.id.text_right);
        rightText.setText(R.string.commit);
        rightText.setVisibility(View.VISIBLE);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_report);
        mEditText = (EditText) findViewById(R.id.report_reason);

        leftImage.setOnClickListener(this);
        rightText.setOnClickListener(this);

    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mList = generateList();
        mAdapter = new ReportAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<ReportEntity> generateList() {
        String[] items = getResources().getStringArray(R.array.report_item);
        LinkedList<ReportEntity> reportEntities = new LinkedList<>();
        for (int i=0; i<items.length; i++) {
            ReportEntity entity = new ReportEntity(items[i], i+"", false);
            reportEntities.add(entity);
        }
        return reportEntities;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_left) {
            finish();
        } else if (id == R.id.text_right) {
            mPresenter.uploadReport(friendId, groupId, mAdapter.getCheckResult(), mEditText.getText().toString().trim());
        }
    }
}
