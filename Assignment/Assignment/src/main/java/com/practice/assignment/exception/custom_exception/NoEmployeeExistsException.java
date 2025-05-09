package com.practice.assignment.exception.custom_exception;

public class NoEmployeeExistsException extends RuntimeException {

    public NoEmployeeExistsException() {
        super();
    }

    public NoEmployeeExistsException(String msg) {
        super(msg);
    }
}
