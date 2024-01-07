package com.astra;

import java.awt.datatransfer.DataFlavor;
import java.io.Serializable;

/**
 * Packet
 */
public class Packet implements Serializable {

  private DataFlavor flavor;
  private byte[] rawData;

  public Packet(DataFlavor dataFlavor, byte[] rawData) {
    this.flavor = dataFlavor;
    this.rawData = rawData;
  }

  public DataFlavor getFlavor() {
    return flavor;
  }

  public void setFlavor(DataFlavor flavor) {
    this.flavor = flavor;
  }

  public byte[] getRawData() {
    return rawData;
  }

  public void setRawData(byte[] rawData) {
    this.rawData = rawData;
  }
}

