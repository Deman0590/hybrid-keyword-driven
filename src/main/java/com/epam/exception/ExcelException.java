package com.epam.exception;

import com.epam.utility.Log;

public class ExcelException extends Exception{

    public ExcelException(String message) {
        super(message);
        Log.error(message);
    }
}
