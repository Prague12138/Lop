package com.marvin.lop.config;

import android.os.Environment;

/**
 * Created by Marvin on 2016/3/19.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * Describtion: 参数设置
 */
public final class Constants {

    /**
     * 应用名称
     */
    public static final String APP_NAME = "兔子二手货";

    /**
     * 服务器图片路径
     */
    // TODO: 2016/3/19 添加服务器图片路径
    public static final String IMAGE_URL = "";

    /**
     * 服务器视频路径
     */
    // TODO: 2016/3/19 添加服务器视频路径
    public static final String VIDEO_URL = "";

    /**
     * 保存参数的文件夹名称
     */
    public static final String SHARED_PERFERENCE_NAME = "marvin_lop_perfs";

    /**
     * SDCard路径
     */
    public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + APP_NAME + "Lop";

    /**
     * 图片存储路径
     */
    public static final String BASE_PATH = SD_PATH + "/pic";

    /**
     * 图片缓存路径
     */
    public static final String BASE_IMAGE_CACHE = BASE_PATH + "/cache/images/";

    /**
     * 需要分享的图片
     */
    // TODO: 2016/3/19 处理需要分享的图片
    public static final String SHARE_FILE = BASE_PATH + "ShareImage.png";

    /**
     * 手机IMEI号
     */
    public static String IMEI = "";

    /**
     * 手机号
     */
    public static String TEL = "";

    /**
     * 屏幕高度
     */
    // TODO: 2016/3/19 需要修改屏幕宽高
    public static int SCREEN_HEIGHT = 800;

    /**
     * 屏幕宽度
     */
    // TODO: 2016/3/19 需要修改屏幕宽高
    public static int SCREEN_WIDTH = 480;

    /**
     * 屏幕密度
     */
    // TODO: 2016/3/19 需要修改屏幕密度
    public static float SCREEN_DENSITY = 1.5F;

    // ********************************************旧数据**********0x1000-0x10000***************************************

    /**
     * 分享成功
     */
    public static final int SHARE_SUCCESS = 0x1000;

    /**
     * 分享取消
     */
    public static final int SHARE_CANCEL = 0x2000;

    /**
     * 分享失败
     */
    public static final int SHARE_ERROR = 0x3000;

    /**
     * 开始执行
     */
    public static final int EXECUTE_STARTING = 0x4000;

    /**
     * 正在执行
     */
    public static final int EXECUTE_RUNNING = 0x5000;

    /**
     * 执行完成
     */
    public static final int EXECUTE_SUCCESS = 0x6000;

    /**
     * 加载数据成功
     */
    public static final int LOAD_DATA_SUCCESS = 0x7000;

    /**
     * 加载数据失败
     */
    public static final int LOAD_DATA_FAILED = 0x8000;

    /**
     * 动态加载数据
     */
    public static final int DYNAMIC_LOAD_DATA = 0x9000;

    /**
     * 未登录
     */
    public static final int NOT_LOGGED_IN = 0x10000;

    // ********************************************旧数据*********0x1000-0x10000****************************************


    /**
     * Bmob配置信息
     */
    public static final class BmobConfig {
        /**
         * Bmob应用ID
         */
        public static final String ApplicationID = "5640b7364e4d5f912b5fad1f882daad7";
        /**
         * Bmob短信验证码模板名称
         */
        public static final String BmobSMSTemplateName = "杂货铺子";
    }

