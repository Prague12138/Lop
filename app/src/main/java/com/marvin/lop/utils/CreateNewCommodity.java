package com.marvin.lop.utils;

import com.marvin.lop.bean.CommodityInfo;

/**
 * Created by Marvin on 2016/6/3.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 用来创建新的商品信息
 */
public class CreateNewCommodity {

    private CommodityInfo commodityInfo;
    public CommodityInfo createCommodityInfoInServer(String commodityTitle,
                                                     String commodityDescribe,
                                                     String commodityPicPath1,
                                                     String commodityPicPath2,
                                                     String commodityPicPath3,
                                                     String commodityPicPath4,
                                                     String commodityPicPath5,
                                                     String commodityOriPrice,
                                                     String commodityPrice,
                                                     String commodityCategory1,
                                                     String commodityCategory2,
                                                     String userObjectId,
                                                     String userPhoneNumber,
                                                     Boolean commodityState) {
        commodityInfo = new CommodityInfo();
        commodityInfo.setCommodityTitle(commodityTitle);
        commodityInfo.setCommodityDescribe(commodityDescribe);
        commodityInfo.setCommodityPicPath1(commodityPicPath1);
        commodityInfo.setCommodityPicPath2(commodityPicPath2);
        commodityInfo.setCommodityPicPath3(commodityPicPath3);
        commodityInfo.setCommodityPicPath4(commodityPicPath4);
        commodityInfo.setCommodityPicPath5(commodityPicPath5);
        commodityInfo.setCommodityOriPrice(commodityOriPrice);
        commodityInfo.setCommodityPrice(commodityPrice);
        commodityInfo.setCommodityCategory1(commodityCategory1);
        commodityInfo.setCommodityCategory2(commodityCategory2);
        commodityInfo.setUserObjectId(userObjectId);
        commodityInfo.setUserPhoneNumber(userPhoneNumber);
        commodityInfo.setCommodityState(commodityState);

        return commodityInfo;
    }


}
