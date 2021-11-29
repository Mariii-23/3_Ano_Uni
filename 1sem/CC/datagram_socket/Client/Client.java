import java.net.*;
import java.io.*;

/**
 *  small and stupid UDP client
 */


public class Client {

    private final static int MTU = 1500;

    public static void main(String[] args) {
        try {
            InetAddress ipServer = InetAddress.getByName(args[0]);                       // receives the IP of the server
            int port = Integer.parseInt(args[1]);                                        // recieves the port of the server
            System.out.println("Conecting to: " +ipServer.toString() +":" +port);      
            

            DatagramSocket clientSocket = new DatagramSocket();                         // creates a socket - port not define - system gives an available port

            // buffer to read from the console
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String lineFromConsole = reader.readLine();                                 // reading from the console

            while (!lineFromConsole.equalsIgnoreCase("quit")) {
                byte[] inBuffer = new byte[MTU];
                byte[] outBuffer = new byte[MTU];

                // from the console to the socket - sending a message
                outBuffer = lineFromConsole.getBytes();
                DatagramPacket outPacket = new DatagramPacket(outBuffer, outBuffer.length, ipServer, port);
                clientSocket.send(outPacket);

                // from the socket to the console - reading a message
                DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);
                clientSocket.receive(inPacket);
                System.out.println(new String(inPacket.getData()));

                lineFromConsole = reader.readLine();                                    // reading from console
            }
            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}

