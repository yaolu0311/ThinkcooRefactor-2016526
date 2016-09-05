package com.thinkcoo.mobile.presentation.mvp.presenters;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.presentation.ErrorMessageFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2016/6/14.
 */
public class SchoolSearchPresenter extends BaseSearchPresenter{


    @Inject
    public SchoolSearchPresenter(
            @Named("")
            UseCase searchUseCase,
            UseCase mSaveDataUseCase,
            UseCase mQueryDataUseCase, ErrorMessageFactory errorMessageFactory) {
        super(searchUseCase, mSaveDataUseCase, mQueryDataUseCase, errorMessageFactory);
    }
}
