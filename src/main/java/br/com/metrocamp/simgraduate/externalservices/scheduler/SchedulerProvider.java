package br.com.metrocamp.simgraduate.externalservices.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class SchedulerProvider implements SchedulerIntegration{

  private String url;

  public SchedulerProvider(@Value("${external-services.scheduler.allan}") String url) {
    this.url = url;
  }

  @Override
  public void createSchedule(Schedule schedule) {
    RestTemplate restTemplate = new RestTemplate();

    log.info("scheduler URL: " + this.url );
    log.info("scheduler doby: " + schedule.toString() );

    restTemplate.postForObject(url, schedule, Void.class);
  }
}
