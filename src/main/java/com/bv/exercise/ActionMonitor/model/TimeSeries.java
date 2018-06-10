package com.bv.exercise.ActionMonitor.model;

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
  private String id;
  @NotNull
  private Long time;
}
