-- INIT DATABASE AT BOOT TIME
-- FLYWAY SHOULD BE USED IN PRODUCTION ENV
CREATE TABLE IF NOT EXISTS time (
  id VARCHAR(10),
  time TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);