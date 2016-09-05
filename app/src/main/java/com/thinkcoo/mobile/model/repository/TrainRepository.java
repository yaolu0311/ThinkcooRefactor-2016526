package com.thinkcoo.mobile.model.repository;

import com.thinkcoo.mobile.model.entity.Address;
import com.thinkcoo.mobile.model.entity.TrainCourse;
import com.thinkcoo.mobile.model.entity.TrainCourseFilter;

import java.util.List;

import rx.Observable;

/**
 * Created by Robert.yao on 2016/7/18.
 */
public interface TrainRepository {

    Observable<List<TrainCourse>> loadTrainCourseList(TrainCourseFilter trainCourseFilter);

    Observable<List<TrainCourse>> loadMyAppointmentList(String pageNow, String pageSize);

    Observable<List<TrainCourse>> loadMyCollectionList(String pageNow, String pageSize);

    Observable deleteMyAppointmentOrCollection(String id);

    Observable cancelAppointmentOrCollection(String id);

    Observable addAppointmentOrCollection(String courseId, String type);

    Observable<Object> loadCompanyDetails(String companyId);

    Observable<Object> loadTrainCourseDetails(String courseId);

    Observable<List<Address>> loadAreaByParentCode(String areaCode);

}
