import java.net.*;
import java.io.*;

/**
 * simple and stupid TCP client
 */

public class Client {
    public static void main(String[] args) {
        String serverIP =  "localhost"; //args[0];                      // get server IP
        int port = 80;//Integer.parseInt(args[1]);           // get server port
        System.out.println("connecting to " + serverIP + ":" + port);
        
        try {
            // buffer to read from console
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String lineFromConsole = reader.readLine();  // read line from console

            while (!lineFromConsole.contentEquals("quit")) {
                Socket client = new Socket(serverIP, port);  // socket with the server ip and port

                // write and read streams from socket
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream in = new DataInputStream(client.getInputStream());
            
                out.writeUTF(lineFromConsole);     // send to server
                System.out.println(in.readUTF());  // read the response from server

                in.close();

                out.flush();
                out.close();

                client.close();

                lineFromConsole = reader.readLine();   // read new line from console
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}