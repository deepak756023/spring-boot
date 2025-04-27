package com.practice.Assignment.exception.custom_exception;

public class NoEmployeeExistsException extends RuntimeException {

    public NoEmployeeExistsException() {
        super();
    }

    public NoEmployeeExistsException(String msg) {
        super(msg);
    }
}
