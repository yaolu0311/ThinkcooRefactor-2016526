package com.thinkcoo.mobile.model.entity.serverresponse;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 */
public class CourseDetails {
    /**
     * courseTimeList : [{"userId":20022,"classRoom":"1101","weeks":"1","weekNo":"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24","courseTimeId":4325,"timeStart":"08:30","timeEnd":"09:00","courseNoLast":"5","courseNoFirst":"5","courseId":4224,"weekNoView":"全部"}]
     * color : #f895a4      颜色
     * school : 西北政法大学    学校的名称
     * groupName : 0627         班组的名称
     * statusId : 221           学校的ID
     * oldCourseId : null          //有值表示是学生添加老师课，因为学生添加任何人课程时createType变成1
     * courseId : 4224             课程ID
     * startSchoolTime : 2016-06-27    开课的日期
     * courseName : Android             课程名称
     * courseType : 0              课程的类型
     * teacherName : 马璐          授课者老师的名称
     */

    /**
     * userId : 20022
     * classRoom : 1101   教室
     * weeks : 1         周
     * weekNo : 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24   编号（单双周 ）
     * courseTimeId : 4325    时段id
     * timeStart : 08:30     上课开始时间
     * timeEnd : 09:00       下课的结束时间
     * courseNoLast : 5          节数的开始
     * courseNoFirst : 5           节数的结束
     * courseId : 4224          课程的ID
     * weekNoView : 全部         显示如 单周,双周
     */

    private String color;
    private String school;
    private String groupName;
    private String statusId;
    private String oldCourseId;  //有值表示是学生添加老师课，因为学生添加任何人课程时createType变成1
    private String courseId;
    private String startSchoolTime;
    private String courseName;
    private String courseType;
    private String teacherName;

    public String getOldCourseId() {
        return oldCourseId;
    }

    public void setOldCourseId(String oldCourseId) {
        this.oldCourseId = oldCourseId;
    }




    private List<CourseTimeListBean> courseTimeList;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }



    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStartSchoolTime() {
        return startSchoolTime;
    }

    public void setStartSchoolTime(String startSchoolTime) {
        this.startSchoolTime = startSchoolTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<CourseTimeListBean> getCourseTimeList() {
        return courseTimeList;
    }

    public void setCourseTimeList(List<CourseTimeListBean> courseTimeList) {
        this.courseTimeList = courseTimeList;
    }

    public static class CourseTimeListBean {
        private String userId;
        private String classRoom;
        private String weeks;
        private String weekNo;
        private String courseTimeId;
        private String timeStart;
        private String timeEnd;
        private String courseNoLast;
        private String courseNoFirst;
        private String courseId;
        private String weekNoView;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getClassRoom() {
            return classRoom;
        }

        public void setClassRoom(String classRoom) {
            this.classRoom = classRoom;
        }

        public String getWeeks() {
            return weeks;
        }

        public void setWeeks(String weeks) {
            this.weeks = weeks;
        }

        public String getWeekNo() {
            return weekNo;
        }

        public void setWeekNo(String weekNo) {
            this.weekNo = weekNo;
        }

        public String getCourseTimeId() {
            return courseTimeId;
        }

        public void setCourseTimeId(String courseTimeId) {
            this.courseTimeId = courseTimeId;
        }

        public String getTimeStart() {
            return timeStart;
        }

        public void setTimeStart(String timeStart) {
            this.timeStart = timeStart;
        }

        public String getTimeEnd() {
            return timeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
        }

        public String getCourseNoLast() {
            return courseNoLast;
        }

        public void setCourseNoLast(String courseNoLast) {
            this.courseNoLast = courseNoLast;
        }

        public String getCourseNoFirst() {
            return courseNoFirst;
        }

        public void setCourseNoFirst(String courseNoFirst) {
            this.courseNoFirst = courseNoFirst;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getWeekNoView() {
            return weekNoView;
        }

        public void setWeekNoView(String weekNoView) {
            this.weekNoView = weekNoView;
        }
    }





}



