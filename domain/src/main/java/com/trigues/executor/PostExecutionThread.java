package com.trigues.executor;

/**
 * Created by inlab on 01/02/2017.
 */

public interface PostExecutionThread {

    void post(Runnable runnable);
}