    /**
     * 学院代码
     * 0x0001-0x0013
     */
    public static final class CollegeCode {
        /**
         * 机电工程学院
         */
        public static final int USER_COLLEGE_JIDIAN = 0x0001;
        /**
         * 机械与动力工程学院
         */
        public static final int USER_COLLEGE_JIXIE = 0x0002;
        /**
         * 材料科学与工程学院
         */
        public static final int USER_COLLEGE_CAILIAO = 0x0003;
        /**
         * 化工与环境学院
         */
        public static final int USER_COLLEGE_HUAGONG = 0x0004;
        /**
         * 信息与通信工程学院
         */
        public static final int USER_COLLEGE_TONGXIN = 0x0005;
        /**
         * 仪器与电子学院
         */
        public static final int USER_COLLEGE_YIQI = 0x0006;
        /**
         * 计算机与控制工程学院
         */
        public static final int USER_COLLEGE_JISUANJI = 0x0007;
        /**
         * 理学院
         */
        public static final int USER_COLLEGE_LIXUEYUAN = 0x0008;
        /**
         * 经济与管理学院
         */
        public static final int USER_COLLEGE_JINGGUAN = 0x0009;
        /**
         * 人文社会科学学院
         */
        public static final int USER_COLLEGE_RENWEN = 0x000A;
        /**
         * 体育学院
         */
        public static final int USER_COLLEGE_TIYUAN = 0x000B;
        /**
         * 艺术学院
         */
        public static final int USER_COLLEGE_YISHU = 0x000C;
        /**
         * 软件学院
         */
        public static final int USER_COLLEGE_RUANJIAN = 0x000D;
        /**
         * 研究生院
         */
        public static final int USER_COLLEGE_YANJIUSHENG = 0x000E;
        /**
         * 继续教育学院
         */
        public static final int USER_COLLEGE_JIXUJIAOYU = 0x000F;
        /**
         * 后备军官教育学院（武装部）
         */
        public static final int USER_COLLEGE_GUOFANGSHENG = 0x0010;
        /**
         * 国际教育学院（筹）
         */
        public static final int USER_COLLEGE_GUOJIJIAOYU = 0x0011;
        /**
         * 信息商务学院
         */
        public static final int USER_COLLEGE_XINSHANG = 0x0012;
        /**
         * 中北大学朔州校区
         */
        public static final int USER_COLLEGE_SHUOZHOUDIANLI = 0x0013;
    }


    /**
     * 用户身份权限
     * 0x1001-0x1006
     */
    public static final class UserPermission {
        /**
         * 认证用户-管理员
         */
        public static final int USER_PERMISSION_ADMIN = 0x1001;
        /**
         * 认证用户-班长
         */
        public static final int USER_PERMISSION_MONITOR = 0x1002;
        /**
         * 认证用户-学生
         */
        public static final int USER_PERMISSION_STUDENT = 0x1003;
        /**
         * 已验证手机号的注册用户
         */
        public static final int USER_PERMISSION_REGISTER_PHONE = 0x1004;
        /**
         * 游客
         */
        public static final int USRE_PERMISSION_VISTOR = 0x1006;
    }


    /**
     * SharedPreferences表单信息
     */
    public static final class SharedPreferencesConfig {
        /**
         * SharedPreferences保存的XML文件名
         * 保存方式：MODE_PRIVATE
         */
        public static final String FILENAME = "marvin_lop_perfs";
        /**
         * 用户手机号
         * String
         */
        public static final String PHONE_NUMBER = "phoneNumber";
        /**
         * 用户密码
         * String
         */
        public static final String USER_PASSWORD = "password";
        /**
         * 用户登录状态
         * boolean
         */
        public static final String USER_LOGIN_STATE = "isUserLogin";
        /**
         * 用户实名认证进度
         * String
         */
        public static final String USER_AUTHEN_PROGRESS = "isRealNameAuthen";
        /**
         * 用户权限代号
         * Integer
         */
        public static final String USER_PERMISSION = "userPermission";
        /**
         * 用户学院代号
         * Integer
         */
        public static final String USER_COLLEGE = "userCollege";
        /**
         * 班级
         * String
         */
        public static final String USER_CLASS = "userClass";
        /**
         * 学号
         * String
         */
        public static final String USER_STUDENT_ID = "userStudentId";
        /**
         * 姓名
         * String
         */
        public static final String USER_REAL_NAME = "userRealName";
        /**
         * 电子邮箱
         * String
         */
        public static final String USER_EMAIL_ADDRESS = "userEmailAddress";
        /**
         * 认证结果
         * Boolean
         */
        public static final String USER_AUTHEN_RESULT = "userAuthenResult";
        /**
         * 用户在服务器端的ID
         * String
         */
        public static final String USER_OBJECT_ID = "userObjectId";
        /**
         * 请求认证的用户的数量
         * int
         */
        public static final String Authen_Request_User_Number = "requestAuthenUserNumber";
    }

    /**
     * 用户实名认证状态
     */
    public static final class UserAuthenState {
        /**
         * 未认证
         */
        public static final String UNAUTHORIZED = "unauthorized";
        /**
         * 正在认证
         */
        public static final String BEING_CERTIFIED = "being_certified";
        /**
         * 已认证
         */
        public static final String CERTIFIED = "certified";
    }

