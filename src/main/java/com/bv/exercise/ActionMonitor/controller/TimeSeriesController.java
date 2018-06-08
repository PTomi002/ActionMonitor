package com.bv.exercise.ActionMonitor.controller;

import com.bv.exercise.ActionMonitor.exception.NoSuchTimeSeriesException;
import com.bv.exercise.ActionMonitor.model.TimeSeries;
import com.bv.exercise.ActionMonitor.repository.TimeSeriesRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/timeseries")
public class TimeSeriesController {

    private final TimeSeriesRepository timeSeriesRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimeSeries insertTime(@Validated @RequestBody final TimeSeries timeSeries) {
        log.info("Inserting into database with: {}", timeSeries);
        return timeSeriesRepository.save(timeSeries);
    }

    @PutMapping
    public void updateTime(@RequestBody final TimeSeries time) {
    }

    @GetMapping("/{id}")
    public TimeSeries getTime(@Validated @PathVariable final String id) {
        log.info("Get time series data with: {}", id);
        final TimeSeries foundTimeSeries = timeSeriesRepository.findOne(id);
        if (Objects.isNull(foundTimeSeries)) {
            log.warn("Time series data not found with: {}", id);
            throw new NoSuchTimeSeriesException("No time series data found with: " + id + "!");
        }
        return foundTimeSeries;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTime(@PathVariable final String id) {
    }
}
