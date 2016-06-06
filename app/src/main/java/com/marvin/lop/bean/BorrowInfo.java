package com.marvin.lop.bean;

/**
 * Created by Marvin on 2016/6/4.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */

import cn.bmob.v3.BmobObject;

/**
 * Bmob数据库求租信息表
 */
public class BorrowInfo extends BmobObject {

    private String borrowTitle;
    private String borrowDescribe;
    private String borrowPrice;
    private String borrowDeadlineDate;
    private String borrowDeadlineTime;
    private String borrowCategory1;
    private String borrowCategory2;
    private String userObjectId;
    private Boolean borrowState;

    public String getBorrowTitle() {
        return borrowTitle;
    }

    public String getBorrowDescribe() {
        return borrowDescribe;
    }

    public String getBorrowPrice() {
        return borrowPrice;
    }

    public String getBorrowDeadlineDate() {
        return borrowDeadlineDate;
    }

    public String getBorrowDeadlineTime() {
        return borrowDeadlineTime;
    }

    public String getBorrowCategory1() {
        return borrowCategory1;
    }

    public String getBorrowCategory2() {
        return borrowCategory2;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public Boolean getBorrowState() {
        return borrowState;
    }

    public void setBorrowTitle(String borrowTitle) {
        this.borrowTitle = borrowTitle;
    }

    public void setBorrowDescribe(String borrowDescribe) {
        this.borrowDescribe = borrowDescribe;
    }

    public void setBorrowPrice(String borrowPrice) {
        this.borrowPrice = borrowPrice;
    }

    public void setBorrowDeadlineDate(String borrowDeadlineDate) {
        this.borrowDeadlineDate = borrowDeadlineDate;
    }

    public void setBorrowDeadlineTime(String borrowDeadlineTime) {
        this.borrowDeadlineTime = borrowDeadlineTime;
    }

    public void setBorrowCategory1(String borrowCategory1) {
        this.borrowCategory1 = borrowCategory1;
    }

    public void setBorrowCategory2(String borrowCategory2) {
        this.borrowCategory2 = borrowCategory2;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public void setBorrowState(Boolean borrowState) {
        this.borrowState = borrowState;
    }
}
