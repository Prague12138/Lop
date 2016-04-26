package com.marvin.lop.task;

/**
 * Created by Marvin on 2016/3/19.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public interface Callback<T> {

    /**
     * 当加载完成后回调，加载完毕的时候处理
     * @param pCallbackValue
     */
    void onCallback(final T pCallbackValue);
}
