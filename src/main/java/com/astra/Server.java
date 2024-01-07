package com.astra;

import com.astra.util.Colors;
import java.awt.datatransfer.Transferable;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Receive data
 */
public class Server implements Runnable {

  private int LOCAL_PORT;

  public Server(int LOCAL_PORT) {
    this.LOCAL_PORT = LOCAL_PORT;
  }

  public void start() {
    Thread receptor = new Thread(this);
    receptor.start();
  }

  @Override
  public void run() {
    System.out.println(Colors.YELLOW + "Waiting for packages..." + Colors.RESET);
    while (true) {
      try {
        while (true) {
          ServerSocket serverSocket = new ServerSocket(LOCAL_PORT);
          Socket socket = serverSocket.accept();
          ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
          Packet packet = (Packet) inputStream.readObject();
          Transferable transferable = ClipboardMarshaller.unmarshal(packet);
          ClipboardHandler.push(transferable);
          inputStream.close();
          socket.close();
          serverSocket.close();
        }
      } catch (Exception e) {
        System.out.println(Colors.RED + e.getMessage() + Colors.RESET);
        e.printStackTrace();
      }
    }
  }

  public int getLOCAL_PORT() {
    return LOCAL_PORT;
  }

  public void setLOCAL_PORT(int LOCAL_PORT) {
    this.LOCAL_PORT = LOCAL_PORT;
  }
}

