package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.baidu.LoadSchoolBaiduAddressUseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.LoadSchoolBaiduAddressParam;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.entity.SchoolLocation;
import com.thinkcoo.mobile.presentation.mvp.views.LoadSchoolBaiduAddressView;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/11.
 */
public class LoadSchoolBaiduAddressPresenter extends BaseLcePresenter<SchoolLocation,LoadSchoolBaiduAddressView>{

    @Inject
    public LoadSchoolBaiduAddressPresenter(LoadSchoolBaiduAddressUseCase useCase) {
        super(useCase);
    }
    @Override
    protected RequestParam buildLceLoadDataParams(boolean pullToRefresh) {
        LoadSchoolBaiduAddressParam requestParam = new LoadSchoolBaiduAddressParam();
        requestParam.setUpdateNow(pullToRefresh);
        requestParam.setSchoolName(getView().getSchoolName());
        return requestParam;
    }


}
