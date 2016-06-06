package com.marvin.lop.bean;

/**
 * Created by Marvin on 2016/6/4.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Bmob数据库求购信息表
 */
public class BuyingInfo extends BmobObject {

    private String buyingTitle;
    private String buyingDescribe;
    private String buyingPrice;
    private String buyingDeadlineDate;
    private String buyingDeadlineTime;
    private String buyingCategory1;
    private String buyingCategory2;
    private String userObjectId;
    private Boolean buyingState;

    public String getBuyingTitle() {
        return buyingTitle;
    }

    public String getBuyingDescribe() {
        return buyingDescribe;
    }

    public String getBuyingPrice() {
        return buyingPrice;
    }

    public String getBuyingDeadlineDate() {
        return buyingDeadlineDate;
    }

    public String getBuyingDeadlineTime() {
        return buyingDeadlineTime;
    }

    public String getBuyingCategory1() {
        return buyingCategory1;
    }

    public String getBuyingCategory2() {
        return buyingCategory2;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public Boolean getBuyingState() {
        return buyingState;
    }

    public void setBuyingTitle(String buyingTitle) {
        this.buyingTitle = buyingTitle;
    }

    public void setBuyingDescribe(String buyingDescribe) {
        this.buyingDescribe = buyingDescribe;
    }

    public void setBuyingPrice(String buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public void setBuyingDeadlineDate(String buyingDeadlineDate) {
        this.buyingDeadlineDate = buyingDeadlineDate;
    }

    public void setBuyingDeadlineTime(String buyingDeadlineTime) {
        this.buyingDeadlineTime = buyingDeadlineTime;
    }

    public void setBuyingCategory1(String buyingCategory1) {
        this.buyingCategory1 = buyingCategory1;
    }

    public void setBuyingCategory2(String buyingCategory2) {
        this.buyingCategory2 = buyingCategory2;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public void setBuyingState(Boolean buyingState) {
        this.buyingState = buyingState;
    }

}
