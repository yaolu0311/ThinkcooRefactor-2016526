package com.yz.im.model.db.entity;

import com.example.administrator.publicmodule.entity.nullentities.Nullable;
import com.yzkj.android.orm.annotation.Column;
import com.yzkj.android.orm.annotation.Table;

/**
 * Created by cys on 2016/7/19
 * 用户信息数据表
 */
@Table(name = "PersonalUserInfo")
public class User implements Nullable{

    @Column(name = "id", isId = true)
    private int id;// 本地数据库编号
    @Column(name = "userId")
    private String userId;  // 用户id
    @Column(name = "idType")
    private String idType;  // 用户 是否学生 1是，2否
    @Column(name = "headPortrait")
    private String headPortrait; // 用户头像 url地址
    @Column(name = "sex")
    private String sex; // 性别
    @Column(name = "certificateNumber")
    private String certificateNumber; // 证件号
    @Column(name = "certificateType")
    private String certificateType; // 证件类型
    @Column(name = "highestEducation")
    private String highestEducation;  // 最高学历
    @Column(name = "maritalStatus")
    private String maritalStatus;  // 婚姻状态
    @Column(name = "politicalStatus")
    private String politicalStatus;  // 政治面貌
    @Column(name = "fullName")
    private String fullName;  // 姓名
    @Column(name = "nation")
    private String nation;  // 民族
    @Column(name = "birthDate")
    private String birthDate;  // 出生日期
    @Column(name = "signature")
    private String signature;  // 个性签名
    @Column(name = "personalPhone")
    private String personalPhone;  // 手机号码
    @Column(name = "mail")
    private String mail;  // 邮箱
    @Column(name = "liveAreaCode")
    private String liveAreaCode;  // 居住地code
    @Column(name = "liveStreet")
    private String liveStreet;  // 居住地（街办路一级）
    @Column(name = "birthPlace")
    private String birthPlace;  // 出生地code
    @Column(name = "birthStreet")
    private String birthStreet;  // 出生地(街办路一级)

    @Override
    public boolean isEmpty() {
        return false;
    }
}
