package teste20210721;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class servidor {

    public static class HandlerClient implements Runnable {
        private final ex6.gestaoVacinas gestaoVacinas;
        private final Socket socket;
        private final DataOutputStream outputStream;
        private final DataInputStream inputStream;

        public HandlerClient(ex6.gestaoVacinas gestaoVacinas, Socket socket) throws IOException {
            this.gestaoVacinas = gestaoVacinas;
            this.socket = socket;
            this.outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            this.inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        }

        private void sendMsg(String msg) throws IOException {
            byte[] bufffer = msg.getBytes(StandardCharsets.UTF_8);
            outputStream.writeInt(bufffer.length);
            outputStream.write(bufffer);
        }

        @Override
        public void run() {
            int type;
            try {
                type = inputStream.readInt();
            } catch (IOException ignored) {return;}

            try {
                switch (PEDIDO.getPedidoType(type)) {
                    case FORNECER -> {
                        gestaoVacinas.fornecerFrascos(inputStream.readInt());
                        sendMsg("Frascos fornecidos");
                        break;
                    }
                    case SER_VACINADO -> {
                        gestaoVacinas.pedirParaVacinar();
                        sendMsg("Vacinado com sucesso");
                        break;
                    }
                    default -> System.out.println("Dont recognized");
                }
            } catch (Exception ignored) {return;}
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(3001);
        ex6.gestaoVacinas centroVacinacao = new ex6.gestaoVacinas(4);

        while (true) {
            Socket s = socket.accept();
            Thread t = new Thread(new HandlerClient(centroVacinacao, s));
            t.start();
        }
    }
}
