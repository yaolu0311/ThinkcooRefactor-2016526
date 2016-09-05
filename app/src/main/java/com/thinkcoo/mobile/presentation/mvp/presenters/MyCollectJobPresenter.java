package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.get_job.MyCollectJobUseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.presentation.views.PageMachine;

/**
 * Created by Robert.yao on 2016/8/20.
 */
public class MyCollectJobPresenter extends BaseLcePagedPresenter{

    public MyCollectJobPresenter(MyCollectJobUseCase useCase) {
        super(useCase);
    }

    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {
        return pageMachine;
    }
}
