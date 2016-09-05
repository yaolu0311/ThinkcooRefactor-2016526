package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.get_job.LoadMyShieldCompanyUseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.entity.serverresponse.MyShieldCompanyResponse;
import com.thinkcoo.mobile.presentation.mvp.views.BaseLceView;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class MyShieldCompanyPresenter extends BaseLcePagedPresenter<MyShieldCompanyResponse,BaseLceView<MyShieldCompanyResponse>>{

    @Inject
    public MyShieldCompanyPresenter(LoadMyShieldCompanyUseCase loadMyShieldCompanyUseCase) {
        super(loadMyShieldCompanyUseCase);
    }

    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {
        return pageMachine;
    }
}
