package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.get_job.RequestJobListUseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.entity.serverresponse.QueryRequestJobResponse;
import com.thinkcoo.mobile.presentation.mvp.views.BaseLceView;
import com.thinkcoo.mobile.presentation.views.PageMachine;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class RequestJobListPresenter extends BaseLcePagedPresenter<QueryRequestJobResponse,BaseLceView<QueryRequestJobResponse>>{

    public RequestJobListPresenter(RequestJobListUseCase useCase) {
        super(useCase);
    }
    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {
        return pageMachine;
    }


}
