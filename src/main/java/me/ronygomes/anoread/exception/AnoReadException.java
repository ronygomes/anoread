package me.ronygomes.anoread.exception;

public class AnoReadException extends RuntimeException {

    private String displayMessage;

    public AnoReadException() {
    }

    public AnoReadException(String displayMessage) {
        super();
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
