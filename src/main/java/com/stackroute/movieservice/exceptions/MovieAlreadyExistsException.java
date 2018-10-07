package com.stackroute.movieservice.exceptions;

public class MovieAlreadyExistsException extends Exception{

    private String msg;

    public MovieAlreadyExistsException() {
    }

    public MovieAlreadyExistsException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
