package com.bv.exercise.ActionMonitor.database.migration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.bv.exercise.ActionMonitor.model.TimeSeries;
import org.junit.Before;
import org.junit.Test;

public class BaseTriggerTest {

  private static final String ELEMENT_1 = "id-1";
  private static final Long ELEMENT_2 = 100L;
  private static final Object[] INSERTED = new Object[]{ELEMENT_1, ELEMENT_2};
  private static final TimeSeries TIME_SERIES;

  static {
    TIME_SERIES = initTimeSeries(ELEMENT_1, ELEMENT_2);
  }

  private InsertTrigger insertTrigger;

  private static TimeSeries initTimeSeries(final String id, final Long time) {
    final TimeSeries timeSeries = new TimeSeries();
    timeSeries.setId(id);
    timeSeries.setTime(time);
    return timeSeries;
  }

  @Before
  public void setup() {
    insertTrigger = new InsertTrigger();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullElements() {
    insertTrigger.transformTriggerObjects(new Object[]{ELEMENT_1, null, ELEMENT_2});
  }

  @Test
  public void testTransformTriggerObjects() {
    final TimeSeries timeSeries = insertTrigger.transformTriggerObjects(INSERTED);

    assertThat("Time series data should be equal to: " + TIME_SERIES.toString() + "!",
        timeSeries, is(TIME_SERIES));
  }
}
