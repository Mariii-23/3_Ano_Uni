package src;

import java.io.*;
import java.net.Socket;

public class FramedConnection implements AutoCloseable {
  private final DataOutputStream out;
  private final DataInputStream in;

  public FramedConnection(Socket socket) throws IOException {
    this.out = new DataOutputStream(socket.getOutputStream());
    this.in = new DataInputStream(socket.getInputStream());
  }

  public void send(byte[] data) throws IOException {
    out.writeInt(data.length);
    out.write(data);
    out.flush();
  }

  public byte[] receive() throws IOException {
    int length = in.readInt();
    var buffer = new byte[length];

    in.readFully(buffer);
    return buffer;
  }

  public void close() throws IOException {
    out.close();
    in.close();
  }
}
