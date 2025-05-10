package com.practice.assignment.exception.custom_exception;

public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException() {
        super();
    }

    public WrongPasswordException(String msg) {
        super(msg);
    }
}
