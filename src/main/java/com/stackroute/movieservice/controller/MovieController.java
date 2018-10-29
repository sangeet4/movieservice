package com.stackroute.movieservice.controller;

import com.stackroute.movieservice.domain.Movie;
import com.stackroute.movieservice.exceptions.MovieAlreadyExistsException;
import com.stackroute.movieservice.service.MovieService;
import com.stackroute.movieservice.service.MovieServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);

    MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping("movies")
    public ResponseEntity<?> saveMovies(@Valid @RequestBody List<Movie> movies){
        try{
            List<Movie> savedMovie = movieService.saveAllMovie(movies);
            LOGGER.info("Saved All the Movies Provided");
            return new ResponseEntity<List<Movie>>(savedMovie, HttpStatus.CREATED);
        } catch (Exception e){
            LOGGER.info(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("movie")
    public ResponseEntity<?> saveMovie(@Valid @RequestBody Movie movie){
        ResponseEntity responseEntity;
        try{
            Movie savedMovie = movieService.saveMovie(movie);
            LOGGER.info("Saved the Movie Provided");
            responseEntity = new ResponseEntity<Movie>(savedMovie, HttpStatus.CREATED);
        }catch (MovieAlreadyExistsException e){
            LOGGER.info(e.getMessage());
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("movie")
    public ResponseEntity<?> getAllMovies(){
        ResponseEntity responseEntity;
        try{
            List<Movie> movieInDb = movieService.getAllMovies();
            LOGGER.info("Got All the Movies Stored");
            return new ResponseEntity<List<Movie>>(movieInDb, HttpStatus.OK);
        } catch(Exception e){
            LOGGER.info(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("movie/{id}")
//    public ResponseEntity<?> getMovieById(@PathVariable int id){
//        try{
//            return new ResponseEntity<Movie>(movieService.getMovieById(id), HttpStatus.FOUND);
//        } catch(Exception e){
//            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("movie/{name}")
    public ResponseEntity<?> getMovieByTitle(@PathVariable String name){
        try{
            List<Movie> retrivedMovies = movieService.getMovieByTitle(name);
            LOGGER.info("Got the Movie(s) by name: \""+name+"\"");
            return new ResponseEntity<List<Movie>>(retrivedMovies, HttpStatus.OK);
        } catch(Exception e){
            LOGGER.info(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("movie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id){
        try{
            List<Movie> remainingMovies = movieService.deleteMovie(id);
            LOGGER.info("Deleted the Movie with id: \""+id+"\"");
            return new ResponseEntity<List<Movie>>(remainingMovies, HttpStatus.OK);
        } catch (Exception e){
            LOGGER.info(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("movie/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable int id, @RequestBody String comment){
        try{
            Movie updatedMovie = movieService.updateMovie(id, comment);
            LOGGER.info("Updated the comment of Movie with id: \""+id+"\" to \""+updatedMovie.getComments()+"\"");
            return new ResponseEntity<Movie>(updatedMovie, HttpStatus.OK);
        } catch (Exception e){
            LOGGER.info(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
