package com.bv.exercise.ActionMonitor.controller;

import com.bv.exercise.ActionMonitor.exception.NoSuchTimeSeriesException;
import com.bv.exercise.ActionMonitor.model.TimeSeries;
import com.bv.exercise.ActionMonitor.repository.TimeSeriesRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/timeseries")
public class TimeSeriesController {

    private final TimeSeriesRepository timeSeriesRepository;

    @PostMapping
    public void insertTime(@RequestBody final TimeSeries time) {
        //TODO: Insert into DB
    }

    @PutMapping
    public void updateTime(@RequestBody final TimeSeries time) {
        //TODO: Update an already existing time series with the request body
    }

    @GetMapping("/{id}")
    public TimeSeries getTime(@Validated @PathVariable final String id) {
        final TimeSeries foundTimeSeries = timeSeriesRepository.findOne(id);
        if (Objects.isNull(foundTimeSeries)) {
            throw new NoSuchTimeSeriesException("No time series data found with: " + id + "!");
        }
        return foundTimeSeries;
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable final String id) {

    }
}
