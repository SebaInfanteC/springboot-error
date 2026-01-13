package com.sebastian.curso.springboot.error.springboot_error.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.sebastian.curso.springboot.error.springboot_error.exceptions.UserNotFoundException;
import com.sebastian.curso.springboot.error.springboot_error.models.Error;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Error> divisionByZero(Exception ex) {
        Error error = new Error();
        error.setDate(new Date());
        error.setMessage("You can't divide by zero");
        error.setError("Arithmetic Exception");
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        // return ResponseEntity.internalServerError().body(error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> numberFormatException(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("date", new Date());
        error.put("error", "Numero invalido o incorrecto");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());

        return error;
    }

    @ExceptionHandler({NullPointerException.class, HttpMessageNotWritableException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> userNotFoundException(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("date", new Date());
        error.put("error", "Usuario no existe");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());

        return error;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> notFoundEx(NoHandlerFoundException ex) {
        Error error = new Error();
        error.setDate(new Date());
        error.setMessage(ex.getMessage());
        error.setError("Not Found Exception");
        error.setStatus(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
