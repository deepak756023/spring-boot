package com.practice.assignment.exception.custom_exception;

public class NoSuchEmployeeExistsException extends RuntimeException {

    public NoSuchEmployeeExistsException() {
        super();
    }

    public NoSuchEmployeeExistsException(String msg) {
        super(msg);
    }
}
