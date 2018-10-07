package com.stackroute.movieservice.service;

import com.stackroute.movieservice.domain.Movie;
import com.stackroute.movieservice.exceptions.MovieAlreadyExistsException;
import com.stackroute.movieservice.exceptions.MovieListEmptyExpception;
import com.stackroute.movieservice.exceptions.MovieNotFoundException;

import java.util.List;

public interface MovieService {

    public List<Movie> saveAllMovie(List<Movie> movie);

    public Movie saveMovie(Movie movie) throws MovieAlreadyExistsException;

    public List<Movie> getAllMovies() throws MovieListEmptyExpception;

    public Movie getMovieById(int id) throws MovieNotFoundException;

    public List<Movie> getMovieByTitle(String title) throws MovieNotFoundException;

    public List<Movie> deleteMovie(int id) throws MovieNotFoundException;

    public Movie updateMovie(int id, String comment) throws MovieNotFoundException;
}
