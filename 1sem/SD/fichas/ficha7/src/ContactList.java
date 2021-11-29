import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

class ContactList extends ArrayList<Contact> {

    // @TODO
    public void serialize (DataOutputStream out) throws IOException {
        out.writeInt(size());
        for (Contact e : this) {
            e.serialize(out);
        }
    }

    // @TODO
    public static ContactList deserialize (DataInputStream in) throws IOException {
        int size = in.readInt();
        return new ContactList();
        //for(int i=0; i<size;i++)
            //this.add(Contact.deserialize(in));
    }

}
