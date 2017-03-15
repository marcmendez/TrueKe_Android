package com.trigues.interactor;

import com.trigues.callback.DefaultCallback;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface Interactor<ReturnType> extends Runnable {
    void run();

    <R extends DefaultCallback<ReturnType>> void execute(R defaultCallback);
}
