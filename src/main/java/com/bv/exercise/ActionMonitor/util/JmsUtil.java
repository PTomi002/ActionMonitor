package com.bv.exercise.ActionMonitor.util;

import java.util.concurrent.CompletableFuture;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

public final class JmsUtil {

  public static final void sendAsync(final String destination, final Object message) {
    final ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
        ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
    final JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
    CompletableFuture.supplyAsync(() -> {
      jmsTemplate.convertAndSend(destination, message);
      return null;
    });
  }
}
