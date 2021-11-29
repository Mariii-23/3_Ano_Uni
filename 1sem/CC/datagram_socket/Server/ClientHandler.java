import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private DatagramPacket inPacket;
    private DatagramSocket socket;

    public ClientHandler (DatagramPacket inPacket) throws SocketException {
        this.inPacket = inPacket;
        System.out.println("Packet Received from: " +inPacket.getAddress().toString() +":" +inPacket.getPort());
        this.socket = new DatagramSocket();
    }

    @Override
    public void run() {
        byte[] inBuffer = this.inPacket.getData();                  // get client Data
        InetAddress clientIp = this.inPacket.getAddress();          // get client IP
        int port = this.inPacket.getPort();                         // get client port

        String receivedString = new String(inBuffer);
        receivedString = receivedString.toUpperCase();              // to Upper case
        byte[] outBuffer = receivedString.getBytes();

        // create outgoing packet with data, client IP and client port
        DatagramPacket outPacket = new DatagramPacket(outBuffer, outBuffer.length, clientIp, port);
        try {
            this.socket.send(outPacket);                            // send packet
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }
    
}
