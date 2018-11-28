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

import java.util.List;
import java.util.Optional;

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
        return movieRepository.saveAll(movie);
    }

    @Override
    public Movie saveMovie(Movie movie) throws MovieAlreadyExistsException {
        if (!(movieRepository.existsById(movie.getId()))){
            Movie savedMovie = movieRepository.save(movie);
            if(savedMovie == null)
                throw new MovieAlreadyExistsException(String.format("Movie with id: \"%d\" already exists", movie.getId()));
            return savedMovie;
        }
        else
            throw new MovieAlreadyExistsException(String.format("Movie with id: \"%d\" already exists", movie.getId()));
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
            Optional<Movie> value =  movieRepository.findById(id);
            if(value.isPresent())
                return value.get();
            else
                return null;
        }
        else
            throw new MovieNotFoundException(String.format("Movie with id: \"%d\" not found", id));
    }

    @Override
    public List<Movie> getMovieByTitle(String title) throws MovieNotFoundException{
        List<Movie> allMovie;
        allMovie = movieRepository.findByMovieTitle(title);
        if(!(allMovie.isEmpty()))
            return allMovie;
        else
            throw new MovieNotFoundException(String.format("Movie with name: \"%s\" not found", title));
    }

    @Override
    public List<Movie> deleteMovie(int id) throws MovieNotFoundException{
        if(movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return movieRepository.findAll();
        }
        else
            throw new MovieNotFoundException(String.format("Movie with id: \"%d\" not found", id));
    }

    @Override
    public Movie updateMovie(int id, String comment) throws MovieNotFoundException{
        if(movieRepository.existsById(id)) {
            Optional<Movie> value = movieRepository.findById(id);
            if(value.isPresent()) {
                Movie movieToUpdate = value.get();
                movieToUpdate.setComments(comment);
                movieToUpdate = movieRepository.save(movieToUpdate);
                return movieToUpdate;
            } else
                return null;
        }
        else
            throw new MovieNotFoundException("Movie with id: \""+id+"\" not found");
    }
}
