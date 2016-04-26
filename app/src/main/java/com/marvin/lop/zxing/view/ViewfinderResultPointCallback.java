package com.marvin.lop.zxing.view;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
/**
 * Created by Marvin on 2016/3/31.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public final class ViewfinderResultPointCallback implements ResultPointCallback {

    private final ViewfinderView viewfinderView;

    public ViewfinderResultPointCallback(ViewfinderView viewfinderView) {
        this.viewfinderView = viewfinderView;
    }

    public void foundPossibleResultPoint(ResultPoint point) {
        viewfinderView.addPossibleResultPoint(point);
    }

}
