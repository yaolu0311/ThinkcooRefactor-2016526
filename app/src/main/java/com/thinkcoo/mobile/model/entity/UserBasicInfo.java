package com.thinkcoo.mobile.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.annotation.AnnotationConstant;
import com.thinkcoo.mobile.annotation.FieldEmptyCheck;
import com.thinkcoo.mobile.annotation.FieldFormatCheck;
import com.thinkcoo.mobile.model.entity.nullentities.Nullable;
import com.thinkcoo.mobile.model.entity.serverresponse.UserBasicInfoResponse;
import com.thinkcoo.mobile.model.entity.serverresponse.robust.ServerDataConverter;

import javax.inject.Inject;

/**
 * Created by Leevin
 * CreateTime: 2016/5/27  9:54
 */
public class UserBasicInfo implements Nullable, Parcelable {

    public static UserBasicInfo fromServerResponse(UserBasicInfoResponse userBasicInfoResponse , ServerDataConverter serverDataConverter) {

        UserBasicInfo userBasicInfo = new UserBasicInfo();
        userBasicInfo.setUserId(userBasicInfoResponse.getUserId() == null ?"0":userBasicInfoResponse.getUserId());
        userBasicInfo.setHeadPortrait(userBasicInfoResponse.getHeadPortrait() == null ?"":userBasicInfoResponse.getHeadPortrait());
        userBasicInfo.setBirthStreet(userBasicInfoResponse.getBirthStreet() == null ?"":userBasicInfoResponse.getBirthStreet());
        userBasicInfo.setBirthPlace(userBasicInfoResponse.getBirthPlace() == null ?"-1":userBasicInfoResponse.getBirthPlace());
        userBasicInfo.setBirthStreet(userBasicInfoResponse.getBirthStreet() == null ?"":userBasicInfoResponse.getBirthStreet() );
        userBasicInfo.setLiveStreet(userBasicInfoResponse.getLiveStreet() == null ?"":userBasicInfoResponse.getLiveStreet() );
        userBasicInfo.setCertificateNumber(userBasicInfoResponse.getCertificateNumber() == null ?"":userBasicInfoResponse.getCertificateNumber());
        userBasicInfo.setBirthDate(userBasicInfoResponse.getBirthDate() == null ?"":userBasicInfoResponse.getBirthDate());
        userBasicInfo.setHighestEducation(userBasicInfoResponse.getHighestEducation() == null ?"":userBasicInfoResponse.getHighestEducation());
        userBasicInfo.setLiveAreaCode(userBasicInfoResponse.getLiveAreaCode() == null ?"-1":userBasicInfoResponse.getLiveAreaCode() );
        userBasicInfo.setCertificateType(userBasicInfoResponse.getCertificateType() == null ?"":userBasicInfoResponse.getCertificateType() );
        userBasicInfo.setMail(userBasicInfoResponse.getMail() == null ?"":userBasicInfoResponse.getMail() );
        userBasicInfo.setMaritalStatus(userBasicInfoResponse.getMaritalStatus() == null ?"":userBasicInfoResponse.getMaritalStatus());
        userBasicInfo.setNation(userBasicInfoResponse.getNation() == null ?"":userBasicInfoResponse.getNation());
        userBasicInfo.setPersonalPhone(userBasicInfoResponse.getPersonalPhone() == null ?"":userBasicInfoResponse.getPersonalPhone());
        userBasicInfo.setPoliticalStatus(userBasicInfoResponse.getPoliticalStatus() == null ?"":userBasicInfoResponse.getPoliticalStatus());
        userBasicInfo.setFullName(userBasicInfoResponse.getFullName() == null ?"":userBasicInfoResponse.getFullName());
        userBasicInfo.setSex(userBasicInfoResponse.getSex() == null ?"":userBasicInfoResponse.getSex());
        userBasicInfo.setSignature(userBasicInfoResponse.getSignature() == null ?"":userBasicInfoResponse.getSignature());
        userBasicInfo.setHeadPortrait(userBasicInfoResponse.getHeadPortrait() == null ?"":userBasicInfoResponse.getHeadPortrait());
        userBasicInfo.setCertificateTypeName(userBasicInfoResponse.getCertificateTypeName() == null ?"":userBasicInfoResponse.getCertificateTypeName());
        userBasicInfo.setPoliticalStatusName(userBasicInfoResponse.getPoliticalStatusName() == null ?"":userBasicInfoResponse.getPoliticalStatusName());
        userBasicInfo.setMaritalStatusName(userBasicInfoResponse.getMaritalStatusName() == null ?"":userBasicInfoResponse.getMaritalStatusName());
        userBasicInfo.setNationName(userBasicInfoResponse.getNationName() == null ?"":userBasicInfoResponse.getNationName());
        userBasicInfo.setHighestEducationName(userBasicInfoResponse.getHighestEducationName() == null ?"":userBasicInfoResponse.getHighestEducationName());
        userBasicInfo.setLiveAreaName(userBasicInfoResponse.getLiveAreaName() == null ?"":userBasicInfoResponse.getLiveAreaName());
        userBasicInfo.setBirthPlaceName(userBasicInfoResponse.getBirthPlaceName() == null ?"":userBasicInfoResponse.getBirthPlaceName());
        Address birthAddress = new Address(userBasicInfo.getBirthPlaceName(),serverDataConverter.stringToInt(userBasicInfo.getBirthPlace(),0));
        userBasicInfo.setBirthAddress(birthAddress);
        Address liveAddress = new Address(userBasicInfo.getLiveAreaName(),serverDataConverter.stringToInt(userBasicInfo.getLiveAreaCode(),0));
        userBasicInfo.setLiveAddress(birthAddress);
        return userBasicInfo;
    }

