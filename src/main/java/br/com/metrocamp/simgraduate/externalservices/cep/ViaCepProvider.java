package br.com.metrocamp.simgraduate.externalservices.cep;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ViaCepProvider implements CepIntegration{

  private String url;

  public ViaCepProvider(@Value("${external-services.cep.via-cep-url}") String url) {
    this.url = url;
  }

  @Override
  public SearchCep searchCep(String cep) {

    RestTemplate restTemplate = new RestTemplate();

    // URI (URL) parameters
    Map<String, String> uriParams = new HashMap<>();
    uriParams.put("cep", cep);

    // Query parameters
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

    log.info("cep Request: " + builder.buildAndExpand(uriParams).toUri() );

    SearchCep body = restTemplate.getForEntity(builder.buildAndExpand(uriParams).toUri(), SearchCep.class).getBody();

    log.info("cep Response: " + body);

    return body;
  }
}
