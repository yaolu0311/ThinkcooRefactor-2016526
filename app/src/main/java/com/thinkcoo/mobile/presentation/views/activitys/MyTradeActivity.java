package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/5.
 */
public class MyTradeActivity extends BaseSimpleActivity {

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_other)
    TextView mTvOther;
    @Bind(R.id.iv_more)
    ImageView mIvMore;
    @Bind(R.id.iv_icon2)
    ImageView mIvIcon2;
    @Bind(R.id.rl_mytrade_sale)
    RelativeLayout mRlMytradeSale;
    @Bind(R.id.iv_icon3)
    ImageView mIvIcon3;
    @Bind(R.id.rl_mytrade_tobuy)
    RelativeLayout mRlMytradeTobuy;
    @Bind(R.id.iv_icon4)
    ImageView mIvIcon4;
    @Bind(R.id.rl_mytrade_release_sale)
    RelativeLayout mRlMytradeReleaseSale;
    @Bind(R.id.iv_icon5)
    ImageView mIvIcon5;
    @Bind(R.id.rl_mytrade_release_tobuy)
    RelativeLayout mRlMytradeReleaseTobuy;
    @Bind(R.id.iv_icon1)
    ImageView mIvIcon1;
    @Bind(R.id.rl_mytrade_collect)
    RelativeLayout mRlMytradeCollect;


    public static Intent getJumpIntent(Context context){
        Intent intent = new Intent(context,MyTradeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trade);
        ButterKnife.bind(this);
        setupTitle();
    }

    private void setupTitle() {
        mTvTitle.setText("我的自贸区");
    }

    @OnClick({R.id.rl_mytrade_sale, R.id.rl_mytrade_tobuy, R.id.rl_mytrade_release_sale, R.id.rl_mytrade_release_tobuy, R.id.rl_mytrade_collect,R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_mytrade_sale:
                mNavigator.navigatorToMyGoodsActivity(this, String.valueOf(Goods.SELL));
                break;
            case R.id.rl_mytrade_tobuy:
                mNavigator.navigatorToMyGoodsActivity(this, String.valueOf(Goods.BUY));
                break;
            case R.id.rl_mytrade_release_sale:
                mNavigator.navigatorToReleaseMySellGoodsActivity(this);
                break;
            case R.id.rl_mytrade_release_tobuy:
                //mNavigator.navigateToCreateBuyActivity(this);
                break;
            case R.id.rl_mytrade_collect:
                mNavigator.navigatorToMyCollectionGoodsActivity(this);
                break;
        }
    }
}
