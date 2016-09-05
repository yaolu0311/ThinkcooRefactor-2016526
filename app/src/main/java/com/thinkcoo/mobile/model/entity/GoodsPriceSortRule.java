package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.presentation.views.component.GoodsFilterView;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Robert.yao on 2016/8/3.
 */
public class GoodsPriceSortRule implements GoodsFilterView.GoodsFilterContent{

    public static final String RULE_CODE_AUTO_SORT = "0";//智能排序
    public static final String RULE_CODE_PRICE_DESC = "1";//价格由高到低
    public static final String RULE_CODE_PRICE_ASC = "2";//价格由低到高
    public static final String RULE_CODE_TIME_SORT = "3";//时间最新

    public static List<GoodsPriceSortRule> createList(){

        List<GoodsPriceSortRule> goodsPriceSortRuleList = new ArrayList<>();

        GoodsPriceSortRule autoSortRule = new GoodsPriceSortRule();
        GoodsPriceSortRule priceDescRule = new GoodsPriceSortRule();
        GoodsPriceSortRule priceAscRule = new GoodsPriceSortRule();
        GoodsPriceSortRule timeSortRule = new GoodsPriceSortRule();

        autoSortRule.setSelected(true);
        autoSortRule.setDisPlayName("智能排序");
        autoSortRule.setRuleCode(RULE_CODE_AUTO_SORT);

        priceDescRule.setSelected(false);
        priceDescRule.setDisPlayName("价格由高到低");
        priceDescRule.setRuleCode(RULE_CODE_PRICE_DESC);

        priceAscRule.setSelected(false);
        priceAscRule.setDisPlayName("价格由低到高");
        priceAscRule.setRuleCode(RULE_CODE_PRICE_ASC);

        timeSortRule.setSelected(false);
        timeSortRule.setDisPlayName("价格由高到低");
        timeSortRule.setRuleCode(RULE_CODE_TIME_SORT);

        goodsPriceSortRuleList.add(autoSortRule);
        goodsPriceSortRuleList.add(priceDescRule);
        goodsPriceSortRuleList.add(priceAscRule);
        goodsPriceSortRuleList.add(timeSortRule);

        return goodsPriceSortRuleList;
    }

    public static GoodsPriceSortRule createDefault() {
        GoodsPriceSortRule autoSortRule = new GoodsPriceSortRule();
        autoSortRule.setSelected(true);
        autoSortRule.setDisPlayName("智能排序");
        autoSortRule.setRuleCode(RULE_CODE_AUTO_SORT);
        return autoSortRule;
    }

    String ruleCode;
    String disPlayName;
    boolean selected;

    @Override
    public String getContentName() {
        return disPlayName;
    }
    @Override
    public boolean isSelected() {
        return selected;
    }
    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public String getRuleCode() {
        return ruleCode;
    }
    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }
    public void setDisPlayName(String disPlayName) {
        this.disPlayName = disPlayName;
    }

}
