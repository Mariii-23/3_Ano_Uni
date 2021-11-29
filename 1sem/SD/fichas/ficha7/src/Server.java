import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Arrays.asList;

class ContactManager {
    private HashMap<String, Contact> contacts;

    public ContactManager() {
        contacts = new HashMap<>();
        // example pre-population
        update(new Contact("John", 20, 253123321, null, asList("john@mail.com")));
        update(new Contact("Alice", 30, 253987654, "CompanyInc.", asList("alice.personal@mail.com", "alice.business@mail.com")));
        update(new Contact("Bob", 40, 253123456, "Comp.Ld", asList("bob@mail.com", "bob.work@mail.com")));
    }


    // @TODO
    public void update(Contact c) {
        //contacts.putIfAbsient(c.name(),c);
        contacts.put(c.name(),c);
    }

    // @TODO
    public ContactList getContacts() {
        ContactList c = new ContactList();
        c.addAll(contacts.values());
        return c;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("ContactManager\n");
        for (Contact c :contacts.values()) {
            s.append(c.toString());
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}

class ServerWorker implements Runnable {
    private final Socket socket;
    private final ContactManager manager;
    private final ReentrantLock l = new ReentrantLock();

    public ServerWorker (Socket socket, ContactManager manager) {
        this.socket = socket;
        this.manager = manager;
    }

    public void update(Contact c){
       l.lock();
       try { manager.update(c);
       } finally { l.unlock();}
    }

    // @TODO
    @Override
    public void run() {
        DataInputStream in = null;
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            return;
        }
        try {
            // ate recever end of file
            while (true) {
                Contact c = Contact.deserialize(in);
                //System.out.println(c.toString());
                update(c);
            }
        } catch (EOFException e1){
            try {
                in.close();
                System.out.println(manager.toString());
            } catch (IOException ignored) {}
        } catch (IOException e) {
            System.out.println("ERROR: "+e);
        }
    }
}



public class Server {

    public static void main (String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        ContactManager manager = new ContactManager();

        while (true) {
            Socket socket = serverSocket.accept();
            Thread worker = new Thread(new ServerWorker(socket, manager));
            worker.start();
        }
    }

}