    /**
     * Intent请求码
     * 0x2001-
     */
    public static final class IntentRequestCode {
        /**
         * 登录请求码
         */
        public static final int IN_LOGIN = 0x2001;
        /**
         * 进入用户中心请求码
         */
        public static final int IN_PERSONAL_CENTER = 0x2002;
        /**
         * 实名认证请求码
         */
        public static final int IN_REAL_NAME_AUTHEN = 0x2003;
        /**
         * 从认证请求列表跳转到认证处理界面的请求码
         */
        public static final int AuthenHandleList2HandleActivity = 0x2004;
        /**
         * 从个人中心界面跳转到用户认证信息显示界面
         */
        public static final int PersonalCenter2ShowCertifiedInfo = 0x2005;
        /**
         * 从个人中心界面跳转到物品发布界面
         */
        public static final int PersonalCenter2Publish = 0x2006;
        /**
         * 启动拍照程序01
         */
        public static final int TakePicture01 = 0x2007;
        /**
         * 启动裁剪程序01
         */
        public static final int CropPicture01 = 0x2008;
        /**
         * 启动拍照程序02
         */
        public static final int TakePicture02 = 0x2009;
        /**
         * 启动裁剪程序02
         */
        public static final int CropPicture02 = 0x200A;
        /**
         * 启动拍照程序03
         */
        public static final int TakePicture03 = 0x200B;
        /**
         * 启动裁剪程序03
         */
        public static final int CropPicture03 = 0x200C;
        /**
         * 启动拍照程序04
         */
        public static final int TakePicture04 = 0x200D;
        /**
         * 启动裁剪程序04
         */
        public static final int CropPicture04 = 0x200E;
        /**
         * 启动拍照程序05
         */
        public static final int TakePicture05 = 0x200F;
        /**
         * 启动裁剪程序05
         */
        public static final int CropPicture05 = 0x2010;
        /**
         * 从发布物品信息界面跳转到物品分类一级界面
         */
        public static final int Publish2PublishCategoryList = 0x2011;
        /**
         * 从分类一级界面跳转到分类二级界面
         */
        public static final int CategoryList2CategoryListNext = 0x2012;
        /**
         * 从求购界面跳转到物品分类一级界面
         */
        public static final int Buying2PublishCategoryList = 0x2013;
        /**
         * 从个人信息中心界面跳转到求购信息发布界面
         */
        public static final int PersonalCenter2Buying = 0x2014;
        /**
         * 从个人信息中心界面跳转到出租信息发布界面
         */
        public static final int PersonalCenter2Rent = 0x2015;
        /**
         * 从个人信息中心界面跳转到求租信息发布界面
         */
        public static final int PersonalCenter2Borrow = 0x2016;
        /**
         * 从首页跳转到物品详情页面
         */
        public static final int Index2ProductDetails = 0x2017;
        /**
         * 从搜索界面到物品详情界面
         */
        public static final int Search2ProductDetails = 0x2018;

    }

