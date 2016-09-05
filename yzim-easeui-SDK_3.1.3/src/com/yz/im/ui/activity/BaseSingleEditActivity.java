package com.yz.im.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.yz.im.ui.base.HXBaseActivity;

public abstract class BaseSingleEditActivity extends HXBaseActivity implements View.OnClickListener{

    protected static String KEY_STRATEGY = "key_strategy";

    protected LinearLayout layout;
    protected ImageView leftImage;
    protected TextView tvTitle;
    protected TextView tv_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void continueOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_base_single_edit);
        initViewAndEvent();
    }

    public void initViewAndEvent() {
        layout = (LinearLayout) findViewById(R.id.layout_update_friend_remark);
        leftImage = (ImageView) findViewById(R.id.image_left);
        tvTitle = (TextView) findViewById(R.id.text_title);
        tv_commit = (TextView) findViewById(R.id.text_right);
        leftImage.setImageResource(R.drawable.back);
        leftImage.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tv_commit.setVisibility(View.VISIBLE);

        leftImage.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
    }

    protected void setTitle(String title){
        tvTitle.setText(title);
    }

    protected void setCommitText(String commit){
        tv_commit.setText(commit);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(R.id.image_left == id){
            finish();
        }
    }

    @Override
    public void closeSelf() {
        setResult(RESULT_OK);
        finish();
    }

}
