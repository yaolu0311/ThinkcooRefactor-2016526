package com.thinkcoo.mobile.presentation.views.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.mvp.presenters.CooperationProjectPresenter;
import com.thinkcoo.mobile.presentation.views.dialog.ApplyProjectDialog;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CooperationProjectFragment extends BaseFragment implements View.OnClickListener{


    @Bind(R.id.tv_collect_project_name)
    TextView tvCollectProjectName;
    @Bind(R.id.tv_collect_company_name)
    TextView tvCollectCompanyName;
    @Bind(R.id.tv_collect_project_pubtime)
    TextView tvCollectProjectPubtime;
    @Bind(R.id.lauoyt01)
    RelativeLayout lauoyt01;
    @Bind(R.id.view01)
    View view001;
    @Bind(R.id.view02)
    View view02;
    @Bind(R.id.tv_collect_project_time_text)
    TextView tvCollectProjectTimeText;
    @Bind(R.id.tv_collect_project_time)
    TextView tvCollectProjectTime;
    @Bind(R.id.tv_collect_project_pay_text)
    TextView tvCollectProjectPayText;
    @Bind(R.id.ac_collect_project_pay)
    TextView tvCollectProjectPay;
    @Bind(R.id.tv_collect_project_num_text)
    TextView tvCollectProjectNumText;
    @Bind(R.id.ac_collect_project_num)
    TextView tvCollectProjectNum;
    @Bind(R.id.layout02)
    RelativeLayout layout02;
    @Bind(R.id.view03)
    View view03;
    @Bind(R.id.view04)
    View view04;
    @Bind(R.id.tv_collect_text01)
    TextView tvCollectText01;
    @Bind(R.id.view_01)
    View view01;
    @Bind(R.id.tv_collecy_project_about)
    TextView tvCollecyProjectAbout;
    @Bind(R.id.layout03)
    RelativeLayout layout03;
    @Bind(R.id.tv_collect_infor_project_view)
    View tvCollectInforProjectView;
    @Bind(R.id.ac_image_apply)
    ImageView acImageApply;
    @Bind(R.id.ac_text_apply)
    TextView acTextApply;
    @Bind(R.id.rl_layout_apply)
    RelativeLayout rlLayoutApply;
    @Bind(R.id.ac_image_collect)
    ImageView acImageCollect;
    @Bind(R.id.ac_text_collect)
    TextView tv_TextCollect;
    @Bind(R.id.rl_layout_collect)
    RelativeLayout rlLayoutCollect;
    @Bind(R.id.ac_image_contact)
    ImageView ivImageContact;
    @Bind(R.id.ac_text_contact)
    TextView tv_TextContact;
    @Bind(R.id.rl_layout_contact)
    RelativeLayout rlLayoutContact;
    @Bind(R.id.tv_collect_layout04)
    LinearLayout tvCollectLayout04;
    @Inject
    CooperationProjectPresenter mCooperationProjectPresenter;


    public CooperationProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cooperation_fragment_collect_project_info, null);
        initView();
        ButterKnife.bind(this, view);
        return view;
    }
    private void initView() {
        tv_TextContact.setText(R.string.contact);
        tvCollectText01.setText(R.string.Aboutproject);
        // TODO: 2016/7/27 其他字段


    }


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initDaggerInject() {
        // TODO: 2016/7/26

    }

    @Override
    protected MvpBasePresenter generatePresenter() {
        return mCooperationProjectPresenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rl_layout_apply, R.id.rl_layout_collect, R.id.rl_layout_contact})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_layout_apply:// 申请
                showDialog();
                break;
            case R.id.rl_layout_collect:// 收藏  取消收藏
                // TODO: 2016/7/27   点击执行两个case
                
                break;
            case R.id.rl_layout_contact:  //  联系
                // TODO: 2016/7/26  跳转单聊   无此人  toast 显示
                break;
        }
    }

    private void showDialog() {
        final ApplyProjectDialog dialog = new ApplyProjectDialog(getActivity());
        dialog.setPositiveButton("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/7/26   确定的 不等于空 传递数据  到上传文件的列表
            }
        }).setNegativeButton("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消按钮
                dialog.hide();
            }
        }).show();
    }
}
