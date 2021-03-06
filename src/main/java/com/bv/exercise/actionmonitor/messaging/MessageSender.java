package com.bv.exercise.actionmonitor.messaging;

import java.util.Map;

public interface MessageSender<T> {

  void send(T body, Map<String, ?> headers);
}
