package com.bv.exercise.actionmonitor.repository;

import com.bv.exercise.actionmonitor.model.TimeSeries;
import org.springframework.data.repository.CrudRepository;

public interface TimeSeriesRepository extends CrudRepository<TimeSeries, String> {

}
