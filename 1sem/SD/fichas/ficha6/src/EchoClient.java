import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {

    public static void ex3_1() {
        try {
            Socket socket = new Socket("localhost", 12345);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = systemIn.readLine()) != null) {
                try {
                    Double.valueOf(userInput).doubleValue();
                    out.println(userInput);
                    out.flush();

                } catch (Exception e){
                    if (userInput.equals("")) {
                        System.out.println("We will end");
                        out.println(userInput);
                        out.flush();
                        break;
                    } else {
                        System.out.println("Is not a number");
                    }
                }
            }

            String response = in.readLine();
            System.out.println("Server response: " + response);

            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void stor() {
        try {
            Socket socket = new Socket("localhost", 12345);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = systemIn.readLine()) != null) {
                out.println(userInput);
                out.flush();

                String response = in.readLine();
                System.out.println("Server response: " + response);
            }

            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        byte ola = (byte) 42;
        byte o1 = (byte) (ola >> 4);
        byte o2 = (byte) (ola >> 6);
        byte o3 = byte ((byte) (ola << 6)) >> 6;

        //System.out.println("Exercicio 3 -> 1");
        //ex3_1();
    }
}
