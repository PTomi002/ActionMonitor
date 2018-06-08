package com.bv.exercise.ActionMonitor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TimeSeriesAlreadyExistException extends RuntimeException {

    public TimeSeriesAlreadyExistException(String message) {
        super(message);
    }
}
