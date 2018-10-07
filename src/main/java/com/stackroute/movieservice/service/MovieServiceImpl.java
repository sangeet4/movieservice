package com.stackroute.movieservice.service;

import com.stackroute.movieservice.domain.Movie;
import com.stackroute.movieservice.exceptions.MovieAlreadyExistsException;
import com.stackroute.movieservice.exceptions.MovieListEmptyExpception;
import com.stackroute.movieservice.exceptions.MovieNotFoundException;
import com.stackroute.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@Qualifier("Impl1")
public class MovieServiceImpl implements MovieService{

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> saveAllMovie(List<Movie> movie) {
        List<Movie> savedMovies= movieRepository.saveAll(movie);
        return savedMovies;
    }

    @Override
    public Movie saveMovie(Movie movie) throws MovieAlreadyExistsException {
        if (!(movieRepository.existsById(movie.getId()))){
            Movie savedMovie = movieRepository.save(movie);
            if(savedMovie == null)
                throw new MovieAlreadyExistsException("Movie already exists");
            return savedMovie;
        }
        else
            throw new MovieAlreadyExistsException("Movie already exists");
    }

    @Override
    public List<Movie> getAllMovies() throws MovieListEmptyExpception{
        List<Movie> movieList = movieRepository.findAll();
        if(!(movieList.isEmpty()))
            return movieList;
        else
            throw new MovieListEmptyExpception("Movie d/b is empty");
    }

    @Override
    public Movie getMovieById(int id) throws MovieNotFoundException {
        if(movieRepository.existsById(id)) {
            Movie savedMovie = movieRepository.findById(id).get();
            return savedMovie;
        }
        else
            throw new MovieNotFoundException("Movie with id: "+id+" not found");
    }

    @Override
    public List<Movie> getMovieByTitle(String title) throws MovieNotFoundException{
        //List<Integer> ids = new ArrayList<>();
        List<Movie> allMovie = new ArrayList<>();//movieRepository.findAll();
//        for(Movie m:allMovie) {
//            if (m.getMovieTitle().equalsIgnoreCase(title))
//                ids.add(m.getId());
//        }
        allMovie = movieRepository.findByMovieTitle(title);
        if(!(allMovie.isEmpty()))
            return allMovie;
        else
            throw new MovieNotFoundException("Movie with name: "+title+" not found");
    }

    @Override
    public List<Movie> deleteMovie(int id) throws MovieNotFoundException{
        if(movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return movieRepository.findAll();
        }
        else
            throw new MovieNotFoundException("Movie with id: "+id+" not found");
    }

    @Override
    public Movie updateMovie(int id, String comment) throws MovieNotFoundException{
        if(movieRepository.existsById(id)) {
            Movie movieToUpdate = movieRepository.findById(id).get();
            movieToUpdate.setComments(comment);
            //movieRepository.deleteById(id);
            movieToUpdate = movieRepository.save(movieToUpdate);
            return movieToUpdate;
        }
        else
            throw new MovieNotFoundException("Movie with id: "+id+" not found");
    }
}
