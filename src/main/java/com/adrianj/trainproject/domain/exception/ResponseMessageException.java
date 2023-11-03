package com.adrianj.trainproject.domain.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseMessageException {

    private int statusCode;
    private String message;
    private Date timestamp;
}
