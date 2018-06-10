package com.bv.exercise.ActionMonitor.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

@Slf4j
@EnableJms
@Configuration
public class MessagingConfiguration {

  public static final String TIME_SERIES_TOPIC = "time_series";
  public static final String DEFAUlT_TYPE_PROPERTY = "_type";
  public static final String DEFAUlT_MESSAGE_BROKER_HOST = "vm://localhost";

  @Bean
  public MessageConverter messageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTypeIdPropertyName(DEFAUlT_TYPE_PROPERTY);
    return converter;
  }
}
