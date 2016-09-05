package com.thinkcoo.mobile.model.entity.RequestParam;

import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.presentation.views.PageMachine;

/**
 * Created by Robert.yao on 2016/7/25.
 */
public class GoodsRecommendParam implements RequestParam{

    private boolean isPullToRefresh;
    private PageMachine mPageMachine;
    private Location location;
    private int goodsRecommendType;

    private GoodsRecommendParam(Builder builder) {
        setPullToRefresh(builder.isPullToRefresh);
        setPageMachine(builder.mPageMachine);
        setLocation(builder.location);
        setGoodsRecommendType(builder.goodsRecommendType);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(GoodsRecommendParam copy) {
        Builder builder = new Builder();
        builder.isPullToRefresh = copy.isPullToRefresh;
        builder.mPageMachine = copy.mPageMachine;
        builder.location = copy.location;
        builder.goodsRecommendType = copy.goodsRecommendType;
        return builder;
    }


    public boolean isPullToRefresh() {
        return isPullToRefresh;
    }

    public void setPullToRefresh(boolean pullToRefresh) {
        isPullToRefresh = pullToRefresh;
    }

    public PageMachine getPageMachine() {
        return mPageMachine;
    }

    public void setPageMachine(PageMachine pageMachine) {
        mPageMachine = pageMachine;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getGoodsRecommendType() {
        return goodsRecommendType;
    }

    public void setGoodsRecommendType(int goodsRecommendType) {
        this.goodsRecommendType = goodsRecommendType;
    }

    public static final class Builder {
        private boolean isPullToRefresh;
        private PageMachine mPageMachine;
        private Location location;
        private int goodsRecommendType;

        private Builder() {
        }

        public Builder isPullToRefresh(boolean val) {
            isPullToRefresh = val;
            return this;
        }

        public Builder mPageMachine(PageMachine val) {
            mPageMachine = val;
            return this;
        }

        public Builder location(Location val) {
            location = val;
            return this;
        }

        public Builder goodsRecommendType(int val) {
            goodsRecommendType = val;
            return this;
        }

        public GoodsRecommendParam build() {
            return new GoodsRecommendParam(this);
        }
    }
}
