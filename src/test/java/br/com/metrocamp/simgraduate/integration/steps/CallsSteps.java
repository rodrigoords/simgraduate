package br.com.metrocamp.simgraduate.integration.steps;

import com.xebialabs.restito.builder.stub.StubHttp;
import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.semantics.ConditionWithApplicables;
import com.xebialabs.restito.server.StubServer;

import org.glassfish.grizzly.http.util.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import lombok.Value;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.resourceContent;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Condition.delete;
import static com.xebialabs.restito.semantics.Condition.get;
import static com.xebialabs.restito.semantics.Condition.parameter;
import static com.xebialabs.restito.semantics.Condition.post;
import static com.xebialabs.restito.semantics.Condition.put;

public class CallsSteps {
  private StubServer server;
  private StubHttp   stubHttp;

  @Before
  public void setUp() {
    server = new StubServer(9093).run();
    stubHttp = whenHttp(server);
  }

  @After
  public void tearDown() {
    server.stop();
  }

  @Given("^The call to external service should be:$")
  public void theCallToExternalServiceShouldBe(final DataTable data){

    final List<Call> calls = data.asList(Call.class);

    for (final Call call : calls) {
      stubHttp.match(call.getHttpMethod(), call.buildQueryParams()).then(
              status(HttpStatus.getHttpStatus(call.getStatusCode())),
              resourceContent(Thread.currentThread().getContextClassLoader()
                      .getResource("restito/" + call.getFilename())));
    }
  }

  @Value
  private static class Call {
    private String method;
    private String url;
    private int    statusCode;
    private String filename;

    ConditionWithApplicables getHttpMethod() {
      switch (getMethod().toUpperCase()) {
        case "POST":
          return post(buildUrl());
        case "PUT":
          return put(buildUrl());
        case "DELETE":
          return delete(buildUrl());
        case "GET":
          return get(buildUrl());
        default:
          return get(buildUrl());
      }
    }

    String buildUrl() {
      return (getUrl().contains("?")) ? getUrl().split("\\?")[0] : getUrl();
    }

    Condition buildQueryParams() {
      final String queryParamsStr = (getUrl().contains("?")) ? getUrl().split("\\?")[1] : null;

      final String[] kvs = queryParamsStr != null ? queryParamsStr.split("\\&") : new String[0];
      final List<Condition> conditions = new ArrayList<>();
      for (final String kv : kvs) {
        final String[] sp = kv.split("\\=");
        conditions.add(parameter(sp[0], sp[1]));
      }
      return Condition.composite(conditions.toArray(new Condition[conditions.size()]));
    }
  }
}
