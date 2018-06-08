package com.bv.exercise.ActionMonitor.repository;

import com.bv.exercise.ActionMonitor.model.TimeSeries;
import org.springframework.data.repository.CrudRepository;

public interface TimeSeriesRepository extends CrudRepository<TimeSeries, String> {

}
