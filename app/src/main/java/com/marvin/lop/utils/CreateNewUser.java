package com.marvin.lop.utils;

import com.marvin.lop.bean.CertifiedUsers;

/**
 * Created by Marvin on 2016/4/17.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 用来创建新的用户
 */
public class CreateNewUser {

    private CertifiedUsers certifiedUsers;

    /**
     * 根据传入的数据来生成一个用于存储数据库数据的对象
     * @param phoneNumber
     * @param password
     * @param permission
     * @param college
     * @param classNumber
     * @param studentId
     * @param realName
     * @param emailAddress
     * @param progress
     * @param result
     * @return
     */
    public CertifiedUsers createUserInServer(String phoneNumber,
                                             String password,
                                             Integer permission,
                                             Integer college,
                                             String classNumber,
                                             String studentId,
                                             String realName,
                                             String emailAddress,
                                             String progress,
                                             Boolean result) {
        certifiedUsers = new CertifiedUsers();
        certifiedUsers.setUserPhoneNumber(phoneNumber);
        certifiedUsers.setUserPassword(password);
        certifiedUsers.setUserPermission(permission);
        certifiedUsers.setUserCollege(college);
        certifiedUsers.setUserClass(classNumber);
        certifiedUsers.setUserStudentId(studentId);
        certifiedUsers.setUserRealName(realName);
        certifiedUsers.setUserEmailAddress(emailAddress);
        certifiedUsers.setUserAuthenProgress(progress);
        certifiedUsers.setUserAuthenResult(result);

        return certifiedUsers;

    }
}
