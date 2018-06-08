package com.bv.exercise.ActionMonitor.configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.Service;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Slf4j
@EnableJms
@Configuration
public class MessagingConfiguration {

  public static final String TIME_SERIES_TOPIC = "time_series";

  private Service service;

  @PostConstruct
  public void brokerService() throws Exception {
    final BrokerService brokerService = BrokerFactory.createBroker("broker:tcp://localhost:61616");
    brokerService.setPersistent(false);
    brokerService.start();
    service = brokerService;
  }

  @PreDestroy
  public void stopBrokerService() {
    try {
      service.stop();
    } catch (final Exception e) {
      log.warn("Could not shut down broker service gracefully!", e);
    }
  }
}
