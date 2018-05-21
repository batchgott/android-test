package com.evelope.events.tools.exceptions;


public class LoginInputException extends Exception {

    public LoginInputException() { super(); }
    public LoginInputException(String message) { super(message); }
    public LoginInputException(String message, Throwable cause) { super(message, cause); }
    public LoginInputException(Throwable cause) { super(cause); }
}
