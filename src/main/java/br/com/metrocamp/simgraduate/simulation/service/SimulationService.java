package br.com.metrocamp.simgraduate.simulation.service;

import br.com.metrocamp.simgraduate.externalservices.cep.CepIntegration;
import br.com.metrocamp.simgraduate.externalservices.cep.SearchCep;
import br.com.metrocamp.simgraduate.externalservices.scheduler.Schedule;
import br.com.metrocamp.simgraduate.externalservices.scheduler.SchedulerIntegration;
import br.com.metrocamp.simgraduate.simulation.model.Simulation;
import br.com.metrocamp.simgraduate.utils.base.service.BaseService;
import br.com.metrocamp.simgraduate.utils.base.utils.Command;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SimulationService extends BaseService<Simulation> {


  private final CepIntegration cepIntegration;
  private final SchedulerIntegration schedulerIntegration;

  public SimulationService(CepIntegration cepIntegration,
                           SchedulerIntegration schedulerIntegration) {
    this.cepIntegration = cepIntegration;
    this.schedulerIntegration = schedulerIntegration;
  }

  @Override
  protected Command<Simulation> afterPost() {

    return t -> {
      final DateTimeFormatter isoLocalDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

      SearchCep searchCep = this.cepIntegration.searchCep(t.getCep());

      Schedule build = Schedule.builder()
              .address(searchCep.getLogradouro())
              .description("Automatic scheduler create by simgraduate system")
              .when(isoLocalDateTime.format(LocalDateTime.now().plusDays(1)))
              .who(t.getName())
              .build();

      this.schedulerIntegration.createSchedule(build);

      return t;
    };
  }
}