    /**
     * Intent响应码
     * 0x3001-
     */
    public static final class IntentResultCode {
        /**
         * 登录成功响应码
         */
        public static final int LOGIN_SUCCESS = 0x3001;
        /**
         * 成功退出登录响应码
         */
        public static final int SIGN_OUT_SUCCESS = 0x3002;
        /**
         * 未认证响应码
         */
        public static final int UNAUTHEN = 0x3003;
        /**
         * 正在认证响应码
         */
        public static final int BEINGCERTIFIED = 0x3004;
        /**
         * 已认证响应码
         */
        public static final int CERTIFIED = 0x3005;
        /**
         * 在认证界面点击界面上的返回按钮返回到个人信息中心界面
         */
        public static final int Authen2PersonalCenter = 0x3006;
        /**
         * 在认证界面点击提交按钮返回个人信息中心界面
         */
        public static final int AuthenCommitButton = 0x3007;
        /**
         * 在个人信息中心界面点击返回箭头返回到个人界面
         */
        public static final int PersonalCenter2Personal = 0x3008;
        /**
         * 在登录界面返回到个人界面
         */
        public static final int Login2Personal = 0x3009;
        /**
         * 从处理界面确认后返回到List界面响应码
         */
        public static final int HandleActivity2AuthenHandleList = 0x300A;
        /**
         * 从处理界面返回到List界面响应码
         */
        public static final int AuthenHandleBackToAuthenList = 0x300B;
        /**
         * 从认证请求处理列表返回到Personal界面
         */
        public static final int HandleAuthenList2Personal = 0x300C;
        /**
         * 从显示认证用户信息的界面返回用户个人中心
         */
        public static final int ShowCertifiedInfo2PersonalCenter = 0x300D;
        /**
         * 从物品信息发布界面返回到个人中心界面
         */
        public static final int PublishBack2PersonalCenter = 0x300E;
        /**
         * 发布物品信息成功后返回到个人中心界面
         */
        public static final int PublishSuccess = 0x300F;
        /**
         * 从分类一级界面返回发布物品信息界面
         */
        public static final int PublishCategoryListBack2Publish = 0x3010;
        /**
         * 从分类二级界面返回分类一级界面
         */
        public static final int CategoryListBack2CategoryList = 0x3011;
        /**
         * 获取物品分类信息返回到物品发布页面
         */
        public static final int CategoryName = 0x3012;
        /**
         * 从分类一级界面返回发布求购信息界面
         */
        public static final int PublishCategoryListBack2Buying = 0x3013;
        /**
         * 获取物品分类信息返回到求购信息发布页面
         */
        public static final int CategoryName2Buying = 0x3014;
        /**
         * 从求购界面返回到个人信息中心界面
         */
        public static final int BuyingBack2PersonalCenter = 0x3015;
        /**
         * 成功发布物品求购信息
         */
        public static final int BuyingSuccess = 0x3016;
        /**
         * 从发布出租信息界面返回到个人信息中心界面
         */
        public static final int RentBack2PersonalCenter = 0x3017;
        /**
         * 发布出租信息成功
         */
        public static final int RentSuccess = 0x3018;
        /**
         * 从发布求租信息界面返回到个人信息中心界面
         */
        public static final int BorrowBack2PersonalCenter = 0x3019;
        /**
         * 发布求租信息成功
         */
        public static final int BorrowSuccess = 0x301A;
        /**
         * 从物品详情页面返回首页
         */
        public static final int Product2Index = 0x301B;

    }

    /**
     * Notification的ID
     * 0x4001-
     */
    public static final class NotificationID {
        /**
         * 当用户通过认证后发送的通知的ID
         */
        public static final int Certified = 0x4001;
    }

    /**
     * 请求认证的用户数据库
     */
    public static final class AuthenRequestDataBaseConfig {
        /**
         * 数据库名称
         */
        public static final String DataBaseName = "AuthenRequestUserDataBase.db";
        /**
         * 表名
         */
        public static final String TableName = "AuthenRequestUserData";
        /**
         * _id
         */
        public static final String ID = "_id";
        /**
         * 字段--用户手机号
         */
        public static final String PhoneNumber = "userPhoneNumber";
        /**
         * 字段--用户权限
         */
        public static final String Permission = "userPermission";
        /**
         * 字段--学院
         */
        public static final String College = "userCollege";
        /**
         * 字段--班级
         */
        public static final String Class = "userClass";
        /**
         * 字段--学号
         */
        public static final String StudentID = "userStudentID";
        /**
         * 字段--姓名
         */
        public static final String RealName = "userRealName";
        /**
         * 字段--邮箱
         */
        public static final String EmailAddress = "userEmailAddress";
        /**
         * 字段--ObjectId
         */
        public static final String ObjectID = "userObjectId";
    }

    /**
     * 认证用户信息数据库
     */
    public static final class UserCertifiedInfoDataConfig {
        /**
         * 数据库名称
         */
        public static final String DataBaseName = "UserCertifiedInfoDataBase.db";
        /**
         * 表名
         */
        public static final String TableName = "CertifiedInfoData";
        /**
         * _id
         */
        public static final String ID = "_id";
        /**
         * 字段--用户手机号
         */
        public static final String PhoneNumber = "userPhoneNumber";
        /**
         * 字段--用户权限
         */
        public static final String Permission = "userPermission";
        /**
         * 字段--学院
         */
        public static final String College = "userCollege";
        /**
         * 字段--班级
         */
        public static final String Class = "userClass";
        /**
         * 字段--学号
         */
        public static final String StudentID = "userStudentID";
        /**
         * 字段--姓名
         */
        public static final String RealName = "userRealName";
        /**
         * 字段--邮箱
         */
        public static final String EmailAddress = "userEmailAddress";
        /**
         * 字段--ObjectId
         */
        public static final String ObjectID = "userObjectId";
    }

