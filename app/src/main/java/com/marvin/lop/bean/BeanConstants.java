package com.marvin.lop.bean;

/**
 * Created by Marvin on 2016/3/31.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public final class BeanConstants {


    //**************************************旧数据********************************************************
    public static String MOBILE_SERVERS_URL = "http://mserver.e-cology.cn/servers.do";
    public static String serverAdd = "";
    public static String serverVersion = "";
    public static String sessionKey = "";
    public static String clientLogoPath = "";
    public static String headpic = "";
    public static String HOME_MODULE = "0";
    public static String MORE_MODULE = "-1";
    public static String SETTING_MODULE = "-2";
    public static String ABOUT_MODULE = "-3";
    public static String ADDRESSBOOK_MODULE = "6";
    public static int createworkflow = 0;
//	public static Config config = new Config();
//	public static ContactItem contactItem = new ContactItem();


    public static String clientVersion = "";
    public static String deviceid = "";
    public static String token = "";
    public static String clientOs = "";
    public static String clientOsVer = "";
    public static String language = "";
    public static String country = "";
    public static String user = "";
    public static String pass = "";
    //**************************************旧数据********************************************************

    /**
     * 数据库表名信息TableName
     */
    public static final class TableName {
        /**
         * 认证用户信息表
         */
        public static final String CertifiedUsersTableName = "CertifiedUsers";
    }


    /**
     * 认证用户信息表--属性名
     */
    public static final class CertifiedUsersProperties {
        /**
         * 用户手机号
         */
        public static final String UserPhoneNumber = "userPhoneNumber";
        /**
         * 密码
         */
        public static final String UserPassword = "userPassword";
        /**
         * 认证进度
         */
        public static final String UserAuthenProgress = "userAuthenProgress";
        /**
         * 认证结果
         */
        public static final String UserAuthenResult = "userAuthenResult";
        /**
         * 用户身份
         */
        public static final String UserPermission = "userPermission";
        /**
         * 用户学院
         */
        public static final String UserCollege = "userCollege";
        /**
         * 用户班级
         */
        public static final String UserClass = "userClass";
        /**
         * 学号
         */
        public static final String UserStudentId = "userStudentId";
        /**
         * 真实姓名
         */
        public static final String UserRealName = "userRealName";
        /**
         * 电子邮箱
         */
        public static final String UserEmailAddress = "userEmailAddress";
        /**
         * ObjectId
         */
        public static final String UserObjectId = "objectId";
    }


}

