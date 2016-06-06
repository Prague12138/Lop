package com.marvin.lop.utils;

import com.marvin.lop.bean.BuyingInfo;

import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by Marvin on 2016/6/4.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 用来创建新的求购信息
 */
public class CreateNewBuying {

    private BuyingInfo buyingInfo;

    public BuyingInfo createBuyingInfoInServer(String buyingTitle,
                                               String buyingDescribe,
                                               String buyingPrice,
                                               String buyingDeadlineDate,
                                               String buyingDeadlineTime,
                                               String buyingCategory1,
                                               String buyingCategory2,
                                               String userObjectId,
                                               Boolean buyingState) {
        buyingInfo = new BuyingInfo();
        buyingInfo.setBuyingTitle(buyingTitle);
        buyingInfo.setBuyingDescribe(buyingDescribe);
        buyingInfo.setBuyingPrice(buyingPrice);
        buyingInfo.setBuyingDeadlineDate(buyingDeadlineDate);
        buyingInfo.setBuyingDeadlineTime(buyingDeadlineTime);
        buyingInfo.setBuyingCategory1(buyingCategory1);
        buyingInfo.setBuyingCategory2(buyingCategory2);
        buyingInfo.setUserObjectId(userObjectId);
        buyingInfo.setBuyingState(buyingState);

        return buyingInfo;
    }
}
