package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.nullentities.NullUserSkill;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.entity.serverresponse.UserSkillResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class UserSkill implements Nullable {


    public static final UserSkill NULL_USER_SKILL = new NullUserSkill();

    @Override
    public boolean isEmpty() {
        return false;
    }

        /**
         * 技能 的编号
         */
        private String skillId;

        /**
         * 技能的名称
         */
        private String skill;

        public String getSkillId() {
            return skillId;
        }

        public void setSkillId(String skillId) {
            this.skillId = skillId;
        }

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }


    public static List<UserSkill> fromServerResponse(List<UserSkillResponse> data) {
        ArrayList<UserSkill> userSkillList = new ArrayList<>();
        if (null==userSkillList) {
            return userSkillList;
        }
        for (UserSkillResponse userskill :data) {
          UserSkill userSkill=  createNewUserSkill(userskill);
          userSkillList.add(userSkill);
        }
              return userSkillList;
    }

     private static UserSkill createNewUserSkill(UserSkillResponse userskill) {
        UserSkill userSkills = new UserSkill();
        userSkills.setSkill(userskill.getSkill());
        userSkills.setSkillId(userskill.getSkillId());

        return userSkills;
    }


}
