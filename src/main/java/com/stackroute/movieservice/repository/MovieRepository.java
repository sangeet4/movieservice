package com.stackroute.movieservice.repository;

import com.stackroute.movieservice.domain.Movie;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, Integer> {
    public List<Movie> findByMovieTitle(String movieTitle);
}
