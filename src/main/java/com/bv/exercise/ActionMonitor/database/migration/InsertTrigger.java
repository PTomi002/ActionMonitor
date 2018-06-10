package com.bv.exercise.ActionMonitor.database.migration;

import com.bv.exercise.ActionMonitor.model.TimeSeries;
import com.bv.exercise.ActionMonitor.util.JmsUtil;
import java.sql.Connection;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.h2.api.Trigger;

@Slf4j
public class InsertTrigger extends BaseTrigger implements Trigger {

  @Override
  public void init(Connection connection, String s, String s1, String s2, boolean b, int i) {
  }

  @Override
  public void fire(final Connection conn, final Object[] oldRow, final Object[] newRow) {
    log.info("INSERT trigger fired with old values: {} and new values: {}", oldRow, newRow);
    final TimeSeries timeSeries = transformTriggerObjects(newRow);
    JmsUtil.sendMessage(timeSeries).whenCompleteAsync((result, throwable) -> {
      if (Objects.isNull(throwable)) {
        log.info("Sent JMS message successfully: {}", String.valueOf(timeSeries));
      } else {
        log.error("Error happened during execution!", String.valueOf(throwable));
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
