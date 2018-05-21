package com.evelope.events.tools.exceptions;

/**
 * Created by Stefan on 08.04.2018.
 */

public class UpdateUserInputException extends Exception {

    public UpdateUserInputException() { super(); }
    public UpdateUserInputException(String message) { super(message); }
    public UpdateUserInputException(String message, Throwable cause) { super(message, cause); }
    public UpdateUserInputException(Throwable cause) { super(cause); }
}
