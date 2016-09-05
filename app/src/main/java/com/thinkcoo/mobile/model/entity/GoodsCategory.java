package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.entity.serverresponse.TeacherAddCourseInformation;
import com.thinkcoo.mobile.presentation.views.component.GoodsFilterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert.yao on 2016/8/1.
 */
public class GoodsCategory implements GoodsFilterView.GoodsFilterContent{

    /**
     * 自贸区中的类别资源图片
     */
    public final static int GOODS_CATEGORY_IMAGE[] = {

            R.mipmap.trade_index_book,
            R.mipmap.trade_index_outlets,
            R.mipmap.trade_index_life_product,
            R.mipmap.trade_index_life_electric,
            R.mipmap.trade_index_ecomputer,
            R.mipmap.trade_index_digital_products,
            R.mipmap.trade_index_other,
            R.mipmap.trade_index_nearby
    };

    public final static String GOODS_CATEGORY_CODE [] = {
            "1","2","3","4","5","6","7","8"
    };

    /**
     * 自贸区中的类别
     */
    public final static String[] goodsTypes = new String[] { "图书/音像", "文体户外",
            "生活用品", "小家电", "电脑/配件", "数码产品", "其他", "附近" };

    public static List<GoodsCategory> createList(boolean containNearby){
        List<GoodsCategory> goodsCategoryList = new ArrayList<>();
        for (int i = 0; i < GOODS_CATEGORY_IMAGE.length; i++) {
            if (!containNearby && i == GOODS_CATEGORY_IMAGE.length -1){
                break;
            }
            GoodsCategory goodsCategory = new GoodsCategory(goodsTypes[i],GOODS_CATEGORY_IMAGE[i],false,GOODS_CATEGORY_CODE[i]);
            goodsCategoryList.add(goodsCategory);
        }
        return goodsCategoryList;
    }
    public static List<GoodsCategory>createFilterList(){
        List<GoodsCategory> goodsCategoryList = new ArrayList();
        goodsCategoryList.add(createDefault());
        goodsCategoryList.addAll(createList(false));
        return goodsCategoryList;
    }
    public static GoodsCategory createDefault() {
        return new GoodsCategory("全部",0,true,"0");
    }

    public GoodsCategory(String categoryName, int imageHead , boolean isSelected,String categoryCode) {
        this.categoryName = categoryName;
        this.imageHead = imageHead;
        this.isSelected= isSelected;
        this.categotyCode = categoryCode;
    }

    private String categoryName;
    private String categotyCode;
    private int imageHead;
    private boolean isSelected;

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public int getImageHead() {
        return imageHead;
    }
    public void setImageHead(int imageHead) {
        this.imageHead = imageHead;
    }
    @Override
    public String getContentName() {
        return getCategoryName();
    }
    @Override
    public boolean isSelected() {
        return isSelected;
    }
    @Override
    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
    public void setCategotyCode(String categotyCode) {
        this.categotyCode = categotyCode;
    }
    public String getCategotyCode() {
        return categotyCode;
    }
}
