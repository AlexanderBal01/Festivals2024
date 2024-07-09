package com.example.festivals2024;

import exceptions.ArtistFestivalNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ArtistErrorAdvice {
    @ResponseBody
    @ExceptionHandler(ArtistFestivalNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String artistFestivalNotFoundHandler(ArtistFestivalNotFoundException ex) {
        return ex.getMessage();
    }
}
