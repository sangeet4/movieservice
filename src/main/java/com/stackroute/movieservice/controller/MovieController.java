package com.stackroute.movieservice.controller;

import com.stackroute.movieservice.domain.Movie;
import com.stackroute.movieservice.exceptions.MovieAlreadyExistsException;
import com.stackroute.movieservice.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1")
public class MovieController {

    MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping("movies")
    public ResponseEntity<?> saveMovies(@RequestBody List<Movie> movies){
        try{
            return new ResponseEntity<List<Movie>>(movieService.saveAllMovie(movies), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("movie")
    public ResponseEntity<?> saveMovie(@RequestBody Movie movie){
        ResponseEntity responseEntity;
        try{
            Movie savedMovie = movieService.saveMovie(movie);
            responseEntity = new ResponseEntity<Movie>(savedMovie, HttpStatus.CREATED);
        }catch (MovieAlreadyExistsException e){
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("movie")
    public ResponseEntity<?> getAllMovies(){
        ResponseEntity responseEntity;
        try{
            return new ResponseEntity<List<Movie>>(movieService.getAllMovies(), HttpStatus.FOUND);
        } catch(Exception e){
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
            return new ResponseEntity<List<Movie>>(movieService.getMovieByTitle(name), HttpStatus.FOUND);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("movie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id){
        try{
            return new ResponseEntity<List<Movie>>(movieService.deleteMovie(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("movie/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable int id, @RequestBody String comment){
        try{
            return new ResponseEntity<Movie>(movieService.updateMovie(id, comment), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
