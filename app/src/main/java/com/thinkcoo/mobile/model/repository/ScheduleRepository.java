package com.thinkcoo.mobile.model.repository;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.thinkcoo.mobile.model.entity.ClassGroup;
import com.thinkcoo.mobile.model.entity.Location;
import com.thinkcoo.mobile.model.entity.Schedule;
import com.thinkcoo.mobile.model.entity.Student;
import com.thinkcoo.mobile.model.entity.serverresponse.RockSingleResByUuidResponse;
import com.thinkcoo.mobile.presentation.views.PageMachine;
import com.thinkcoo.mobile.presentation.views.component.mydayview.Event;

import java.util.List;

import rx.Observable;

/**
 * Created by Leevin
 * CreateTime: 2016/6/28  14:50
 */
public interface ScheduleRepository {

    Observable<BaseResponse> AddSchedule(Schedule schedule);

    Observable removeClass(String groupId);

    Observable confimStudents(String eventId, String groupId, String accoundIds);

    Observable<List<Event>> loadEventList(String startDate, String endDate);

    Observable<List<Student>> loadStudentListByClassId(String classid);

    Observable<List<Student>> serchStudents(String eventId, String schoolname, String classname, String keyWord, PageMachine pageMachine);
    Observable<List<Student>> maualSerchStudents(String eventId, String schoolname, String classname, String keyWord, PageMachine pageMachine);

    Observable<List<Student>> loadStudentList(String eventId, String groupId);

    Observable<List<ClassGroup>> loadClassList(String eventId);

    Observable<Schedule> loadSchedule(String scheduleId);

    Observable updateSchedule(Schedule schedule);

    Observable addMerber(String eventId, String groupId, String accountIds);

    Observable createClass(String eventId, String schoolname, String classname);

    Observable deleteEvent(String eventId);
    // 更新半径信息
    Observable updateAttenceRadiu(String accountId,String eventId, String attenceRadiu);
    // 点名
    Observable startRollCall(Event event, String uuid, Location location);
    // 学生签到
    Observable signIn(Event event, Location location);
    // 事件成员点名插入
    Observable memberInsertEvent(String accountId, String eventId, String eventTimeId, String longitude, String latitude,String uuid);
    // 通过按uuid分页排序查询单次点名
    Observable loadResultData(String uuid,String groupId,PageMachine pageMachine);
    // 修改点名记录
    Observable modifyRockCallResult(String eventId, String eventRosterId);

    Observable loadNoticeList(String eventId);

    Observable addNotice(String eventId, String content);

    // 通过按uuid分页排序查询单次点名
    Observable<RockSingleResByUuidResponse> loadRockStudentListData(String uuid, String groupId, PageMachine pageMachine);



}
