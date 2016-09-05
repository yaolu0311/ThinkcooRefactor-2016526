package com.thinkcoo.mobile.model.entity.serverresponse;

import com.thinkcoo.mobile.model.entity.TrainDetail;
import com.thinkcoo.mobile.model.entity.UserStatusDetail;

/**
 * Created by Leevin
 * CreateTime: 2016/5/27  14:36
 */
public class UserTrainStatusDetails {

    private String memberId;
    private String sId;
    private String trainContent;
    private String position4Name;
    private String statusTime;
    private String name;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public String getTrainContent() {
        return trainContent;
    }

    public void setTrainContent(String trainContent) {
        this.trainContent = trainContent;
    }

    public String getPosition4Name() {
        return position4Name;
    }

    public void setPosition4Name(String position4Name) {
        this.position4Name = position4Name;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static UserStatusDetail fromServerResponse(UserTrainStatusDetails data) {
        TrainDetail trainDetail = new TrainDetail();
        if (null == trainDetail) {
            return trainDetail;
        }
        trainDetail.setId(data.getSId());
        trainDetail.setClassNumber(data.getMemberId());
        trainDetail.setStudentId(data.getPosition4Name());
        return trainDetail;
    }
}
