package com.thinkcoo.mobile.model.repository;

import com.thinkcoo.mobile.model.entity.UserBasicInfo;
import com.thinkcoo.mobile.model.entity.UserHarvest;
import com.thinkcoo.mobile.model.entity.UserHarvestDetail;
import com.thinkcoo.mobile.model.entity.UserHobby;
import com.thinkcoo.mobile.model.entity.UserSkill;
import com.thinkcoo.mobile.model.entity.UserWorkExperienceEntity;

import java.util.List;

import rx.Observable;

/**
 * 用户相关的数据操作接口
 */
public interface UserRepository {



    Observable getUserMainInfo();
    Observable<UserBasicInfo> getUserBasicInfo(boolean isUpdateNow);
    Observable changeChangeUserSignature(String signature);
    Observable changeUserSex(String sex);
    Observable changeUserName(String name);
    Observable<List<UserHarvest>> loadUserHarvestList(String pageIndex,String pageSize,boolean isUpdateNow);
    Observable  addUserHarvest(UserHarvest userHarvest);
    Observable updateUserHarvest(UserHarvest userHarvest);
    Observable <UserHarvestDetail> loadUserHarvestDetail(UserHarvest userHarvest);
    Observable  deleteUserHarvest(String harvestId);
    Observable deleteUserSkill(String skillId);
    Observable addUserSkill(String skill);
    Observable <List<UserSkill>> getUserSkill();
    Observable <List<UserHobby>> getUserHobby();
    Observable deleteUserHobby(String hobbyId);
    Observable addUserHobby(String hobby);
    Observable SetUserBasicInfo(UserBasicInfo userBasicInfo);
    Observable<List<UserWorkExperienceEntity>> loadWorkExperienceList(String workId);
    Observable deleteWorkExperience(String workId);
    Observable addWorkExperience(String workId, String content, String time);
    Observable updateWorkExperience(UserWorkExperienceEntity response);
    Observable<Boolean> uploadPhoto(String s);
    Observable feedBack(String feedbackContent, String phone, String email);

    Observable getInviteFriend(String invitedUserId);
}
