package com.stackroute.movieservice.service;

import com.stackroute.movieservice.domain.Movie;
import com.stackroute.movieservice.exceptions.MovieAlreadyExistsException;
import com.stackroute.movieservice.exceptions.MovieListEmptyExpception;
import com.stackroute.movieservice.exceptions.MovieNotFoundException;
import com.stackroute.movieservice.repository.MovieRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    Movie movie;

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieServiceImpl movieService;
    List<Movie> list = null;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        movie = new Movie();
        movie.setMovieTitle("John");
        movie.setId(1);
        movie.setComments("Jenny");
        movie.setRating("7.0");
        movie.setYearOfRelease(2010);
        list = new ArrayList<>();
        list.add(movie);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveAllMovieTestSuccess(){
        when(movieRepository.saveAll((List<Movie>) any())).thenReturn(list);
        List<Movie> savedList = movieService.saveAllMovie(list);
        Assert.assertEquals(list, savedList);
        verify(movieRepository, times(1)).saveAll(list);
    }

    @Test
    public void saveMovieTestSuccess() throws MovieAlreadyExistsException {
        when(movieRepository.existsById(anyInt())).thenReturn(false);
        when(movieRepository.save((Movie) any())).thenReturn(movie);
        Movie savedMovie = movieService.saveMovie(movie);
        Assert.assertEquals(movie,savedMovie);

        //verify here verifies that movieRepository save method is only called once
        verify(movieRepository,times(1)).save(movie);

    }

    @Test(expected = MovieAlreadyExistsException.class)
    public void saveMovieTestFailure() throws MovieAlreadyExistsException {
        when(movieRepository.existsById(anyInt())).thenReturn(true);
        //when(movieRepository.save((Movie)any())).thenReturn(null);
        Movie savedMovie = movieService.saveMovie(movie);
        //System.out.println("savedMovie" + savedMovie);
        //Assert.assertEquals(movie,savedMovie);
//add verify
       /*doThrow(new MovieAlreadyExistsException()).when(movieRepository).findById(eq(101));
       movieService.saveMovie(movie);*/
        //verify(movieRepository, times(1)).findById(movie.getId());

    }

    @Test
    public void getAllMovie() throws MovieListEmptyExpception {
        //stubbing the mock to return specific data
        when(movieRepository.findAll()).thenReturn(list);
        List<Movie> movielist = movieService.getAllMovies();
        Assert.assertEquals(list,movielist);

        //add verify
        verify(movieRepository, times(1)).findAll();
    }

    @Test(expected = MovieListEmptyExpception.class)
    public void getAllMovieError() throws MovieListEmptyExpception{
        //movieRepository.save(movie);
        List<Movie> tempList = new ArrayList<>();
        when(movieRepository.findAll()).thenReturn(tempList);
        List<Movie> movielist = movieService.getAllMovies();
        Assert.assertEquals(tempList, movielist);
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    public void getMovieByTitle() throws MovieNotFoundException{
        String movie = "John";
        when(movieRepository.findByMovieTitle((String) any())).thenReturn(list);
        List<Movie> savedList = movieService.getMovieByTitle(movie);
        Assert.assertEquals(list, savedList);
        verify(movieRepository, times(1)).findByMovieTitle(movie);
    }

    @Test(expected = MovieNotFoundException.class)
    public void getMovieByTitleError() throws MovieNotFoundException{
        String movie = "Anthony";
        List<Movie> tempList= new ArrayList<>();
        when(movieRepository.findByMovieTitle((String) any())).thenReturn(tempList);
        List<Movie> savedList = movieService.getMovieByTitle(movie);
        Assert.assertEquals(tempList, savedList);
        verify(movieRepository, times(1)).findByMovieTitle(movie);
    }

    @Test/*(expected = NoSuchElementException.class)*/
    public void deleteMovie() throws MovieNotFoundException, MovieListEmptyExpception{
        int id = 1;
        List<Movie> tempList= new ArrayList<>();
        when(movieRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(movieRepository).deleteById(anyInt());
        when(movieRepository.findAll()).thenReturn(tempList);
        List<Movie> savedList = movieService.deleteMovie(id);
        /*for (Movie m:savedList) {
            System.out.println(m);
        }*/
        Assert.assertEquals(tempList, savedList);
        verify(movieRepository, times(1)).deleteById(id);
    }

    @Test(expected = MovieNotFoundException.class)
    public void deleteMovieError() throws MovieNotFoundException, MovieListEmptyExpception{
        int id = 1;
        //List<Movie> tempList= new ArrayList<>();
        when(movieRepository.existsById(anyInt())).thenReturn(false);
        //doNothing().when(movieRepository).deleteById(anyInt());
        //when(movieRepository.findAll()).thenReturn(tempList);
        List<Movie> savedList = movieService.deleteMovie(id);
        /*for (Movie m:savedList) {
            System.out.println(m);
        }*/
        //Assert.assertEquals(tempList, savedList);
        //verify(movieRepository, times(1)).deleteById(id);
    }

    @Test(expected = NoSuchElementException.class)
    public void updateMovie() throws MovieNotFoundException, NoSuchElementException{
//        movie.setComments("wick");
        //when(movieRepository.save((Movie)any())).thenReturn(movie);
        //movieRepository.save(movie);
        int id = 1;
        System.out.println("hrllo");
        when(movieRepository.existsById(anyInt())).thenReturn(true);
        when(movieRepository.findById(anyInt()).get()).thenReturn(movie);
        String comment = "wick";
        movie.setComments(comment);
        when(movieRepository.save((Movie) any())).thenReturn(movie);
        System.out.println("111");
        Movie updatedMovie = movieService.updateMovie(id, comment);
        Assert.assertEquals(movie, updatedMovie);
    }
}