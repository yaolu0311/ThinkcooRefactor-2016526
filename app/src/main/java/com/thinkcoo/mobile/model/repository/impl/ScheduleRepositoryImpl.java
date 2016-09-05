package com.thinkcoo.mobile.model.repository.impl;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import com.google.gson.Gson;
import com.thinkcoo.mobile.model.cache.LoginUserCache;
import com.thinkcoo.mobile.model.cache.ScheduleCacheProvides;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.model.entity.EventNoticeEntity;
import com.thinkcoo.mobile.model.entity.EventTime;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.model.entity.Schedule;
import com.thinkcoo.mobile.model.entity.ScheduleCreateUser;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.model.entity.serverresponse.ClassResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.EventNoticeEntityResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.EventTimeResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.RockSingleResByUuidResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.ScheduleResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.StudentCheckResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.StudentResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;
import com.thinkcoo.mobile.model.exception.EmptyMaualStudentsInClassException;
import com.thinkcoo.mobile.model.exception.EmptyStudentsInClassException;
import com.thinkcoo.mobile.model.exception.ServerResponseException;
import com.thinkcoo.mobile.model.exception.user.EmptyException;
import com.thinkcoo.mobile.model.repository.ScheduleRepository;
import com.thinkcoo.mobile.model.rest.apis.ScheduleApi;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Leevin
 * CreateTime: 2016/6/28  14:54
 */
@Singleton
public class ScheduleRepositoryImpl implements ScheduleRepository {

    public static final String TAG = "ScheduleRepositoryImpl";

    ScheduleApi mScheduleApi;
    ScheduleCacheProvides mScheduleCacheProvides;
    LoginUserCache mLoginUserCache;
    ServerDataConverter mServerDataConverter;

    @Inject
    public ScheduleRepositoryImpl(ScheduleApi scheduleApi, ScheduleCacheProvides scheduleCacheProvides, LoginUserCache loginUserCache, ServerDataConverter serverDataConverter) {
        mScheduleApi = scheduleApi;
        mScheduleCacheProvides = scheduleCacheProvides;
        mLoginUserCache = loginUserCache;
        mServerDataConverter = serverDataConverter;
    }

