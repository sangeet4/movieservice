package com.stackroute.movieservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movie")
public class Movie {

    @Id
    int id;
    String movieTitle;
    String comments;
    String rating;
    int yearOfRelease;

    public Movie() {
    }

    public Movie(int id, String movieTitle, String comments, String rating, int yearOfRelease) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.comments = comments;
        this.rating = rating;
        this.yearOfRelease = yearOfRelease;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getRating() {
        return rating;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieTitle='" + movieTitle + '\'' +
                ", comments='" + comments + '\'' +
                ", rating=" + rating +
                ", yearOfRelease=" + yearOfRelease +
                '}';
    }
}
