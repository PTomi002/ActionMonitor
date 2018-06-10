package com.bv.exercise.ActionMonitor.database.migration;

import com.bv.exercise.ActionMonitor.configuration.MessagingConfiguration.ExecutionType;
import com.bv.exercise.ActionMonitor.model.TimeSeries;
import com.bv.exercise.ActionMonitor.util.JmsUtil;
import java.sql.Connection;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.h2.api.Trigger;

@Slf4j
public class UpdateTrigger extends BaseTrigger implements Trigger {

  private static final ExecutionType TYPE = ExecutionType.UPDATE;

  @Override
  public void init(Connection conn, String schemaName, String triggerName, String tableName,
      boolean before, int type) {
  }

  @Override
  public void fire(final Connection conn, final Object[] oldRow, final Object[] newRow) {
    log.info("UPDATE trigger fired with old values: {} and new values: {}", oldRow, newRow);
    final TimeSeries updatedTimeSeries = transformTriggerObjects(newRow);
    JmsUtil.sendMessage(updatedTimeSeries, TYPE).whenCompleteAsync((result, throwable) -> {
      if (Objects.isNull(throwable)) {
        log.info("Sent " + TYPE + " JMS message successfully: {}",
            String.valueOf(updatedTimeSeries));
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
