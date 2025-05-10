package com.practice.assignment.exception.custom_exception;

public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException() {
        super();
    }

    public EmailAlreadyUsedException(String msg) {
        super(msg);
    }
}
