package com.nagarro.pointofsaleapplication.dto;

/**
 * @author chetanmahajan send error object as response when any error happened
 */
public class MyError {

    private String message;
    private boolean status;

    public MyError(final String message) {
        this.message = message;
        this.status = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
