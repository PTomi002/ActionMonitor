package com.bv.exercise.actionmonitor.messaging;

import org.springframework.messaging.Message;

public interface MessageReceiver<T> {

  void receive(final Message<T> message);
}
