package com.bv.exercise.actionmonitor.database;

import com.bv.exercise.actionmonitor.database.migration.InsertTrigger;
import com.bv.exercise.actionmonitor.database.migration.UpdateTrigger;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MigrationConfiguration {

  private static final String COMMON_TRIGGER_SQL_COMMAND = "CREATE OR REPLACE TRIGGER %s AFTER %s on %s FOR EACH ROW CALL \"%s\"";
  private final JdbcTemplate jdbcTemplate;
  @Value("${migration.db.table.name:time_series}")
  private String table;

  @PostConstruct
  public void migrateDatabase() {
    log.info("Migrate table: {} to add notifications", table);
    final String insertTrigger = String
        .format(COMMON_TRIGGER_SQL_COMMAND, "notification_on_insert", "INSERT", table,
            InsertTrigger.class.getName());
    execute(jdbcTemplate, insertTrigger);
    final String updateTrigger = String
        .format(COMMON_TRIGGER_SQL_COMMAND, "notification_on_update", "UPDATE", table,
            UpdateTrigger.class.getName());
    execute(jdbcTemplate, updateTrigger);
    log.info("Migration completed");
  }

  private void execute(final JdbcTemplate jdbcTemplate, final String sql) {
    log.info("Executing SQL: {} on table: {}", sql, table);
    jdbcTemplate.execute(sql);
  }
}
