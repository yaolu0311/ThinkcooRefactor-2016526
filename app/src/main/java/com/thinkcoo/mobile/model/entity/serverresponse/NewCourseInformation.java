package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * Created by Administrator on 2016/6/23.
 *
 *  登录后软课表加载的最新的课程信息
 */
public class NewCourseInformation {

//           "easemobGroupId": "210834782894948776",
//            "courseNoFirst": "3",
//            "courseNoLast": "3",
//            "school": "西安工业大学",
//            "circleId": "387",
//            "color": "#f2ac8c",
//            "groupName": "1601",
//            "createType": "2",
//            "courseName": "大学语文",
//            "courseType": "0",
//            "teacherName": "马璐",
//            "oldCourseId": null,
//            "courseId": "622",
//            "startSchoolTime": "2016-06-23 00:00:00.0",
//            "classRoom": "101",
//            "weeks": "3",
//            "weekNo": "1,3,5,7,9,11,13,15,17,19,21,23",
//            "courseTimeId": "667",
//            "timeStart": "11:30",
//            "timeEnd": "12:00",
//            "isSystem": "0"
    private String easemobGroupId; // 环信圈子的ID
    private String courseNoFirst; // 节数的开始
    private String courseNoLast; // 节数结束
    private String school;    // 学校
    private String circleId;   // 圈子Id
    private String color; // 课程的颜色
    private String groupName;  // 班组
    private String createType;// 常见的类型（0系统默认创建;1学生(学生自己创建或者学生非关联老师);2老师(老师自己创建或者学生关联老师)）
    private String courseName; // 课程的名称
    private String courseType; //课程的类型（0是自己创建的课 1是添加非关联老师的课2是复制其他学习者的课3是花名册关联的课程）
    private String teacherName;// 老师的名称
    private String oldCourseId;  // 有值表示是学生添加老师课，因为学生添加任何人课程时createType变成1
    private String startSchoolTime; //开学的日期
    private String classRoom; // 教室
    private String weeks;  // 周
    private String weekNo; //周数
    private String courseTimeId;// 时段的ID
    private String timeStart; // 上课开始时间
    private String timeEnd;// 上课结束时间
    private String isSystem; // 0 正常的 1 系统推荐的

    public String getEasemobGroupId() {
        return easemobGroupId;
    }

    public void setEasemobGroupId(String easemobGroupId) {
        this.easemobGroupId = easemobGroupId;
    }

    public String getCourseNoFirst() {
        return courseNoFirst;
    }

    public void setCourseNoFirst(String courseNoFirst) {
        this.courseNoFirst = courseNoFirst;
    }

    public String getCourseNoLast() {
        return courseNoLast;
    }

    public void setCourseNoLast(String courseNoLast) {
        this.courseNoLast = courseNoLast;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
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

    public String getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(String weekNo) {
        this.weekNo = weekNo;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getCourseTimeId() {
        return courseTimeId;
    }

    public void setCourseTimeId(String courseTimeId) {
        this.courseTimeId = courseTimeId;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getStartSchoolTime() {
        return startSchoolTime;
    }

    public void setStartSchoolTime(String startSchoolTime) {
        this.startSchoolTime = startSchoolTime;
    }

    public String getOldCourseId() {
        return oldCourseId;
    }

    public void setOldCourseId(String oldCourseId) {
        this.oldCourseId = oldCourseId;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewCourseInformation)) return false;

        NewCourseInformation that = (NewCourseInformation) o;

        if (easemobGroupId != null ? !easemobGroupId.equals(that.easemobGroupId) : that.easemobGroupId != null)
            return false;
        if (courseNoFirst != null ? !courseNoFirst.equals(that.courseNoFirst) : that.courseNoFirst != null)
            return false;
        if (courseNoLast != null ? !courseNoLast.equals(that.courseNoLast) : that.courseNoLast != null)
            return false;
        if (school != null ? !school.equals(that.school) : that.school != null) return false;
        if (circleId != null ? !circleId.equals(that.circleId) : that.circleId != null)
            return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (groupName != null ? !groupName.equals(that.groupName) : that.groupName != null)
            return false;
        if (createType != null ? !createType.equals(that.createType) : that.createType != null)
            return false;
        if (courseName != null ? !courseName.equals(that.courseName) : that.courseName != null)
            return false;
        if (courseType != null ? !courseType.equals(that.courseType) : that.courseType != null)
            return false;
        if (teacherName != null ? !teacherName.equals(that.teacherName) : that.teacherName != null)
            return false;
        if (oldCourseId != null ? !oldCourseId.equals(that.oldCourseId) : that.oldCourseId != null)
            return false;
        if (startSchoolTime != null ? !startSchoolTime.equals(that.startSchoolTime) : that.startSchoolTime != null)
            return false;
        if (classRoom != null ? !classRoom.equals(that.classRoom) : that.classRoom != null)
            return false;
        if (weeks != null ? !weeks.equals(that.weeks) : that.weeks != null) return false;
        if (weekNo != null ? !weekNo.equals(that.weekNo) : that.weekNo != null) return false;
        if (courseTimeId != null ? !courseTimeId.equals(that.courseTimeId) : that.courseTimeId != null)
            return false;
        if (timeStart != null ? !timeStart.equals(that.timeStart) : that.timeStart != null)
            return false;
        if (timeEnd != null ? !timeEnd.equals(that.timeEnd) : that.timeEnd != null) return false;
        return isSystem != null ? isSystem.equals(that.isSystem) : that.isSystem == null;

    }

    @Override
    public int hashCode() {
        int result = easemobGroupId != null ? easemobGroupId.hashCode() : 0;
        result = 31 * result + (courseNoFirst != null ? courseNoFirst.hashCode() : 0);
        result = 31 * result + (courseNoLast != null ? courseNoLast.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        result = 31 * result + (circleId != null ? circleId.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (createType != null ? createType.hashCode() : 0);
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (courseType != null ? courseType.hashCode() : 0);
        result = 31 * result + (teacherName != null ? teacherName.hashCode() : 0);
        result = 31 * result + (oldCourseId != null ? oldCourseId.hashCode() : 0);
        result = 31 * result + (startSchoolTime != null ? startSchoolTime.hashCode() : 0);
        result = 31 * result + (classRoom != null ? classRoom.hashCode() : 0);
        result = 31 * result + (weeks != null ? weeks.hashCode() : 0);
        result = 31 * result + (weekNo != null ? weekNo.hashCode() : 0);
        result = 31 * result + (courseTimeId != null ? courseTimeId.hashCode() : 0);
        result = 31 * result + (timeStart != null ? timeStart.hashCode() : 0);
        result = 31 * result + (timeEnd != null ? timeEnd.hashCode() : 0);
        result = 31 * result + (isSystem != null ? isSystem.hashCode() : 0);
        return result;
    }
}
