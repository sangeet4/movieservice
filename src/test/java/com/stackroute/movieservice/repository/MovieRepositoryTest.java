package com.stackroute.movieservice.repository;

import com.stackroute.movieservice.domain.Movie;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class MovieRepositoryTest {

    @Autowired
    MovieRepository movieRepository;
    Movie movie;

    @Before
    public void setUp() throws Exception {
        movie = new Movie();
        movie.setId(1);
        movie.setMovieTitle("abc");
        movie.setComments("xyz");
        movie.setRating("7.2");
        movie.setYearOfRelease("2010");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSaveMovie(){
        movieRepository.save(movie);
        Movie fetchMovie = movieRepository.findById(movie.getId()).get();
        Assert.assertEquals(1, fetchMovie.getId());

    }

    @Test
    public void testSaveMovieFailure(){
        Movie testMovie = new Movie(1,"","wick","", "5.4","2013");
        movieRepository.save(movie);
        Movie fetchMovie = movieRepository.findById(movie.getId()).get();
        Assert.assertNotSame(fetchMovie, movie);
    }

    @Test
    public void testGetAllMovie() throws Exception{
        Movie m = new Movie(1,"Johnny","English","","7.9","2010");
        Movie m1 = new Movie(2,"Jenny","Atkinson","","6.2","2013");
        movieRepository.save(m);
        movieRepository.save(m1);

        List<Movie> list = movieRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        //Assert.assertEquals("abc", list.get(0).getMovieTitle());
        Assert.assertEquals("Johnny",list.get(0).getMovieTitle());
        Assert.assertEquals("Jenny", list.get(1).getMovieTitle());
    }

    @Test
    public void testGetMovieByName() throws Exception{
        Movie m = new Movie(3,"Johnny","English","","7.9","2010");
        Movie m1 = new Movie(4,"Johnny","Atkinson","","6.2","2013");
        movieRepository.save(m);
        movieRepository.save(m1);

        List<Movie> list = new ArrayList<>();
        list.add(m);
        list.add(m1);

        Assert.assertEquals(list, movieRepository.findByMovieTitle("Johnny"));
    }

    @Test
    public void testDeleteMovie(){
        movieRepository.save(movie);
        movieRepository.delete(movie);

        Assert.assertEquals(Optional.empty(), movieRepository.findById(movie.getId()));
    }
}