package com.thinkcoo.mobile.model.entity.serverresponse;

import java.util.List;

/**
 * author ：ml on 2016/7/19
 */
public class ProViewResumeResponse {

/**
 * SpinfoTable spinfoBean ;//个人信息
 JobTable jobBean; //工作信息
 String skillStrs;//技能拼接用","
 favoriteStrs;//技能拼接用","
 */
    /**
     * workExperienceList : [{"responsibilities":"写代码","place":null,"statusCategory":"3","vitaeId":"1030","industryDirection":"计算机软件/系统集成","postOffice":"开发","worktimeBegin":"2016-07-19 00:00:00.0","worktimeEnd":null,"company":"影子","experience":"2016-06-19 啦咔咔","department":"1"}]
     * skillStrs : 你好,啦啦啦,你好,你好
     * educationList : [{"school":"西北政法大学","isNational":null,"specialty":"法律","evaluation":"硕士/研究生","statusCategory":"1","positionName":null,"vitaeId":"221","trainContent":null,"beginTime":"2016-05-12 00:00:00.0","endTime":null},{"school":"西安交通大学","isNational":null,"specialty":"数量经济学","evaluation":"硕士/研究生","statusCategory":"1","positionName":null,"vitaeId":"183","trainContent":null,"beginTime":"2016-05-05 00:00:00.0","endTime":null}]
     * jobBean : {"industryName":"IT支持及其它,计算机硬件/设备","money":"3000元以下","jobType":"兼职","job":"技术总监/经理,信息技术经理/主管,信息技术专员,技术支持/维护经理","industry":"1106,1103","place":"陕西省","keyword":null}
     * spinfoBean : {"birthDate":"2016-07-18","certificateNumber":"0808008","education":"硕士","currentAddress":"陕西省西安市新城区科技一路","workYears":"无","birthPlace":"陕西省西安市新城区","nationName":"汉族","politicalName":"中共党员","certificateTypeName":"其它证件","maritalStatusCode":"未婚","maritalStatusName":"未婚","liveStreet":"科技一路","birthStreet":null,"phone":"13484811129","accountId":"20022","sex":"男","email":"m@163.com","name":"马路"}
     * honorPraiseList : [{"hobby":null,"picPath":null,"picPathList":[],"honorId":"62","grantName":"定OTG","grantTime":"2016-06-30","ranking":"抨击","grantUnits":"您是","grantLevel":"定局","id":null}]
     * favoriteStrs : 啦啦啦,啊啊啊
     */

    private String skillStrs;
    /**
     * industryName : IT支持及其它,计算机硬件/设备
     * money : 3000元以下
     * jobType : 兼职
     * job : 技术总监/经理,信息技术经理/主管,信息技术专员,技术支持/维护经理
     * industry : 1106,1103
     * place : 陕西省
     * keyword : null
     */

    private JobBeanBean jobBean;
    /**
     * birthDate : 2016-07-18
     * certificateNumber : 0808008
     * education : 硕士
     * currentAddress : 陕西省西安市新城区科技一路
     * workYears : 无
     * birthPlace : 陕西省西安市新城区
     * nationName : 汉族
     * politicalName : 中共党员
     * certificateTypeName : 其它证件
     * maritalStatusCode : 未婚
     * maritalStatusName : 未婚
     * liveStreet : 科技一路
     * birthStreet : null
     * phone : 13484811129
     * accountId : 20022
     * sex : 男
     * email : m@163.com
     * name : 马路
     */

    private SpinfoBeanBean spinfoBean;
    private String favoriteStrs;
    /**
     * responsibilities : 写代码
     * place : null
     * statusCategory : 3
     * vitaeId : 1030
     * industryDirection : 计算机软件/系统集成
     * postOffice : 开发
     * worktimeBegin : 2016-07-19 00:00:00.0
     * worktimeEnd : null
     * company : 影子
     * experience : 2016-06-19 啦咔咔
     * department : 1
     */

    private List<WorkExperienceListBean> workExperienceList;
    /**
     * school : 西北政法大学
     * isNational : null
     * specialty : 法律
     * evaluation : 硕士/研究生
     * statusCategory : 1
     * positionName : null
     * vitaeId : 221
     * trainContent : null
     * beginTime : 2016-05-12 00:00:00.0
     * endTime : null
     */

