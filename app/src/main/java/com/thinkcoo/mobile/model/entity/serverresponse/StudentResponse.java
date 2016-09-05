package com.thinkcoo.mobile.model.entity.serverresponse;

import java.util.List;

/**
 * Created by Wyy on 2016/5/19.
 */
public class StudentResponse  {


    /**
     * className : 班组2
     * eventRosterId : null
     * headPortrait : personal/headportrait/20160413063811870.JPG
     * studentNo : 6644
     * realName : zbc
     * accountId : 2013
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String className;
        private String eventRosterId;
        private String headPortrait;
        private String studentNo;
        private String realName;
        private String accountId;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

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

        public String getStudentNo() {
            return studentNo;
        }

        public void setStudentNo(String studentNo) {
            this.studentNo = studentNo;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }
    }
}
