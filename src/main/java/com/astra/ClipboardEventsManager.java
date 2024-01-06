package com.astra;

/**
 * ClipboardEventsManager
 */
public class ClipboardEventsManager {

  private boolean pause = false;

  public synchronized void waitForContent() throws InterruptedException {
    while (!pause) {
      wait();
    }
    pause = false;
  }

  public synchronized void notifyContentAvailable() {
    pause = true;
    notify();
  }
}

