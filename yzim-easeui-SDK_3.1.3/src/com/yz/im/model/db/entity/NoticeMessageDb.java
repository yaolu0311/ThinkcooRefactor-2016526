package com.yz.im.model.db.entity;

import com.yzkj.android.orm.annotation.Column;
import com.yzkj.android.orm.annotation.Table;

/**
 * Created by cys on 2016/8/10
 */
@Table(name = "new_friends_msgs_v2")
public class NoticeMessageDb {

    @Column(name = "id", isId = true)
    private String id;
    @Column(name = "username")  //环信用户id
    private String username;
    @Column(name = "groupid")
    private String groupid;
    @Column(name = "groupname")
    private String groupname;
    @Column(name = "time")
    private String time;
    @Column(name = "reason")
    private String reason;
    @Column(name = "status")
    private String status;
    @Column(name = "isInviteFromMe")
    private String isInviteFromMe;

    public String getId() {
        return id;
    }

    public NoticeMessageDb setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public NoticeMessageDb setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getGroupid() {
        return groupid;
    }

    public NoticeMessageDb setGroupid(String groupid) {
        this.groupid = groupid;
        return this;
    }

    public String getGroupname() {
        return groupname;
    }

    public NoticeMessageDb setGroupname(String groupname) {
        this.groupname = groupname;
        return this;
    }

    public String getTime() {
        return time;
    }

    public NoticeMessageDb setTime(String time) {
        this.time = time;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public NoticeMessageDb setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public NoticeMessageDb setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getIsInviteFromMe() {
        return isInviteFromMe;
    }

    public NoticeMessageDb setIsInviteFromMe(String isInviteFromMe) {
        this.isInviteFromMe = isInviteFromMe;
        return this;
    }
}
