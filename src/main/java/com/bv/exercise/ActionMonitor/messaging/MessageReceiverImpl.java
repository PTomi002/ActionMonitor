package com.bv.exercise.ActionMonitor.messaging;

import static com.bv.exercise.ActionMonitor.configuration.MessagingConfiguration.EXECUTION_TYPE_KEY;
import static com.bv.exercise.ActionMonitor.configuration.MessagingConfiguration.TIME_SERIES_TOPIC;

import com.bv.exercise.ActionMonitor.model.TimeSeries;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageReceiverImpl implements MessageReceiver<TimeSeries> {

  @Override
  @JmsListener(destination = TIME_SERIES_TOPIC)
  public void receive(final Message<TimeSeries> message) {
    log.info("Received message: {}", message.getPayload());
    final String executionType = getProperty(message.getHeaders(), EXECUTION_TYPE_KEY,
        String.class);
    log.info("Execution type is: {}", executionType);
  }

  private <T> T getProperty(final MessageHeaders messageHeaders, final Object key,
      final Class<T> clazz) {
    return messageHeaders.get(key, clazz);
  }
}
