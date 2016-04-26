package com.marvin.lop.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.marvin.lop.bean.CertifiedUsers;
import com.marvin.lop.config.Constants;
import com.marvin.lop.receiver.HandleCertifiedReceiver;
import com.marvin.lop.task.LoadingAuthenRequestTask;
import com.marvin.lop.ui.HandleNewUserAuthenList;
import com.marvin.lop.ui.PersonalCenterActivity;
import com.marvin.lop.ui.linstener.OnDataLoadSuccessLinstener;
import com.marvin.lop.utils.QueryFromServer;
import com.marvin.lop.utils.ShowNotification;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Marvin on 2016/4/14.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 描述： 这个服务用来检测已登录并且处于认证状态的用户是否已经通过认证，还处理服务器是否还有新的用户需要来认证
 */
public class HandleCertifiedService extends Service {

    private static final String TAG = HandleCertifiedService.class.getSimpleName();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "查询服务启动成功");
                // TODO: 2016/4/14 根据已登录的用户状态从服务器查询是否通过认证
                // 如果通过认证就通过Handler来发送到主线程来处理UI变化
                // 获取用户当前的登录状态和认证进度
                boolean isLogin = sharedPreferences.getBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, false);
                Log.i(TAG, "登录情况：" + isLogin);
                String authenState = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_AUTHEN_PROGRESS, Constants
                        .UserAuthenState.UNAUTHORIZED);
                // 获取已登录用户Id
                String userObjectId = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, null);
                if (isLogin != false) {
                    Log.i(TAG, "成功登录，正在查询认证信息...");
                    switch (authenState) {
                        case Constants.UserAuthenState.BEING_CERTIFIED:
                            // 如果用户已登录并且认证进度是正在认证，就从服务器查询该用户是否已经通过认证
                            new QueryFromServer()
                                    .queryAuthenStateFromServer(HandleCertifiedService.this, userObjectId);
                            if (sharedPreferences.getBoolean(Constants.SharedPreferencesConfig.USER_AUTHEN_RESULT, false)) {
                                // 如果通过了认证，就把本地的认证进度改为已认证状态，
                                editor.putString(Constants.SharedPreferencesConfig.USER_AUTHEN_PROGRESS,
                                        Constants.UserAuthenState.CERTIFIED);
                                editor.putBoolean(Constants.SharedPreferencesConfig.USER_AUTHEN_RESULT,
                                        true);
                                editor.commit();
                                // 将服务器端的认证进度改为Certified
                                CertifiedUsers certifiedUsers = new CertifiedUsers();
                                certifiedUsers.setUserAuthenProgress(Constants.UserAuthenState.CERTIFIED);
                                certifiedUsers.update(HandleCertifiedService.this, sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, null),
                                        new UpdateListener() {
                                            @Override
                                            public void onSuccess() {
                                                Log.i(TAG, "服务器端认证进度更新成功");
                                            }

                                            @Override
                                            public void onFailure(int i, String s) {
                                                Log.i(TAG, "服务器端认证进度更新失败" + s);
                                            }
                                        });
                                // 给用户发送一条通知消息，通知用户已经通过认证
                                ShowNotification.handleNotification(HandleCertifiedService.this,
                                        "杂货铺子认证结果", "你已经通过实名认证！", "杂货铺子认证结果", PersonalCenterActivity.class);
                            } else {

                            }
                            break;
                        case Constants.UserAuthenState.CERTIFIED:
                            Log.i(TAG, "通过认证Certified,正在查询其他用户的认证请求...");
                            // 如果用户已经登录并且也通过了认证，就从根据自己的权限从服务器查询是否有新的用户
                            // 需要自己验证,弹出一个通知栏，通知栏显示有多少条认证请求，然后用户点击通知栏
                            // 进入一个认证请求列表界面ListView，每一个List都对应详细的认证页面和按钮，用户点击按钮
                            // 进行通过认证和不通过认证的选择，选择的结果返回给服务器，用户再通过请求查询可以查询自己是否通过认证
                            new QueryFromServer().getUserPermissionFromServer(HandleCertifiedService.this,
                                    userObjectId);
                            int userPermission = sharedPreferences.getInt(Constants.SharedPreferencesConfig.USER_PERMISSION, -1);
                            Log.i(TAG, "获取登录用户的权限" + userPermission);
                            if (userPermission != -1) {
                                switch (userPermission) {
                                    case Constants.UserPermission.USER_PERMISSION_ADMIN:
                                        // 管理员权限
                                        // 显示返回符合查询条件的用户的数量，在通知栏显示
                                        new QueryFromServer().queryAuthenNumberFromServer(HandleCertifiedService.this,
                                                Constants.UserPermission.USER_PERMISSION_MONITOR,
                                                Constants.UserAuthenState.BEING_CERTIFIED, false);
                                        int num = sharedPreferences.getInt(Constants
                                                .SharedPreferencesConfig.Authen_Request_User_Number, -1);
                                        if (num != -1) {
                                            if (num != 0) {
                                                new QueryFromServer().queryAuthenDataFromServer(HandleCertifiedService.this,
                                                        Constants.UserPermission.USER_PERMISSION_MONITOR,
                                                        Constants.UserAuthenState.BEING_CERTIFIED, false);
                                                ShowNotification.handleNotification(HandleCertifiedService.this,
                                                        "班长请求认证", "请求认证数量" + num,
                                                        "班长请求认证", HandleNewUserAuthenList.class);


                                                // 使用异步加载去获取服务器数据
//                                                LoadingAuthenRequestTask loadAuthenRequestTask = new LoadingAuthenRequestTask(HandleCertifiedService.this);
//                                                loadAuthenRequestTask.setOnDataLoadSuccessLinstener(new OnDataLoadSuccessLinstener() {
//                                                    @Override
//                                                    public void onSuccess(Boolean isSuccess) {
//                                                        if (isSuccess) {
//
//                                                        }
//                                                    }
//                                                });
//                                                loadAuthenRequestTask.execute();

                                            } else {
                                                Log.i(TAG, "没有用户请求认证");
                                            }
                                        } else {
                                            Log.i(TAG, "本地不存在认证用户请求数量");
                                        }
                                        break;
                                    case Constants.UserPermission.USER_PERMISSION_MONITOR:
                                        // 班长权限
                                        // 显示返回符合查询条件的用户的数量，在通知栏显示
                                        new QueryFromServer().getMonitorClass(HandleCertifiedService.this, userObjectId);
                                        new QueryFromServer().queryAuthenNumberFromServer(HandleCertifiedService.this,
                                                Constants.UserPermission.USER_PERMISSION_STUDENT, Constants.UserAuthenState.BEING_CERTIFIED
                                                , false, sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_CLASS, null));
                                        int num1 = sharedPreferences.getInt(
                                                Constants.SharedPreferencesConfig.Authen_Request_User_Number, -1);
                                        if (num1 != -1) {
                                            if (num1 != 0) {
                                                new QueryFromServer().queryAuthenDataFromServer(HandleCertifiedService.this,
                                                        Constants.UserPermission.USER_PERMISSION_STUDENT,
                                                        Constants.UserAuthenState.BEING_CERTIFIED, false,
                                                        sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_CLASS, null));
                                                ShowNotification.handleNotification(HandleCertifiedService.this,
                                                        "学生请求认证", "请求认证数量" + num1,
                                                        "学生请求认证", HandleNewUserAuthenList.class);





                                                // 使用异步加载去获取服务器数据
//                                                LoadingAuthenRequestTask loadAuthenRequestTask = new LoadingAuthenRequestTask(HandleCertifiedService.this);
//                                                loadAuthenRequestTask.setOnDataLoadSuccessLinstener(new OnDataLoadSuccessLinstener() {
//                                                    @Override
//                                                    public void onSuccess(Boolean isSuccess) {
//                                                        if (isSuccess) {
//
//                                                        }
//
//                                                    }
//                                                });
//                                                loadAuthenRequestTask.execute();
                                            } else {
                                                Log.i(TAG, "没有用户请求认证");
                                            }
                                        } else {
                                            Log.i(TAG, "本地不存在认证用户请求数量");
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                            break;
                        case Constants.UserAuthenState.UNAUTHORIZED:
                            // 如果用户已登录但是没有进行认证，就不作处理
                            break;
                        default:
                            break;
                    }
                } else {
                    Log.i(TAG, "没有成功登录，本地数据文件记录出问题");
                }
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        int time = 5 * 60 * 1000;// 这是5分钟的毫秒数，每5分钟启动一次服务从服务器查询数据
        int time = (int) (0.1 * 60 * 1000); // 这是10秒的毫秒数，用于测试，每10秒启动一次服务用来查询数据
        long triggerAtTime = SystemClock.elapsedRealtime() + time;
        Intent i = new Intent(this, HandleCertifiedReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


}
