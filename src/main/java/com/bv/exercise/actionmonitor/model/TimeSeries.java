package com.bv.exercise.actionmonitor.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class TimeSeries {

  @Id
  @Size(min = 1, max = 10)
  protected String id;
  @NotNull
  protected Long time;
}
