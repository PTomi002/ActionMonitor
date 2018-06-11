package com.bv.exercise.actionmonitor.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

  @Value("${messaging.websocket.broker.destination.prefix:/topic}")
  private String brokerDestinationPrefix;

  @Value("${messaging.websocket.stomp.endpoint:/db_actions_stomp}")
  private String stompEndpoint;

  @Override
  public void configureMessageBroker(final MessageBrokerRegistry messageBrokerRegistry) {
    messageBrokerRegistry.enableSimpleBroker(brokerDestinationPrefix);
  }

  @Override
  public void registerStompEndpoints(final StompEndpointRegistry stompEndpointRegistry) {
    stompEndpointRegistry.addEndpoint(stompEndpoint).withSockJS();
  }
}
