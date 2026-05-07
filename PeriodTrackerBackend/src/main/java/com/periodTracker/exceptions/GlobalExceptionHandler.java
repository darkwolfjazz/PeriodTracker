package com.periodTracker.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleValidation(MethodArgumentNotValidException ex){
       Map<String,String>errors=new HashMap<>();
       ex.getBindingResult().getAllErrors().forEach(error->{
           String field=((FieldError)error).getField();
           String message=error.getDefaultMessage();
           errors.put(field,message);
       });
       return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppExceptions.class)
    public ResponseEntity<Map<String,String>>appExceptionHandler(AppExceptions ex){
        Map<String,String>error=new HashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    public ResponseEntity<Map<String,String>>genericException(Exception ex){
        Map<String,String>error=new HashMap<>();
        error.put("message","Something went wrong");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
















}
