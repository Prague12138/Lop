package com.marvin.lop.utils;

import com.marvin.lop.bean.RentInfo;

/**
 * Created by Marvin on 2016/6/4.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class CreateNewRent {

    private RentInfo rentInfo;

    public RentInfo createRentInfoInServer(String rentTitle,
                                           String rentDescribe,
                                           String rentPicPath1,
                                           String rentPicPath2,
                                           String rentPicPath3,
                                           String rentPicPath4,
                                           String rentPicPath5,
                                           String rentPrice,
                                           String rentCategory1,
                                           String rentCategory2,
                                           String userObjectId,
                                           Boolean rentState) {
        rentInfo = new RentInfo();
        rentInfo.setRentTitle(rentTitle);
        rentInfo.setRentDescribe(rentDescribe);
        rentInfo.setRentPicPath1(rentPicPath1);
        rentInfo.setRentPicPath2(rentPicPath2);
        rentInfo.setRentPicPath3(rentPicPath3);
        rentInfo.setRentPicPath4(rentPicPath4);
        rentInfo.setRentPicPath5(rentPicPath5);
        rentInfo.setRentPrice(rentPrice);
        rentInfo.setRentCategory1(rentCategory1);
        rentInfo.setRentCategory2(rentCategory2);
        rentInfo.setUserObjectId(userObjectId);
        rentInfo.setRentState(rentState);

        return rentInfo;

    }
}
