package com.bv.exercise.ActionMonitor.database.migration;

import java.sql.Connection;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.h2.api.Trigger;

@Slf4j
public class InsertTrigger implements Trigger {

    @Override
    public void init(Connection connection, String s, String s1, String s2, boolean b, int i) throws SQLException {
        // IGNORE
    }

    @Override
    public void fire(Connection connection, Object[] objects, Object[] objects1) throws SQLException {
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
