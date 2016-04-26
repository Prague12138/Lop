package com.marvin.lop.utils;

import com.marvin.lop.config.Constants;

/**
 * Created by Marvin on 2016/4/11.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 描述 ；用来匹配学院代号和身份权限代号的类
 */
public class CodeMatcher {

    /**
     * 根据输入的学院名返回学院代号
     *
     * @param collegeName 学院名称
     * @return 学院名称对应的代号
     */
    public static Integer CollegeCodeMatcher(String collegeName) {
        switch (collegeName) {
            case "机电工程学院":
                // Java已经自动将int类型自动装箱成Integer类型的
                return Constants.CollegeCode.USER_COLLEGE_JIDIAN;
            case "机械与动力工程学院":
                return Constants.CollegeCode.USER_COLLEGE_JIXIE;
            case "材料科学与工程学院":
                return Constants.CollegeCode.USER_COLLEGE_CAILIAO;
            case "化工与环境学院":
                return Constants.CollegeCode.USER_COLLEGE_HUAGONG;
            case "信息与通信工程学院":
                return Constants.CollegeCode.USER_COLLEGE_TONGXIN;
            case "仪器与电子学院":
                return Constants.CollegeCode.USER_COLLEGE_YIQI;
            case "计算机与控制工程学院":
                return Constants.CollegeCode.USER_COLLEGE_JISUANJI;
            case "理学院":
                return Constants.CollegeCode.USER_COLLEGE_LIXUEYUAN;
            case "经济与管理学院":
                return Constants.CollegeCode.USER_COLLEGE_JINGGUAN;
            case "人文社会科学学院":
                return Constants.CollegeCode.USER_COLLEGE_RENWEN;
            case "体育学院":
                return Constants.CollegeCode.USER_COLLEGE_TIYUAN;
            case "艺术学院":
                return Constants.CollegeCode.USER_COLLEGE_YISHU;
            case "软件学院":
                return Constants.CollegeCode.USER_COLLEGE_RUANJIAN;
            case "研究生院":
                return Constants.CollegeCode.USER_COLLEGE_YANJIUSHENG;
            case "继续教育学院":
                return Constants.CollegeCode.USER_COLLEGE_JIXUJIAOYU;
            case "后备军官教育学院（武装部）":
                return Constants.CollegeCode.USER_COLLEGE_GUOFANGSHENG;
            case "国际教育学院（筹）":
                return Constants.CollegeCode.USER_COLLEGE_GUOJIJIAOYU;
            case "信息商务学院":
                return Constants.CollegeCode.USER_COLLEGE_XINSHANG;
            case "中北大学朔州校区":
                return Constants.CollegeCode.USER_COLLEGE_SHUOZHOUDIANLI;
            default:
                break;
        }
        return null;
    }

    /**
     * 根据输入的学院代码返回相应的学院名称
     *
     * @param collegeCode 学院代码
     * @return 相应的学院名称
     */
    public static String CollegeCodeMatcher(Integer collegeCode) {
        // 自动拆箱
        switch (collegeCode) {
            case Constants.CollegeCode.USER_COLLEGE_JIDIAN:
                return "机电工程学院";
            case Constants.CollegeCode.USER_COLLEGE_JIXIE:
                return "机械与动力工程学院";
            case Constants.CollegeCode.USER_COLLEGE_CAILIAO:
                return "材料科学与工程学院";
            case Constants.CollegeCode.USER_COLLEGE_HUAGONG:
                return "化工与环境学院";
            case Constants.CollegeCode.USER_COLLEGE_TONGXIN:
                return "信息与通信工程学院";
            case Constants.CollegeCode.USER_COLLEGE_YIQI:
                return "仪器与电子学院";
            case Constants.CollegeCode.USER_COLLEGE_JISUANJI:
                return "计算机与控制工程学院";
            case Constants.CollegeCode.USER_COLLEGE_LIXUEYUAN:
                return "理学院";
            case Constants.CollegeCode.USER_COLLEGE_JINGGUAN:
                return "经济与管理学院";
            case Constants.CollegeCode.USER_COLLEGE_RENWEN:
                return "人文社会科学学院";
            case Constants.CollegeCode.USER_COLLEGE_TIYUAN:
                return "体育学院";
            case Constants.CollegeCode.USER_COLLEGE_YISHU:
                return "艺术学院";
            case Constants.CollegeCode.USER_COLLEGE_RUANJIAN:
                return "软件学院";
            case Constants.CollegeCode.USER_COLLEGE_YANJIUSHENG:
                return "研究生院";
            case Constants.CollegeCode.USER_COLLEGE_JIXUJIAOYU:
                return "继续教育学院";
            case Constants.CollegeCode.USER_COLLEGE_GUOFANGSHENG:
                return "后备军官教育学院（武装部）";
            case Constants.CollegeCode.USER_COLLEGE_GUOJIJIAOYU:
                return "国际教育学院（筹）";
            case Constants.CollegeCode.USER_COLLEGE_XINSHANG:
                return "信息商务学院";
            case Constants.CollegeCode.USER_COLLEGE_SHUOZHOUDIANLI:
                return "中北大学朔州校区";
            default:
                break;
        }
        return null;
    }
    
    /**
     * 根据身份权限名称获取相应的代号
     *
     * @param permissionName 用户身份权限名称
     * @return 身份对应的代号
     */
    public static Integer PermissionCodeMatcher(String permissionName) {
        switch (permissionName) {
            case "认证用户-管理员":
                return Constants.UserPermission.USER_PERMISSION_ADMIN;
            case "认证用户-班长":
                return Constants.UserPermission.USER_PERMISSION_MONITOR;
            case "认证用户-学生":
                return Constants.UserPermission.USER_PERMISSION_STUDENT;
            case "注册用户":
                return Constants.UserPermission.USER_PERMISSION_REGISTER_PHONE;
            case "游客":
                return Constants.UserPermission.USRE_PERMISSION_VISTOR;
            default:
                break;
        }
        return null;
    }

    /**
     * 根据用户身份代号获取相应的身份权限名称
     *
     * @param permissionCode 用户身份代号
     * @return 相应的身份权限名称
     */
    public static String PermissionCodeMatcher(Integer permissionCode) {
        switch (permissionCode) {
            case Constants.UserPermission.USER_PERMISSION_ADMIN:
                return "认证用户-管理员";
            case Constants.UserPermission.USER_PERMISSION_MONITOR:
                return "认证用户-班长";
            case Constants.UserPermission.USER_PERMISSION_STUDENT:
                return "认证用户-学生";
            case Constants.UserPermission.USER_PERMISSION_REGISTER_PHONE:
                return "注册用户";
            case Constants.UserPermission.USRE_PERMISSION_VISTOR:
                return "游客";
            default:
                break;
        }
        return null;
    }
}
