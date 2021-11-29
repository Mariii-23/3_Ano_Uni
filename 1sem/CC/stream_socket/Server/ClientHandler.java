import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        
        try {
            // getting write and read streams from socket
            DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
            DataInputStream in = new DataInputStream(this.socket.getInputStream());

            String fromClient = in.readUTF(); // reads from socket input stream.
            
            System.out.println(fromClient);
            fromClient = fromClient.toUpperCase();
            out.writeUTF(fromClient);

            in.close();

            out.flush();
            out.close();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
