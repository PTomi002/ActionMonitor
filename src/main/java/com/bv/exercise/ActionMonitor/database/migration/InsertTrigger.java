package com.bv.exercise.ActionMonitor.database.migration;

import com.bv.exercise.ActionMonitor.configuration.MessagingConfiguration;
import com.bv.exercise.ActionMonitor.model.TimeSeries;
import com.bv.exercise.ActionMonitor.util.JmsUtil;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.h2.api.Trigger;

@Slf4j
public class InsertTrigger implements Trigger {

  @Override
  public void init(Connection connection, String s, String s1, String s2, boolean b, int i)
      throws SQLException {
    // IGNORE
  }

  @Override
  public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
    log.info("INSERT trigger fired");
    JmsUtil.sendAsync(MessagingConfiguration.TIME_SERIES_TOPIC, new TimeSeries());
  }

  @Override
  public void close() throws SQLException {
    // IGNORE
  }

  @Override
  public void remove() throws SQLException {
    // IGNORE
  }
}
