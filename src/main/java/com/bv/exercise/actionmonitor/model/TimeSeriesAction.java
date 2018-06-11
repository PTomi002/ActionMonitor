package com.bv.exercise.actionmonitor.model;

import com.bv.exercise.actionmonitor.configuration.MessagingConfiguration.ExecutionType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TimeSeriesAction extends TimeSeries {

  private ExecutionType executionType;
}
