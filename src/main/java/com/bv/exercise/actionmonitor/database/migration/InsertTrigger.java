package com.bv.exercise.actionmonitor.database.migration;

import com.bv.exercise.actionmonitor.configuration.MessagingConfiguration.ExecutionType;
import com.bv.exercise.actionmonitor.model.TimeSeries;
import com.bv.exercise.actionmonitor.util.JmsUtil;
import java.sql.Connection;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.h2.api.Trigger;

@Slf4j
public class InsertTrigger extends BaseTrigger implements Trigger {

  private static final ExecutionType TYPE = ExecutionType.INSERT;

  @Override
  public void init(Connection connection, String s, String s1, String s2, boolean b, int i) {
  }

  @Override
  public void fire(final Connection conn, final Object[] oldRow, final Object[] newRow) {
    log.info("INSERT trigger fired with old values: {} and new values: {}", oldRow, newRow);
    final TimeSeries insertedTimeSeries = transformTriggerObjects(newRow);
    JmsUtil.sendMessage(insertedTimeSeries, TYPE).whenCompleteAsync((result, throwable) -> {
      if (Objects.isNull(throwable)) {
        log.info("Sent " + TYPE + " JMS message successfully: {}",
            String.valueOf(insertedTimeSeries));
      } else {
        log.error("Error happened during " + TYPE + " execution!", throwable);
      }
    });
  }

  @Override
  public void close() {
  }

  @Override
  public void remove() {
  }
}
