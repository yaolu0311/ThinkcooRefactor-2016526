package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.get_job.LoadJobResultUseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.entity.RequestParam.SearchJobParams;
import com.thinkcoo.mobile.model.entity.serverresponse.FindJobResponse;
import com.thinkcoo.mobile.presentation.mvp.views.JobResultView;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import javax.inject.Inject;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class JobResultPresenter extends BaseLcePagedPresenter<FindJobResponse,JobResultView<FindJobResponse>>{

    @Inject
    public JobResultPresenter(LoadJobResultUseCase useCase) {
        super(useCase);
    }
    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {
        SearchJobParams searchJobParams = new SearchJobParams();
        searchJobParams.setPageMachine(pageMachine);
        searchJobParams.setUpdateNow(pullToRefresh);
        searchJobParams.setJobFilter(getView().getJobFilter());
        return searchJobParams;
    }
}
