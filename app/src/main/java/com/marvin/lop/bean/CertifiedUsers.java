package com.marvin.lop.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Marvin on 2016/4/11.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class CertifiedUsers extends BmobObject {

    private String userPhoneNumber;// 用户手机号
    private String userPassword;// 用户密码
    private Integer userPermission;// 用户身份权限
    private Integer userCollege;// 用户所在学院
    private String userClass;// 用户所在班级
    private String userStudentId;// 用户学号
    private String userRealName;// 用户真实姓名
    private String userEmailAddress;// 用户邮箱
    private Boolean userAuthenResult;// 认证结果
    private String userAuthenProgress; // 用户认证进度

    public CertifiedUsers() {

    }

    public String getUserAuthenProgress() {
        return userAuthenProgress;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public Integer getUserPermission() {
        return userPermission;
    }

    public Integer getUserCollege() {
        return userCollege;
    }

    public String getUserClass() {
        return userClass;
    }

    public String getUserStudentId() {
        return userStudentId;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public Boolean getUserAuthenResult() {
        return userAuthenResult;
    }

    public void setUserPermission(Integer userPermission) {
        this.userPermission = userPermission;
    }

    public void setUserCollege(Integer userCollege) {
        this.userCollege = userCollege;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public void setUserStudentId(String userStudentId) {
        this.userStudentId = userStudentId;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public void setUserAuthenResult(Boolean userAuthenResult) {
        this.userAuthenResult = userAuthenResult;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserAuthenProgress(String userAuthenProgress) {
        this.userAuthenProgress = userAuthenProgress;
    }


}
