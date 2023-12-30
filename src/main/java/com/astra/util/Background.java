package com.astra.util;

/**
 * Background colors for console prints
 */
public enum Background {
  WHITE("\u001b[47m"),
  RESET("\u001b[0m");

  public final String code;

  private Background(String code) {
    this.code = code;
  }
}
