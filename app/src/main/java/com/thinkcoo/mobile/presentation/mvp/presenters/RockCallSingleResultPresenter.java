package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.domain.schedule.LoadClassListCase;
import com.thinkcoo.mobile.domain.schedule.LoadRockStudentListUseCase;
import com.thinkcoo.mobile.domain.schedule.ModifyRockCallResultCase;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.model.entity.serverresponse.RockSingleResByUuidResponse;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.RockCallSingleResultView;
import com.thinkcoo.mobile.presentation.views.PageMachine;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * author ：ml on 2016/8/20
 */
public class RockCallSingleResultPresenter extends MvpBasePresenter<RockCallSingleResultView> {

    private static final String TAG = "RockCallSingleResultPresenter";

    LoadClassListCase mLoadClassListCase;
    LoadRockStudentListUseCase mLoadStudentListCase;
    ModifyRockCallResultCase mModifyRockCallResultCase;
    ErrorMessageFactory mErrorMessageFactory;

    @Inject
    public RockCallSingleResultPresenter(LoadClassListCase loadClassListCase, LoadRockStudentListUseCase loadStudentListCase, ModifyRockCallResultCase modifyRockCallResultCase, ErrorMessageFactory errorMessageFactory) {
        this.mLoadClassListCase = loadClassListCase;
        this.mLoadStudentListCase = loadStudentListCase;
        this.mModifyRockCallResultCase =modifyRockCallResultCase;
        this.mErrorMessageFactory = errorMessageFactory;
    }

    public void loadClassListData(String eventId, String uuid, String groupId) {
        getView().showProgressDialog(R.string.loading);
        mLoadClassListCase.execute(getLoadClassListDataSub(uuid, groupId), eventId);
    }

    private Subscriber getLoadClassListDataSub(final String uuid, final String groupId) {

        return new Subscriber<List<ClassGroup>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e("SEE", e.getMessage(), e);
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
                getView().closeSelf();
            }

            @Override
            public void onNext(List<ClassGroup> classResponses) {
                if (!isViewAttached()) {
                    return;
                }
                getView().setClassList(classResponses);
                //拉取学生列表数据
                loadStudentListData(uuid, groupId);
            }
        };
    }

    // TODO: 2016/8/20  未传入参数
    public void loadStudentListData(String uuid, String groupId) {
        PageMachine pageMachine = new PageMachine();
        pageMachine.setPageContentCount(500);
        pageMachine.setTotalPage(20);
        mLoadStudentListCase.execute(getStudentListSub(), uuid, groupId, pageMachine);
    }

    private Subscriber getStudentListSub() {
        return new Subscriber<RockSingleResByUuidResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ThinkcooLog.e(TAG, "onError");
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().showToast(mErrorMessageFactory.createErrorMsg(e));
            }


            @Override
            public void onNext(RockSingleResByUuidResponse response) {
                ThinkcooLog.e(TAG, "onNext");
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().setStudentList(response);
            }
        };
    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mLoadClassListCase.unSubscribe();
        mLoadStudentListCase.unSubscribe();
    }

    public void modifyRockCallResult(String eventId, String eventRosterId) {
        //// TODO: 2016/8/23 判断条件
        getView().showProgressDialog(R.string.modifying);
        mModifyRockCallResultCase.execute(getModifyRockCallResultSub(),eventId,eventRosterId);
    }

    private Subscriber getModifyRockCallResultSub() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                getView().hideProgressDialogIfShowing();
                getView().toggleCheckBoxStatus();
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                ThinkcooLog.e(TAG,e.getMessage(),e);
                getView().hideProgressDialogIfShowing();
                getView().showToast(getView().getActivityContext().getString(R.string.modify_failture));
            }

            @Override
            public void onNext(Object o) {

            }
        };
    }

}
