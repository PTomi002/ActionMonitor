package com.bv.exercise.ActionMonitor.controller;

import com.bv.exercise.ActionMonitor.exception.NoSuchTimeSeriesException;
import com.bv.exercise.ActionMonitor.exception.TimeSeriesAlreadyExistException;
import com.bv.exercise.ActionMonitor.model.TimeSeries;
import com.bv.exercise.ActionMonitor.repository.TimeSeriesRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/timeseries")
public class TimeSeriesController {

  private final TimeSeriesRepository timeSeriesRepository;

  @PostMapping
  public ResponseEntity<TimeSeries> insertTimeSeries(
      @Validated @RequestBody final TimeSeries timeSeries) {
    log.info("Inserting into database with: {}", timeSeries);
    final TimeSeries foundTimeSeries = timeSeriesRepository.findOne(timeSeries.getId());
    if (!Objects.isNull(foundTimeSeries)) {
      throw new TimeSeriesAlreadyExistException(
          "Time series data already exist: " + timeSeries.getId() + "!");
    }
    final TimeSeries savedTimeSeries = timeSeriesRepository.save(timeSeries);
    return ResponseEntity.created(
        UriComponentsBuilder.fromPath("/timeseries/").path("/{id}")
            .buildAndExpand(savedTimeSeries.getId())
            .toUri()).build();
  }

  @PutMapping
  public void updateTimeSeries(@Validated @RequestBody final TimeSeries time) {
    log.info("Update time series data with: {}", time);
    timeSeriesRepository.save(time);
  }

  @GetMapping("/{id}")
  public TimeSeries getTimeSeries(@PathVariable final String id) {
    log.info("Get time series data with: {}", id);
    final TimeSeries foundTimeSeries = timeSeriesRepository.findOne(id);
    if (Objects.isNull(foundTimeSeries)) {
      throw new NoSuchTimeSeriesException("No time series data found with: " + id + "!");
    }
    return foundTimeSeries;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTimeSeries(@PathVariable final String id) {
    log.info("Delete time series data with: {}", id);
    timeSeriesRepository.delete(id);
  }
}
