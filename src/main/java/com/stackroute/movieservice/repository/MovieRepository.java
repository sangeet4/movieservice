package com.stackroute.movieservice.repository;

import com.stackroute.movieservice.domain.Movie;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface MovieRepository extends MongoRepository<Movie, Integer> {
    public List<Movie> findByMovieTitle(String movieTitle);
}
