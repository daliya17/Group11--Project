package com.rating.ratingmanagementsystem.exception;

import com.rating.ratingmanagementsystem.entity.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RatingsException.class)
    public ResponseEntity<?> handleIdNotFound(RatingsException ratingsException)
    {
        ErrorObject object = new ErrorObject();
        object.setStatusCode(HttpStatus.NOT_FOUND.value());
        object.setMessage(
                ratingsException.getMessage());
        return new ResponseEntity<>(object,HttpStatus.NOT_FOUND);
    }

}