    private List<EducationListBean> educationList;
    /**
     * hobby : null
     * picPath : null
     * picPathList : []
     * honorId : 62
     * grantName : 定OTG
     * grantTime : 2016-06-30
     * ranking : 抨击
     * grantUnits : 您是
     * grantLevel : 定局
     * id : null
     */

    private List<HonorPraiseListBean> honorPraiseList;

    public String getSkillStrs() {
        return skillStrs;
    }

    public void setSkillStrs(String skillStrs) {
        this.skillStrs = skillStrs;
    }

    public JobBeanBean getJobBean() {
        return jobBean;
    }

    public void setJobBean(JobBeanBean jobBean) {
        this.jobBean = jobBean;
    }

    public SpinfoBeanBean getSpinfoBean() {
        return spinfoBean;
    }

    public void setSpinfoBean(SpinfoBeanBean spinfoBean) {
        this.spinfoBean = spinfoBean;
    }

    public String getFavoriteStrs() {
        return favoriteStrs;
    }

    public void setFavoriteStrs(String favoriteStrs) {
        this.favoriteStrs = favoriteStrs;
    }

    public List<WorkExperienceListBean> getWorkExperienceList() {
        return workExperienceList;
    }

    public void setWorkExperienceList(List<WorkExperienceListBean> workExperienceList) {
        this.workExperienceList = workExperienceList;
    }