    @Override
    public Observable AddSchedule(final Schedule schedule) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.addSchedule(scheduleToJson(schedule, user));
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new Throwable(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

    @Override
    public Observable updateSchedule(final Schedule schedule) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.updateSchedule(scheduleToJson(schedule, user));
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new Throwable(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

    public String scheduleToJson(Schedule schedule, User user) {
        schedule.setScheduleCreateUser(ScheduleCreateUser.createByUser(user));
        return new Gson().toJson(Schedule.toServerResponse(schedule));
    }

    @Override
    public Observable<Schedule> loadSchedule(final String scheduleId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<Schedule>>() {
            @Override
            public Observable<Schedule> call(final User user) {
                return mScheduleApi.loadSchedule(user.getUserId(), scheduleId).flatMap(new Func1<BaseResponse<ScheduleResponse>, Observable<Schedule>>() {
                    @Override
                    public Observable<Schedule> call(BaseResponse<ScheduleResponse> scheduleResponseBaseResponse) {
                        if (!scheduleResponseBaseResponse.isSuccess()) {
                            return Observable.error(new Throwable(scheduleResponseBaseResponse.getMsg()));
                        }
                        ScheduleResponse data = scheduleResponseBaseResponse.getData();
                        return Observable.just(Schedule.fromServerResponse(data, user));
                    }
                });
            }
        });
    }

    @Override
    public Observable<List<Event>> loadEventList(final String startDate, final String endDate) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<List<Event>>>() {
            @Override
            public Observable<List<Event>> call(final User user) {
                Observable<BaseResponse<List<EventTimeResponse>>> baseResponseObservable = mScheduleApi.loadTimeEvents(user.getUserId(), startDate, endDate);
                return baseResponseObservable.flatMap(new Func1<BaseResponse<List<EventTimeResponse>>, Observable<List<EventTime>>>() {
                    @Override
                    public Observable<List<EventTime>> call(BaseResponse<List<EventTimeResponse>> listBaseResponse) {
                        if (!listBaseResponse.isSuccess()) {
                            return Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                        }
                        List<EventTimeResponse> serverEventTimeList = listBaseResponse.getData();
                        if (serverEventTimeList == null || serverEventTimeList.size() == 0) {
                            return Observable.error(new EmptyException());
                        }
                        return Observable.just(EventTime.fromServerResponse(serverEventTimeList, mServerDataConverter, user));
                    }
                }).flatMap(new Func1<List<EventTime>, Observable<List<Event>>>() {
                    @Override
                    public Observable<List<Event>> call(List<EventTime> eventTimes) {
                        return Observable.just(EventTime.toEvent(eventTimes));
                    }
                });
            }
        });
    }

    @Override
    public Observable removeClass(final String groupId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {

            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.removeClass(user.getUserId(), groupId);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }

        });


    }

    @Override
    public Observable confimStudents(final String eventId, final String groupId, final String accoundIds) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {

            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.confimStudents(user.getUserId(), eventId, groupId, accoundIds);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });

    }

    @Override
    public Observable<List<Student>> loadStudentListByClassId(String classid) {
        return null;
    }


    @Override
    public Observable<List<Student>> serchStudents(final String eventId, final String schoolname, final String classname, final String keyWord, final PageMachine pageMachine) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<StudentResponse>>>() {

            @Override
            public Observable<BaseResponse<StudentResponse>> call(User user) {
                return mScheduleApi.serchStudent(user.getUserId(), eventId, schoolname, classname, keyWord, pageMachine.getPageIndex(), pageMachine.getPageContentCount());

            }
        }).
                flatMap(new Func1<BaseResponse<StudentResponse>, Observable<List<Student>>>() {
                    @Override
                    public Observable<List<Student>> call(BaseResponse<StudentResponse> listBaseResponse) {
                        if (!listBaseResponse.isSuccess()) {
                            return Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                        }
                        if (null == listBaseResponse.getData()) {
                            return Observable.error(new EmptyStudentsInClassException());
                        }
                        pageMachine.setTotalPage(listBaseResponse.getPage().getPageCount());
                        return Observable.just(Student.fromServerResponse(listBaseResponse.getData(), mServerDataConverter));

                    }
                });
    }

    @Override
    public Observable<List<Student>> maualSerchStudents(final String eventId, final String schoolname, final String classname, final String keyWord, final PageMachine pageMachine) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<StudentResponse>>>() {

            @Override
            public Observable<BaseResponse<StudentResponse>> call(User user) {
                return mScheduleApi.serchStudent(user.getUserId(), eventId, schoolname, classname, keyWord, pageMachine.getPageIndex(), pageMachine.getPageContentCount());

            }
        }).
                flatMap(new Func1<BaseResponse<StudentResponse>, Observable<List<Student>>>() {
                    @Override
                    public Observable<List<Student>> call(BaseResponse<StudentResponse> listBaseResponse) {
                        if (!listBaseResponse.isSuccess()) {
                            return Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                        }
                        if (null == listBaseResponse.getData()) {
                            return Observable.error(new EmptyMaualStudentsInClassException());
                        }
                        pageMachine.setTotalPage(listBaseResponse.getPage().getPageCount());
                        return Observable.just(Student.fromServerResponse(listBaseResponse.getData(), mServerDataConverter));

                    }
                });
    }


    @Override
    public Observable<List<Student>> loadStudentList(final String eventId, final String groupId) {

        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<List<StudentCheckResponse>>>>() {

            @Override
            public Observable<BaseResponse<List<StudentCheckResponse>>> call(User user) {
                return mScheduleApi.loadStudentList(user.getUserId(), eventId, groupId);

            }
        }).
                flatMap(new Func1<BaseResponse<List<StudentCheckResponse>>, Observable<List<Student>>>() {
                    @Override
                    public Observable<List<Student>> call(BaseResponse<List<StudentCheckResponse>> listBaseResponse) {
                        if (!listBaseResponse.isSuccess()) {
                            return Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                        }
                        if (!listBaseResponse.isSuccess()) {
                            return Observable.error(new Throwable(listBaseResponse.getMsg()));
                        }
                        return Observable.just(Student.checkFromServerResponse(listBaseResponse.getData(), mServerDataConverter));

                    }
                });
    }


    @Override
    public Observable<List<ClassGroup>> loadClassList(final String eventId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<List<ClassResponse>>>>() {

            @Override
            public Observable<BaseResponse<List<ClassResponse>>> call(User user) {
                return mScheduleApi.loadClassList(user.getUserId(), eventId);
            }
        }).flatMap(new Func1<BaseResponse<List<ClassResponse>>, Observable<List<ClassGroup>>>() {
            @Override
            public Observable<List<ClassGroup>> call(BaseResponse<List<ClassResponse>> listBaseResponse) {
                if (!listBaseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                }
                if (null == listBaseResponse.getData()) {
                    return Observable.error(new ClassListEmptyException());
                }
                return Observable.just(ClassGroup.fromServerResponse(listBaseResponse.getData(), mServerDataConverter));

            }
        });

    }

    @Override
    public Observable addMerber(final String eventId, final String groupId, final String studentsIds) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {

            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.addMerber(user.getUserId(), eventId, groupId, studentsIds);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }

        });
    }

    @Override
    public Observable<String> createClass(final String eventId, final String schoolname, final String classname) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {

            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.createClass(user.getUserId(), eventId, schoolname, classname);
            }
        }).flatMap(new Func1<BaseResponse, Observable<String>>() {
            @Override
            public Observable<String> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.just(baseResponse.getMsg());
            }

        });
    }

    @Override
    public Observable deleteEvent(final String eventId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {

            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.deletEvent(user.getUserId(), eventId);
            }
        }).flatMap(new Func1<BaseResponse, Observable<String>>() {
            @Override
            public Observable<String> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.just(baseResponse.getMsg());
            }

        });
    }

    @Override
    public Observable loadNoticeList(final String eventId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<List<EventNoticeEntityResponse>>>>() {

            @Override
            public Observable<BaseResponse<List<EventNoticeEntityResponse>>> call(User user) {
                return mScheduleApi.loadNoticeList(user.getUserId(), eventId);
            }
        }).flatMap(new Func1<BaseResponse<List<EventNoticeEntityResponse>>, Observable<List<EventNoticeEntity>>>() {
            @Override
            public Observable<List<EventNoticeEntity>> call(BaseResponse<List<EventNoticeEntityResponse>> listBaseResponse) {
                if (!listBaseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(listBaseResponse.getMsg()));
                }
                if (!listBaseResponse.isSuccess()) {
                    return Observable.error(new Throwable(listBaseResponse.getMsg()));
                }
                return Observable.just(EventNoticeEntity.fromServerResponse(listBaseResponse.getData(), mServerDataConverter));

            }
        });

    }

    @Override
    public Observable addNotice(final String eventId, final String content) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {

            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.addNotice(user.getUserId(), eventId, content);
            }
        }).flatMap(new Func1<BaseResponse, Observable<String>>() {
            @Override
            public Observable<String> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.just(baseResponse.getMsg());
            }

        });
    }

    @Override
    public Observable<RockSingleResByUuidResponse> loadRockStudentListData(final String uuid, final String groupId, final PageMachine pageMachine) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<RockSingleResByUuidResponse>>>() {
            @Override
            public Observable<BaseResponse<RockSingleResByUuidResponse>> call(User user) {
                return mScheduleApi.findByAttencePageList(user.getUserId(), uuid, groupId, 1, pageMachine.getPageContentCount());
            }
        }).flatMap(new Func1<BaseResponse<RockSingleResByUuidResponse>, Observable<RockSingleResByUuidResponse>>() {
            @Override
            public Observable<RockSingleResByUuidResponse> call(BaseResponse<RockSingleResByUuidResponse> rockResultByUuidResponseBaseResponse) {
                if (!rockResultByUuidResponseBaseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(rockResultByUuidResponseBaseResponse.getMsg()));
                }
                pageMachine.setTotalPage(rockResultByUuidResponseBaseResponse.getPage().getPageCount());
                RockSingleResByUuidResponse rockResultByUuidResponse = rockResultByUuidResponseBaseResponse.getData();
                return Observable.just(rockResultByUuidResponse);
            }
        });
    }


    // 更新半径信息
    @Override
    public Observable updateAttenceRadiu(final String accountId, final String eventId, final String attenceRadiu) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.updateAttenceRadiu(user.getUserId(), accountId, eventId, attenceRadiu);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

    // 点名
    @Override
    public Observable startRollCall(final Event event, final String uuid, final Location location) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.insertAttenceByowner(user.getUserId(), event.createdUser.getUserId(), event.scheduleId, event.id, location.getLonString(), location.getLatString(), uuid);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

    //  签到
    @Override
    public Observable signIn(final Event event, final Location location) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.insertEventSignInfo(user.getUserId(), event.createdUser.getUserId(), event.scheduleId, event.id, location.getLonString(), location.getLatString());
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

    //  事件成员插入
    @Override
    public Observable memberInsertEvent(final String accountId, final String eventId, final String eventTimeId, final String longitude, final String latitude, final String uuid) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.memberInsertEvent(user.getUserId(), eventId, eventTimeId, longitude, latitude, uuid);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ThinkcooLog.e(TAG, throwable.getMessage(), throwable);
            }
        });
    }//  按uuid分页排序查询单次点名

    @Override
    public Observable<List<RockSingleResByUuidResponse.ListBean>> loadResultData(final String uuid, final String groupId, final PageMachine pageMachine) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse<RockSingleResByUuidResponse>>>() {
            @Override
            public Observable<BaseResponse<RockSingleResByUuidResponse>> call(User user) {
                return mScheduleApi.findByAttencePageList(user.getUserId(), uuid, groupId, pageMachine.getPageIndex(), pageMachine.getPageContentCount());
            }
        }).flatMap(new Func1<BaseResponse<RockSingleResByUuidResponse>, Observable<List<RockSingleResByUuidResponse.ListBean>>>() {
            @Override
            public Observable<List<RockSingleResByUuidResponse.ListBean>> call(BaseResponse<RockSingleResByUuidResponse> rockResultByUuidResponseBaseResponse) {
                if (!rockResultByUuidResponseBaseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(rockResultByUuidResponseBaseResponse.getMsg()));
                }
                pageMachine.setTotalPage(rockResultByUuidResponseBaseResponse.getPage().getPageCount());
                RockSingleResByUuidResponse rockResultByUuidResponse = rockResultByUuidResponseBaseResponse.getData();
                return Observable.just(rockResultByUuidResponse.getList());
            }
        });
    }

    @Override
    public Observable modifyRockCallResult(final String eventId, final String eventRosterId) {
        return mLoginUserCache.get().flatMap(new Func1<User, Observable<BaseResponse>>() {
            @Override
            public Observable<BaseResponse> call(User user) {
                return mScheduleApi.modfiyRockCall(user.getUserId(), eventId, eventRosterId);
            }
        }).flatMap(new Func1<BaseResponse, Observable<?>>() {
            @Override
            public Observable<?> call(BaseResponse baseResponse) {
                if (!baseResponse.isSuccess()) {
                    return Observable.error(new ServerResponseException(baseResponse.getMsg()));
                }
                return Observable.empty();
            }
        });
    }

}
