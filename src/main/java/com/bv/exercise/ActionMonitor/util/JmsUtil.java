package com.bv.exercise.ActionMonitor.util;

import com.bv.exercise.ActionMonitor.configuration.MessagingConfiguration;
import com.bv.exercise.ActionMonitor.configuration.MessagingConfiguration.ExecutionType;
import java.util.concurrent.CompletableFuture;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

public final class JmsUtil {

  private static final String DEFAULT_EXECUTION_TYPE_KEY = MessagingConfiguration.EXECUTION_TYPE_KEY;
  private static final String DEFAULT_TOPIC = MessagingConfiguration.TIME_SERIES_TOPIC;
  private static final String DEFAULT_TYPE_PROPERTY = MessagingConfiguration.DEFAUlT_TYPE_PROPERTY;
  private static final String DEFAULT_MESSAGE_BROKER_HOST = MessagingConfiguration.DEFAUlT_MESSAGE_BROKER_HOST;

  public static <T> CompletableFuture<T> sendMessage(final T message,
      final ExecutionType executionType) {
    return sendMessage(DEFAULT_MESSAGE_BROKER_HOST, DEFAULT_TOPIC, message, executionType);
  }

  public static <T> CompletableFuture<T> sendMessage(final String host,
      final String destination, final T message, final ExecutionType executionType) {
    final JmsTemplate jmsTemplate = new JmsTemplate(getMessageBrokerConnectionFactory(host));
    jmsTemplate.setMessageConverter(addJacksonMessageConverter());
    return CompletableFuture.supplyAsync(() -> {
      jmsTemplate.convertAndSend(destination, message,
          messagePostProcessor(DEFAULT_EXECUTION_TYPE_KEY, executionType));
      return null;
    });
  }

  private static <T> MessagePostProcessor messagePostProcessor(final String header, final T value) {
    return message -> {
      message.setStringProperty(header, value.toString());
      return message;
    };
  }

  private static ConnectionFactory getMessageBrokerConnectionFactory(final String host) {
    return new ActiveMQConnectionFactory(host);
  }

  private static MessageConverter addJacksonMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTypeIdPropertyName(DEFAULT_TYPE_PROPERTY);
    return converter;
  }
}
