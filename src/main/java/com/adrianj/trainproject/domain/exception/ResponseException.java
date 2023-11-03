package com.adrianj.trainproject.domain.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@Log4j2
@ControllerAdvice
public class ResponseException{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> getNotFoundException(ResourceNotFoundException e, String message){

        ResponseMessageException response = new ResponseMessageException();
        response.setMessage(message);
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(new Date());

        ResponseMessageException internalResponse = new ResponseMessageException();
        internalResponse.setMessage(e.getMessage());
        internalResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        internalResponse.setTimestamp(new Date());

        //log.error(internalResponse);


        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
