package com.bv.exercise.ActionMonitor.database.migration;

import com.bv.exercise.ActionMonitor.model.TimeSeries;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class BaseTrigger {

  protected List<TimeSeries> transformTriggerObjects(final Object[] objects) {

  }
}
