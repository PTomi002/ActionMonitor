package com.bv.exercise.ActionMonitor.model;

import com.bv.exercise.ActionMonitor.configuration.MessagingConfiguration.ExecutionType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TimeSeriesAction extends TimeSeries {

  private ExecutionType executionType;
}
