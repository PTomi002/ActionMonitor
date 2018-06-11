package com.bv.exercise.actionmonitor.messaging;

import com.bv.exercise.actionmonitor.model.TimeSeriesAction;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender<TimeSeriesAction> {

  @Value("${messaging.websocket.broker.destination.prefix:/topic}")
  private String brokerDestinationPrefix;

  @Value("${messaging.websocket.broker.destination:/db_actions}")
  private String applicationDestination;

  private final SimpMessagingTemplate simpMessagingTemplate;

  @Override
  public void send(final TimeSeriesAction body, final Map<String, ?> headers) {
    final String destination = brokerDestinationPrefix + applicationDestination;
    log.info("Sending message with body: {} to: {}", body, destination);
    simpMessagingTemplate.convertAndSend(destination, body);
  }
}
