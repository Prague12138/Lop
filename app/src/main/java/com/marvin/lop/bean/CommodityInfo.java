package com.marvin.lop.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Marvin on 2016/6/3.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */

/**
 * Bmob数据库商品信息表
 */
public class CommodityInfo extends BmobObject {

    private String commodityTitle;
    private String commodityDescribe;
    private String commodityPicPath1;
    private String commodityPicPath2;
    private String commodityPicPath3;
    private String commodityPicPath4;
    private String commodityPicPath5;
    private String commodityOriPrice;
    private String commodityPrice;
    private String commodityCategory1;
    private String commodityCategory2;
    private String userObjectId;
    private String userPhoneNumber;
    private Boolean commodityState;

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }


    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getCommodityTitle() {
        return commodityTitle;
    }

    public String getCommodityDescribe() {
        return commodityDescribe;
    }

    public String getCommodityPicPath1() {
        return commodityPicPath1;
    }

    public String getCommodityPicPath2() {
        return commodityPicPath2;
    }

    public String getCommodityPicPath3() {
        return commodityPicPath3;
    }

    public String getCommodityPicPath4() {
        return commodityPicPath4;
    }

    public String getCommodityPicPath5() {
        return commodityPicPath5;
    }

    public String getCommodityOriPrice() {
        return commodityOriPrice;
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public String getCommodityCategory1() {
        return commodityCategory1;
    }

    public String getCommodityCategory2() {
        return commodityCategory2;
    }


    public String getUserObjectId() {
        return userObjectId;
    }

    public void setCommodityTitle(String commodityTitle) {
        this.commodityTitle = commodityTitle;
    }

    public void setCommodityDescribe(String commodityDescribe) {
        this.commodityDescribe = commodityDescribe;
    }

    public void setCommodityPicPath1(String commodityPicPath1) {
        this.commodityPicPath1 = commodityPicPath1;
    }

    public void setCommodityPicPath2(String commodityPicPath2) {
        this.commodityPicPath2 = commodityPicPath2;
    }

    public void setCommodityPicPath3(String commodityPicPath3) {
        this.commodityPicPath3 = commodityPicPath3;
    }

    public void setCommodityPicPath4(String commodityPicPath4) {
        this.commodityPicPath4 = commodityPicPath4;
    }

    public void setCommodityPicPath5(String commodityPicPath5) {
        this.commodityPicPath5 = commodityPicPath5;
    }

    public void setCommodityOriPrice(String commodityOriPrice) {
        this.commodityOriPrice = commodityOriPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public void setCommodityCategory1(String commodityCategory1) {
        this.commodityCategory1 = commodityCategory1;
    }

    public void setCommodityCategory2(String commodityCategory2) {
        this.commodityCategory2 = commodityCategory2;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public void setCommodityState(Boolean commodityState) {
        this.commodityState = commodityState;
    }

    public Boolean getCommodityState() {
        return commodityState;
    }


}
