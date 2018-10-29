package com.stackroute.movieservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document(collection = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    int id;

    @NotBlank(message = "movie title can't be blank")
    String movieTitle;

    String comments;

    String posterUrl;

    @NotBlank(message = "a movie can't be so bad to not have any rating")
    String rating;

    int yearOfRelease;
}
