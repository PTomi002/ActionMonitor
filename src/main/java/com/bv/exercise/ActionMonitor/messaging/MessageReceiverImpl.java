package com.bv.exercise.ActionMonitor.messaging;

import com.bv.exercise.ActionMonitor.configuration.MessagingConfiguration;
import com.bv.exercise.ActionMonitor.model.TimeSeries;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

@Slf4j
@RequiredArgsConstructor
public class MessageReceiverImpl implements MessageReceiver<TimeSeries> {

  private final JmsTemplate jmsTemplate;

  @Override
  @JmsListener(destination = MessagingConfiguration.TIME_SERIES_TOPIC)
  public void receive(final TimeSeries message) {
    log.info("Received message: {}", message);
  }
}
