package com.astra.util;

/**
 * Colors for console prints
 */
public enum Colors {
  BLACK("\u001b[30m"),
  RED("\u001b[31m"),
  GREEN("\u001b[32m"),
  YELLOW("\u001b[33m"),
  BLUE("\u001b[34m"),
  MAGENTA("\u001b[35m"),
  CYAN("\u001b[36m"),
  WHITE("\u001b[37m"),
  RESET("\u001b[0m");

  public final String code;

  private Colors(String code) {
    this.code = code;
  }
}