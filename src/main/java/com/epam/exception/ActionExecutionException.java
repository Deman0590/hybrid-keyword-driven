package com.epam.exception;

import com.epam.utility.Log;

public class ActionExecutionException extends Exception {

    public ActionExecutionException(String message) {
        super(message);
        Log.error(message);
    }
}
