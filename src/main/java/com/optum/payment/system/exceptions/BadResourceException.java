package com.optum.payment.system.exceptions;
import java.util.ArrayList;
import java.util.List;

public class BadResourceException extends Exception {

    private final List<String> errorMessages = new ArrayList<>();

    public BadResourceException() {
    }

    public BadResourceException(String msg) {
        super(msg);
    }

    /**
     * @return the errorMessages
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errors) {
        this.errorMessages.add(errors.toString());
    }

    public void addErrorMessage(String message) {
        this.errorMessages.add(message);
    }
}