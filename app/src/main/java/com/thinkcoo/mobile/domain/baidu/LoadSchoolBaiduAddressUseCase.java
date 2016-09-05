package com.thinkcoo.mobile.domain.baidu;

import com.thinkcoo.mobile.domain.UseCase;
import com.thinkcoo.mobile.model.entity.RequestParam.LoadSchoolBaiduAddressParam;
import com.thinkcoo.mobile.model.repository.BaiduRepository;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Robert.yao on 2016/8/11.
 */
public class LoadSchoolBaiduAddressUseCase extends UseCase<Object>{

    BaiduRepository mBaiduRepository;

    public LoadSchoolBaiduAddressUseCase(Scheduler mUiThread, Scheduler mExecutorThread, BaiduRepository baiduRepository) {
        super(mUiThread, mExecutorThread);
        mBaiduRepository = baiduRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object... q) {
        return mBaiduRepository.loadSchoolLocationList(((LoadSchoolBaiduAddressParam)q[0]).getSchoolName());
    }
}
