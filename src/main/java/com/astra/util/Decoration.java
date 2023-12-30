package com.astra.util;

/**
 * Decorations for console prints
 */
public enum Decoration {
  BOLD("\u001b[1m"),
  UNDERLINE("\u001b[4m"),
  REVERSED("\u001b[7m"),
  RESET("\u001b[0m");

  public final String code;

  private Decoration(String code) {
    this.code = code;
  }
}
