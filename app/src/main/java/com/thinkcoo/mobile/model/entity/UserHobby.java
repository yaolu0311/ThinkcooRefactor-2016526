package com.thinkcoo.mobile.model.entity;

import com.thinkcoo.mobile.model.entity.nullentities.NullUserHobby;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.entity.serverresponse.UserHobbyResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert.yao on 2016/5/30.
 */
public class UserHobby implements Nullable {

    public static final UserHobby NULL_USER_HOBBY = new NullUserHobby();

    @Override
    public boolean isEmpty() {
        return false;
    }

    private String hobbyId;
    private String hobby;

    public String getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(String hobbyId) {
        this.hobbyId = hobbyId;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public static List<UserHobby> fromServerResponse(List<UserHobbyResponse> data) {
        ArrayList<UserHobby> userHobbies = new ArrayList<>();
        if (null == userHobbies) {
            return userHobbies;
        }
        for (UserHobbyResponse userhobby : data) {
            UserHobby UserHobby = creatNewUserHobby(userhobby);
            userHobbies.add(UserHobby);
        }
        return userHobbies;
    }

    private static UserHobby creatNewUserHobby(UserHobbyResponse userhobby) {
        UserHobby hobby = new UserHobby();
        hobby.setHobby(userhobby.getHobby());
        hobby.setHobbyId(userhobby.getHobbyId());
        return hobby;
    };


}
