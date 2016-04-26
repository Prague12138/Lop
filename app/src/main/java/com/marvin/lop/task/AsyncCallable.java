package com.marvin.lop.task;

/**
 * Created by Marvin on 2016/3/19.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public interface AsyncCallable<T> {
    void call(final Callback<T> pCallback, final Callback<Exception> pExceptionCallback);
}
