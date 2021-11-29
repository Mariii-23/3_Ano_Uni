import java.io.IOException;

public class Demultiplexer  implements AutoCloseable{

//TODO
  //
    public Demultiplexer(TaggedConnection conn) {  }
    public void start() {
      //poor o gajo sempre a ler e ir guardardando as cenas
      // quando receve acorda as condictions
    }
    public void send(Frame frame) throws IOException {  }
    public void send(int tag, byte[] data) throws IOException { }
    public byte[] receive(int tag) throws IOException, InterruptedException {
      // so vai ao buffer busca lo, quando tiver livre
    }

  @Override
  public void close() throws Exception {

  }
}
