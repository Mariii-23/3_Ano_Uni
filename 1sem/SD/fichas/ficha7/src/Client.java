import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {

    public static Contact parseLine (String userInput) throws EOFException {
        if (userInput.isEmpty())
            throw new EOFException();

        String[] tokens = userInput.split(" ");

        if (tokens[3].equals("null")) tokens[3] = null;

        return new Contact(
                tokens[0],
                Integer.parseInt(tokens[1]),
                Long.parseLong(tokens[2]),
                tokens[3],
                new ArrayList<>(Arrays.asList(tokens).subList(4, tokens.length)));
    }


    public static void main (String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String userInput;
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        while ((userInput = in.readLine()) != null) {
            Contact newContact;
            try {
                newContact = parseLine(userInput);
            } catch (EOFException e) {break;}
            System.out.println(newContact.toString());
            try {
                newContact.serialize(out);
            }catch (IOException e) {
                System.out.println("ERROR: "+e);
            }
        }
        System.out.println("Connection close");
        out.close();
        socket.close();
    }
}
