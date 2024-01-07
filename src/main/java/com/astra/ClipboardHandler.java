package com.astra;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import com.astra.util.Colors;

/**
 * ClipboardMonitor
 */
public class ClipboardHandler implements Runnable {

  private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
  private static final ClipboardEventsManager clipboardEventsManager = new ClipboardEventsManager();
  private static final ClipboardOwner clipboardOwner = (arg0, arg1) -> {
    Packet packet = ClipboardMarshaller.marshal(clipboard.getContents(null));
    ClipboardHandler.client.send(packet);
    clipboardEventsManager.notifyContentAvailable();
  };
  private static Client client;

  public static void startMonitoring(Client _client) {
    client = _client;
    System.out.println(Colors.YELLOW + "Starting clipboard monitoring..." +
        Colors.RESET);
    Thread tracer = new Thread(new ClipboardHandler());
    tracer.start();
  }

  @Override
  public void run() {
    while (true) {
      try {
        clipboard.setContents(clipboard.getContents(null), clipboardOwner);
        clipboardEventsManager.waitForContent();
      } catch (Exception e) {
        System.out.println(Colors.RED + e.getMessage() + Colors.RESET);
        e.printStackTrace();
      }
    }
  }

  public static void push(Transferable transferable) {
    try {
      if (transferable.isDataFlavorSupported(DataFlavor.fragmentHtmlFlavor)) {
        String htmlText = (String) transferable.getTransferData(DataFlavor.fragmentHtmlFlavor);
        System.out.println(Colors.GREEN + "Content received: \n" +
            htmlText + Colors.RESET);
        StringSelection stringSelection = new StringSelection(htmlText);
        clipboard.setContents(stringSelection, clipboardOwner);
      } else if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        String plainText = (String) transferable.getTransferData(DataFlavor.stringFlavor);
        System.out.println(Colors.GREEN + "Content received: \n" +
            plainText + Colors.RESET);
        StringSelection stringSelection = new StringSelection(plainText);
        clipboard.setContents(stringSelection, clipboardOwner);
      }
    } catch (Exception e) {
      System.out.println(Colors.RED + e.getMessage() + Colors.RESET);
      e.printStackTrace();
    }
  }
}