    /**
     * 商品信息数据库
     */
    public static final class CommodityInfoDataConfig {
        public static final String DataBaseName = "CommodityInfoDataBase.db";
        public static final String TableName = "CommodityInfoData";
        public static final String ObjectId = "ObjectId";
        public static final String CommodityPrice = "CommodityPrice";
        public static final String CommodityTitle = "CommodityTitle";
        public static final String UserObjectId = "UserObjectId";
        public static final String CommodityCategory1 = "CommodityCategory1";
        public static final String CommodityCategory2 = "CommodityCategory2";
        public static final String CommodityDescribe = "CommodityDescribe";
        public static final String CommodityOriPrice = "CommodityOriPrice";
        public static final String CommodityPicPath1 = "CommodityPicPath1";
        public static final String CommodityPicPath2 = "CommodityPicPath2";
        public static final String CommodityPicPath3 = "CommodityPicPath3";
        public static final String CommodityPicPath4 = "CommodityPicPath4";
        public static final String CommodityPicPath5 = "CommodityPicPath5";
        public static final String CreateAt = "CreateAt";
        public static final String UserPhoneNumber = "UserPhoneNumber";
    }

    /**
     * 查询结果数据库
     */
    public static final class SearchResultDataConfig {
        public static final String DataBaseName = "SearchResultDataBase.db";
        public static final String TableName = "SearchResultData";
        public static final String ObjectId = "ObjectId";
        public static final String CommodityPrice = "CommodityPrice";
        public static final String CommodityTitle = "CommodityTitle";
        public static final String UserObjectId = "UserObjectId";
        public static final String CommodityCategory1 = "CommodityCategory1";
        public static final String CommodityCategory2 = "CommodityCategory2";
        public static final String CommodityDescribe = "CommodityDescribe";
        public static final String CommodityOriPrice = "CommodityOriPrice";
        public static final String CommodityPicPath1 = "CommodityPicPath1";
        public static final String CommodityPicPath2 = "CommodityPicPath2";
        public static final String CommodityPicPath3 = "CommodityPicPath3";
        public static final String CommodityPicPath4 = "CommodityPicPath4";
        public static final String CommodityPicPath5 = "CommodityPicPath5";
        public static final String CreateAt = "CreateAt";
        public static final String UserPhoneNumber = "UserPhoneNumber";
    }

    /***
     * 历史记录数据表
     */
    public static final class HistoryInfoDataConfig {
        public static final String DataBaseName = "HistoryInfoDataBase.db";
        public static final String TableName = "HistoryInfoData";
        public static final String ObjectId = "ObjectId";
        public static final String UserObjectid = "UserObjectid";
        public static final String CommodityCategory1 = "CommodityCategory1";
        public static final String CommodityObjectid = "CommodityObjectid";
        public static final String CommodityTitle = "commodityTitle";
        public static final String UserPhoneNumber = "UserPhoneNumber";
        public static final String SellerPhoneNumber = "sellerPhoneNumber";
        public static final String CreateAt = "CreateAt";
    }

    /**
     * 推送商品信息
     */
    public static final class PushInfoSharePreferencesConfig {
        public static final String FileName = "marvin_lop_pushInfo";
        public static final String CommodityTitle = "CommodityTitle";
        public static final String ComodityDescribe = "ComodityDescribe";
        public static final String commodityPicPath1 = "commodityPicPath1";
        public static final String commodityPicPath2 = "commodityPicPath2";
        public static final String commodityPicPath3 = "commodityPicPath3";
        public static final String commodityPicPath4 = "commodityPicPath4";
        public static final String commodityPicPath5 = "commodityPicPath5";
        public static final String commodityOriPrice = "commodityOriPrice";
        public static final String commodityPrice = "commodityPrice";
        public static final String commodityCategory1 = "commodityCategory1";
        public static final String commodityCategory2 = "commodityCategory2";
        public static final String userObjectId = "userObjectId";
        public static final String userPhoneNumber = "userPhoneNumber";
        public static final String commodityState = "commodityState";
        public static final String objectid = "objectid";
        public static final String createAt = "createAt";

    }


}
