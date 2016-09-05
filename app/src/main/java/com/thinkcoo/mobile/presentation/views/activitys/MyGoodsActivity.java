package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;
import com.thinkcoo.mobile.presentation.views.fragment.MyGoodsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/9.
 */
public class MyGoodsActivity extends BaseSimpleActivity {

    public static final String MY_GOODS_TYPE_KEY = "my_goods_type_key";
    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;

    private String myGoodsType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sale);
        ButterKnife.bind(this);
        setupTitle();
        myGoodsType = getGoodsTypeFromIntent();
        addFragment(R.id.fragment_container, MyGoodsFragment.getInstance(myGoodsType));
    }

    private void setupTitle() {
        mTvTitle.setText(typeIsBuy() ? "我的求购":"我的出售");
    }

    private String getGoodsTypeFromIntent() {
        return getIntent().getStringExtra(MY_GOODS_TYPE_KEY);
    }

    public static Intent getJumpIntent(Context context, String myGoodsType) {
        Intent intent = new Intent(context, MyGoodsActivity.class);
        intent.putExtra(MY_GOODS_TYPE_KEY, myGoodsType);
        return intent;
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

    private boolean typeIsBuy(){
        return String.valueOf(Goods.BUY).equals(myGoodsType);
    }
}
