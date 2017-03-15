package com.trigues.callback;

import com.trigues.exception.ErrorBundle;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface DefaultCallback<T> {

    void onError(ErrorBundle errorBundle);

    void onSuccess(T returnParam);
}
