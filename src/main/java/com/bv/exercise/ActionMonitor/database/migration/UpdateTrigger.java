package com.bv.exercise.ActionMonitor.database.migration;

import java.sql.Connection;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.h2.api.Trigger;

@Slf4j
public class UpdateTrigger implements Trigger {

    @Override
    public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type)
        throws SQLException {
        // IGNORE
    }

    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
        log.info("Triggered");
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
