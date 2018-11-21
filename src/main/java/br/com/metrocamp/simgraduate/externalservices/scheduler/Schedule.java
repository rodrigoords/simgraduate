package br.com.metrocamp.simgraduate.externalservices.scheduler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
  private String who;
  private String description;
  private String address;
  private String when;
}
