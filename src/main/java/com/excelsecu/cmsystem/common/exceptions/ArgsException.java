package com.excelsecu.cmsystem.common.exceptions;

public class ArgsException extends RuntimeException{

    public ArgsException() {
    }

    public ArgsException(String message) {
        super(message);
    }

    public ArgsException(String message, Throwable cause) {
        super(message, cause);
    }

}
