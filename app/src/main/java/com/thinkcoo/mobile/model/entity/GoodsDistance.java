package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.presentation.views.component.GoodsFilterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert.yao on 2016/8/3.
 */
public class GoodsDistance implements GoodsFilterView.GoodsFilterContent{


    public static final List< GoodsDistance> createList(){

        List< GoodsDistance> goodsDistanceList = new ArrayList<>();

        GoodsDistance goodsDistance1000 = new GoodsDistance();
        GoodsDistance goodsDistance3000 = new GoodsDistance();
        GoodsDistance goodsDistance5000 = new GoodsDistance();

        goodsDistance1000.setSelected(true);
        goodsDistance3000.setSelected(false);
        goodsDistance5000.setSelected(false);

        goodsDistance1000.setDisplayName("1千米");
        goodsDistance3000.setDisplayName("3千米");
        goodsDistance5000.setDisplayName("5千米");

        goodsDistanceList.add(goodsDistance1000);
        goodsDistanceList.add(goodsDistance3000);
        goodsDistanceList.add(goodsDistance5000);

        goodsDistance1000.setDistanceCode("1");
        goodsDistance3000.setDistanceCode("2");
        goodsDistance5000.setDistanceCode("3");

        return goodsDistanceList;

    }

    public static GoodsDistance createDefault() {
        GoodsDistance goodsDistance1000 = new GoodsDistance();
        goodsDistance1000.setSelected(true);
        goodsDistance1000.setDisplayName("1千米");
        goodsDistance1000.setDistanceCode("1");
        return goodsDistance1000;
    }

    private String displayName;
    private String distanceCode;
    private boolean selected;

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getContentName() {
        return displayName;
    }
    @Override
    public boolean isSelected() {
        return selected;
    }
    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getDistanceCode() {
        return distanceCode;
    }

    public void setDistanceCode(String distanceCode) {
        this.distanceCode = distanceCode;
    }


}
