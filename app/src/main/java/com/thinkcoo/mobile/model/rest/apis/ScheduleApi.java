package com.thinkcoo.mobile.model.rest.apis;

import com.example.administrator.publicmodule.entity.BaseResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.ClassResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.EventNoticeEntityResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.EventTimeResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.RockSingleResByUuidResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.ScheduleResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.StudentCheckResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.StudentResponse;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Leevin
 * CreateTime: 2016/6/29  9:26
 */
public interface ScheduleApi {
    

    @POST("insertEventInfo.json")
    Observable<BaseResponse>  addSchedule(@Query("jsonString") String scheduleJson);
    @POST("insertEventInfo.json")
    Observable<BaseResponse>  updateSchedule(@Query("jsonString") String scheduleJson);
    @POST("queryEventInfoById.json")
    Observable<BaseResponse<ScheduleResponse>>  loadSchedule(@Query("userId") String userId,@Query("eventId") String eventId);
    @POST("eventInfoListByWeek.json")
    Observable<BaseResponse<List<EventTimeResponse>>> loadTimeEvents(@Query("userId") String userId, @Query("startDate") String startDate, @Query("endDate") String endDate);

    @POST("findByGroupList.json")
    Observable<BaseResponse<List<ClassResponse>>> loadClassList(@Query("userId") String userId, @Query("eventId") String eventId);
    @POST("findByMemberList.json")
    Observable<BaseResponse<List<StudentCheckResponse>>> loadStudentList(@Query("userId") String userId, @Query("eventId") String eventId, @Query("groupId") String groupId);
    @POST("upadteMemberList.json")
    Observable<BaseResponse> confimStudents(@Query("userId") String userId, @Query("eventId") String eventId, @Query("groupId") String groupId, @Query("accountIds") String accountIds);
    @POST("deleteGroupInfo.json")
    Observable<BaseResponse> removeClass(@Query("userId") String userId, @Query("groupId") String groupId);
    @POST("deleteEventInfo.json")
    Observable<BaseResponse> deletEvent(@Query("userId") String userId, @Query("eventId") String eventId);
    @POST("insertMemberByGroupInfo.json")
    Observable<BaseResponse> addMerber(@Query("userId") String userId,@Query("eventId") String eventId, @Query("groupId") String groupId, @Query("accountIds") String accountIds);
    @POST("insertGroupInfo.json")
    Observable<BaseResponse> createClass(@Query("userId") String userId, @Query("eventId") String eventId, @Query("unitName") String unitName, @Query("groupName") String groupName);
    @POST("findByMemberPageList.json")
    Observable<BaseResponse<StudentResponse>>  serchStudent(@Query("userId") String userId, @Query("eventId") String eventId, @Query("school") String school, @Query("classProfession") String classProfession, @Query("keyWord") String keyWord, @Query("pageNow") int pageNow, @Query("pageSize") int pageSize);
    @POST("findEventNoticeByeventIdList.json")
    Observable<BaseResponse<List<EventNoticeEntityResponse>>> loadNoticeList(@Query("accountId") String accountId, @Query("eventId") String eventId);
    @POST("insertEventNoticeInfo.json")
    Observable<BaseResponse> addNotice(@Query("accountId") String accountId, @Query("eventId") String eventId, @Query("noticeContent") String noticeContent);
    // 修改半径
    @POST("upadteAttenceRadiu.json")
    Observable<BaseResponse> updateAttenceRadiu(@Query("userId") String userId,@Query("accountId") String accountId,@Query("eventId") String eventId,@Query("attenceRadiu") String attenceRadiu);
    // 事件所有者点名
    @POST("insertAttenceByowner.json")
    Observable<BaseResponse> insertAttenceByowner(@Query("userId") String userId,@Query("accountId") String accountId,@Query("eventId") String eventId, @Query("eventTimeId") String eventTimeId,@Query("longitude") String longitude,@Query("latitude") String latitude,@Query("uuid") String uuid);
    // 学生签到
    @POST("insertEventSignInfo.json")
    Observable<BaseResponse> insertEventSignInfo(@Query("userId") String userId,@Query("accountId") String accountId,@Query("eventId") String eventId, @Query("eventTimeId") String eventTimeId,@Query("longitude") String longitude,@Query("latitude") String latitude);
    // 事件成员点名插入
    @POST("insertAttenceBymember.json")
    Observable<BaseResponse> memberInsertEvent(@Query("accountId") String accountId,@Query("eventId") String eventId, @Query("eventTimeId") String eventTimeId,@Query("longitude") String longitude,@Query("latitude") String latitude,@Query("uuid") String uuid);
    // 通过UUid 分页排序查询单次点名
    @POST("findByAttencePageList.json")
    Observable<BaseResponse<RockSingleResByUuidResponse>> findByAttencePageList(@Query("userId") String userId, @Query("uuid")String uuid, @Query("groupId") String groupId, @Query("pageNow")int pageNow, @Query("pageSize") int pageSize );
    //修改点名记录
    @POST("upadteAttenceInfo.json ")
    Observable<BaseResponse> modfiyRockCall(@Query("userId") String userId,@Query("accountId") String eventId,@Query("attenceId") String eventRosterId);
}
