package com.thinkcoo.mobile.model.entity.serverresponse;

import java.util.List;

/**
 * author ：ml on 2016/8/22
 */
public class RockSingleResByUuidResponse {


    /**
     * list : [{"eventRosterId":"点名Id","headPortrait":"头像","studentName":"学生姓名","studentNo":"学号","groupName":"班组名称","eventPlace":"事件地点","isRegister":"是否登记","checkDate":"签到时间","status":"状态 0 未到 1已到"}]
     * totalCnt : 总人数
     * absenceCnt : 未到人数
     * normalCnt : 已到人数
     * registerCnt : 未注册（暂无）
     * beginDate : 签到时间
     */

    private String totalCnt;
    private String absenceCnt;
    private String normalCnt;
    private String registerCnt;
    private String beginDate;
    /**
     * eventRosterId : 点名Id
     * headPortrait : 头像
     * studentName : 学生姓名
     * studentNo : 学号
     * groupName : 班组名称
     * eventPlace : 事件地点
     * isRegister : 是否登记
     * checkDate : 签到时间
     * status : 状态 0 未到 1已到
     */

    private List<ListBean> list;

    public String getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(String totalCnt) {
        this.totalCnt = totalCnt;
    }

    public String getAbsenceCnt() {
        return absenceCnt;
    }

    public void setAbsenceCnt(String absenceCnt) {
        this.absenceCnt = absenceCnt;
    }

    public String getNormalCnt() {
        return normalCnt;
    }

    public void setNormalCnt(String normalCnt) {
        this.normalCnt = normalCnt;
    }

    public String getRegisterCnt() {
        return registerCnt;
    }

    public void setRegisterCnt(String registerCnt) {
        this.registerCnt = registerCnt;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String eventRosterId;
        private String headPortrait;
        private String studentName;
        private String studentNo;
        private String groupName;
        private String eventPlace;
        private String isRegister;
        private String checkDate;
        private String status;

        public String getEventRosterId() {
            return eventRosterId;
        }

        public void setEventRosterId(String eventRosterId) {
            this.eventRosterId = eventRosterId;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getStudentNo() {
            return studentNo;
        }

        public void setStudentNo(String studentNo) {
            this.studentNo = studentNo;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getEventPlace() {
            return eventPlace;
        }

        public void setEventPlace(String eventPlace) {
            this.eventPlace = eventPlace;
        }

        public String getIsRegister() {
            return isRegister;
        }

        public void setIsRegister(String isRegister) {
            this.isRegister = isRegister;
        }

        public String getCheckDate() {
            return checkDate;
        }

        public void setCheckDate(String checkDate) {
            this.checkDate = checkDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
