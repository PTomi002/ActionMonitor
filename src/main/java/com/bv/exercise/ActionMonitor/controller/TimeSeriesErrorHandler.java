package com.bv.exercise.ActionMonitor.controller;

import com.bv.exercise.ActionMonitor.exception.NoSuchTimeSeriesException;
import com.bv.exercise.ActionMonitor.exception.TimeSeriesAlreadyExistException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.VndErrors.VndError;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseBody
@ControllerAdvice
public class TimeSeriesErrorHandler {

  private static final String OLD_PATTERN = "-";
  private static final String NEW_PATTERN = "";

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchTimeSeriesException.class)
  public VndError handleNoSuchTimeSeriesException(final NoSuchTimeSeriesException exception) {
    log.info("Handle [{}] error", exception.getMessage());
    return new VndError(generateLogRef(), exception.getMessage());
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ExceptionHandler(EmptyResultDataAccessException.class)
  public void handleEmptyResultDataAccessException(final EmptyResultDataAccessException exception) {
    log.info("Handle [{}] error", exception.getMessage());
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(TimeSeriesAlreadyExistException.class)
  public VndError handleNoSuchTimeSeriesException(final TimeSeriesAlreadyExistException exception) {
    log.info("Handle [{}] error", exception.getMessage());
    return new VndError(generateLogRef(), exception.getMessage());
  }

  private String generateLogRef() {
    return StringUtils.replace(UUID.randomUUID().toString(), OLD_PATTERN, NEW_PATTERN);
  }
}
