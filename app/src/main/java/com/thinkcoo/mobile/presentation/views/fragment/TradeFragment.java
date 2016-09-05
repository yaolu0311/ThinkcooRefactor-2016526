package com.thinkcoo.mobile.presentation.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.presentation.mvp.presenters.BlankPresenter;
import com.thinkcoo.mobile.presentation.views.activitys.MainActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/27.
 */
public class TradeFragment extends BaseFragment {


    @Bind(R.id.ac_home_layy)
    RelativeLayout mAcHomeLayy;
    @Bind(R.id.ac_find_image01)
    ImageView mAcFindImage01;
    @Bind(R.id.ac_find_image02)
    ImageView mAcFindImage02;
    @Bind(R.id.ac_find_image03)
    ImageView mAcFindImage03;
    @Bind(R.id.ac_find_image04)
    ImageView mAcFindImage04;
    @Bind(R.id.ac_find_but)
    LinearLayout mAcFindBut;
    @Bind(R.id.ac_find_text01)
    TextView mAcFindText01;
    @Bind(R.id.ac_find_text02)
    TextView mAcFindText02;
    @Bind(R.id.ac_find_text03)
    TextView mAcFindText03;
    @Bind(R.id.ac_find_text04)
    TextView mAcFindText04;
    @Bind(R.id.ac_lay_res)
    RelativeLayout mAcLayRes;
    @Bind(R.id.ac_line20)
    View mAcLine20;

    public TradeFragment() {

    }

    @Override
    public MvpBasePresenter<MvpView> createPresenter() {
        return new BlankPresenter();
    }

    public static TradeFragment newInstance() {
        TradeFragment fragment = new TradeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        setupTextViews();
        return rootView;
    }

    private void setupTextViews() {
        mAcFindText01.setText(R.string.zimaoqu);
        mAcFindText02.setText(R.string.zhaogongzuo);
        mAcFindText03.setText(R.string.zhaopeixun);
        mAcFindText04.setText(R.string.zhaohezuo);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trade_main;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected void initDaggerInject() {
        getHostActivity();
    }

    private MainActivity getHostActivity() {
        return (MainActivity) getActivity();
    }

    @Override
    protected MvpBasePresenter generatePresenter() {
        return new BlankPresenter();
    }

    @OnClick({R.id.ac_find_image01, R.id.ac_find_image02, R.id.ac_find_image03, R.id.ac_find_image04})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ac_find_image01:
                getHostActivity().mNavigator.navigateToTradeMainActivity(getHostActivity());
                break;
            case R.id.ac_find_image02:
                getHostActivity().mNavigator.navigateToGetJobActivity(getHostActivity());
                break;
            case R.id.ac_find_image03:
                //getHostActivity().mNavigator.navigateToGetTrainActivity(getHostActivity());
                break;
            case R.id.ac_find_image04:
                //getHostActivity().mNavigator.navigateToGetCollaborateActivity(getHostActivity());
                break;
        }
    }


}
