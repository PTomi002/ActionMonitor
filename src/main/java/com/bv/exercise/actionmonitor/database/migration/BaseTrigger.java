package com.bv.exercise.actionmonitor.database.migration;

import com.bv.exercise.actionmonitor.model.TimeSeries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@Slf4j
abstract class BaseTrigger {

  private static final int ID_INDEX = 0;
  private static final int TIME_INDEX = 1;

  protected TimeSeries transformTriggerObjects(final Object[] objects) {
    Assert.noNullElements(objects,
        "There is a null object between the inserted elements" + objects + "!");
    final TimeSeries timeSeries = new TimeSeries();
    timeSeries.setId(String.class.cast(objects[ID_INDEX]));
    timeSeries.setTime(Long.class.cast(objects[TIME_INDEX]));
    return timeSeries;
  }
}
