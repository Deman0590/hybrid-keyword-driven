package com.epam.utility;

public class ActionExecutionException extends Exception {

    public ActionExecutionException(String message) {
        super(message);
        Log.error(message);
    }
}
