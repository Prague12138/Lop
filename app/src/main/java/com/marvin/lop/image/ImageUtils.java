package com.marvin.lop.image;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Marvin on 2016/3/19.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class ImageUtils {

    /**
     * 根据宽度等比例缩放图片
     * @param defaultBitmap
     * @param targetWidth
     * @return
     */
    public static Bitmap resizeImageByWidth(Bitmap defaultBitmap, int targetWidth) {
        int rawWidth = defaultBitmap.getWidth();
        int rawHeight = defaultBitmap.getHeight();
        float targetHeight = targetWidth * (float) rawHeight / (float) rawWidth;
        float scaleWidth = targetWidth / (float) rawWidth;
        float scaleHeight = targetHeight / (float) rawHeight;
        Matrix localMatrix = new Matrix();
        localMatrix.postScale(scaleHeight, scaleWidth);
        return Bitmap.createBitmap(defaultBitmap, 0, 0, rawWidth, rawHeight, localMatrix, true);
    }
}
