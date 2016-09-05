package com.thinkcoo.mobile.presentation.mvp.views;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.thinkcoo.mobile.model.entity.Province;
import java.util.List;

/**
 * Created by Leevin
 * CreateTime: 2016/6/14  11:05
 */
public interface AddressView extends MvpView{

    void setAddressDataDictionary(List<Province> provinceList);
    void setSelect();
}
