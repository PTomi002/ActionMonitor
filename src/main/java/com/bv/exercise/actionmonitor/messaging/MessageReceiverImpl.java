package com.bv.exercise.actionmonitor.messaging;

import static com.bv.exercise.actionmonitor.configuration.MessagingConfiguration.EXECUTION_TYPE_KEY;
import static com.bv.exercise.actionmonitor.configuration.MessagingConfiguration.TIME_SERIES_TOPIC;

import com.bv.exercise.actionmonitor.configuration.MessagingConfiguration.ExecutionType;
import com.bv.exercise.actionmonitor.model.TimeSeries;
import com.bv.exercise.actionmonitor.model.TimeSeriesAction;
import java.util.Collections;
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

  private final MessageSender<TimeSeriesAction> timeSeriesActionMessageSender;

  @Override
  @JmsListener(destination = TIME_SERIES_TOPIC)
  public void receive(final Message<TimeSeries> message) {
    log.info("Received message: {}", message.getPayload());
    final String executionType = getProperty(message.getHeaders(), EXECUTION_TYPE_KEY,
        String.class);
    final TimeSeriesAction timeSeriesAction = createTimeSeriesAction(message.getPayload(),
        ExecutionType.valueOf(executionType));
    log.info("Created payload: {}", timeSeriesAction);
    timeSeriesActionMessageSender.send(timeSeriesAction, Collections.emptyMap());
  }

  private TimeSeriesAction createTimeSeriesAction(final TimeSeries timeSeries,
      final ExecutionType executionType) {
    final TimeSeriesAction timeSeriesAction = new TimeSeriesAction();
    timeSeriesAction.setId(timeSeries.getId());
    timeSeriesAction.setTime(timeSeries.getTime());
    timeSeriesAction.setExecutionType(executionType);
    return timeSeriesAction;
  }

  private <T> T getProperty(final MessageHeaders messageHeaders, final Object key,
      final Class<T> clazz) {
    return messageHeaders.get(key, clazz);
  }
}
