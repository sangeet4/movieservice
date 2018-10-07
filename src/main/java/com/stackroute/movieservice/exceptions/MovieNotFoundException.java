package com.stackroute.movieservice.exceptions;

public class MovieNotFoundException extends Exception {

    private String msg;

    public MovieNotFoundException() {
    }

    public MovieNotFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
