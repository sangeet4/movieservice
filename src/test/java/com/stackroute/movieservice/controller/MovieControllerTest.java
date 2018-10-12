package com.stackroute.movieservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.movieservice.domain.Movie;
import com.stackroute.movieservice.exceptions.MovieAlreadyExistsException;
import com.stackroute.movieservice.exceptions.MovieListEmptyExpception;
import com.stackroute.movieservice.exceptions.MovieNotFoundException;
import com.stackroute.movieservice.repository.MovieRepository;
import com.stackroute.movieservice.service.MovieService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private Movie movie;

    @MockBean
    private MovieService movieService;

    @MockBean
    MovieRepository movieRepository;

    @InjectMocks
    private MovieController movieController;

    private List<Movie> list = null;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        movie = new Movie();
        movie.setId(1);
        movie.setMovieTitle("Jonny");
        movie.setComments("English");
        movie.setRating("7.1");
        movie.setYearOfRelease(2013);
        list = new ArrayList();

        list.add(movie);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveMovies() throws Exception {
        when(movieService.saveAllMovie(any())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(list)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void saveMovie() throws Exception {
        when(movieService.saveMovie(any())).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void saveMovieFailure() throws Exception {
        when(movieService.saveMovie(any())).thenThrow(MovieAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllMovie() throws Exception {
        when(movieService.getAllMovies()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void getAllMovieError() throws Exception{
        when(movieService.getAllMovies()).thenThrow(MovieListEmptyExpception.class);
        //movieRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getMovieByName() throws Exception{
        String name = "Jonny";
        when(movieService.getMovieByTitle(any())).thenReturn(list);
        //movieRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/"+name))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getMovieByNameError() throws Exception{
        String name = "Johnny";
        when(movieService.getMovieByTitle(any())).thenThrow(MovieNotFoundException.class);
        //movieRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/"+name))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteMovie() throws Exception{
        int id = 2;
        List<Movie> ret= new ArrayList<>(list);
        movie.setId(2);
        list.add(movie);

        when(movieService.deleteMovie(anyInt())).thenReturn(ret);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movie/"+id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteMovieError() throws Exception{
        int id = 2;
        when(movieService.deleteMovie(anyInt())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movie/"+id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateMovie() throws Exception{
        //when(movieRepository.save(any())).thenReturn(movie);
        //movieRepository.save(movie);
        int id = 1;
        String comment = "abcdxyz";
        //movie.setComments(comment);
        movie.setComments(comment);
        when(movieService.updateMovie(anyInt(), any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/movie/"+id)
                .contentType(MediaType.APPLICATION_JSON).content(comment))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateMovieError() throws Exception{
        int id = 1;
        String comment = "abcdxyz";
        movie.setComments(comment);
        //movieRepository.save(movie);
        when(movieService.updateMovie(anyInt(), any())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/movie/"+id)
                .contentType(MediaType.APPLICATION_JSON).content(comment))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    private static String asJsonString(final Object obj)
    {
        try{
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}