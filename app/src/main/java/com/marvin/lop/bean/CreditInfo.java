package com.marvin.lop.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Marvin on 2016/6/11.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class CreditInfo extends BmobObject {

    private String creditContent;
    private String buyerObjectid;
    private String sellerObjectid;
    private Integer sellerStar;
    private Integer commodityStar;

    public String getCreditContent() {
        return creditContent;
    }

    public String getBuyerObjectid() {
        return buyerObjectid;
    }

    public String getSellerObjectid() {
        return sellerObjectid;
    }

    public Integer getSellerStar() {
        return sellerStar;
    }

    public Integer getCommodityStar() {
        return commodityStar;
    }

    public void setCreditContent(String creditContent) {
        this.creditContent = creditContent;
    }

    public void setBuyerObjectid(String buyerObjectid) {
        this.buyerObjectid = buyerObjectid;
    }

    public void setSellerObjectid(String sellerObjectid) {
        this.sellerObjectid = sellerObjectid;
    }

    public void setSellerStar(Integer sellerStar) {
        this.sellerStar = sellerStar;
    }

    public void setCommodityStar(Integer commodityStar) {
        this.commodityStar = commodityStar;
    }
}
