package com.trigues.interactor;


import com.trigues.callback.DefaultCallback;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;

/**
 * Created by mbaque on 15/03/2017.
 */

public class BaseUseCase<T> {

    private final PostExecutionThread postExecutionThread;

    public BaseUseCase(PostExecutionThread postExecutionThread) {
        this.postExecutionThread = postExecutionThread;
    }

    public void notifyOnError(final ErrorBundle errorBundle, final DefaultCallback<T> callback) {
        postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorBundle);
            }
        });
    }

    public void notifyOnSuccess(final T param, final DefaultCallback<T> callback) {
        postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(param);
            }
        });
    }

}
