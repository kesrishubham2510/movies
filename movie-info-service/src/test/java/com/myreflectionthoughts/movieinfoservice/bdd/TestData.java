package com.myreflectionthoughts.movieinfoservice.bdd;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.myreflectionthoughts.movieinfoservice.models.MovieInfo;


public class TestData {


    public static List<MovieInfo> getMovieList(){

         List<MovieInfo> initialMovieList = new ArrayList<>();
            
         List<String>  starCast = new ArrayList<>(){
                {
                    new String("Actor 1");
                    new String("Actor 2");
                    new String("Actor 3");
                }
            };
            
            MovieInfo movieInfo_first = new MovieInfo();
            movieInfo_first.setTitle("movie_one");
            movieInfo_first.setCast(starCast);
            movieInfo_first.setYear(1999);
            // YYYY-MM-dd
            movieInfo_first.setReleaseDate(LocalDate.parse("1998-12-25", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            
            MovieInfo movieInfo_second= new MovieInfo();
            movieInfo_second.setTitle("movie_two");
            movieInfo_second.setCast(starCast);
            movieInfo_second.setYear(2000);
            // YYYY-MM-dd
            movieInfo_second.setReleaseDate(LocalDate.parse("2010-12-25", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            
            MovieInfo movieInfo_third = new MovieInfo();
            movieInfo_third.setTitle("movie_three");
            movieInfo_third.setCast(starCast);
            movieInfo_third.setYear(2001);
            // YYYY-MM-dd
            movieInfo_third.setReleaseDate(LocalDate.parse("2001-12-25", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            
            MovieInfo movieInfo_fourth = new MovieInfo();
            movieInfo_fourth.setTitle("movie_four");
            movieInfo_fourth.setCast(starCast);
            movieInfo_fourth.setYear(2002);
            // YYYY-MM-dd
            movieInfo_fourth.setReleaseDate(LocalDate.parse("2002-12-25", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            
            initialMovieList.addAll(List.of(movieInfo_first,movieInfo_second,movieInfo_third,movieInfo_fourth));
        
            return initialMovieList;
        }
    }