    private String sex;//性别
    private String headPortrait;//图像
    @FieldEmptyCheck(R.string.personal_mybasicinfo_csrqk)
    private String birthDate;//出生日期
    private String certificateNumber;//证件号码
    @FieldEmptyCheck(R.string.personal_mybasicinfo_csdk)
    private String birthPlace;//出生地区
    private String liveStreet;//居住地址
    private String birthStreet;//出生地址

    private String idType;//身份类型
    @FieldEmptyCheck(R.string.zhengzhimianmao_empty)
    private String politicalStatus;//政治面貌
    @FieldEmptyCheck(R.string.huiyinzhuangtai_empty)
    private String maritalStatus;//婚姻状态
    @FieldEmptyCheck(R.string.minzu_empty)
    private String nation;//民族
    @FieldEmptyCheck(R.string.personal_mybasicinfo_xlk)
    private String highestEducation;//学历
    @FieldEmptyCheck(R.string.personal_mybasicinfo_jzdk)
    private String liveAreaCode;//居住地代码
    @FieldEmptyCheck(R.string.certificate_empty)
    private String certificateType;//证件类型
    @FieldEmptyCheck(R.string.personal_mybasicinfo_lxdhk)
    @FieldFormatCheck(regex = AnnotationConstant.PHONE_FORMAT_CHECK,  value = R.string.phone_number_format_error)
    private String personalPhone;//联系电话
    @FieldFormatCheck(regex = AnnotationConstant.EMAIL_FORMAT_CHECK,  value = R.string.email_number_format_error)
    private String mail;//邮箱
    private String userId;//
    private String signature;//签名
    private String fullName;//名称

    private String certificateTypeName; // 身份证类型名称
    private String politicalStatusName; // 政治面貌名称
    private String maritalStatusName; // 婚姻状态名称
    private String nationName; // 民族名称
    private String highestEducationName; // 最高学历名称
    private String liveAreaName; // 居住地名称
    private String birthPlaceName; // 出生地名称

    private Address birthAddress;

    public Address getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(Address liveAddress) {
        this.liveAddress = liveAddress;
    }

    public Address getBirthAddress() {
        return birthAddress;
    }

    public void setBirthAddress(Address birthAddress) {
        this.birthAddress = birthAddress;
    }

    private Address liveAddress;

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
    public boolean isEmpty() {
        return false;
    }




    public UserBasicInfo() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sex);
        dest.writeString(this.headPortrait);
        dest.writeString(this.birthDate);
        dest.writeString(this.certificateNumber);
        dest.writeString(this.birthPlace);
        dest.writeString(this.liveStreet);
        dest.writeString(this.birthStreet);
        dest.writeString(this.idType);
        dest.writeString(this.politicalStatus);
        dest.writeString(this.maritalStatus);
        dest.writeString(this.nation);
        dest.writeString(this.highestEducation);
        dest.writeString(this.liveAreaCode);
        dest.writeString(this.certificateType);
        dest.writeString(this.personalPhone);
        dest.writeString(this.mail);
        dest.writeString(this.userId);
        dest.writeString(this.signature);
        dest.writeString(this.fullName);
        dest.writeString(this.certificateTypeName);
        dest.writeString(this.politicalStatusName);
        dest.writeString(this.maritalStatusName);
        dest.writeString(this.nationName);
        dest.writeString(this.highestEducationName);
        dest.writeString(this.liveAreaName);
        dest.writeString(this.birthPlaceName);
        dest.writeParcelable(this.birthAddress, flags);
        dest.writeParcelable(this.liveAddress, flags);
    }

    protected UserBasicInfo(Parcel in) {
        this.sex = in.readString();
        this.headPortrait = in.readString();
        this.birthDate = in.readString();
        this.certificateNumber = in.readString();
        this.birthPlace = in.readString();
        this.liveStreet = in.readString();
        this.birthStreet = in.readString();
        this.idType = in.readString();
        this.politicalStatus = in.readString();
        this.maritalStatus = in.readString();
        this.nation = in.readString();
        this.highestEducation = in.readString();
        this.liveAreaCode = in.readString();
        this.certificateType = in.readString();
        this.personalPhone = in.readString();
        this.mail = in.readString();
        this.userId = in.readString();
        this.signature = in.readString();
        this.fullName = in.readString();
        this.certificateTypeName = in.readString();
        this.politicalStatusName = in.readString();
        this.maritalStatusName = in.readString();
        this.nationName = in.readString();
        this.highestEducationName = in.readString();
        this.liveAreaName = in.readString();
        this.birthPlaceName = in.readString();
        this.birthAddress = in.readParcelable(Address.class.getClassLoader());
        this.liveAddress = in.readParcelable(Address.class.getClassLoader());
    }

    public static final Creator<UserBasicInfo> CREATOR = new Creator<UserBasicInfo>() {
        @Override
        public UserBasicInfo createFromParcel(Parcel source) {
            return new UserBasicInfo(source);
        }

        @Override
        public UserBasicInfo[] newArray(int size) {
            return new UserBasicInfo[size];
        }
    };
}
