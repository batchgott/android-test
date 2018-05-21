package com.evelope.events.tools.inputCheck;


public abstract class AInputCheck {

    protected String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public abstract Boolean Check();

}
