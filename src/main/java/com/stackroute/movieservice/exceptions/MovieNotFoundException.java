package com.stackroute.movieservice.exceptions;

public class MovieNotFoundException extends Exception {

    public MovieNotFoundException() {
    }

    public MovieNotFoundException(String msg) {
        super(msg);
    }
}
