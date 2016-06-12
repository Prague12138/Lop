package com.marvin.lop.utils;

import com.marvin.lop.bean.HistoryInfo;

/**
 * Created by Marvin on 2016/6/12.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class CreateNewHistoryInfo {

    private HistoryInfo historyInfo;

    public HistoryInfo createHistoryInfoInServer(String userObjectid,
                                                 String commodityCategory1,
                                                 String commodityObjectid,
                                                 String commodityTitle,
                                                 String userPhoneNumber,
                                                 String sellerPhoneNumber) {
        historyInfo = new HistoryInfo();
        historyInfo.setUserObjectid(userObjectid);
        historyInfo.setCommodityCategory1(commodityCategory1);
        historyInfo.setCommodityObjectid(commodityObjectid);
        historyInfo.setCommodityTitle(commodityTitle);
        historyInfo.setUserPhoneNumber(userPhoneNumber);
        historyInfo.setSellerPhoneNumber(sellerPhoneNumber);
        return historyInfo;
    }
}
