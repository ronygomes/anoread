package me.ronygomes.anoread.exception;

public class ValidationError extends AnoReadException {

    public ValidationError() {
    }

    public ValidationError(String displayMessage) {
        super(displayMessage);
    }
}
