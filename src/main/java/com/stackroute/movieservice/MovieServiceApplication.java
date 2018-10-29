package com.stackroute.movieservice;

import com.stackroute.movieservice.domain.Movie;
import com.stackroute.movieservice.repository.MovieRepository;
import com.stackroute.movieservice.service.MovieServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@SpringBootApplication
@EnableEurekaClient
public class MovieServiceApplication extends SpringBootServletInitializer implements ApplicationListener<ContextRefreshedEvent>, CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MovieServiceApplication.class, args);
		LOGGER.info("\n\n*************************** MOVIE SERVICE APPLICATION STARTED ***************************\n");
	}

	MovieRepository movieRepository;

	@Autowired
	public MovieServiceApplication(MovieRepository movieRepository){
		this.movieRepository = movieRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		movieRepository.save(new Movie(1, "final destination", "maut ka khel","", "6.9", 2006));
		movieRepository.save(new Movie(2, "harry Potter", "Tom riddle", "", "7.8", 2007));
		movieRepository.save(new Movie(3, "Stree", "Oo Stree kal aana", "", "7.2", 2018));
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MovieServiceApplication.class);
	}
	@Override
	public void run(String... args) throws Exception {
		//logger.info("Application Started!!");
//		movieRepository.save(new Movie(4, "final destination VI", "maut ka khel", "6.9", 2012));
//		movieRepository.save(new Movie(5, "harry Potter", "Tom riddle", "7.8", 2010));
//		movieRepository.save(new Movie(6, "Stree 2", "Oo Stree kal aana", "7.2", 2019));
	}
}
