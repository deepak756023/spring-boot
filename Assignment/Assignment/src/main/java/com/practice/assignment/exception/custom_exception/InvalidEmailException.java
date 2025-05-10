package com.practice.assignment.exception.custom_exception;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException() {
        super();
    }

    public InvalidEmailException(String msg) {
        super(msg);
    }

}
