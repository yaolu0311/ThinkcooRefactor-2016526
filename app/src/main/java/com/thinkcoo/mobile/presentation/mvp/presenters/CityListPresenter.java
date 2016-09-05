package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.data_dictionary.GetAddressDDUseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.entity.SortedCity;
import com.thinkcoo.mobile.presentation.mvp.views.BaseLceView;

import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/7/26.
 */
public class CityListPresenter  extends BaseLcePresenter<SortedCity, BaseLceView<SortedCity>>{

    @Inject
    public CityListPresenter(GetAddressDDUseCase mGetAddressDDUseCase) {
        super(mGetAddressDDUseCase);
    }
    @Override
    protected RequestParam buildLceLoadDataParams(boolean pullToRefresh) {
        return null;//case 不需要参数，所以返回null
    }

}
