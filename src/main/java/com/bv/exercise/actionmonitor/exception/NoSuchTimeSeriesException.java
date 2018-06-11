package com.bv.exercise.actionmonitor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchTimeSeriesException extends RuntimeException {

  public NoSuchTimeSeriesException(final String message) {
    super(message);
  }
}
