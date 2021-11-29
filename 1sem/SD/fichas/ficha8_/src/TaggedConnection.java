
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class TaggedConnection implements  AutoCloseable {
  private final DataOutputStream out;
  private final ReentrantLock l_out= new ReentrantLock();
  private final DataInputStream in;
  private final ReentrantLock l_in= new ReentrantLock();

  public TaggedConnection(Socket socket) throws IOException {
    this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
  }

  public void send(Frame frame) throws IOException {
    try {
      l_out.lock();
      out.writeInt(frame.data.length + 8);
      out.writeInt(frame.tag);
      out.write(frame.data);
      out.flush();
    } finally {
      l_out.unlock();
    }
  }

  public void send(int tag, byte[] data) throws IOException {
    try {
      l_out.lock();
      out.writeInt(data.length + Integer.BYTES); // len
      out.writeInt(tag);
      out.write(data);
      out.flush();
    } finally {
      l_out.unlock();
    }
  }

  public Frame receive() throws IOException {
    try {
      l_in.lock();
      int len = in.readInt() - Integer.BYTES;  // len de tudo - inteiro
      int tag = in.readInt();
      byte[] buff =  new byte[len];
      in.readFully(buff);

      return new Frame(tag,buff);
    } finally {
      l_in.unlock();
    }
  }

  public void close() throws IOException {
    try {
      l_out.lock();
      l_in.lock();
      out.close();
      in.close();
    } finally {
      l_out.unlock();
      l_in.unlock();
    }
  }
}