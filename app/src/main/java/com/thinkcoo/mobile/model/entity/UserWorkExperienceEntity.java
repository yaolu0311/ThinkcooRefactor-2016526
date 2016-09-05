package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.annotation.FieldEmptyCheck;
import com.thinkcoo.mobile.annotation.FieldPosition;
import com.thinkcoo.mobile.model.entity.serverresponse.UserWorkExperienceResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/17.
 */
public class UserWorkExperienceEntity implements Parcelable {

    public static List<UserWorkExperienceEntity> fromServerResponse(List<UserWorkExperienceResponse> responsesList, ServerDataConverter serverDataConverter) {
        List<UserWorkExperienceEntity> formatList = new ArrayList<>();
        if(null == responsesList){
            return formatList;
        }
        for (UserWorkExperienceResponse response: responsesList) {
            UserWorkExperienceEntity entity =new UserWorkExperienceEntity();
            entity.setId(response.getEId());
            entity.setTime(serverDataConverter.stringReplace(response.getTime()," 00:00:00.0",""));
            entity.setContent(response.getContent().toString().trim());
            formatList.add(entity);
        }
        return formatList;
    }

    private String id;
    @FieldEmptyCheck(R.string.time_is_empty)
    @FieldPosition(1)
    private String time;
    @FieldEmptyCheck(value = R.string.personal_neirong_no_kong)
    @FieldPosition(2)
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.time);
        dest.writeString(this.content);
    }

    public UserWorkExperienceEntity() {
    }

    protected UserWorkExperienceEntity(Parcel in) {
        this.id = in.readString();
        this.time = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<UserWorkExperienceEntity> CREATOR = new Parcelable.Creator<UserWorkExperienceEntity>() {
        @Override
        public UserWorkExperienceEntity createFromParcel(Parcel source) {
            return new UserWorkExperienceEntity(source);
        }

        @Override
        public UserWorkExperienceEntity[] newArray(int size) {
            return new UserWorkExperienceEntity[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserWorkExperienceEntity entity = (UserWorkExperienceEntity) o;

        if (time != null ? !time.equals(entity.time) : entity.time != null) return false;
        return content != null ? content.equals(entity.content) : entity.content == null;

    }

    @Override
    public String toString() {
        return "UserWorkExperienceEntity{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = time != null ? time.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
