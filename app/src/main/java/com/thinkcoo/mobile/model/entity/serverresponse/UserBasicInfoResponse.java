package com.thinkcoo.mobile.model.entity.serverresponse;

/**
 * Created by Leevin
 * CreateTime: 2016/5/27  9:54
 */
public class UserBasicInfoResponse {

    private String sex;
    private String headPortrait;
    private String birthDate;
    private String certificateNumber;
    private String birthPlace;
    private String liveStreet;
    private String birthStreet;
    private String idType;
    private String politicalStatus;
    private String maritalStatus;
    private String nation;
    private String highestEducation;
    private String liveAreaCode;
    private String certificateType;
    private String personalPhone;
    private String mail;
    private String userId;
    private String signature;
    private String fullName;

    private String certificateTypeName; // 身份证类型名称
    private String  politicalStatusName; // 政治面貌名称
    private String  maritalStatusName; // 婚姻状态名称
    private String nationName; // 民族名称
    private String  highestEducationName; // 最高学历名称
    private String liveAreaName; // 居住地名称
    private String  birthPlaceName; // 出生地名称

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public String getCertificateTypeName() {
        return certificateTypeName;
    }

    public void setCertificateTypeName(String certificateTypeName) {
        this.certificateTypeName = certificateTypeName;
    }

    public String getPoliticalStatusName() {
        return politicalStatusName;
    }

    public void setPoliticalStatusName(String politicalStatusName) {
        this.politicalStatusName = politicalStatusName;
    }

    public String getMaritalStatusName() {
        return maritalStatusName;
    }

    public void setMaritalStatusName(String maritalStatusName) {
        this.maritalStatusName = maritalStatusName;
    }

    public String getHighestEducationName() {
        return highestEducationName;
    }

    public void setHighestEducationName(String highestEducationName) {
        this.highestEducationName = highestEducationName;
    }

    public String getLiveAreaName() {
        return liveAreaName;
    }

    public void setLiveAreaName(String liveAreaName) {
        this.liveAreaName = liveAreaName;
    }

    public String getBirthPlaceName() {
        return birthPlaceName;
    }

    public void setBirthPlaceName(String birthPlaceName) {
        this.birthPlaceName = birthPlaceName;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

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

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
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

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getLiveAreaCode() {
        return liveAreaCode;
    }

    public void setLiveAreaCode(String liveAreaCode) {
        this.liveAreaCode = liveAreaCode;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getPersonalPhone() {
        return personalPhone;
    }

    public void setPersonalPhone(String personalPhone) {
        this.personalPhone = personalPhone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "UserBasicInfoResponse{" +
                "sex='" + sex + '\'' +
                ", headPortrait='" + headPortrait + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", certificateNumber='" + certificateNumber + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", liveStreet='" + liveStreet + '\'' +
                ", birthStreet='" + birthStreet + '\'' +
                ", idType='" + idType + '\'' +
                ", politicalStatus='" + politicalStatus + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", nation='" + nation + '\'' +
                ", highestEducation='" + highestEducation + '\'' +
                ", liveAreaCode='" + liveAreaCode + '\'' +
                ", certificateType='" + certificateType + '\'' +
                ", personalPhone='" + personalPhone + '\'' +
                ", mail='" + mail + '\'' +
                ", userId='" + userId + '\'' +
                ", signature='" + signature + '\'' +
                ", fullName='" + fullName + '\'' +
                ", certificateTypeName='" + certificateTypeName + '\'' +
                ", politicalStatusName='" + politicalStatusName + '\'' +
                ", maritalStatusName='" + maritalStatusName + '\'' +
                ", nationName='" + nationName + '\'' +
                ", highestEducationName='" + highestEducationName + '\'' +
                ", liveAreaName='" + liveAreaName + '\'' +
                ", birthPlaceName='" + birthPlaceName + '\'' +
                '}';
    }
}
