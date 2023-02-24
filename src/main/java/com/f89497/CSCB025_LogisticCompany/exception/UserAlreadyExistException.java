package com.f89497.CSCB025_LogisticCompany.exception;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String error){
        super(error);
    }
}
