package com.marvin.lop.bean;

import java.io.Serializable;

/**
 * Created by Marvin on 2016/4/16.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 显示在请求认证的ListView中
 */
public class AuthenRequestList implements Serializable{

    private String userPhoneNumber;// 手机号
    private Integer userPermission;// 权限
    private Integer userCollege; // 学院
    private String userClass;// 班级
    private String userStudentId;// 学号
    private String userRealName;// 用户真实姓名
    private String userEmailAddress;// 用户邮箱
    private String objectId;// 用户ID


    public String getObjectId() {
        return objectId;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
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

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
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

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
