package com.nagarro.pointofsaleapplication.dto;

import org.springframework.http.HttpStatus;

/**
 * @author chetanmahajan response object took model obj status message to the front end
 * @param <T>
 */
public class Response<T> {

    private String message;
    private boolean status;
    private T modelObj;
    private HttpStatus responseStatus;

    public Response() {

    }

    public Response(T model, HttpStatus status) {
        this.modelObj = model;
        this.responseStatus = status;
        this.status = false;
    }

    public Response(T model, HttpStatus responseStatus, String message, boolean status) {
        this.modelObj = model;
        this.responseStatus = responseStatus;
        this.status = status;
        this.message = message;
    }

    public Response(boolean status, String message, HttpStatus responseStatus) {
        this.responseStatus = responseStatus;
        this.status = false;
        this.message = message;
    }

    public HttpStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(final HttpStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public T getModelObj() {
        return modelObj;
    }

    public void setModelObj(T modelObj) {
        this.modelObj = modelObj;
    }

}
