package com.thinkcoo.mobile.model.entity;

import com.yzkj.android.orm.annotation.Column;
import com.yzkj.android.orm.annotation.Table;

/**
 * Created by Robert.yao on 2016/8/2.
 */
@Table(name = "goods_search_history")
public class GoodsSearchHistory {

    @Column(name = "id" , isId = true,autoGen = true)
    private int id;
    @Column(name = "content")
    private String content;
    @Column(name = "goodsType")
    private int goodsType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }
}
