package com.marvin.lop.utils;

import com.marvin.lop.bean.BorrowInfo;

/**
 * Created by Marvin on 2016/6/4.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class CreateNewBorrow {

    private BorrowInfo borrowInfo;

    public BorrowInfo createBorrowInfoInServer(String borrowTitle,
                                               String borrowDescribe,
                                               String borrowPrice,
                                               String borrowDeadlineDate,
                                               String borrowDeadlineTime,
                                               String borrowCategory1,
                                               String borrowCategory2,
                                               String userObjectId,
                                               Boolean borrowState) {
        borrowInfo = new BorrowInfo();
        borrowInfo.setBorrowTitle(borrowTitle);
        borrowInfo.setBorrowDescribe(borrowDescribe);
        borrowInfo.setBorrowPrice(borrowPrice);
        borrowInfo.setBorrowDeadlineDate(borrowDeadlineDate);
        borrowInfo.setBorrowDeadlineTime(borrowDeadlineTime);
        borrowInfo.setBorrowCategory1(borrowCategory1);
        borrowInfo.setBorrowCategory2(borrowCategory2);
        borrowInfo.setUserObjectId(userObjectId);
        borrowInfo.setBorrowState(borrowState);

        return borrowInfo;


    }

}
