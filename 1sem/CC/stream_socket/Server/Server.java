import java.net.*;
import java.io.*;

/**
 * Small and stupid TCP server
 */
public class Server {

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);                           // get Server port
        System.out.println("Listening on port " + port);
        boolean running = true;
        try {
            ServerSocket ssocket = new ServerSocket(port);
            while (running) {                                           // bad partice - inifinite loop
                // one thread by client     
                ClientHandler ch = new ClientHandler(ssocket.accept()); // socket.accept - listens to clients and accepts the connection returning a new socket 
                Thread t = new Thread(ch);
                t.start();
            }
            ssocket.close();
        } catch (IOException e)  {
            e.printStackTrace();
        }

        
    }

    
}