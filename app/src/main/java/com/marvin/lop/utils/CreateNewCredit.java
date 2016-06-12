package com.marvin.lop.utils;

import com.marvin.lop.bean.CreditInfo;

/**
 * Created by Marvin on 2016/6/11.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class CreateNewCredit {

    private CreditInfo creditInfo;

    public CreditInfo createCreditInfoInServer(String creditContent,
                                               String buyerObjectid,
                                               String sellerObjectid,
                                               Integer sellerStar,
                                               Integer commodityStar) {
        creditInfo = new CreditInfo();
        creditInfo.setCreditContent(creditContent);
        creditInfo.setBuyerObjectid(buyerObjectid);
        creditInfo.setSellerObjectid(sellerObjectid);
        creditInfo.setSellerStar(sellerStar);
        creditInfo.setCommodityStar(commodityStar);

        return creditInfo;
    }
}
