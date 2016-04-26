package com.marvin.lop.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.marvin.lop.bean.AuthenRequestList;
import com.marvin.lop.bean.BeanConstants;
import com.marvin.lop.bean.CertifiedUsers;
import com.marvin.lop.config.Constants;
import com.marvin.lop.database.AuthenRequestDataBaseHelper;
import com.marvin.lop.database.GetUserCertifiedInfoDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by Marvin on 2016/4/16.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 从服务器获取数据
 */
public class QueryFromServer {

    private static final String TAG = QueryFromServer.class.getSimpleName();

    /**
     * 管理员权限的用户根据权限查询请求认证的用户数据并存储在本地数据库中
     *
     * @param permission 请求认证的用户权限
     * @param progress   请求认证的用户认证进度
     * @param result     请求认证的用户的认证结果
     */
    public void queryAuthenDataFromServer(final Context context, int permission, String progress, Boolean result) {
        BmobQuery<CertifiedUsers> userQueryPermission = new BmobQuery<>();
        // 认证权限为班长的用户
        userQueryPermission.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserPermission, permission);
        BmobQuery<CertifiedUsers> userQueryProgress = new BmobQuery<>();
        // 认证进度为正在认证的用户
        userQueryProgress.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserAuthenProgress, progress);
        BmobQuery<CertifiedUsers> userQueryResult = new BmobQuery<>();
        // 认证结果为false的用户
        userQueryResult.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserAuthenResult, result);
        // 把查询条件组合成完成的and条件
        List<BmobQuery<CertifiedUsers>> andQuerys = new ArrayList<>();
        andQuerys.add(userQueryPermission);
        andQuerys.add(userQueryProgress);
        andQuerys.add(userQueryResult);
        // 查询符合条件的用户
        BmobQuery<CertifiedUsers> query = new BmobQuery<>();
        query.and(andQuerys);
        query.findObjects(context, new FindListener<CertifiedUsers>() {
            @Override
            public void onSuccess(List<CertifiedUsers> list) {
                // 创建本地数据库表用来保存数据，如果该表已经存在，就检查是否为空
                // 如果不为空，就在写入的时候检查objectid是否有相同的，有相同的就不写入，
                // 不同的才写入，保证数据库中的数据不重复，数据库直接映射到ListView上，如果
                // 该条认证请求通过，就将这条数据从数据库中删除并提交给服务器进行修改
                AuthenRequestDataBaseHelper dbHelper = new AuthenRequestDataBaseHelper(context,
                        Constants.AuthenRequestDataBaseConfig.DataBaseName, null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                Cursor cursor = db.rawQuery("select * from " + Constants.AuthenRequestDataBaseConfig.TableName, null);
                if (cursor.getCount() < 1) {
                    Log.i(TAG, "数据库为空");
                    for (CertifiedUsers user : list) {
                        Log.i(TAG, "数据库为空，直接写入数据");
                        Log.i(TAG, "QueryData获取用户数据的大小:" + list.size());
                        values.put(Constants.AuthenRequestDataBaseConfig.PhoneNumber, user.getUserPhoneNumber());
                        values.put(Constants.AuthenRequestDataBaseConfig.Class, user.getUserClass());
                        values.put(Constants.AuthenRequestDataBaseConfig.College, user.getUserCollege());
                        values.put(Constants.AuthenRequestDataBaseConfig.EmailAddress, user.getUserEmailAddress());
                        values.put(Constants.AuthenRequestDataBaseConfig.RealName, user.getUserRealName());
                        values.put(Constants.AuthenRequestDataBaseConfig.Permission, user.getUserPermission());
                        values.put(Constants.AuthenRequestDataBaseConfig.StudentID, user.getUserStudentId());
                        values.put(Constants.AuthenRequestDataBaseConfig.ObjectID, user.getObjectId());
                        db.insert(Constants.AuthenRequestDataBaseConfig.TableName, null, values);// 插入数据
                        values.clear();//清空
                    }
                } else {
                    //如果数据库不为空，就要把服务器请求来的数据跟数据库中的数据的ObjectId进行比较，重复就不再添加，不存在就添加
                    Log.i(TAG, "数据库不为空");
                    for (CertifiedUsers user : list) {
                        Log.i(TAG, "QueryData获取用户数据的大小:" + list.size());
                        String objectid = user.getObjectId();
                        Cursor c = db.rawQuery("select " + Constants.AuthenRequestDataBaseConfig.ObjectID +
                                " from " + Constants.AuthenRequestDataBaseConfig.TableName + " where "
                                + Constants.AuthenRequestDataBaseConfig.ObjectID + "='" + objectid + "'", null);
                        if (c.getCount() < 1) {
                            Log.i(TAG, "该数据不存在数据库中，可以插入");
                            values.put(Constants.AuthenRequestDataBaseConfig.PhoneNumber, user.getUserPhoneNumber());
                            values.put(Constants.AuthenRequestDataBaseConfig.Class, user.getUserClass());
                            values.put(Constants.AuthenRequestDataBaseConfig.College, user.getUserCollege());
                            values.put(Constants.AuthenRequestDataBaseConfig.EmailAddress, user.getUserEmailAddress());
                            values.put(Constants.AuthenRequestDataBaseConfig.RealName, user.getUserRealName());
                            values.put(Constants.AuthenRequestDataBaseConfig.Permission, user.getUserPermission());
                            values.put(Constants.AuthenRequestDataBaseConfig.StudentID, user.getUserStudentId());
                            values.put(Constants.AuthenRequestDataBaseConfig.ObjectID, user.getObjectId());
                            db.insert(Constants.AuthenRequestDataBaseConfig.TableName, null, values);// 插入数据
                            values.clear();//清空
                        } else {
                            Log.i(TAG, "该数据已经存在数据库中，不能插入");
                        }
                        c.close();
                    }
                }
                cursor.close();
                db.close();
                dbHelper.close();
            }

            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "获取数据失败失败");
            }
        });
    }

    /**
     * 班长权限的用户获取跟自己班级号相同的学生用户信息
     *
     * @param context
     * @param permission
     * @param progress
     * @param result
     * @param classNumber
     */
    public void queryAuthenDataFromServer(final Context context, int permission, String progress, Boolean result, String classNumber) {
        // 认证权限为学生的用户
        BmobQuery<CertifiedUsers> userQueryPermission = new BmobQuery<>();
        userQueryPermission.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserPermission, permission);
        // 认证进度为正在认证的用户
        BmobQuery<CertifiedUsers> userQueryProgress = new BmobQuery<>();
        userQueryProgress.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserAuthenProgress, progress);
        // 认证结果为false的用户
        BmobQuery<CertifiedUsers> userQueryResult = new BmobQuery<>();
        userQueryResult.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserAuthenResult, result);
        // 属于班长所在班级的学生
        BmobQuery<CertifiedUsers> userQueryClass = new BmobQuery<>();
        userQueryClass.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserClass, classNumber);
        // 把查询条件组合成完成的and条件
        List<BmobQuery<CertifiedUsers>> andQuerys = new ArrayList<>();
        andQuerys.add(userQueryPermission);
        andQuerys.add(userQueryProgress);
        andQuerys.add(userQueryResult);
        andQuerys.add(userQueryClass);
        // 查询符合条件的用户
        BmobQuery<CertifiedUsers> query = new BmobQuery<>();
        query.and(andQuerys);
        query.findObjects(context, new FindListener<CertifiedUsers>() {
            @Override
            public void onSuccess(List<CertifiedUsers> list) {
                // 创建本地数据库表用来保存数据，如果该表已经存在，就检查是否为空
                // 如果不为空，就在写入的时候检查objectid是否有相同的，有相同的就不写入，
                // 不同的才写入，保证数据库中的数据不重复，数据库直接映射到ListView上，如果
                // 该条认证请求通过，就将这条数据从数据库中删除并提交给服务器进行修改
                AuthenRequestDataBaseHelper dbHelper = new AuthenRequestDataBaseHelper(context,
                        Constants.AuthenRequestDataBaseConfig.DataBaseName, null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                Cursor cursor = db.rawQuery("select * from " + Constants.AuthenRequestDataBaseConfig.TableName, null);
                if (cursor.getCount() < 1) {
                    Log.i(TAG, "数据库为空");
                    for (CertifiedUsers user :list) {
                        Log.i(TAG, "数据库为空，直接写入数据");
                        Log.i(TAG, "QueryData获取用户数据的大小:" + list.size());
                        values.put(Constants.AuthenRequestDataBaseConfig.PhoneNumber, user.getUserPhoneNumber());
                        values.put(Constants.AuthenRequestDataBaseConfig.Class, user.getUserClass());
                        values.put(Constants.AuthenRequestDataBaseConfig.College, user.getUserCollege());
                        values.put(Constants.AuthenRequestDataBaseConfig.EmailAddress, user.getUserEmailAddress());
                        values.put(Constants.AuthenRequestDataBaseConfig.RealName, user.getUserRealName());
                        values.put(Constants.AuthenRequestDataBaseConfig.Permission, user.getUserPermission());
                        values.put(Constants.AuthenRequestDataBaseConfig.StudentID, user.getUserStudentId());
                        values.put(Constants.AuthenRequestDataBaseConfig.ObjectID, user.getObjectId());
                        db.insert(Constants.AuthenRequestDataBaseConfig.TableName, null, values);// 插入数据
                        values.clear();//清空
                    }
                } else {
                    //如果数据库不为空，就要把服务器请求来的数据跟数据库中的数据的ObjectId进行比较，重复就不再添加，不存在就添加
                    Log.i(TAG, "数据库不为空");
                    for (CertifiedUsers user : list) {
                        Log.i(TAG, "QueryData获取用户数据的大小:" + list.size());
                        String objectid = user.getObjectId();
                        Cursor c = db.rawQuery("select " + Constants.AuthenRequestDataBaseConfig.ObjectID +
                                " from " + Constants.AuthenRequestDataBaseConfig.TableName + " where "
                                + Constants.AuthenRequestDataBaseConfig.ObjectID + "='" + objectid + "'", null);
                        if (c.getCount() < 1) {
                            Log.i(TAG, "该数据不存在数据库中，可以插入");
                            values.put(Constants.AuthenRequestDataBaseConfig.PhoneNumber, user.getUserPhoneNumber());
                            values.put(Constants.AuthenRequestDataBaseConfig.Class, user.getUserClass());
                            values.put(Constants.AuthenRequestDataBaseConfig.College, user.getUserCollege());
                            values.put(Constants.AuthenRequestDataBaseConfig.EmailAddress, user.getUserEmailAddress());
                            values.put(Constants.AuthenRequestDataBaseConfig.RealName, user.getUserRealName());
                            values.put(Constants.AuthenRequestDataBaseConfig.Permission, user.getUserPermission());
                            values.put(Constants.AuthenRequestDataBaseConfig.StudentID, user.getUserStudentId());
                            values.put(Constants.AuthenRequestDataBaseConfig.ObjectID, user.getObjectId());
                            db.insert(Constants.AuthenRequestDataBaseConfig.TableName, null, values);// 插入数据
                            values.clear();//清空
                        } else {
                            Log.i(TAG, "该数据已经存在数据库中，不能插入");
                        }
                        c.close();
                    }
                }
                cursor.close();
                db.close();
                dbHelper.close();
            }

            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "获取数据失败失败");
            }
        });
    }

    /**
     * 管理员权限查询需要认证的新用户
     *
     * @param context
     * @param permission
     * @param progress
     * @param result
     */
    public void queryAuthenNumberFromServer(final Context context, int permission, String progress, Boolean result) {
        // 查询认证进度为正在认证、认证权限为班长、认证结果为false的用户
        BmobQuery<CertifiedUsers> userQueryPermission = new BmobQuery<>();
        // 认证权限为班长\学生的用户
        userQueryPermission.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserPermission, permission);
        BmobQuery<CertifiedUsers> userQueryProgress = new BmobQuery<>();
        // 认证进度为正在认证的用户
        userQueryProgress.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserAuthenProgress, progress);
        BmobQuery<CertifiedUsers> userQueryResult = new BmobQuery<>();
        // 认证结果为false的用户
        userQueryResult.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserAuthenResult, result);
        // 把查询条件组合成完成的and条件
        List<BmobQuery<CertifiedUsers>> andQuerys = new ArrayList<>();
        andQuerys.add(userQueryPermission);
        andQuerys.add(userQueryProgress);
        andQuerys.add(userQueryResult);
        // 查询符合条件的用户
        BmobQuery<CertifiedUsers> query = new BmobQuery<>();
        query.and(andQuerys);
        query.findObjects(context, new FindListener<CertifiedUsers>() {
            @Override
            public void onSuccess(List<CertifiedUsers> list) {
                // 显示返回符合查询条件的用户的数量，在通知栏显示
                SharedPreferences sharedPreferences = context.getSharedPreferences(Constants
                        .SharedPreferencesConfig.FILENAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // 请求认证的用户数量在每次查询后都刷新，存储在本地
                editor.putInt(Constants.SharedPreferencesConfig.Authen_Request_User_Number,
                        list.size());
                editor.commit();
                Log.i(TAG, "显示符合查询条件用户数量：" + list.size());
            }

            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "查询失败");
            }
        });
    }

    /**
     * 班长查询需要认证的新学生
     *
     * @param context
     * @param permission
     * @param progress
     * @param result
     */
    public void queryAuthenNumberFromServer(final Context context, int permission, String progress, Boolean result, String classNum) {
        // 查询认证进度为正在认证、认证权限为学生、认证结果为false，班级号与班长 相同的用户
        BmobQuery<CertifiedUsers> userQueryPermission = new BmobQuery<>();
        // 认证权限为班长\学生的用户
        userQueryPermission.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserPermission, permission);
        BmobQuery<CertifiedUsers> userQueryProgress = new BmobQuery<>();
        // 认证进度为正在认证的用户
        userQueryProgress.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserAuthenProgress, progress);
        BmobQuery<CertifiedUsers> userQueryResult = new BmobQuery<>();
        // 认证结果为false的用户
        userQueryResult.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserAuthenResult, result);
        BmobQuery<CertifiedUsers> userQueryClass = new BmobQuery<>();
        userQueryClass.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserClass, classNum);
        // 把查询条件组合成完成的and条件
        List<BmobQuery<CertifiedUsers>> andQuerys = new ArrayList<>();
        andQuerys.add(userQueryPermission);
        andQuerys.add(userQueryProgress);
        andQuerys.add(userQueryResult);
        andQuerys.add(userQueryClass);
        // 查询符合条件的用户
        BmobQuery<CertifiedUsers> query = new BmobQuery<>();
        query.and(andQuerys);
        query.findObjects(context, new FindListener<CertifiedUsers>() {
            @Override
            public void onSuccess(List<CertifiedUsers> list) {
                // 显示返回符合查询条件的用户的数量，在通知栏显示
                SharedPreferences sharedPreferences = context.getSharedPreferences(Constants
                        .SharedPreferencesConfig.FILENAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Log.i(TAG, "查询成功");
                editor.putInt(Constants.SharedPreferencesConfig.Authen_Request_User_Number,
                        list.size());
                editor.commit();
                Log.i(TAG, "显示符合查询条件用户数量：" + list.size());
            }

            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "查询失败");
            }
        });
    }

    /**
     * 查询认证结果
     *
     * @param context
     * @param userObjectId
     */
    public void queryAuthenStateFromServer(final Context context, String userObjectId) {
        if (userObjectId != null) {
            BmobQuery<CertifiedUsers> userQuery = new BmobQuery<>();
            userQuery.getObject(context, userObjectId,
                    new GetListener<CertifiedUsers>() {
                        @Override
                        public void onSuccess(CertifiedUsers certifiedUsers) {
                            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants
                                    .SharedPreferencesConfig.FILENAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Log.i(TAG, "用户查询个人认证状态中。。。");
                            editor.putBoolean(Constants.SharedPreferencesConfig.USER_AUTHEN_RESULT,
                                    certifiedUsers.getUserAuthenResult());
                            editor.commit();
                            Log.i(TAG, "用户查询的个人认证结果为：" + certifiedUsers.getUserAuthenResult());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.i(TAG, "查询失败，失败原因：" + s + "失败代号:" + i);
                        }
                    });
        } else {
            Log.i(TAG, "用户的ObjectID为空");
        }
    }

    /**
     * 根据班长的objectid来获取班长所在班级号
     *
     * @param context
     * @param objectid
     */
    public void getMonitorClass(final Context context, String objectid) {
        if (objectid != null) {
            BmobQuery<CertifiedUsers> userQuery = new BmobQuery<>();
            userQuery.getObject(context, objectid,
                    new GetListener<CertifiedUsers>() {
                        @Override
                        public void onSuccess(CertifiedUsers certifiedUsers) {
                            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants
                                    .SharedPreferencesConfig.FILENAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Log.i(TAG, "班长班号查询中。。。");
                            editor.putString(Constants.SharedPreferencesConfig.USER_CLASS, certifiedUsers.getUserClass());
                            editor.commit();
                            Log.i(TAG, "班长班号查询结果：" + certifiedUsers.getUserClass());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.i(TAG, "查询失败，失败原因：" + s + "失败代号:" + i);
                        }
                    });
        } else {
            Log.i(TAG, "用户的ObjectID为空");
        }
    }

    /**
     * 获取用户的认证进度
     *
     * @param context
     * @param objectId
     */
    public void getUserAuthenProgress(final Context context, String objectId) {
        Log.i(TAG, "ObjectId:" + objectId);
        if (objectId != null) {
            BmobQuery<CertifiedUsers> userQuery = new BmobQuery<>();
            userQuery.getObject(context, objectId,
                    new GetListener<CertifiedUsers>() {
                        @Override
                        public void onSuccess(CertifiedUsers certifiedUsers) {
                            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants
                                    .SharedPreferencesConfig.FILENAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Log.i(TAG, "用户认证进度查询中。。。");
                            editor.putString(Constants.SharedPreferencesConfig.USER_AUTHEN_PROGRESS,
                                    certifiedUsers.getUserAuthenProgress());
                            editor.commit();
                            Log.i(TAG, "用户认证进度查询结果：" + certifiedUsers.getUserAuthenProgress());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.i(TAG, "查询失败，失败原因：" + s + "失败代号:" + i);
                        }
                    });
        } else {
            Log.i(TAG, "用户的ObjectID为空");
        }
    }

    /**
     * 根据当前登录用户的ObjectID来获取服务器端认证的身份权限
     *
     * @param context
     * @param objectId
     */
    public void getUserPermissionFromServer(final Context context, String objectId) {
        if (objectId != null) {
            BmobQuery<CertifiedUsers> userQuery = new BmobQuery<>();
            userQuery.getObject(context, objectId,
                    new GetListener<CertifiedUsers>() {
                        @Override
                        public void onSuccess(CertifiedUsers certifiedUsers) {
                            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants
                                    .SharedPreferencesConfig.FILENAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Log.i(TAG, "用户权限查询中。。。");
                            editor.putInt(Constants.SharedPreferencesConfig.USER_PERMISSION,
                                    certifiedUsers.getUserPermission());
                            editor.commit();
                            Log.i(TAG, "用户权限查询结果：" + certifiedUsers.getUserPermission());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.i(TAG, "查询失败，失败原因：" + s + "失败代号:" + i);
                        }
                    });
        } else {
            Log.i(TAG, "用户的ObjectID为空");
        }
    }

    /**
     * 根据传入的ObjectID来获取当前登录用户的认证信息并保存在数据库中
     * @param context 上下文
     * @param objectId 用户ID
     */
    public void getUserCertifiedInfo(final Context context, String objectId) {
        // 查询objectID为当前id的用户
        BmobQuery<CertifiedUsers> query = new BmobQuery<>();
        query.getObject(context, objectId, new GetListener<CertifiedUsers>() {
            @Override
            public void onSuccess(CertifiedUsers certifiedUsers) {
                Log.i(TAG, "获取用户认证信息成功");
                // 创建本地数据库表用来保存用户的认证信息,如果该表存在，就检查是否为空
                // 如果不为空，就在写入数据的时候检查ObjectID是否相同，有不相同的就写入
                GetUserCertifiedInfoDataBaseHelper dbHelper = new GetUserCertifiedInfoDataBaseHelper(context,
                        Constants.UserCertifiedInfoDataConfig.DataBaseName, null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                Cursor cursor = db.rawQuery("select * from " + Constants.UserCertifiedInfoDataConfig.TableName, null);
                if (cursor.getCount() < 1) {
                    Log.i(TAG, "数据库为空，直接写入数据");
                    values.put(Constants.UserCertifiedInfoDataConfig.PhoneNumber, certifiedUsers.getUserPhoneNumber());
                    values.put(Constants.UserCertifiedInfoDataConfig.Class, certifiedUsers.getUserClass());
                    values.put(Constants.UserCertifiedInfoDataConfig.College, certifiedUsers.getUserCollege());
                    values.put(Constants.UserCertifiedInfoDataConfig.EmailAddress, certifiedUsers.getUserEmailAddress());
                    values.put(Constants.UserCertifiedInfoDataConfig.RealName, certifiedUsers.getUserRealName());
                    values.put(Constants.UserCertifiedInfoDataConfig.Permission, certifiedUsers.getUserPermission());
                    values.put(Constants.UserCertifiedInfoDataConfig.StudentID, certifiedUsers.getUserStudentId());
                    values.put(Constants.UserCertifiedInfoDataConfig.ObjectID, certifiedUsers.getObjectId());
                    db.insert(Constants.UserCertifiedInfoDataConfig.TableName, null, values);// 插入数据
                    values.clear();// 清空
                } else {
                    Log.i(TAG, "数据库不为空");
                    String objectId = certifiedUsers.getObjectId();
                    Cursor c = db.rawQuery("select " + Constants.UserCertifiedInfoDataConfig.ObjectID +
                            " from " + Constants.UserCertifiedInfoDataConfig.TableName + " where "
                            + Constants.UserCertifiedInfoDataConfig.ObjectID + "='" + objectId + "'", null);
                    if (c.getCount() < 1) {
                        Log.i(TAG, "该数据不存在，可以插入");
                        values.put(Constants.UserCertifiedInfoDataConfig.PhoneNumber, certifiedUsers.getUserPhoneNumber());
                        values.put(Constants.UserCertifiedInfoDataConfig.Class, certifiedUsers.getUserClass());
                        values.put(Constants.UserCertifiedInfoDataConfig.College, certifiedUsers.getUserCollege());
                        values.put(Constants.UserCertifiedInfoDataConfig.EmailAddress, certifiedUsers.getUserEmailAddress());
                        values.put(Constants.UserCertifiedInfoDataConfig.RealName, certifiedUsers.getUserRealName());
                        values.put(Constants.UserCertifiedInfoDataConfig.Permission, certifiedUsers.getUserPermission());
                        values.put(Constants.UserCertifiedInfoDataConfig.StudentID, certifiedUsers.getUserStudentId());
                        values.put(Constants.UserCertifiedInfoDataConfig.ObjectID, certifiedUsers.getObjectId());
                        db.insert(Constants.UserCertifiedInfoDataConfig.TableName, null, values);// 插入数据
                        values.clear();// 清空
                    } else {
                        Log.i(TAG, "该数据已经存在数据库中，不能插入");
                    }
                    c.close();
                }
                cursor.close();
                db.close();
                dbHelper.close();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }
}
