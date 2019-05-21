package com.epam.utility;

public class ExcelException extends Exception{

    public ExcelException(String message) {
        super(message);
        Log.error(message);
    }
}
