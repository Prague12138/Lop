package com.marvin.lop.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Marvin on 2016/6/12.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class HistoryInfo extends BmobObject {

    private String userObjectid;
    private String commodityCategory1;//商品大类
    private String commodityObjectid;
    private String commodityTitle;//商品标题
    private String userPhoneNumber;//当前用户的手机号
    private String sellerPhoneNumber;//商品卖家的手机号

    public String getUserObjectid() {
        return userObjectid;
    }

    public String getCommodityCategory1() {
        return commodityCategory1;
    }

    public String getCommodityObjectid() {
        return commodityObjectid;
    }

    public String getCommodityTitle() {
        return commodityTitle;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getSellerPhoneNumber() {
        return sellerPhoneNumber;
    }

    public void setUserObjectid(String userObjectid) {
        this.userObjectid = userObjectid;
    }

    public void setCommodityCategory1(String commodityCategory1) {
        this.commodityCategory1 = commodityCategory1;
    }

    public void setCommodityObjectid(String commodityObjectid) {
        this.commodityObjectid = commodityObjectid;
    }

    public void setCommodityTitle(String commodityTitle) {
        this.commodityTitle = commodityTitle;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setSellerPhoneNumber(String sellerPhoneNumber) {
        this.sellerPhoneNumber = sellerPhoneNumber;
    }
}
