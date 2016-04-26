package com.marvin.lop.task;

/**
 * Created by Marvin on 2016/3/19.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public interface ProgressCallable<T> {
    public T call(final IProgressListener pProgressListener) throws Exception;
}
