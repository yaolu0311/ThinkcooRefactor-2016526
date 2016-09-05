package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.domain.train.LoadTrainSearchResultUseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.RequestParam;
import com.thinkcoo.mobile.model.entity.TrainCourse;
import com.thinkcoo.mobile.model.entity.TrainCourseFilter;
import com.thinkcoo.mobile.presentation.mvp.views.TrainSearchResultView;
import com.thinkcoo.mobile.presentation.views.PageMachine;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Leevin
 * CreateTime: 2016/8/20  13:15
 */
public class TrainSearchResultPresenter extends BaseLcePagedPresenter<TrainCourse,TrainSearchResultView<TrainCourse>> {

    private static final String TAG = "TrainSearchResultPresenter";
    LoadTrainSearchResultUseCase mLoadTrainSearchResultUseCase;


    public TrainSearchResultPresenter(UseCase useCase, LoadTrainSearchResultUseCase loadTrainSearchResultUseCase) {
        super(useCase);
        mLoadTrainSearchResultUseCase = loadTrainSearchResultUseCase;
    }

    @Override
    protected RequestParam buildLcePagedParams(boolean pullToRefresh, PageMachine pageMachine) {
       TrainCourseFilter trainCourseFilter =  getView().getFilter();
        trainCourseFilter.setPageMachine(pageMachine);
        trainCourseFilter.setUpdateNow(pullToRefresh);
        return trainCourseFilter;
    }

    private Subscriber getLoadTrainSearchResultUseSub() {
        return new Subscriber<List<TrainCourse>>() {
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
            public void onNext(List<TrainCourse> trainCourses) {
                if (!isViewAttached()) {
                    return;
                }
                getView().setDataList(trainCourses);
            }
        };
    }

}
