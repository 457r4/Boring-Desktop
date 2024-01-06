package com.astra;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.astra.exceptions.MissingConfigurationException;

/**
 * Boring
 */
public class Boring {

  public static final String CONFIGURATION_PATH = Paths.get("src/main/resources/config/config.properties")
      .toAbsolutePath()
      .toString();
  public static final Integer DEFAULT_REMOTE_PORT = 4574;
  public static final Integer DEFAULT_LOCAL_PORT = 4574;

  public static void main(String[] args) throws IOException {

    Options options = new Options();

    Option connectOption = new Option(
        "c", "connect", false, "Attempt connection with local configuration");
    Option macOption = new Option("m", "mac", true, "MAC Address");
    Option ipOption = new Option("ip", "ip-address", true, "Provide the Host Ip Address to be used within this instance");
    Option remotePortOption = new Option("rp", "remote-port", true, "Remote port");
    Option localPortOption = new Option("lc", "local-port", true, "Local port");
    Option helpOption = new Option("h", "help", false, "Print this message");

    options.addOption(connectOption);
    options.addOption(macOption);
    options.addOption(ipOption);
    options.addOption(remotePortOption);
    options.addOption(localPortOption);
    options.addOption(helpOption);

    CommandLineParser parser = new DefaultParser();

    try {

      CommandLine cmd = parser.parse(options, args);

      if (cmd.hasOption("help")) {
        printHelp(options);
        return;
      }

      String MAC_ADDRESS = cmd.getOptionValue("mac");
      String IP_ADDRESS = cmd.getOptionValue("ip-address");
      String REMOTE_PORT = cmd.getOptionValue("remote-port", DEFAULT_REMOTE_PORT.toString());
      String LOCAL_PORT = cmd.getOptionValue("local-port", DEFAULT_REMOTE_PORT.toString());

      if (MAC_ADDRESS != null)
        ConfigManager.setMAC_ADDRESS(MAC_ADDRESS);
      if (REMOTE_PORT != null)
        ConfigManager.setREMOTE_PORT(REMOTE_PORT);
      if (LOCAL_PORT != null)
        ConfigManager.setLOCAL_PORT(LOCAL_PORT);

      Server server = new Server(ConfigManager.getLOCAL_PORT());
      Client client = new Client();
      client.setREMOTE_PORT(ConfigManager.getREMOTE_PORT());

      if (IP_ADDRESS != null) {
        client.setHOST(IP_ADDRESS);
      } else {
        try {
          client.setHOST(ConfigManager.getHost());
        } catch (MissingConfigurationException e) {
          System.out.println(
              "No address has been setup, add one with the command -m --mac or add a temporary\nIp as an argument to the -c --connect option");
          return;
        } 
      }

      if (cmd.hasOption("connect")) {
        server.start();
        ClipboardHandler.startMonitoring(client);
      }

    } catch (Exception e) {
      System.out.println("Error: invalid argument");
      printHelp(options);
    }
  }

  private static void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("Boring", options);
  }
}

