package com.thinkcoo.mobile.presentation.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.Goods;
import com.thinkcoo.mobile.presentation.views.activitys.base.BaseSimpleActivity;
import com.thinkcoo.mobile.presentation.views.fragment.MyCollectGoodsFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Robert.yao on 2016/8/17.
 */
public class MyCollectGoodsActivity extends BaseSimpleActivity {

    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Bind(R.id.sliding_tabs)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;


    public static Intent getJumpIntent(Context context){
        Intent intent = new Intent(context,MyCollectGoodsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect_goods);
        ButterKnife.bind(this);
        setupTitle();
        setupTabLayout();
    }

    private void setupTitle() {
        mTvTitle.setText("我的收藏");
    }

    private void setupTabLayout() {
        mViewpager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewpager);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[]{"出售", "求购"};

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            return MyCollectGoodsFragment.newInstance(getType(position));
        }

        private int getType(int position) {
            if (position == 0) {
                return Goods.SELL;
            } else {
                return Goods.BUY;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
