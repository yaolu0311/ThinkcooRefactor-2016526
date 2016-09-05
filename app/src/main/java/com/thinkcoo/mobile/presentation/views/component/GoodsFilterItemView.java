package com.thinkcoo.mobile.presentation.views.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkcoo.mobile.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Robert.yao on 2016/8/3.
 */
public class GoodsFilterItemView extends RelativeLayout implements View.OnClickListener {

    @Bind(R.id.goods_category)
    TextView mContentTv;
    @Bind(R.id.filter_arrow_iv01)
    ImageView mArrowIv;

    private boolean isOpen = false;

    public interface OnStatusChangeListener {
        void onStatusChange(boolean isOpen);
    }

    OnStatusChangeListener mOnStatusChangeListener;

    public void setOnStatusChangeListener(OnStatusChangeListener onStatusChangeListener) {
        mOnStatusChangeListener = onStatusChangeListener;
    }

    public GoodsFilterItemView(Context context) {
        super(context);
        View view = inflate(context, R.layout.view_arrow_text_view, this);
        ButterKnife.bind(this, view);
        initViews();
        setOnClickListener(this);
    }

    private void initViews() {
        mContentTv.setTextColor(getResources().getColor(R.color.color_smoke));
        mArrowIv.setBackgroundResource(R.mipmap.trade_filter_gray_down);
    }

    public GoodsFilterItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.view_arrow_text_view, this);
        ButterKnife.bind(this, view);
        initViews();
        setOnClickListener(this);
    }

    public void onClick(View v) {
        if (!isOpen) {
            open();
        } else {
            close();
        }
        callBackOnArrowRotated();
    }

    private void open() {
        selected();
        mArrowIv.startAnimation(getArrowAnimationSet().getAnimations().get(0));
        isOpen = true;
    }

    public void close() {
        mArrowIv.startAnimation(getArrowAnimationSet().getAnimations().get(1));
        isOpen = false;
    }

    public void selected() {
        mContentTv.setTextColor(getResources().getColor(R.color.color_orange));
        mArrowIv.setBackgroundResource(R.mipmap.trade_filter_orange_down);
    }

    public void reset() {
        if (isOpen) {
            mContentTv.setTextColor(getResources().getColor(R.color.color_smoke));
            mArrowIv.setBackgroundResource(R.mipmap.trade_filter_gray_up);
            mArrowIv.startAnimation(getArrowAnimationSet().getAnimations().get(1));
        } else {
            initViews();
        }

        isOpen = false;
    }

    private void callBackOnArrowRotated() {
        if (null != mOnStatusChangeListener) {
            mOnStatusChangeListener.onStatusChange(isOpen);
        }
    }

    private AnimationSet getArrowAnimationSet() {

        AnimationSet animationSet = new AnimationSet(false);

        Animation rotateAnimation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);

        RotateAnimation animation = new RotateAnimation(180, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);

        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(animation);

        return animationSet;
    }

    public void setText(String text) {
        mContentTv.setText(text);
    }
}
