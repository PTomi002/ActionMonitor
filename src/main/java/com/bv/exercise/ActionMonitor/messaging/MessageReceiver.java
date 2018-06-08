package com.bv.exercise.ActionMonitor.messaging;

public interface MessageReceiver<T> {

  void receive(T message);
}
