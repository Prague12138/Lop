package com.marvin.lop.bean;

/**
 * Created by Marvin on 2016/6/4.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */

import cn.bmob.v3.BmobObject;

/**
 * Bmob数据库出租信息表
 */
public class RentInfo extends BmobObject {

    private String rentTitle;
    private String rentDescribe;
    private String rentPicPath1;
    private String rentPicPath2;
    private String rentPicPath3;
    private String rentPicPath4;
    private String rentPicPath5;
    private String rentPrice;
    private String rentCategory1;
    private String rentCategory2;
    private String userObjectId;
    private Boolean rentState;

    public String getRentTitle() {
        return rentTitle;
    }

    public String getRentDescribe() {
        return rentDescribe;
    }

    public String getRentPicPath1() {
        return rentPicPath1;
    }

    public String getRentPicPath2() {
        return rentPicPath2;
    }

    public String getRentPicPath3() {
        return rentPicPath3;
    }

    public String getRentPicPath4() {
        return rentPicPath4;
    }

    public String getRentPicPath5() {
        return rentPicPath5;
    }

    public String getRentPrice() {
        return rentPrice;
    }

    public String getRentCategory1() {
        return rentCategory1;
    }

    public String getRentCategory2() {
        return rentCategory2;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public Boolean getRentState() {
        return rentState;
    }

    public void setRentTitle(String rentTitle) {
        this.rentTitle = rentTitle;
    }

    public void setRentDescribe(String rentDescribe) {
        this.rentDescribe = rentDescribe;
    }

    public void setRentPicPath1(String rentPicPath1) {
        this.rentPicPath1 = rentPicPath1;
    }

    public void setRentPicPath2(String rentPicPath2) {
        this.rentPicPath2 = rentPicPath2;
    }

    public void setRentPicPath3(String rentPicPath3) {
        this.rentPicPath3 = rentPicPath3;
    }

    public void setRentPicPath4(String rentPicPath4) {
        this.rentPicPath4 = rentPicPath4;
    }

    public void setRentPicPath5(String rentPicPath5) {
        this.rentPicPath5 = rentPicPath5;
    }

    public void setRentPrice(String rentPrice) {
        this.rentPrice = rentPrice;
    }

    public void setRentCategory1(String rentCategory1) {
        this.rentCategory1 = rentCategory1;
    }

    public void setRentCategory2(String rentCategory2) {
        this.rentCategory2 = rentCategory2;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public void setRentState(Boolean rentState) {
        this.rentState = rentState;
    }
}
