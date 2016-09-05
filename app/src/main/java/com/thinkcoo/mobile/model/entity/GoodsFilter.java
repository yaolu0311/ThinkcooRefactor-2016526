package com.thinkcoo.mobile.model.entity;

/**
 * Created by Robert.yao on 2016/8/2.
 */
public class GoodsFilter {


    public static final GoodsFilter createDefaule(){

        GoodsFilter goodsFilter = new GoodsFilter();

        goodsFilter.setGoodsCategory(GoodsCategory.createDefault());
        goodsFilter.setGoodsHostSchool(GoodsHostSchool.createDefault());
        goodsFilter.setGoodsPriceSortRule(GoodsPriceSortRule.createDefault());
        goodsFilter.setGoodsDistance(GoodsDistance.createDefault());

        return goodsFilter;
    }

    GoodsCategory mGoodsCategory;
    GoodsHostSchool mGoodsHostSchool;
    GoodsPriceSortRule mGoodsPriceSortRule;
    GoodsDistance mGoodsDistance;


    public GoodsCategory getGoodsCategory() {
        return mGoodsCategory;
    }

    public void setGoodsCategory(GoodsCategory goodsCategory) {
        mGoodsCategory = goodsCategory;
    }

    public GoodsHostSchool getGoodsHostSchool() {
        return mGoodsHostSchool;
    }

    public void setGoodsHostSchool(GoodsHostSchool goodsHostSchool) {
        mGoodsHostSchool = goodsHostSchool;
    }

    public GoodsPriceSortRule getGoodsPriceSortRule() {
        return mGoodsPriceSortRule;
    }

    public void setGoodsPriceSortRule(GoodsPriceSortRule goodsPriceSortRule) {
        mGoodsPriceSortRule = goodsPriceSortRule;
    }

    public GoodsDistance getGoodsDistance() {
        return mGoodsDistance;
    }

    public void setGoodsDistance(GoodsDistance goodsDistance) {
        mGoodsDistance = goodsDistance;
    }
}
