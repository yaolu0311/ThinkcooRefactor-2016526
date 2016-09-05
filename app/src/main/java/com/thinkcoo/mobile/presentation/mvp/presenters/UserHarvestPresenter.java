package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.thinkcoo.mobile.domain.user.DeleteUserHarvestUseCase;
import com.thinkcoo.mobile.domain.user.LoadUserHarvestListUseCase;
import com.thinkcoo.mobile.model.entity.UserHarvest;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Leevin
 * CreateTime: 2016/5/30  16:41
 */
public class UserHarvestPresenter extends MvpBasePresenter<MvpView> {

    private static final String PAGE_INDEX = "1";
    private static final String PAGE_SIZE = "50";
    private LoadUserHarvestListUseCase mLoadUserHarvestListUseCase;
    private DeleteUserHarvestUseCase mDeleteUserHarvestUseCase;
    private ErrorMessageFactory mErrorMessageFactory;

    @Inject
    public UserHarvestPresenter(LoadUserHarvestListUseCase loadUserHarvestListUseCase, DeleteUserHarvestUseCase deleteUserHarvestUseCase, ErrorMessageFactory errorMessageFactory) {
        mLoadUserHarvestListUseCase = loadUserHarvestListUseCase;
        mDeleteUserHarvestUseCase = deleteUserHarvestUseCase;
        mErrorMessageFactory = errorMessageFactory;
    }

    public MvpLceView getMvpLceView() {
        return (MvpLceView) getView();
    }

    // 获取收获详情
    public void loadUserHarvestList(boolean isUpdateNow) {
        if (!isViewAttached()) {
            return;
        }
        getMvpLceView().showLoading(false);
        mLoadUserHarvestListUseCase.execute(getloadUserHarvestListUseCaseSub(), PAGE_INDEX, PAGE_SIZE,isUpdateNow);
    }

    private Subscriber getloadUserHarvestListUseCaseSub() {
       return new Subscriber<List<UserHarvest>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getMvpLceView().showError(e, false);
            }

            @Override
            public void onNext(List<UserHarvest> userHarvests) {
                if (!isViewAttached()) {
                    return;
                }
                getMvpLceView().setData(userHarvests);
                getMvpLceView().showContent();
            }
        };
    }

    // 删除收获
    public void deleteUserHarvest(String harvestId) {
        if (!isViewAttached()) {
            return;
        }
        getMvpLceView().showLoading(false);
        mDeleteUserHarvestUseCase.execute(getdeleteUserHarvestUseCaseSub(), harvestId);
    }

    private Subscriber getdeleteUserHarvestUseCaseSub() {

        return new Subscriber<Void>() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) {
                    return;
                }
                // 重新加载数据
                loadUserHarvestList(true);
            }

            @Override
            public void onError(Throwable e) {
                if (!isViewAttached()) {
                    return;
                }
                getMvpLceView().showError(e, false);
            }

            @Override
            public void onNext(Void aVoid) {
            }
        };
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mLoadUserHarvestListUseCase.unSubscribe();
        mDeleteUserHarvestUseCase.unSubscribe();
    }
}
