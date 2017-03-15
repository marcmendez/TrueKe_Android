package com.trigues.executor;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface PostExecutionThread {

    void post(Runnable runnable);
}