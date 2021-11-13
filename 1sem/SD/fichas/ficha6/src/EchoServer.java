import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Listen implements Runnable{
    ServerSocket ss;

    public Listen(){
        try {
            ss = new ServerSocket(12345);
        } catch (Exception e){
            System.out.println("ERROR");
        }
    }

    public void only_on() throws Exception {
        while (true) {
            Socket socket = ss.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            double soma = 0;
            double n = 0;

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Recebi: " + line);

                try {
                    double number = Double.valueOf(line).doubleValue();
                    soma += number;
                    n++;
                } catch (Exception e) {
                    if (line.equals("")) {
                        System.out.println("We will end");
                        break;
                    } else {
                        System.out.println("ERROR MSG");
                    }
                }
            }

            double mean = soma / n;
            String msg = "Mean-> " + mean + "     Soma -> " + soma + "    Number -> " + n + "\n\n";

            System.out.println(msg);
            out.println(msg);
            out.flush();

            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        }
    }

    @Override
    public void run() {
        try {
            only_on();
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }
}

public class EchoServer {

    public void ex3_1() {
        try {
            ServerSocket ss = new ServerSocket(12345);

            while (true) {
                Socket socket = ss.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());

                double soma = 0;
                double n = 0;

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Recebi: " + line);

                    try {
                        double number = Double.valueOf(line).doubleValue();
                        soma += number;
                        n++;
                    } catch (Exception e) {
                        if (line.equals("")) {
                            System.out.println("We will end");
                            break;
                        } else {
                            System.out.println("ERROR MSG");
                        }
                    }
                }

                double mean = soma/n;
                String msg = "Mean-> " + mean + "     Soma -> "+ soma +"    Number -> " + n +"\n\n";

                System.out.println(msg);
                out.println(msg);
                out.flush();

                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ex3_3(int N) throws InterruptedException {
        Thread[] t = new Thread[N];

        for (int i = 0; i < N; i++)
            t[i] = new Thread(new Listen());

        for (int i = 0; i < N; i++)
            t[i].start();

        for (int i = 0; i < N; i++)
            t[i].join();
    }

    public static void stor() {
        try {
            ServerSocket ss = new ServerSocket(12345);

            while (true) {
                Socket socket = ss.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Recebi: " + line);
                    out.println(line);
                    out.flush();
                }

                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //System.out.println("Exercicio 3 -> 1");
        //EchoServer e = new EchoServer()
        //e.ex3_1();

        System.out.println("Exercicio 3 -> 3");
        int N_threads = 3;
        try {
            ex3_3(N_threads);
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
    }
}
