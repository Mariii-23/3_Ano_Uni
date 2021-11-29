import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class FramedConnection implements AutoCloseable {
  private final DataOutputStream out;
  private final ReentrantLock l_out= new ReentrantLock();
  private final DataInputStream in;
  private final ReentrantLock l_in= new ReentrantLock();

  public FramedConnection(Socket socket) throws IOException {
    this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
  }

  public void send(byte[] data) throws IOException {
    try {
      l_out.lock();
      out.writeInt(data.length);
      out.write(data);
      out.flush();
    } finally {
      l_out.unlock();
    }
  }

  public byte[] receive() throws IOException {
    try {
      l_in.lock();
      int length = in.readInt();
      var buffer = new byte[length];

      in.readFully(buffer);
      return buffer;
    } finally {
      l_in.unlock();
    }
  }

  public void close() throws IOException {
    out.close();
    in.close();
  }
}
