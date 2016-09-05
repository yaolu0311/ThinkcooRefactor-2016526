package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.views.fragment.DownloadFragment;
import com.thinkcoo.mobile.presentation.views.fragment.UploadFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Leevin
 * CreateTime: 2016/8/16  20:04
 */
public class DownloadManagerActivity extends AppCompatActivity {

    public static Intent getJumpIntent(Context context) {
        return new Intent(context, DownloadManagerActivity.class);
    }

    public static final String DOWN_LOAD_TAG =  "down_load_tag";
    public static final String UP_LOAD_TAG =  "up_load_tag";
    public String mCurrentTAG = DOWN_LOAD_TAG;
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_download)
    TextView mTvDownload;
    @Bind(R.id.tv_upload)
    TextView mTvUpload;
    @Bind(R.id.tab_1)
    View mTab1;
    @Bind(R.id.tab_2)
    View mTab2;
    @Bind(R.id.down_and_up_load_container)
    FrameLayout mDownAndUpLoadContainer;
    private DownloadFragment mDownloadFragment;
    private UploadFragment mUploadFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.download_manager);

        mDownloadFragment = DownloadFragment.getInstance();
        mUploadFragment = UploadFragment.getInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.down_and_up_load_container, mDownloadFragment,DOWN_LOAD_TAG)
                .add(R.id.down_and_up_load_container, mUploadFragment, UP_LOAD_TAG)
                .hide(mUploadFragment)
                .commit();
        mCurrentTAG = DOWN_LOAD_TAG;
        mTab1.setVisibility(View.VISIBLE);
        mTab2.setVisibility(View.INVISIBLE);
    }

    private void showRightFragment(String tag) {
        switch (tag) {
            case DOWN_LOAD_TAG:
                mTab1.setVisibility(View.VISIBLE);
                mTab2.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction().show(mDownloadFragment).hide(mUploadFragment).commit();
                break;
            case UP_LOAD_TAG:
                mTab1.setVisibility(View.INVISIBLE);
                mTab2.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().show(mUploadFragment).hide(mDownloadFragment).commit();
                break;
            default:
                throw new IllegalArgumentException(" no know fragment tag!!!");
        }
    }


    @OnClick({R.id.iv_back,R.id.tv_download,R.id.tv_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_download:
                mCurrentTAG = DOWN_LOAD_TAG;
                showRightFragment(mCurrentTAG);
                break;
            case R.id.tv_upload:
                mCurrentTAG = UP_LOAD_TAG;
                showRightFragment(mCurrentTAG);
                break;
        }
    }

}
