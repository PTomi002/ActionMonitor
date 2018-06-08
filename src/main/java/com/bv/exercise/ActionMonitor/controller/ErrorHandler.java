package com.bv.exercise.ActionMonitor.controller;

import com.bv.exercise.ActionMonitor.exception.NoSuchTimeSeriesException;
import java.util.UUID;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.VndErrors.VndError;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchTimeSeriesException.class)
    public VndError handleNoSuchTimeSeriesException(final NoSuchTimeSeriesException exception) {
        return new VndError(generateLogRef(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public void handleEmptyResultDataAccessException() {

    }

    private String generateLogRef() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
