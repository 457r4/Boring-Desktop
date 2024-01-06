package com.astra;

import java.awt.datatransfer.DataFlavor;
import java.io.Serializable;

/**
 * Package
 */
public class Package implements Serializable {

  private DataFlavor flavor;
  private byte[] rawData;

  public Package(DataFlavor dataFlavor, byte[] rawData) {
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

