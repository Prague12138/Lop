package com.marvin.lop.utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.marvin.lop.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marvin on 2016/3/29.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class CommonTools {

    /**
     * 短暂显示Toast消息
     *
     * @param context 上下文内容
     * @param message 消息内容
     */
    public static void showShortToast(Context context, String message) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_toast, null);
        TextView text = (TextView) view.findViewById(R.id.toast_message);
        text.setText(message);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 300);
        toast.setView(view);
        toast.show();
    }

    /**
     * 根据手机分辨率从dp转成px
     *
     * @param context 上下文内容
     * @param dpValue 原dp值
     * @return 转换后的px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机分辨率从px（像素）的单位转成dp
     *
     * @param context 上下文内容
     * @param pxValue 原px值
     * @return 转换后的dp值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f) - 15;
    }

    /**
     * 获取手机状态栏高度
     *
     * @param context 上下文内容
     * @return 手机状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     *验证电子邮箱地址是否是正确格式
     * @param email 需要验证的电子邮箱
     * @return 如果输入的是正确格式的电子邮箱，返回true，否则返回false
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 判断是否是手机号码
     *
     * @param mobiles 输入的手机号
     * @return 返回是否是手机号
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        Log.i("Test", m.matches() + "");
        return m.matches();
    }

    // TODO: 2016/4/5 这是只是一个模板，真正需要使用的是验证是不是学号
    /**
     * 判断输入的字符串是不是数字
     * @param number 输入需要判断的数字
     * @return 如果是数字就返回true，否则返回false
     */
    public static boolean isNum(String number) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[0-9]{5}$");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证输入的密码是否是只包含数字和大小写字母
     * @param password 输入需要验证的密码
     * @return 如果该密码是只包含数字和大小写字母返回true，否则返回false
     */
    public static boolean checkPassword(String password) {
        boolean flag = false;
        String str = "[\\da-zA-Z]+";
        try {
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(password);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }



}
