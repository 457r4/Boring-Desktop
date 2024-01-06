package com.astra;

import static com.astra.Boring.CONFIGURATION_PATH;
import static com.astra.Boring.DEFAULT_LOCAL_PORT;
import static com.astra.Boring.DEFAULT_REMOTE_PORT;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import com.astra.exceptions.MissingConfigurationException;
import com.astra.util.Background;
import com.astra.util.Colors;

/**
 * ConfigManager
 */
public class ConfigManager {

  private static Properties properties;

  public static String findHost(String MAC_ADDRESS) {
    String HOST = null;
    boolean hostFound = false;
    for (int i = 0; i < 5 && !hostFound; i++) {
      InputStream input = arpScan(MAC_ADDRESS);
      BufferedReader br = new BufferedReader(new InputStreamReader(input));
      HOST = readBashOutput(br);
      hostFound = HOST != null;
    }
    return HOST;
  }

  public static String getHost() throws MissingConfigurationException {
    String MAC_ADDRESS = getMAC_ADDRESS();
    if (MAC_ADDRESS == null)
      throw new MissingConfigurationException();
    System.out.println(Background.WHITE + Colors.BLACK +
        "MAC_ADDRESS: " + MAC_ADDRESS + Background.RESET);
    return findHost(MAC_ADDRESS);
  }

  public static String getMAC_ADDRESS() {
    loadProperties();
    String MAC_ADDRESS = properties.getProperty("MAC_ADDRESS");
    return MAC_ADDRESS;
  }

  public static int getREMOTE_PORT() {
    loadProperties();
    String PORT = properties.getProperty("REMOTE_PORT", DEFAULT_REMOTE_PORT.toString());
    return Integer.parseInt(PORT);
  }

  public static int getLOCAL_PORT() {
    loadProperties();
    String LOCAL_PORT = properties.getProperty("LOCAL_PORT", DEFAULT_LOCAL_PORT.toString());
    return Integer.parseInt(LOCAL_PORT);
  }

  public static void setMAC_ADDRESS(String MAC_ADDRESS) {
    System.out.println(Colors.GREEN + "setting address" + Colors.RESET);
    loadProperties();
    properties.setProperty("MAC_ADDRESS", MAC_ADDRESS);
    saveProperties();
  }

  public static void setREMOTE_PORT(String REMOTE_PORT) {
    System.out.println(Colors.GREEN + "setting port" + Colors.RESET);
    loadProperties();
    properties.setProperty("REMOTE_PORT", REMOTE_PORT);
    saveProperties();
  }

  public static void setLOCAL_PORT(String LOCAL_PORT) {
    System.out.println(Colors.GREEN + "setting local port" + Colors.RESET);
    loadProperties();
    properties.setProperty("LOCAL_PORT", LOCAL_PORT);
    saveProperties();
  }

  private static InputStream arpScan(String MAC_ADDRESS) {
    String command = "arp-scan --localnet --format='${mac};${ip}' | grep ";
    InputStream inputStream = null;
    try {
      ProcessBuilder processBuilder = new ProcessBuilder();
      processBuilder.command("bash", "-c", command + MAC_ADDRESS);
      Process process = processBuilder.start();
      inputStream = process.getInputStream();
    } catch (Exception e) {
      System.out.println(Colors.RED + e.getMessage() + Colors.RESET);
      e.printStackTrace();
    }
    return inputStream;
  }

  private static String readBashOutput(BufferedReader br) {
    String line;
    try {
      while ((line = br.readLine()) != null) {
        String arr[] = line.split(";");
        if (arr.length == 2)
          return arr[1];
      }
    } catch (Exception e) {
      System.out.println(Colors.RED + e.getMessage() + Colors.RESET);
      e.printStackTrace();
    }
    return null;
  }

  private static void loadProperties() {
    try (InputStream input = new FileInputStream(CONFIGURATION_PATH)) {
      properties = new Properties();
      properties.load(input);
      input.close();
    } catch (Exception e) {
      System.out.println(Colors.RED + e.getMessage() + Colors.RESET);
      e.printStackTrace();
    }
  }

  private static void saveProperties() {
    try (OutputStream output = new FileOutputStream(CONFIGURATION_PATH)) {
      properties.store(output, null);
      output.close();
    } catch (Exception e) {
      System.out.println(Colors.RED + e.getMessage() + Colors.RESET);
      e.printStackTrace();
    }
  }
}
