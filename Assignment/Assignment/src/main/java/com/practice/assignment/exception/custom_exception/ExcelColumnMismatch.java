package com.practice.assignment.exception.custom_exception;

public class ExcelColumnMismatch extends RuntimeException{
    public ExcelColumnMismatch() {
        super();
    }

    public ExcelColumnMismatch(String msg) {
        super(msg);
    }
}
