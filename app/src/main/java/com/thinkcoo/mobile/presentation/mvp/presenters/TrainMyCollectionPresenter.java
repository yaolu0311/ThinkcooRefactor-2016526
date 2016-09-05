package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.train.DeleteAppointmentOrCollectionUseCase;
import com.thinkcoo.mobile.domain.train.LoadTrainCollectionListUseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.entity.TrainCourse;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;
import com.thinkcoo.mobile.presentation.mvp.views.TrainCollectionView;
import com.thinkcoo.mobile.presentation.views.PageMachine;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * author ：ml on 2016/7/28
 */
public class TrainMyCollectionPresenter extends BaseLcePagedPresenter<TrainCourse,TrainCollectionView> {

    private LoadTrainCollectionListUseCase mLoadTrainCollectionListUseCase;
    private DeleteAppointmentOrCollectionUseCase mDeleteAppointmentOrCollectionUseCase;
    private ErrorMessageFactory mErrorMessageFactory;

    @Inject
    public TrainMyCollectionPresenter(LoadTrainCollectionListUseCase useCase, DeleteAppointmentOrCollectionUseCase deleteAppointmentOrCollectionUseCase, ErrorMessageFactory errorMessageFactory) {
        super(useCase);
        mDeleteAppointmentOrCollectionUseCase = deleteAppointmentOrCollectionUseCase;
        mErrorMessageFactory = errorMessageFactory;
    }

    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {
        return null;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mLoadTrainCollectionListUseCase.unSubscribe();
        mDeleteAppointmentOrCollectionUseCase.unSubscribe();
    }

    public void loadCollectionList() {
        if (!isViewAttached()) {
            return;
        }
        mLoadTrainCollectionListUseCase.execute(getLoadCollectionListSub());
    }

    private Subscriber getLoadCollectionListSub() {
        return new Subscriber<List<TrainCourse>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getView().showError(e,true);
            }

            @Override
            public void onNext(List<TrainCourse> trainCourseEntities) {
                if (!isViewAttached()) {
                    return;
                }
                getView().setDataList(trainCourseEntities);
            }
        };
    }

}
