package com.trigues.exception;

/**
 * Created by mbaque on 15/03/2017.
 */

public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}
