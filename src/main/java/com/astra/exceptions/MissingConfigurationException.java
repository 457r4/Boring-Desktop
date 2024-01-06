package com.astra.exceptions;

/**
 * This exception will be thrown when a MAC address has not
 * been provided
 */
public class MissingConfigurationException extends RuntimeException {

  public MissingConfigurationException() {
    super();
  }

  public MissingConfigurationException(String message) {
    super(message);
  }
}

