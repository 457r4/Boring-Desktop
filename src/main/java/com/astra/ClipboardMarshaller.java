package com.astra;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.nio.charset.StandardCharsets;

import com.astra.util.Colors;

/**
 * ClipboardMarshaller
 */
public class ClipboardMarshaller {

  public static Package marshal(Transferable transferable) {
    DataFlavor flavor = null;
    byte[] rawData = {};

    try {
      if (transferable.isDataFlavorSupported(DataFlavor.fragmentHtmlFlavor)) {
        System.out.println(Colors.YELLOW + "Element is of type enriched text" +
            Colors.RESET);
        flavor = DataFlavor.fragmentHtmlFlavor;
        String enrichedText = (String) transferable.getTransferData(DataFlavor.fragmentHtmlFlavor);
        System.out.println(enrichedText);
        rawData = enrichedText.getBytes(StandardCharsets.UTF_8);

      } else if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        System.out.println(Colors.YELLOW + "Element is of type plain text" +
            Colors.RESET);
        flavor = DataFlavor.stringFlavor;
        String plainText = (String) transferable.getTransferData(DataFlavor.stringFlavor);
        System.out.println(plainText);
        rawData = plainText.getBytes(StandardCharsets.UTF_8);
      }

      /*
       * else if (transferable.isDataFlavorSupported(DataFlavor.imageFlavor))
       * { System.out.println(Colors.YELLOW + "Element is of type image text"
       * + Colors.RESET); flavor = DataFlavor.imageFlavor; Image image =
       * (Image) transferable.getTransferData(DataFlavor.imageFlavor);
       *
       * DataFlavor[] df = transferable.getTransferDataFlavors();
       * for (DataFlavor x : df) {
       * System.out.println(x.getMimeType());
       * }
       *
       * ByteArrayOutputStream baos = new ByteArrayOutputStream();
       * ImageIO.write((BufferedImage) image, "png", baos);
       * rawData = baos.toByteArray();
       * baos.close();
       *
       * }
       */

    } catch (Exception e) {
      System.out.println(Colors.RED + e.getMessage() + Colors.RESET);
      e.printStackTrace();
    }

    return new Package(flavor, rawData);
  }

  public static Transferable unmarshal(Package data) {
    DataFlavor flavor = data.getFlavor();
    if (flavor.equals(DataFlavor.fragmentHtmlFlavor)) {
      String htmlString = new String(data.getRawData());
      return new StringSelection(htmlString);
    } else if (flavor.equals(DataFlavor.stringFlavor)) {
      String plainTextString = new String(data.getRawData());
      return new StringSelection(plainTextString);
    }
    return null;
  }
}

