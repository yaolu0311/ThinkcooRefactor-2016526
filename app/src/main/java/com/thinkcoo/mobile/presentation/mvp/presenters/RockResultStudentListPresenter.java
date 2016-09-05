package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.domain.schedule.LoadRockResultStudentListUseCase;
import com.thinkcoo.mobile.domain.schedule.ModifyRockCallResultCase;
import com.thinkcoo.mobile.model.entity.RequestParam.LoadRockResultStudentListParam;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.entity.serverresponse.RockResultByUuidResponse;
import com.thinkcoo.mobile.presentation.mvp.views.RockResultStudentListView;
import com.thinkcoo.mobile.presentation.views.PageMachine;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * author ：ml on 2016/8/12
 */
public class RockResultStudentListPresenter extends BaseLcePagedPresenter<RockResultByUuidResponse.ListBean,RockResultStudentListView<RockResultByUuidResponse.ListBean>>{


    private static final String TAG = "RockResultStudentListPresenter" ;
    ModifyRockCallResultCase mModifyRockCallResultCase;

    @Inject
    public RockResultStudentListPresenter(LoadRockResultStudentListUseCase useCase ,ModifyRockCallResultCase modifyRockCallResultCase) {

        super(useCase);
        this.mModifyRockCallResultCase=modifyRockCallResultCase;

    }

    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {
        LoadRockResultStudentListParam param = new LoadRockResultStudentListParam();
        param.setGroupId(getView().getGroupId());
        param.setPageMachine(pageMachine);
        param.setUpdateNow(pullToRefresh);
        param.setUuid(getView().getuuid());
        return param;
    }


    public void modifyRockCallResult(String eventId, String eventRosterId) {
        mModifyRockCallResultCase.execute(getModifyRockCallResultSub(),eventId,eventRosterId);
    }

    private Subscriber getModifyRockCallResultSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG,e.getMessage(),e);

            }

            @Override
            public void onNext(Object o) {

            }
        };
    }
}
