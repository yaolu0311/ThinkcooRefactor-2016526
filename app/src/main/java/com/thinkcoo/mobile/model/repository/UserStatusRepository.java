package com.thinkcoo.mobile.model.repository;

import com.thinkcoo.mobile.model.entity.UserStatus;
import com.thinkcoo.mobile.model.entity.UserStatusDetail;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/5/31.
 */
public interface UserStatusRepository {

    Observable<List<UserStatus>> loadUserStatusList(boolean isUpdateNow);
    Observable deleteUserStatus(UserStatus userStatus);
    Observable toggleUserStatusOpenStatus(String statusId, String isDisplay);
    Observable addUserStatus(UserStatus userStatus);
    Observable updateUserStatus(UserStatus userStatus);
    Observable<UserStatusDetail> loadUserEducationStatusDetail(UserStatus userStatus);
    Observable<UserStatusDetail> loadUserTrainStatusDetail(UserStatus userStatus);
    Observable<UserStatusDetail> loadUserFullTimeWorkStatusDetail(UserStatus userStatus);
    Observable<UserStatusDetail> loadUserPartTimeWorkStatusDetail(UserStatus userStatus);
    Observable<UserStatusDetail> loadUserStatusDetail(UserStatus userStatus);
}


