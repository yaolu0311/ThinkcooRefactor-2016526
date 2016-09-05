package com.thinkcoo.mobile.model.repository;

import com.thinkcoo.mobile.model.entity.RequestParam.SearchJobParams;
import com.thinkcoo.mobile.model.entity.serverresponse.FindJobResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyCollectJobResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.MyShieldCompanyResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.QueryRequestJobResponse;
import com.thinkcoo.mobile.presentation.views.PageMachine;

import java.util.List;
import rx.Observable;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public interface GetJobRepository {
    Observable<List<FindJobResponse>> searchJob(SearchJobParams searchJobParams);
    Observable<List<QueryRequestJobResponse>> loadRequestJobList(PageMachine pageMachine);
    Observable<List<MyCollectJobResponse>> loadMyCollect(PageMachine pageMachine);
    Observable<List<MyShieldCompanyResponse>> loadMyShieldCompany(PageMachine pageMachine);
}
