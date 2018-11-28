package com.stackroute.movieservice.exceptions;

public class MovieAlreadyExistsException extends Exception{

    public MovieAlreadyExistsException() {
    }

    public MovieAlreadyExistsException(String msg) {
        super(msg);
    }
}
