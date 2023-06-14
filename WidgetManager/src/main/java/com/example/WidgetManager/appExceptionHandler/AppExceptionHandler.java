package com.example.WidgetManager.appExceptionHandler;


import com.example.WidgetManager.exception.NoSuchElementFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class AppExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex){
        Map<String ,String > errorMap= new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NoSuchElementFoundException.class)
    public Map<String,String> handleWidgetNotFound(NoSuchElementFoundException ex){
        Map<String ,String > errorMap= new HashMap<>();
        errorMap.put("errorMessage: ", ex.getMessage());
        return errorMap;
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(NoSuchElementException.class)
//    public Map<String,String> handleInvalidArgument(NoSuchElementException ex){
//        Map<String ,String > errorMap= new HashMap<>();
//        errorMap.put("errorMessage: ", ex.getMessage()+" "+ex.getLocalizedMessage());
//        return errorMap;
//    }


}
