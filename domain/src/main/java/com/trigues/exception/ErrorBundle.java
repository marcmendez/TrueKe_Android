package com.trigues.exception;

/**
 * Created by inlab on 01/02/2017.
 */

public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}
