package com.example.tgbotanimalshelter.handler;

import com.example.tgbotanimalshelter.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {


    @ExceptionHandler({
            CatNotFoundException.class,
            DogNotFoundException.class,
            DogParentNotFoundException.class,
            CatParentNotFoundException.class,
            UserNotFoundException.class,
            VolunteerNotFoundException.class
    })
    ResponseEntity<Void> handleException() {
        return ResponseEntity.notFound().build();
    }


}