    public List<EducationListBean> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<EducationListBean> educationList) {
        this.educationList = educationList;
    }

    public List<HonorPraiseListBean> getHonorPraiseList() {
        return honorPraiseList;
    }

    public void setHonorPraiseList(List<HonorPraiseListBean> honorPraiseList) {
        this.honorPraiseList = honorPraiseList;
    }

    public static class JobBeanBean {
        private String industryName;
        private String money;
        private String jobType;
        private String job;
        private String industry;
        private String place;
        private String keyword;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getIndustryName() {
            return industryName;
        }

        public void setIndustryName(String industryName) {
            this.industryName = industryName;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getJobType() {
            return jobType;
        }

        public void setJobType(String jobType) {
            this.jobType = jobType;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }
    }

    public static class SpinfoBeanBean {
        private String birthDate;
        private String certificateNumber;
        private String education;
        private String currentAddress;
        private String workYears;
        private String birthPlace;
        private String nationName;
        private String politicalName;
        private String certificateTypeName;
        private String maritalStatusCode;
        private String maritalStatusName;
        private String liveStreet;
        private String birthStreet;
        private String phone;
        private String accountId;
        private String sex;
        private String email;
        private String name;

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getCertificateNumber() {
            return certificateNumber;
        }

        public void setCertificateNumber(String certificateNumber) {
            this.certificateNumber = certificateNumber;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getCurrentAddress() {
            return currentAddress;
        }

        public void setCurrentAddress(String currentAddress) {
            this.currentAddress = currentAddress;
        }

        public String getWorkYears() {
            return workYears;
        }

        public void setWorkYears(String workYears) {
            this.workYears = workYears;
        }

        public String getBirthPlace() {
            return birthPlace;
        }

        public void setBirthPlace(String birthPlace) {
            this.birthPlace = birthPlace;
        }

        public String getNationName() {
            return nationName;
        }

        public void setNationName(String nationName) {
            this.nationName = nationName;
        }

        public String getPoliticalName() {
            return politicalName;
        }

        public void setPoliticalName(String politicalName) {
            this.politicalName = politicalName;
        }

        public String getCertificateTypeName() {
            return certificateTypeName;
        }

        public void setCertificateTypeName(String certificateTypeName) {
            this.certificateTypeName = certificateTypeName;
        }

        public String getMaritalStatusCode() {
            return maritalStatusCode;
        }

        public void setMaritalStatusCode(String maritalStatusCode) {
            this.maritalStatusCode = maritalStatusCode;
        }

        public String getMaritalStatusName() {
            return maritalStatusName;
        }

        public void setMaritalStatusName(String maritalStatusName) {
            this.maritalStatusName = maritalStatusName;
        }

        public String getLiveStreet() {
            return liveStreet;
        }

        public void setLiveStreet(String liveStreet) {
            this.liveStreet = liveStreet;
        }

        public String getBirthStreet() {
            return birthStreet;
        }

        public void setBirthStreet(String birthStreet) {
            this.birthStreet = birthStreet;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class WorkExperienceListBean {
        private String responsibilities;
        private String place;
        private String statusCategory;
        private String vitaeId;
        private String industryDirection;
        private String postOffice;
        private String worktimeBegin;
        private String worktimeEnd;
        private String company;
        private String experience;
        private String department;

        public String getResponsibilities() {
            return responsibilities;
        }

        public void setResponsibilities(String responsibilities) {
            this.responsibilities = responsibilities;
        }


        public String getStatusCategory() {
            return statusCategory;
        }

        public void setStatusCategory(String statusCategory) {
            this.statusCategory = statusCategory;
        }

        public String getVitaeId() {
            return vitaeId;
        }

        public void setVitaeId(String vitaeId) {
            this.vitaeId = vitaeId;
        }

        public String getIndustryDirection() {
            return industryDirection;
        }

        public void setIndustryDirection(String industryDirection) {
            this.industryDirection = industryDirection;
        }

        public String getPostOffice() {
            return postOffice;
        }

        public void setPostOffice(String postOffice) {
            this.postOffice = postOffice;
        }

        public String getWorktimeBegin() {
            return worktimeBegin;
        }

        public void setWorktimeBegin(String worktimeBegin) {
            this.worktimeBegin = worktimeBegin;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getWorktimeEnd() {
            return worktimeEnd;
        }

        public void setWorktimeEnd(String worktimeEnd) {
            this.worktimeEnd = worktimeEnd;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }
    }

    public static class EducationListBean {
        private String school;
        private String specialty;
        private String evaluation;
        private String statusCategory;
        private String vitaeId;
        private String beginTime;
        private String endTime;
        private String trainContent;
        private String positionName;
        private String isNational;

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }


        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public String getEvaluation() {
            return evaluation;
        }

        public void setEvaluation(String evaluation) {
            this.evaluation = evaluation;
        }

        public String getStatusCategory() {
            return statusCategory;
        }

        public void setStatusCategory(String statusCategory) {
            this.statusCategory = statusCategory;
        }


        public String getVitaeId() {
            return vitaeId;
        }

        public void setVitaeId(String vitaeId) {
            this.vitaeId = vitaeId;
        }


        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getTrainContent() {
            return trainContent;
        }

        public void setTrainContent(String trainContent) {
            this.trainContent = trainContent;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public String getIsNational() {
            return isNational;
        }

        public void setIsNational(String isNational) {
            this.isNational = isNational;
        }
    }

    public static class HonorPraiseListBean {

        private String honorId;
        private String grantName;
        private String grantTime;
        private String ranking;
        private String grantUnits;
        private String grantLevel;

        private List<String> picPathList;
        private String hobby;
        private String picPath;
        private String id;

        public String getHobby() {
            return hobby;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public String getPicPath() {
            return picPath;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHonorId() {
            return honorId;
        }

        public void setHonorId(String honorId) {
            this.honorId = honorId;
        }

        public String getGrantName() {
            return grantName;
        }

        public void setGrantName(String grantName) {
            this.grantName = grantName;
        }

        public String getGrantTime() {
            return grantTime;
        }

        public void setGrantTime(String grantTime) {
            this.grantTime = grantTime;
        }

        public String getRanking() {
            return ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }

        public String getGrantUnits() {
            return grantUnits;
        }

        public void setGrantUnits(String grantUnits) {
            this.grantUnits = grantUnits;
        }

        public String getGrantLevel() {
            return grantLevel;
        }

        public void setGrantLevel(String grantLevel) {
            this.grantLevel = grantLevel;
        }


        public List<String> getPicPathList() {
            return picPathList;
        }

        public void setPicPathList(List<String> picPathList) {
            this.picPathList = picPathList;
        }
    }
}
