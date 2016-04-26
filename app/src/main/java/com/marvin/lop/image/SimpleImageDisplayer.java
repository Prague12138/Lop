package com.marvin.lop.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;

/**
 * Created by Marvin on 2016/3/19.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class SimpleImageDisplayer implements BitmapDisplayer {

    private int targetWidth;

    public SimpleImageDisplayer(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    @Override
    public Bitmap display(Bitmap bitmap, ImageView imageView, LoadedFrom loadedFrom) {
        if (bitmap != null) {
            bitmap = ImageUtils.resizeImageByWidth(bitmap, targetWidth);
        }
        imageView.setImageBitmap(bitmap);
        return bitmap;
    }
}
