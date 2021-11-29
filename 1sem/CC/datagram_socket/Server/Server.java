import java.net.*;

/**
 * small and stupid udp server
 */

public class Server {

    private final static int MTU = 1500;

    // receives the port number
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);                           // port to run the server
        boolean running = true;                                         
        System.out.println("listening on port: " +port);
        

        try {
            DatagramSocket serveSocket = new DatagramSocket(port);
            while (running) {                                           // inifnite loop - very bad pratice
                byte[] inBuffer = new byte[MTU];
                // create the packet to receive the data from client
                DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);
                serveSocket.receive(inPacket);
                ClientHandler ch = new ClientHandler(inPacket);         // send receiverd packet to new thread to be treated
                Thread t = new Thread(ch);
                t.start();
            }
            serveSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}