package com.astra;

import java.io.ObjectOutputStream;
import java.net.Socket;

import com.astra.exceptions.MissingConfigurationException;
import com.astra.util.Background;
import com.astra.util.Colors;

/**
 * Send data
 * Will await for clipboard events to send the data
 * to the specified device
 */
public class Client {

  private String HOST;
  private int REMOTE_PORT;

  public Client() {
  }
  public Client(String HOST, int REMOTE_PORT) throws MissingConfigurationException {
    if (HOST == null)
      throw new MissingConfigurationException("Host address provided is null");
    System.out.println(Background.WHITE + Colors.BLACK +
        "HOST IP ADDRESS: " + HOST + Background.RESET);
    this.HOST = HOST;
    this.REMOTE_PORT = REMOTE_PORT;
  }

  public void send(Package data) {
    boolean sent = false;
    for (int i = 0; i < 5 && !sent; i++) {
      try {
        Socket socket = new Socket(HOST, REMOTE_PORT);
        System.out.println("HOST: " + HOST + " PORT: " + REMOTE_PORT);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(data);
        socket.close();
        sent = true;
        System.out.println("sent: " + sent);
      } catch (Exception e) {
        System.out.println(Colors.RED + e.getMessage() + Colors.RESET);
        e.printStackTrace();
      }
    }
  }

  public String getHOST() {
    return HOST;
  }

  public void setHOST(String HOST) throws MissingConfigurationException {
    if (HOST == null)
      throw new MissingConfigurationException("Host address provided is null");
    this.HOST = HOST;
    System.out.println(Background.WHITE + Colors.BLACK +
        "HOST IP ADDRESS: " + HOST + Background.RESET);
  }

  public int getREMOTE_PORT() {
    return REMOTE_PORT;
  }

  public void setREMOTE_PORT(int REMOTE_PORT) {
    this.REMOTE_PORT = REMOTE_PORT;
  }
}

